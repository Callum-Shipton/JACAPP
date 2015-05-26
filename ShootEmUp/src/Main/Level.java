package Main;

import java.awt.image.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import javax.imageio.*;

import Display.Art;
import Display.DPDTRenderer;
import Display.IRenderer;
import Object.Enemies.*;
import GUI.Hud;
import Math.Vector2;
import Object.Coin;
import Object.Collidable;
import Object.Character;
import Object.EntityMap;
import Object.Exp;
import Object.Particle;
import Object.Player;
import Object.Tile;

public class Level {

	private int width;
	private int height;
	private BufferedImage map = null;
	private String file;
	private Vector2[][] backgroundTiles; //Replace with Irenderer changes
	public Vector2[][] wallTiles; // ^^
	private Vector2[][] foregroundTiles; // ^^
	public float[] spawn = new float[] { 480.0f, 480.0f };
	private Player player;
	private Hud hud;

	private DPDTRenderer base;
	private DPDTRenderer stat;
	private IRenderer irBack;
	private IRenderer irWall;
	private IRenderer irFore;

	private int counter = 0;
	
	public EntityMap eMap;

	public HashMap<Vector2,Tile> walls;
	public HashSet<Character> characters;
	public HashSet<Particle> particles;
	public HashSet<Exp> experience;
	public HashSet<Coin> coins;

	public Level(String file) {
		this.file = file;
		loadLevel();
		width = (map.getWidth() / 3);
		height = map.getHeight();
		backgroundTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
		wallTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
		foregroundTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
		eMap = new EntityMap(width,height);
	}

	private void loadLevel() {
		try {
			map = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
		}
	}
	
	public void init(){
		addStuff();
		setTiles();
		renderLowTiles();
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
				default:
					System.out.println(map.getRGB(x, y));
				}

				switch (map.getRGB(x + (map.getWidth() / 3), y)) {
				case -1:
					break;
				case -3584:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false, new Vector2(5.0f, 0.0f)));
					break;
				case -14066:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true,  new Vector2(7.0f, 1.0f)));
					break;
				case -20791:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true,  new Vector2(0.0f, 2.0f)));
					break;
				case -32985:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false,  new Vector2(4.0f, 0.0f)));
					break;
				case -1055568:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true,  new Vector2(6.0f, 1.0f)));
					break;
				case -1237980:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false, new Vector2(3.0f, 0.0f)));
					break;
				case -3620889:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true, new Vector2(2.0f, 1.0f)));
					break;
				case -3947581:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false,new Vector2(2.0f, 2.0f)));
					break;
				case -4621737:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false, new Vector2(1.0f, 2.0f)));
					break;
				case -4856291:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true,new Vector2(5.0f, 1.0f)));
					break;
				case -6075996:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true,new Vector2(1.0f, 1.0f)));
					break;
				case -6694422:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true,new Vector2(4.0f, 1.0f)));
					break;
				case -7864299:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false,new Vector2(2.0f, 0.0f)));
					break;
				case -8355840:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false, new Vector2(3.0f, 2.0f)));
					break;
				case -8421505:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false,new Vector2(1.0f, 0.0f)));
					break;
				case -9399618:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true, new Vector2(3.0f, 1.0f)));
					break;
				case -14503604:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false, new Vector2(6.0f, 0.0f)));
					break;
				case -12629812:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, true,new Vector2(0.0f, 1.0f)));
					break;
				case -16735512:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false,new Vector2(7.0f, 0.0f)));
					break;
				case -16777216:
					walls.put(new Vector2(x,y),new Tile(x * 32.0f, y * 32.0f, 32.0f,
							32.0f, false,new Vector2(0.0f, 0.0f)));
					break;
				default:
					System.out.println(map.getRGB(x + (map.getWidth() / 3), y));
				}

				switch (map.getRGB(x + ((map.getWidth() / 3) * 2), y)) {
				case -1:
					break;
				case -7864299:
					foregroundTiles[x][y] = new Vector2(2.0f, 0.0f);
					break;
				case -8421505:
					foregroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				case -16777216:
					foregroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				default:
					System.out.println(map.getRGB(x
							+ ((map.getWidth() / 3) * 2), y));
				}
			}
		}
		irBack = new IRenderer(backgroundTiles, new Vector2(4.0f, 4.0f), 32.0f,
				32.0f);
		irWall = new IRenderer(walls, new Vector2(8.0f, 8.0f), 32.0f, 32.0f);
		irFore = new IRenderer(foregroundTiles, new Vector2(4.0f, 4.0f), 32.0f,
				32.0f);
	}

	private void addStuff() {
		walls = new HashMap<Vector2,Tile>();
		characters = new HashSet<Character>();
		particles = new HashSet<Particle>();
		experience = new HashSet<Exp>();
		coins = new HashSet<Coin>();

		base = new DPDTRenderer(Art.ShaderBase);
		stat = new DPDTRenderer(Art.ShaderStat);

		player = new Player(spawn[0], spawn[1]);
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
		if (counter == 150) {
			Random rand = new Random();
			do {
				collide = false;
				X = rand.nextFloat()
						* ((float) ((backgroundTiles.length - 1) * 32) - 32.0f)
						+ 32.0f;
				Y = rand.nextFloat()
						* ((float) ((backgroundTiles[0].length - 1) * 32) - 32.0f)
						+ 32.0f;

				for (Character character : characters) {
					if ((character.doesCollide(X, Y, 64.0f, 64.0f) != null)) {
						collide = true;
						break;
					}
				}
				Iterator<Entry<Vector2, Tile>> iterator = walls.entrySet().iterator() ;
				while(iterator.hasNext()){
					Entry<Vector2, Tile> wall = iterator.next();
						if (wall.getValue().doesCollide(X, Y, 64.0f, 64.0f) != null) {
							collide = true;
							break;
						}
					}
			} while (collide == true);
			characters.add(new RedSquare(X, Y));
			counter = 0;
		}

		Iterator<Character> charIter = characters.iterator();
		while(charIter.hasNext()){
			Character c = charIter.next();
			c.update();
			if (c.destroy) charIter.remove();
		}
		Iterator<Particle> partIter = particles.iterator();
		while(partIter.hasNext()){
			Particle p = partIter.next();
			p.update();
			if (p.destroy) partIter.remove();
		}
		for (Exp exp : experience) {
			exp.update();
		}
		for (Coin coin : coins) {
			coin.update();
		}
		hud.update();
	}

	public void render() {
		renderLowTiles();
		renderHighTiles();

		for (Exp exp : experience) {
			exp.render(base);
		}
		for (Coin coin : coins) {
			coin.render(base);
		}
		for (Character character : characters) {
			character.render(base);
		}
		for (Particle particle : particles) {
			particle.render(base);
		}

		renderHighTiles();

		hud.render(stat);

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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Collidable getWall(Vector2 vec){
		return  walls.get(vec);
	}
}
