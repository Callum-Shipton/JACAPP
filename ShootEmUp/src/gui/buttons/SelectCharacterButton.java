package gui.buttons;

import components.attack.TypeAttack;
import display.ImageProcessor;
import gui.ButtonAction;
import gui.Hud;
import gui.menus.LevelSelectMenu;
import level.PlayerBuilder;
import main.ShootEmUp;
import save.Save;

public class SelectCharacterButton implements ButtonAction {

	private TypeAttack characterType;

	public SelectCharacterButton(TypeAttack characterType) {
		this.characterType = characterType;
	}

	@Override
	public void click() {
		Save save = ShootEmUp.getSave();
		if (save == null) {
			ShootEmUp.getGame().setPlayer(PlayerBuilder.buildPlayer(characterType));
			ShootEmUp.getMenuSystem().addMenu(new LevelSelectMenu(ImageProcessor.getImage("MainMenuScreen"), 1));
		} else {
			if (save.getCharacter(characterType) != null) {
				ShootEmUp.getGame()
						.setPlayer(PlayerBuilder.buildPlayer(characterType, save.getCharacter(characterType)));
			} else {
				ShootEmUp.getGame().setPlayer(PlayerBuilder.buildPlayer(characterType));
			}
			ShootEmUp.getMenuSystem()
					.addMenu(new LevelSelectMenu(ImageProcessor.getImage("MainMenuScreen"), save.getLevel()));
		}
		ShootEmUp.getGame().setHud(new Hud(ShootEmUp.getGame().getPlayer(), 0, 0));
	}
}
