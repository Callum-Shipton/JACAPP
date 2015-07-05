package Level;

import Display.Art;

public class Map {

	public Map(){
		
	}
	
	public void renderLowTiles() {
		Art.irBack.draw(Art.background.getID());
		Art.irWall.draw(Art.wall.getID());
	}

	public void renderHighTiles() {
		Art.irFore.draw(Art.foreground.getID());
	}
}
