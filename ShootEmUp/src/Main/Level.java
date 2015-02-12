package Main;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

import Display.Art;
import Display.Renderer;
import Math.Vector2;
import Object.Particle;
import Object.Player;

public class Level {
	private BufferedImage map = null;
	private File file;
	private int[][] tiles;
	private int[] spawn = new int[] {50, 50};
	private Player player;
	private Renderer r;
	
	public static Particle p;
	
	public Level(String file){
		this.file = new File(file);
		loadLevel();
		tiles = new int[map.getWidth()][map.getHeight()];
		setTiles();
		addStuff();
		renderTiles();
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
				case -1: tiles[x][y] = Art.grassID;
				}
			}
		}	
	}
	
	private void addStuff(){
		
		r = new Renderer(Art.ShaderBase);
		
		player = new Player(spawn[0], spawn[1], 5, 0, Art.playerID);
	}
	
	private void renderTiles(){
		for(int i = 0; i < map.getWidth(); i++){
			for(int j = 0; j < map.getHeight(); j++){
				r.draw(tiles[i][j], new Vector2((float)(i*64),(float)(j*64)), new Vector2(64.0f,64.0f), 0.0f, new Vector2(1.0f,1.0f), new Vector2(1.0f,1.0f));
			}
		}
	}
	
	public void update(){
		player.update();
		if(p != null){
			p.update();
		}
	}
	
	public void render(){
		renderTiles();
		player.render(r);
		if(p != null){
			p.render(r);
		}
	}

	public int[] getSpawn() {
		return spawn;
	}

	public void setSpawn(int[] spawn) {
		this.spawn = spawn;
	}
}