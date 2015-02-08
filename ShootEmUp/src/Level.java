import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

public class Level {
	private BufferedImage map = null;
	private File file;
	private File[][] tiles;
	
	public Level(File file){
		this.file = file;
		loadLevel();
		tiles = new File[map.getWidth()][map.getHeight()];
		setTiles();
	}
	
	private void loadLevel(){
		try {
		    map = ImageIO.read(file);
		} catch (IOException e) {
		}			
	}
	
	private void setTiles(){
		for(int x = 0; x < map.getWidth(); x++){
			for(int y = 0; y < map.getHeight(); y++){
				System.out.println(map.getRGB(x, y));
				switch(map.getRGB(x, y)){
				case -1: tiles[x][y] = Art.grass;
				}
			}
		}	
	}
}