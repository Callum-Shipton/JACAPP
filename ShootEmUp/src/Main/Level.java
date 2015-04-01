package Main;

import java.awt.image.*;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.*;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Display.Art;
import Display.DPDTRenderer;
import Display.Hud;
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
	private Vector2[][] wallTiles;
	private Vector2[][] foregroundTiles;
	public float[] spawn = new float[] { 320.0f, 320.0f };
	private Player player;
	private Hud hud;

	private DPDTRenderer r;
	private DPDTRenderer stat;
	private IRenderer irBack;
	private IRenderer irWall;
	private IRenderer irFore;
	
	private int counter = 0;

	public CopyOnWriteArrayList<Collidable> walls;
	public CopyOnWriteArrayList<NPC> characters;
	public CopyOnWriteArrayList<Particle> particles;

	public Level(String file) {
		this.file = file;
		loadLevel();
		backgroundTiles = new Vector2[map.getWidth() / 2][map.getHeight()];
		wallTiles = new Vector2[map.getWidth() / 2][map.getHeight()];
		foregroundTiles = new Vector2[map.getWidth() / 2][map.getHeight()];
		addStuff();
		setTiles();
		renderLowTiles();
	}

	private void loadLevel() {
		try {
			map = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
		}
	}

	private void setTiles() {
		for (int x = 0; x < map.getWidth() / 2; x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				switch (map.getRGB(x, y)) {
				case -1:
					backgroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case -14066:
					backgroundTiles[x][y] = new Vector2(2.0f, 0.0f);
					break;
				case -32985:
					backgroundTiles[x][y] = new Vector2(0.0f, 1.0f);
					break;
				case -1055568: 
					backgroundTiles[x][y] = new Vector2(3.0f, 0.0f);
					break;
				case -1237980: 
					backgroundTiles[x][y] = new Vector2(1.0f, 1.0f);
					break;
				case -16777216:
					backgroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				default: System.out.println(map.getRGB(x, y));
				}
				switch (map.getRGB(x + (map.getWidth() / 2), y)) {
				case -1:
					break;
				case -32985: 
					wallTiles[x][y] = new Vector2(0.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -1237980: 
					wallTiles[x][y] = new Vector2(3.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -3620889:
					wallTiles[x][y] = new Vector2(0.0f, 2.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -6075996:
					wallTiles[x][y] = new Vector2(1.0f, 2.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -6694422:
					wallTiles[x][y] = new Vector2(3.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -7864299: 
					wallTiles[x][y] = new Vector2(1.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -8421505: 
					wallTiles[x][y] = new Vector2(2.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -12629812:
					wallTiles[x][y] = new Vector2(1.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -16735512:
					wallTiles[x][y] = new Vector2(2.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -16777216:
					wallTiles[x][y] = new Vector2(0.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				default: System.out.println(map.getRGB(x + (map.getWidth()/2), y));
				}
			}
		}
		irBack = new IRenderer(backgroundTiles, new Vector2(4.0f, 4.0f), 32.0f,
				32.0f);
		irWall = new IRenderer(wallTiles, new Vector2(8.0f, 8.0f),
				32.0f, 32.0f);
		irFore = new IRenderer(foregroundTiles, new Vector2(4.0f, 4.0f), 32.0f,
				32.0f);
	}

	private void addStuff() {
		walls = new CopyOnWriteArrayList<Collidable>();
		characters = new CopyOnWriteArrayList<NPC>();
		particles = new CopyOnWriteArrayList<Particle>();

		r = new DPDTRenderer(Art.ShaderBase);
		stat = new DPDTRenderer(Art.ShaderStat);
		
		player = new Player(spawn[0], spawn[1], 64.0f, 64.0f, 5, 0,
				Art.player);
		hud = new Hud(player);
		characters.add(player);
	}

	private void renderLowTiles() {
		irBack.draw(Art.background.getID());
		irWall.draw(Art.wall.getID());
	}

	private void renderHighTiles() {
		irFore.draw(Art.foreground.getID());
	}
	
	public void update() {
		float X = 0.0f;
		float Y = 0.0f;
		boolean collide = false;
		
		counter++;
		if(counter == 150){
			Random rand = new Random();
				do {
					collide = false;
					X = rand.nextFloat() * ((float)(backgroundTiles.length * 32) - 32.0f) + 32.0f;
					Y = rand.nextFloat() * ((float)(backgroundTiles[0].length * 32) - 32.0f) + 32.0f;
					
					for (NPC character : ShootEmUp.currentLevel.characters) {
						if ((character.doesCollide(X, Y, 64.0f, 64.0f) != null)) {
							collide = true;
							break;
						}
					}
					for (Collidable wall : ShootEmUp.currentLevel.walls) {
						if (wall.doesCollide(X, Y, 32.0f, 32.0f) != null) {
							collide = true;
							break;
						}
					}
				} while(collide == true);	
			characters.add(new Enemy(X, Y, 64.0f, 64.0f, 5, 0, Art.enemy));
			counter = 0;
		}
		
		for (NPC character : characters) {
			character.update();
		}
		for (Particle particle : particles) {
			particle.update();
		}
		hud.update();
	}

	public void render() {
		GL20.glUseProgram(Art.ShaderInst);
		renderLowTiles();
		GL20.glUseProgram(0);

		GL20.glUseProgram(Art.ShaderBase);
		GL30.glBindVertexArray(r.getVAO());

		for (NPC character : characters) {
			character.render(r);
		}
		for (Particle particle : particles) {
			particle.render(r);
		}

		GL20.glUseProgram(0);
		GL20.glUseProgram(Art.ShaderStat);
		hud.render(stat);
		GL20.glUseProgram(0);
		GL30.glBindVertexArray(0);
	}

	public float[] getSpawn() {
		return spawn;
	}

	public void setSpawn(float[] spawn) {
		this.spawn = spawn;
	}

	public Player getPlayer() {
		return player;
	}
}
