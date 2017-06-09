/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package input;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.java.games.input.Rumbler;

/**
 * A wrapper round a JInput controller that attempts to make the interface more
 * useable.
 *
 * @author Kevin Glass
 */
class JInputController implements Controller {

	private float axisDead = 0.3f;
	/** The JInput controller this class is wrapping */
	private net.java.games.input.Controller target;
	/** The index that has been assigned to this controller */
	private int index;
	/** The Buttons that have been detected on the JInput controller */
	private ArrayList<Component> buttons = new ArrayList<Component>();
	/** The Axes that have been detected on the JInput controller */
	private ArrayList<Component> axes = new ArrayList<Component>();
	/** The POVs that have been detected on the JInput controller */
	private ArrayList<Component> pov = new ArrayList<Component>();
	/** The rumblers exposed by the controller */
	private Rumbler[] rumblers;
	/** The state of the buttons last check */
	private boolean[] buttonState;
	/** The values that were read from the pov last check */
	private float[] povValues;
	/** The values that were read from the axes last check */
	private float[] axesValue;
	/** The maximum values read for each axis */
	private float[] axesMax;
	/** The dead zones for each axis */
	private float[] deadZones;
	/** The index of the X axis or -1 if no X axis is defined */
	private int xaxis = -1;
	/** The index of the Y axis or -1 if no Y axis is defined */
	private int yaxis = -1;
	/** The index of the X axis or -1 if no Z axis is defined */
	private int zaxis = -1;
	/** The index of the RX axis or -1 if no RX axis is defined */
	private int rxaxis = -1;
	/** The index of the RY axis or -1 if no RY axis is defined */
	private int ryaxis = -1;
	/** The index of the RZ axis or -1 if no RZ axis is defined */
	private int rzaxis = -1;

	/**
	 * Create a new controller that wraps round a JInput controller and
	 * hopefully makes it easier to use.
	 *
	 * @param index
	 *            The index this controller has been assigned to
	 * @param target
	 *            The target JInput controller this class is wrapping
	 */
	JInputController(int index, net.java.games.input.Controller target) {
		this.target = target;
		this.index = index;

		Component[] sourceAxes = target.getComponents();

		for (Component sourceAxis : sourceAxes) {
			if (sourceAxis.getIdentifier() instanceof Button) {
				this.buttons.add(sourceAxis);
				System.out.println("Button: " + sourceAxis.getName() + ", index: " + this.buttons.indexOf(sourceAxis));
			} else if (sourceAxis.getIdentifier().equals(Axis.POV)) {
				this.pov.add(sourceAxis);
				System.out.println("POV: " + sourceAxis.getName() + ", index: " + this.pov.indexOf(sourceAxis));
			} else {
				this.axes.add(sourceAxis);
				System.out.println("Axis: " + sourceAxis.getName() + ", index: " + this.axes.indexOf(sourceAxis));
			}
		}

		this.buttonState = new boolean[this.buttons.size()];
		this.povValues = new float[this.pov.size()];
		this.axesValue = new float[this.axes.size()];
		int buttonsCount = 0;
		int axesCount = 0;

		// initialise the state
		for (Component sourceAxis : sourceAxes) {
			if (sourceAxis.getIdentifier() instanceof Button) {
				this.buttonState[buttonsCount] = sourceAxis.getPollData() != 0;
				buttonsCount++;
			} else if (sourceAxis.getIdentifier().equals(Axis.POV)) {
				// no account for POV yet
				// pov.add(sourceAxes[i]);
			} else {
				this.axesValue[axesCount] = sourceAxis.getPollData();
				if (sourceAxis.getIdentifier().equals(Axis.X)) {
					this.xaxis = axesCount;
				}
				if (sourceAxis.getIdentifier().equals(Axis.Y)) {
					this.yaxis = axesCount;
				}
				if (sourceAxis.getIdentifier().equals(Axis.Z)) {
					this.zaxis = axesCount;
				}
				if (sourceAxis.getIdentifier().equals(Axis.RX)) {
					this.rxaxis = axesCount;
				}
				if (sourceAxis.getIdentifier().equals(Axis.RY)) {
					this.ryaxis = axesCount;
				}
				if (sourceAxis.getIdentifier().equals(Axis.RZ)) {
					this.rzaxis = axesCount;
				}

				axesCount++;
			}
		}

		this.axesMax = new float[this.axes.size()];
		this.deadZones = new float[this.axes.size()];

		for (int i = 0; i < this.axesMax.length; i++) {
			this.axesMax[i] = 1.0f;
			this.deadZones[i] = 0.05f;
		}

		this.rumblers = target.getRumblers();
	}

	private void axisMap(int axisIndex, float value) {
		switch (getName()) {
		case "Controller (XBOX 360 For Windows)":
			switch (axisIndex) {
			case 4:
				if (value < (-1 * this.axisDead)) { // Right trigger in
					Keyboard.setKey(GLFW.GLFW_KEY_SPACE, 1);
				} else if (value > this.axisDead) {
					// Left trigger in
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_SPACE, 0); // Both in
																// or
																// out
				}
			}
		case "Controller (Xbox One For Windows)":
			switch (axisIndex) {
			case 0: // Left Stick Y (-1 UP) (1 DOWN)
				if (value < (-1 * this.axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_W, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_S, 0);
				} else if (value > this.axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_W, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_S, 1);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_W, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_S, 0);
				}
				break;
			case 1: // Left Stick X (-1 LEFT) (1 RIGHT)
				if (value < (-1 * this.axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_A, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_D, 0);
				} else if (value > this.axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_A, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_D, 1);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_A, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_D, 0);
				}
				break;
			case 2: // Right Stick Y (-1 UP) (1 DOWN)
				if (value < (-1 * this.axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 0);
				} else if (value > this.axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 1);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 0);
				}
				break;
			case 3: // Right Stick X (-1 LEFT) (1 RIGHT)
				if (value < (-1 * this.axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_LEFT, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_RIGHT, 0);
				} else if (value > this.axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_LEFT, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_RIGHT, 1);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_LEFT, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_RIGHT, 0);
				}
				break;
			case 4: // Left Trigger (-1 OUT) (1 IN)

				break;
			case 5: // Right Trigger (-1 OUT) (1 IN)
				if (value < 0) {
					Keyboard.setKey(GLFW.GLFW_KEY_SPACE, 0);
				} else if (value >= 0) {
					Keyboard.setKey(GLFW.GLFW_KEY_SPACE, 1);
				}
				break;
			}

		}

	}

	private void buttonMap(int buttonIndex, boolean value) {
		switch (getName()) {
		case "Controller (XBOX 360 For Windows)":
		case "Controller (Xbox One For Windows)":
			switch (buttonIndex) {
			case 0: // A
				Keyboard.setKey(GLFW.GLFW_KEY_ENTER, value);
				break;
			case 1: // B
				Keyboard.setKey(GLFW.GLFW_KEY_M, value);
				break;
			case 4: // LB
				Keyboard.setKey(GLFW.GLFW_KEY_LEFT_SHIFT, value);
				break;
			case 5: // RB
				Keyboard.setKey(GLFW.GLFW_KEY_TAB, value);
				break;
			case 6: // Back
				Keyboard.setKey(GLFW.GLFW_KEY_F, value);
				break;
			case 7: // Pause
				Keyboard.setKey(GLFW.GLFW_KEY_P, value);
				break;
			case 2: // X
			case 3: // Y
			case 8: // Left Stick In
			case 9: // Right Stick In
				break;
			}
		}

	}

	/*
	 * @see org.lwjgl.input.Controller#getAxisCount()
	 */
	public int getAxisCount() {
		return this.axes.size();
	}

	/*
	 * @see org.lwjgl.input.Controller#getAxisName(int)
	 */
	public String getAxisName(int index) {
		return this.axes.get(index).getName();
	}

	/*
	 * @see org.lwjgl.input.Controller#getAxisValue(int)
	 */
	public float getAxisValue(int index) {
		return this.axesValue[index];
	}

	/*
	 * @see org.lwjgl.input.Controller#getButtonCount()
	 */
	public int getButtonCount() {
		return this.buttons.size();
	}

	/*
	 * @see org.lwjgl.input.Controller#getButtonName(int)
	 */
	public String getButtonName(int index) {
		return this.buttons.get(index).getName();
	}

	@Override
	public Component getComponent(Identifier id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component[] getComponents() {
		return null;
	}

	@Override
	public Controller[] getControllers() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see org.lwjgl.input.Controller#getDeadZone(int)
	 */
	public float getDeadZone(int index) {
		return this.deadZones[index];
	}

	@Override
	public EventQueue getEventQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see org.lwjgl.input.Controller#getIndex()
	 */
	public int getIndex() {
		return this.index;
	}

	/*
	 * @see org.lwjgl.input.Controller#getName()
	 */
	@Override
	public String getName() {
		String name = this.target.getName();
		return name;
	}

	@Override
	public int getPortNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PortType getPortType() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see org.lwjgl.input.Controller#getPovX()
	 */
	public float getPovX() {
		if (this.pov.isEmpty()) {
			return 0;
		}

		float value = this.povValues[0];

		if ((value == Component.POV.DOWN_LEFT) || (value == Component.POV.UP_LEFT) || (value == Component.POV.LEFT)) {
			return -1;
		}
		if ((value == Component.POV.DOWN_RIGHT) || (value == Component.POV.UP_RIGHT)
				|| (value == Component.POV.RIGHT)) {
			return 1;
		}

		return 0;
	}

	/*
	 * @see org.lwjgl.input.Controller#getPovY()
	 */
	public float getPovY() {
		if (this.pov.isEmpty()) {
			return 0;
		}

		float value = this.povValues[0];

		if ((value == Component.POV.DOWN_LEFT) || (value == Component.POV.DOWN_RIGHT)
				|| (value == Component.POV.DOWN)) {
			return 1;
		}
		if ((value == Component.POV.UP_LEFT) || (value == Component.POV.UP_RIGHT) || (value == Component.POV.UP)) {
			return -1;
		}

		return 0;
	}

	public int getRumblerCount() {
		return this.rumblers.length;
	}

	public String getRumblerName(int index) {
		return this.rumblers[index].getAxisName();
	}

	@Override
	public Rumbler[] getRumblers() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see org.lwjgl.input.Controller#getRXAxisDeadZone()
	 */
	public float getRXAxisDeadZone() {
		if (this.rxaxis == -1) {
			return 0;
		}

		return getDeadZone(this.rxaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getRXAxisValue()
	 */
	public float getRXAxisValue() {
		if (this.rxaxis == -1) {
			return 0;
		}

		return getAxisValue(this.rxaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getRYAxisDeadZone()
	 */
	public float getRYAxisDeadZone() {
		if (this.ryaxis == -1) {
			return 0;
		}

		return getDeadZone(this.ryaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getRYAxisValue()
	 */
	public float getRYAxisValue() {
		if (this.ryaxis == -1) {
			return 0;
		}

		return getAxisValue(this.ryaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getRZAxisDeadZone()
	 */
	public float getRZAxisDeadZone() {
		if (this.rzaxis == -1) {
			return 0;
		}

		return getDeadZone(this.rzaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getRZAxisValue()
	 */
	public float getRZAxisValue() {
		if (this.rzaxis == -1) {
			return 0;
		}

		return getAxisValue(this.rzaxis);
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see org.lwjgl.input.Controller#getXAxisDeadZone()
	 */
	public float getXAxisDeadZone() {
		if (this.xaxis == -1) {
			return 0;
		}

		return getDeadZone(this.xaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getXAxisValue()
	 */
	public float getXAxisValue() {
		if (this.xaxis == -1) {
			return 0;
		}

		return getAxisValue(this.xaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getYAxisDeadZone()
	 */
	public float getYAxisDeadZone() {
		if (this.yaxis == -1) {
			return 0;
		}

		return getDeadZone(this.yaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getYAxisValue()
	 */
	public float getYAxisValue() {
		if (this.yaxis == -1) {
			return 0;
		}

		return getAxisValue(this.yaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getZAxisDeadZone()
	 */
	public float getZAxisDeadZone() {
		if (this.zaxis == -1) {
			return 0;
		}

		return getDeadZone(this.zaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#getZAxisValue()
	 */
	public float getZAxisValue() {
		if (this.zaxis == -1) {
			return 0;
		}

		return getAxisValue(this.zaxis);
	}

	/*
	 * @see org.lwjgl.input.Controller#isButtonPressed(int)
	 */
	public boolean isButtonPressed(int index) {
		return this.buttonState[index];
	}

	/*
	 * @see org.lwjgl.input.Controller#poll()
	 */
	@Override
	public boolean poll() {
		this.target.poll();

		Event event = new Event();
		EventQueue queue = this.target.getEventQueue();

		while (queue.getNextEvent(event)) {
			// handle button event
			if (this.buttons.contains(event.getComponent())) {
				Component button = event.getComponent();
				int buttonIndex = this.buttons.indexOf(button);
				this.buttonState[buttonIndex] = event.getValue() != 0;
				// System.out.println(buttonIndex);
				buttonMap(buttonIndex, this.buttonState[buttonIndex]);
				// fire button pressed event
				Controllers.addEvent(new ControllerEvent(this, event.getNanos(), ControllerEvent.BUTTON, buttonIndex,
						this.buttonState[buttonIndex], false, false, 0, 0));
			}

			// handle pov events
			if (this.pov.contains(event.getComponent())) {
				Component povComponent = event.getComponent();
				int povIndex = this.pov.indexOf(povComponent);
				float prevX = getPovX();
				float prevY = getPovY();
				this.povValues[povIndex] = event.getValue();
				povMap(povIndex, this.povValues[povIndex]);

				// System.out.println(povIndex + ", value: " +
				// povValues[povIndex]);

				if (prevX != getPovX()) {
					Controllers.addEvent(
							new ControllerEvent(this, event.getNanos(), ControllerEvent.POVX, 0, false, false));
				}
				if (prevY != getPovY()) {
					Controllers.addEvent(
							new ControllerEvent(this, event.getNanos(), ControllerEvent.POVY, 0, false, false));
				}
			}

			// handle axis updates
			if (this.axes.contains(event.getComponent())) {
				Component axis = event.getComponent();
				int axisIndex = this.axes.indexOf(axis);
				float value = axis.getPollData();
				float xaxisValue = 0;
				float yaxisValue = 0;

				// fixed dead zone since most axis don't report it :(
				if (Math.abs(value) < this.deadZones[axisIndex]) {
					value = 0;
				}
				if (Math.abs(value) < axis.getDeadZone()) {
					value = 0;
				}
				if (Math.abs(value) > this.axesMax[axisIndex]) {
					this.axesMax[axisIndex] = Math.abs(value);
				}

				// normalize the value based on maximum value read in the past
				value /= this.axesMax[axisIndex];

				if (axisIndex == this.xaxis) {
					xaxisValue = value;
				}
				if (axisIndex == this.yaxis) {
					yaxisValue = value;
				}

				// fire event
				Controllers.addEvent(new ControllerEvent(this, event.getNanos(), ControllerEvent.AXIS, axisIndex, false,
						axisIndex == this.xaxis, axisIndex == this.yaxis, xaxisValue, yaxisValue));
				this.axesValue[axisIndex] = value;
				// System.out.println(axisIndex + ", value: " +
				// axesValue[axisIndex]);
				axisMap(axisIndex, value);
			}
		}
		return false;
	}

	// D Pad
	private void povMap(int povIndex, float value) {
		switch (getName()) {
		case "Controller (XBOX 360 For Windows)":
		case "Controller (Xbox One For Windows)":
			switch (povIndex) {
			case 0:
				switch (Math.round(value * 8)) {
				// No Input
				case 0:
					// Top Right
				case 1:
					// Bottom Right
				case 3:
					// Bottom Left
				case 5:
					// Top Left
				case 7:
					Keyboard.setKey(GLFW.GLFW_KEY_1, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_4, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_3, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_2, 0);
					break;
				// Right
				case 2:
					Keyboard.setKey(GLFW.GLFW_KEY_1, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_4, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_3, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_2, 0);
					break;
				// Bottom
				case 4:
					Keyboard.setKey(GLFW.GLFW_KEY_1, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_4, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_3, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_2, 1);
					break;
				// Left
				case 6:
					Keyboard.setKey(GLFW.GLFW_KEY_1, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_4, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_3, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_2, 0);
					break;
				// Top
				case 8:
					Keyboard.setKey(GLFW.GLFW_KEY_1, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_4, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_3, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_2, 0);
					break;
				}
				break;
			}
		}

	}

	/*
	 * @see org.lwjgl.input.Controller#setDeadZone(int, float)
	 */
	public void setDeadZone(int index, float zone) {
		this.deadZones[index] = zone;
	}

	@Override
	public void setEventQueueSize(int size) {
		// TODO Auto-generated method stub

	}

	public void setRumblerStrength(int index, float strength) {
		this.rumblers[index].rumble(strength);
	}

	/*
	 * @see org.lwjgl.input.Controller#setRXAxisDeadZone(float)
	 */
	public void setRXAxisDeadZone(float zone) {
		setDeadZone(this.rxaxis, zone);
	}

	/*
	 * @see org.lwjgl.input.Controller#setRYAxisDeadZone(float)
	 */
	public void setRYAxisDeadZone(float zone) {
		setDeadZone(this.ryaxis, zone);
	}

	/*
	 * @see org.lwjgl.input.Controller#setRZAxisDeadZone(float)
	 */
	public void setRZAxisDeadZone(float zone) {
		setDeadZone(this.rzaxis, zone);
	}

	/*
	 * @see org.lwjgl.input.Controller#setXAxisDeadZone(float)
	 */
	public void setXAxisDeadZone(float zone) {
		setDeadZone(this.xaxis, zone);
	}

	/*
	 * @see org.lwjgl.input.Controller#setYAxisDeadZone(float)
	 */
	public void setYAxisDeadZone(float zone) {
		setDeadZone(this.yaxis, zone);
	}

	/*
	 * @see org.lwjgl.input.Controller#setZAxisDeadZone(float)
	 */
	public void setZAxisDeadZone(float zone) {
		setDeadZone(this.zaxis, zone);
	}

}
