package gui;

import display.Art;
import display.Image;

public abstract class ButtonBuilder {
	
	public static Button buildButton(TypeButton type, int x, int y){
		
		Image image;
		
		switch(type){
		case NEW_GAME:
			image = Art.newGameButton;
			break;
		case LOAD_GAME:
			image = Art.loadGameButton;
			break;
		case OPTIONS:
			image = Art.optionsButton;
			break;
		case EXIT:
			image = Art.exitButton;
			break;
		case LEVEL1:
			image = Art.level1Button;
			break;
		case LEVEL2:
			image = Art.level2Button;
			break;
		case LEVEL3:
			image = Art.level3Button;
			break;
		case WARRIOR:
			image = Art.warriorButton;
			break;
		case ARCHER:
			image = Art.archerButton;
			break;
		case MAGE:
			image = Art.mageButton;
			break;
		case BATTLE_MAGE:
			image = Art.battleMageButton;
			break;
		case ROGUE:
			image = Art.rogueButton;
			break;
		case BACK:
			image = Art.backButton;
			break;
		case SOUND:
			image = Art.soundButton;;
			break;
		case MUTE:
			image = Art.muteButton;
			break;
		case CONTROLS:
			image = Art.controlsButton;
			break;
		case RESUME:
			image = Art.backButton;
			break;
		case MAIN_MENU:
			image = Art.exitButton;;
			break;
		case INVENTORY:
			image = Art.invButton;
			break;
		case UPGRADES:
			image = Art.upgradesButton;
			break;
		case SKILLS:
			image = Art.skillsButton;
			break;
		case MAP:
			image = Art.mapButton;
			break;
		case SAVE:
			image = Art.saveButton;
			break;
		case HEALTH_REGEN:
			image = Art.healthRegenButton;
			break;
		case HEALTH:
			image = Art.healthButton;
			break;
		case MANA_REGEN:
			image = Art.manaRegenButton;
			break;
		case MANA:
			image = Art.manaButton;
			break;
		case POTIONS_UPGRADE:
			image = Art.potionsButton;
			break;
		case INVENTORY_UPGRADE:
			image = Art.inventoryButton;
			break;
		case SAVE_GAME:
			image = Art.saveGameButton;
			break;
		default:
			image = Art.warrior;
			break;
		}
		
		return new Button(type, image ,x, y);
	}
}
