package gui;

public abstract class MenuItem {
	
	int x;
	int y;
	
	public MenuItem(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public abstract void update();
	public abstract void render();
}
