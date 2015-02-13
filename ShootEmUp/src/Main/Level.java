package Main;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

import Display.Art;
import Display.Renderer;
import Math.Vector2;
import Object.Enemy;
import Object.Particle;
import Object.Player;

public class Level {
	private BufferedImage map = null;
	private File file;
	private int[][] tiles;
	private float[] spawn = new float[] {50.0f, 50.0f};
	private Player player;
	private Enemy enemy;
	
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
			//	System.out.println(map.getRGB(x, y));
				switch(map.getRGB(x, y)){
				case -1: tiles[x][y] = Art.floorID;
						break;
				case -16777216: tiles[x][y] = Art.wallID;
				}
			}
		}	
	}
	
	private void addStuff(){
		
		r = new Renderer(Art.ShaderBase);
		
		player = new Player(spawn[0], spawn[1], 5, 0, Art.playerID);
		enemy = new Enemy(300.0f, 300.0f, 5, 0, Art.enemyID);
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
		enemy.update();
		if(p != null){
			p.update();
		}
	}
	
	public void render(){
		renderTiles();
		player.render(r);
		enemy.render(r);
		if(p != null){
			p.render(r);
		}
	}

	public float[] getSpawn() {
		return spawn;
	}

	public void setSpawn(float[] spawn) {
		this.spawn = spawn;
	}
}