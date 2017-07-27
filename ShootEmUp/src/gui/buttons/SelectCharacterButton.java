package gui.buttons;

import org.joml.Vector2f;

import components.TypeComponent;
import components.spawn.PointSpawn;
import display.ImageProcessor;
import entity.Entity;
import gui.ButtonAction;
import gui.Hud;
import gui.menus.LevelSelectMenu;
import main.ShootEmUp;
import save.ShootEmUpSave;

public class SelectCharacterButton implements ButtonAction {

	private String characterType;

	public SelectCharacterButton(String characterType) {
		this.characterType = characterType;
	}

	@Override
	public void click() {
		ShootEmUpSave save = ShootEmUp.getSave();
		if (save == null) {
			newPlayer();
			ShootEmUp.getMenuSystem().addMenu(new LevelSelectMenu(ImageProcessor.getImage("MainMenuScreen"), 1));
		} else {
			if (save.getCharacter(characterType) != null) {
				// TODO Fix Loading Player
				newPlayer();
			} else {
				newPlayer();
			}
			ShootEmUp.getMenuSystem()
					.addMenu(new LevelSelectMenu(ImageProcessor.getImage("MainMenuScreen"), save.getLevel()));
		}
		ShootEmUp.getGame().setHud(new Hud(ShootEmUp.getGame().getPlayer(), 0, 0));
	}

	private void newPlayer() {
		Entity player = new Entity("Characters", "Players", characterType);
		PointSpawn spawn = player.getComponent(TypeComponent.SPAWN);
		spawn.setSpawnLocation(new Vector2f(480.0f, 480.0f));
		spawn.spawn();
		ShootEmUp.getGame().setPlayer(player);
	}
}
