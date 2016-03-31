package gui;

import display.Art;
import display.Image;

public abstract class ButtonBuilder {

	public static Button buildButton(TypeButton type, float x, float y) {

		Image image;

		switch (type) {
			case NEW_GAME:
				image = Art.getImage("NewGameButton");
				break;
			case LOAD_GAME:
				image = Art.getImage("LoadGameButton");
				break;
			case OPTIONS:
				image = Art.getImage("OptionsButton");
				break;
			case EXIT:
				image = Art.getImage("ExitButton");
				break;
			case LEVEL1:
				image = Art.getImage("Level1Button");
				break;
			case LEVEL2:
				image = Art.getImage("Level2Button");
				break;
			case LEVEL3:
				image = Art.getImage("Level3Button");
				break;
			case WARRIOR:
				image = Art.getImage("WarriorButton");
				break;
			case ARCHER:
				image = Art.getImage("ArcherButton");
				break;
			case MAGE:
				image = Art.getImage("MageButton");
				break;
			case BATTLE_MAGE:
				image = Art.getImage("BattleMageButton");
				break;
			case ROGUE:
				image = Art.getImage("RogueButton");
				break;
			case BACK:
				image = Art.getImage("BackButton");
				break;
			case SOUND:
				image = Art.getImage("SoundButton");
				;
				break;
			case MUTE:
				image = Art.getImage("MuteButton");
				break;
			case CONTROLS:
				image = Art.getImage("ControlsButton");
				break;
			case RESUME:
				image = Art.getImage("BackButton");
				break;
			case MAIN_MENU:
				image = Art.getImage("ExitButton");
				;
				break;
			case INVENTORY:
				image = Art.getImage("InvButton");
				break;
			case UPGRADES:
				image = Art.getImage("UpgradesButton");
				break;
			case SKILLS:
				image = Art.getImage("SkillButton");
				break;
			case MAP:
				image = Art.getImage("MapButton");
				break;
			case SAVE:
				image = Art.getImage("SaveButton");
				break;
			case HEALTH_REGEN:
				image = Art.getImage("HealthRegenButton");
				break;
			case HEALTH:
				image = Art.getImage("HealthButton");
				break;
			case MANA_REGEN:
				image = Art.getImage("ManaRegenButton");
				break;
			case MANA:
				image = Art.getImage("ManaButton");
				break;
			case POTIONS_UPGRADE:
				image = Art.getImage("PotionsButton");
				break;
			case INVENTORY_UPGRADE:
				image = Art.getImage("InventoryButton");
				break;
			case SAVE_GAME:
				image = Art.getImage("SaveGameButton");
				break;
			default:
				image = Art.getImage("Warrior");
				break;
		}

		return new Button(type, image, x, y);
	}
}
