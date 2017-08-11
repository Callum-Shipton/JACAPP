package gui.menus;

import display.Image;
import gui.MenuMap;

public class MapMenu extends PauseMenu {

	public MapMenu(Image menuImage) {
		super(menuImage);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resetMenu() {
		super.resetMenu();
		MenuMap map = new MenuMap(100.0f, 100.0f, 400.0f, 400.0f);
		addMenuItem(map);
	}
}
