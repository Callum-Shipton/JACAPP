package Main;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.*;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Display.Art;
import Display.DPDTRenderer;
import Math.Vector2;
import Object.Enemy;
import Object.Entity;
import Object.Particle;
import Object.Player;

public class Level {
	private BufferedImage map = null;
	private String file;
	private Vector2[][] backgroundTiles;
	private Vector2[][] foregroundTiles;
	private float[] spawn = new float[] {50.0f, 50.0f};
	private Player player;
	private Enemy enemy;
	
	private DPDTRenderer r;
	
	public ArrayList<Entity> collidables;
	public ArrayList<Particle> particles;
	
	public Level(String file){
		this.file = file;
		loadLevel();
		backgroundTiles = new Vector2[map.getWidth()/2][map.getHeight()];
		foregroundTiles = new Vector2[map.getWidth()/2][map.getHeight()];
		setTiles();
		addStuff();
		renderTiles();
	}
	
	private void loadLevel(){
		try {
		    map = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
		}			
	}
	
	private void setTiles(){
		for(int x = 0; x < map.getWidth()/2; x++){
			for(int y = 0; y < map.getHeight(); y++){
				System.out.println(map.getRGB(x, y));
				switch(map.getRGB(x, y)){
					case -1: backgroundTiles[x][y] = new Vector2(0.0f,0.0f);
							break;
					case -16777216: backgroundTiles[x][y] = new Vector2(1.0f,0.0f);
				}
				switch(map.getRGB(x + (map.getWidth()/2), y)){
					case -1: break;
					case -16777216: foregroundTiles[x][y] = new Vector2(2.0f,1.0f);
				}
			}
		}	
	}
	
	private void addStuff(){
		collidables = new ArrayList<Entity>();
		particles = new ArrayList<Particle>();
		
		r = new DPDTRenderer(Art.ShaderBase);
		
		player = new Player(spawn[0], spawn[1], 5, 0, Art.playerID);
		enemy = new Enemy(300.0f, 300.0f, 5, 0, Art.enemyID);
		
		collidables.add(player);
		collidables.add(enemy);
	}
	
	private void renderTiles(){
		for(int i = 0; i < map.getWidth()/2; i++){
			for(int j = 0; j < map.getHeight(); j++){
				r.draw(Art.floorID, new Vector2((float)(i*64),(float)(j*64)), new Vector2(64.0f,64.0f), 0.0f, backgroundTiles[i][j], new Vector2(4.0f,4.0f));
				if(foregroundTiles[i][j] != null){
					r.draw(Art.wallID, new Vector2((float)(i*64),(float)(j*64)), new Vector2(64.0f,64.0f), 0.0f, foregroundTiles[i][j], new Vector2(4.0f,4.0f));
				}
			}
		}
	}
	
	public void update(){
		player.update();
		enemy.update();
		for (Particle particle : particles) {
			particle.update();
		}
	}
	
	public void render(){
		GL20.glUseProgram(Art.ShaderBase);
		GL30.glBindVertexArray(r.getVAO());
		renderTiles();
		player.render(r);
		enemy.render(r);
		for (Particle particle : particles) {
			particle.render(r);
		}
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
	}

	public float[] getSpawn() {
		return spawn;
	}

	public void setSpawn(float[] spawn) {
		this.spawn = spawn;
	}
}