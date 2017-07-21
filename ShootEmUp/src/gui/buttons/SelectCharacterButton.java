package gui.buttons;

import org.joml.Vector2f;

import components.TypeComponent;
import components.attack.TypeAttack;
import components.spawn.PointSpawn;
import display.ImageProcessor;
import entity.Entity;
import gui.ButtonAction;
import gui.Hud;
import gui.menus.LevelSelectMenu;
import level.PlayerBuilder;
import main.ShootEmUp;
import save.Save;

public class SelectCharacterButton implements ButtonAction {

	private String characterType;

	public SelectCharacterButton(String characterType) {
		this.characterType = characterType;
	}

	@Override
	public void click() {
		Save save = ShootEmUp.getSave();
		if (save == null) {
			Entity player = new Entity("Characters", "Players", characterType);
			PointSpawn spawn = player.getComponent(TypeComponent.SPAWN);
			spawn.setSpawnLocation(new Vector2f(480.0f, 480.0f));
			spawn.spawn();
			ShootEmUp.getGame().setPlayer(player);
			ShootEmUp.getMenuSystem().addMenu(new LevelSelectMenu(ImageProcessor.getImage("MainMenuScreen"), 1));
		} else {
			if (save.getCharacter(TypeAttack.WARRIOR) != null) {
				ShootEmUp.getGame().setPlayer(
						PlayerBuilder.buildPlayer(TypeAttack.WARRIOR, save.getCharacter(TypeAttack.WARRIOR)));
			} else {
				Entity player = new Entity("Characters", "Players", characterType);
				PointSpawn spawn = player.getComponent(TypeComponent.SPAWN);
				spawn.setSpawnLocation(new Vector2f(480.0f, 480.0f));
				spawn.spawn();
				ShootEmUp.getGame().setPlayer(player);
			}
			ShootEmUp.getMenuSystem()
					.addMenu(new LevelSelectMenu(ImageProcessor.getImage("MainMenuScreen"), save.getLevel()));
		}
		ShootEmUp.getGame().setHud(new Hud(ShootEmUp.getGame().getPlayer(), 0, 0));
	}
}
