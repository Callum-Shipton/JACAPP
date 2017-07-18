package test.main;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

import org.lwjgl.glfw.GLFW;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.control.TestingControl;
import display.ArtLoader;
import display.ImageProcessor;
import entity.Entity;
import game.Game;
import gui.buttons.OpenMenuButton;
import gui.buttons.SelectCharacterButton;
import gui.buttons.SelectLevelButton;
import gui.menus.CharacterSelectMenu;
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
		ShootEmUp.getMusicPlayer().pause();
		new OpenMenuButton(new CharacterSelectMenu(ImageProcessor.getImage("MainMenuScreen"))).click();
		new SelectCharacterButton(TypeAttack.WARRIOR).click();
		new SelectLevelButton(1).click();
		Entity player = ShootEmUp.getGame().getPlayer();
		player.addComponent(new TestingControl(player.getComponent(TypeComponent.GRAPHICS),
				player.getComponent(TypeComponent.ATTACK), player.getComponent(TypeComponent.MOVEMENT),
				player.getComponent(TypeComponent.INVENTORY)));
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
			int currWave = ShootEmUp.getGame().getCurrentLevel().getWave();
			if (currWave != lastWave) {
				Logger.info("TEST: Wave: " + currWave);
				lastWave = currWave;
			}
			if (ShootEmUp.getGame().getLevel() == 3) {
				setFinished(true);
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
