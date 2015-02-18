package Main;
import java.awt.image.*;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.*;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Display.Art;
import Display.DPDTRenderer;
import Display.IRenderer;
import Math.Vector2;
import Object.Collidable;
import Object.Enemy;
import Object.NPC;
import Object.Particle;
import Object.Player;

public class Level {
	private BufferedImage map = null;
	private String file;
	private Vector2[][] backgroundTiles;
	private Vector2[][] foregroundTiles;
	private float[] spawn = new float[] {50.0f, 50.0f};
	private Player player;
	
	private DPDTRenderer r;
	private IRenderer irBack;
	private IRenderer irFront;
	
	public CopyOnWriteArrayList<Collidable> walls;
	public CopyOnWriteArrayList<NPC> characters;
	public CopyOnWriteArrayList<Particle> particles;
	
	public Level(String file){
		this.file = file;
		loadLevel();
		backgroundTiles = new Vector2[map.getWidth()/2][map.getHeight()];
		foregroundTiles = new Vector2[map.getWidth()/2][map.getHeight()];
		addStuff();
		setTiles();
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
									walls.add(new Collidable(x*64.0f, y*64.0f, 64.0f, 64.0f));
				}
			}
		}	
		irBack = new IRenderer(Art.ShaderInst,backgroundTiles,new Vector2(4.0f,4.0f),64.0f,64.0f);
		irFront = new IRenderer(Art.ShaderInst,foregroundTiles,new Vector2(4.0f,4.0f),64.0f,64.0f);
	}
	
	private void addStuff(){
		walls = new CopyOnWriteArrayList<Collidable>();
		characters = new CopyOnWriteArrayList<NPC>();
		particles = new CopyOnWriteArrayList<Particle>();
		
		r = new DPDTRenderer(Art.ShaderBase);
		
		player = new Player(spawn[0], spawn[1], 64.0f, 64.0f, 5, 0, Art.playerID);
		
		characters.add(player);
		characters.add(new Enemy(800.0f, 50.0f, 64.0f, 64.0f, 5, 0, Art.enemyID));
	}
	
	private void renderTiles(){
		irBack.draw(Art.floorID);
		irFront.draw(Art.wallID);
		
		/*
		for(int i = 0; i < map.getWidth()/2; i++){
			for(int j = 0; j < map.getHeight(); j++){
				r.draw(Art.floorID, new Vector2((float)(i*64),(float)(j*64)), new Vector2(64.0f,64.0f), 0.0f, backgroundTiles[i][j], new Vector2(4.0f,4.0f));
				if(foregroundTiles[i][j] != null){
					r.draw(Art.wallID, new Vector2((float)(i*64),(float)(j*64)), new Vector2(64.0f,64.0f), 0.0f, foregroundTiles[i][j], new Vector2(4.0f,4.0f));
				}
			}
		}
		*/
	}
	
	public void update(){
		for (NPC character : characters) {
			character.update();
		}
		for (Particle particle : particles) {
			particle.update();
		}
	}
	
	public void render(){
		GL20.glUseProgram(Art.ShaderInst);
		renderTiles();
		GL20.glUseProgram(0);
		
		GL20.glUseProgram(Art.ShaderBase);
		GL30.glBindVertexArray(r.getVAO());
		
		for (NPC character : characters) {
			character.render(r);
		}
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
	
	public Player getPlayer(){
		return player;
	}
}