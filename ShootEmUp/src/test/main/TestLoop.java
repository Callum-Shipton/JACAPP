package test.main;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

import org.lwjgl.glfw.GLFW;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.control.TestingControl;
import display.ArtLoader;
import entity.Entity;
import game.Game;
import gui.buttons.NewGameButton;
import logging.Logger;
import loop.Loop;
import main.ShootEmUp;

public class TestLoop extends Loop {

	private boolean finished = false;

	public TestLoop(Game game, double fps, ArtLoader artLoader) {
		super(game, fps, artLoader);

	}

	@Override
	protected void loop() {

		int error;
		GLFW.glfwHideWindow(getDisplay().getWindow());
		// GLFW.glfwSwapInterval(0);
		ShootEmUp.getMusicPlayer().pause();
		new NewGameButton().click();
		Entity player = ShootEmUp.getGame().getPlayer();
		player.addComponent(new TestingControl());
		PlayerAttack pa = player.getComponent(TypeComponent.ATTACK);
		pa.setMaxHealth(400);

		Logger.setDebug(false);
		int lastWave = 0;
		while (!isFinished()) {

			error = glGetError();

			if (error != GL_NO_ERROR) {
				Logger.error("OpenGL Error: " + error);
			}

			update();
			// render();

			int currWave = 0;
			if (currWave != lastWave) {
				player = ShootEmUp.getGame().getPlayer();
				player.addComponent(new TestingControl());
				Logger.info("TEST: Wave: " + currWave);
				lastWave = currWave;
			}
		}
		Logger.info("TEST FINISHED");
	}

	public boolean isFinished() {
		return finished;
	}

	void setFinished(boolean finished) {
		this.finished = finished;
	}

}
