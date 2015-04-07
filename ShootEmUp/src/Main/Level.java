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
import Object.Character;
import Object.Exp;
import Object.Particle;
import Object.Player;

public class Level {
	
	private int width;
	private int height;
	private BufferedImage map = null;
	private String file;
	private Vector2[][] backgroundTiles;
	private Vector2[][] wallTiles;
	private Vector2[][] foregroundTiles;
	public float[] spawn = new float[] { 480.0f, 480.0f };
	private Player player;
	private Hud hud;

	private DPDTRenderer r;
	private DPDTRenderer stat;
	private IRenderer irBack;
	private IRenderer irWall;
	private IRenderer irFore;
	
	private int counter = 0;

	public CopyOnWriteArrayList<Collidable> walls;
	public CopyOnWriteArrayList<Character> characters;
	public CopyOnWriteArrayList<Particle> particles;
	public CopyOnWriteArrayList<Exp> experience;

	public Level(String file) {
		this.file = file;
		loadLevel();
		width = (map.getWidth() / 3);
		height = map.getHeight();
		backgroundTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
		wallTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
		foregroundTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
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
		
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth() / 3; x++) {
				switch (map.getRGB(x, y)) {
				case -1:
					backgroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case -16777216:
					backgroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				default: System.out.println(map.getRGB(x, y));
				}
				
				switch (map.getRGB(x + (map.getWidth() / 3), y)) {
				case -1:
					walls.add(null);
					break;
				case -3584:
					wallTiles[x][y] = new Vector2(5.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -14066:
					wallTiles[x][y] = new Vector2(7.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -20791:
					wallTiles[x][y] = new Vector2(0.0f, 2.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -32985:
					wallTiles[x][y] = new Vector2(4.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -1055568:
					wallTiles[x][y] = new Vector2(6.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -1237980:
					wallTiles[x][y] = new Vector2(3.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -3620889:
					wallTiles[x][y] = new Vector2(2.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -3947581:
					wallTiles[x][y] = new Vector2(2.0f, 2.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -4621737:
					wallTiles[x][y] = new Vector2(1.0f, 2.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -4856291:
					wallTiles[x][y] = new Vector2(5.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -6075996:
					wallTiles[x][y] = new Vector2(1.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -6694422:
					wallTiles[x][y] = new Vector2(4.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -7864299:
					wallTiles[x][y] = new Vector2(2.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -8355840:
					wallTiles[x][y] = new Vector2(3.0f, 2.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -8421505:
					wallTiles[x][y] = new Vector2(1.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -9399618:
					wallTiles[x][y] = new Vector2(3.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -14503604:
					wallTiles[x][y] = new Vector2(6.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -12629812:
					wallTiles[x][y] = new Vector2(0.0f, 1.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, true));
					break;
				case -16735512:
					wallTiles[x][y] = new Vector2(7.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				case -16777216:
					wallTiles[x][y] = new Vector2(0.0f, 0.0f);
					walls.add(new Collidable(x * 32.0f, y * 32.0f, 32.0f, 32.0f, false));
					break;
				default:
					walls.add(null);
					System.out.println(map.getRGB(x + (map.getWidth()/3), y));
				}
				
				switch (map.getRGB(x + ((map.getWidth() / 3) *2), y)) {
				case -1: break;
				case -7864299:
					foregroundTiles[x][y] = new Vector2(2.0f, 0.0f);
					break;
				case -8421505:
					foregroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				case -16777216:
					foregroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				default: System.out.println(map.getRGB(x + ((map.getWidth()/3)*2), y));
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
		characters = new CopyOnWriteArrayList<Character>();
		particles = new CopyOnWriteArrayList<Particle>();
		experience = new CopyOnWriteArrayList<Exp>();
		
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
		if((counter == 150) && (characters.size() <= 16)){
			Random rand = new Random();
				do {
					collide = false;
					X = rand.nextFloat() * ((float)(backgroundTiles.length * 32) - 32.0f) + 32.0f;
					Y = rand.nextFloat() * ((float)(backgroundTiles[0].length * 32) - 32.0f) + 32.0f;
					
					for (Character character : characters) {
						if ((character.doesCollide(X, Y, 64.0f, 64.0f) != null)) {
							collide = true;
							break;
						}
					}
					for (Collidable wall : walls) {
						if(wall != null){
							if (wall.doesCollide(X, Y, 32.0f, 32.0f) != null) {
								collide = true;
								break;
							}
						}
					}
				} while(collide == true);	
			characters.add(new Enemy(X, Y, 64.0f, 64.0f, 5, 0, Art.enemy));
			counter = 0;
		}
		
		for (Character character : characters) {
			character.update();
		}
		for (Particle particle : particles) {
			particle.update();
		}
		for (Exp exp : experience) {
			exp.update();
		}
		hud.update();
	}

	public void render() {
		renderLowTiles();
		renderHighTiles();

		GL20.glUseProgram(Art.ShaderBase);
		GL30.glBindVertexArray(r.getVAO());
		
		for (Exp exp : experience) {
			exp.render(r);
		}
		for (Character character : characters) {
			character.render(r);
		}
		for (Particle particle : particles) {
			particle.render(r);
		}
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
		

		renderHighTiles();
		
		
		GL20.glUseProgram(Art.ShaderStat);
		GL30.glBindVertexArray(r.getVAO());
		hud.render(stat);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);

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
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
