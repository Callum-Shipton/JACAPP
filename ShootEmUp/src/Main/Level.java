package Main;

import java.awt.image.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.*;

import Components.ComponentType;
import Components.Attack.MageAttack;
import Components.Attack.MeleeAttack;
import Components.Collision.RigidCollision;
import Components.Collision.BaseCollision;
import Components.Control.AIControl;
import Components.Control.PlayerControl;
import Components.Graphical.AnimatedGraphics;
import Components.Graphical.BaseGraphics;
import Components.Graphical.MapGraphics;
import Components.Graphical.PlayerGraphics;
import Components.Inventory.PlayerInventory;
import Components.Movement.BaseMovement;
import Components.Movement.BasicMovement;
import Components.Movement.FlyingMovement;
import Components.Spawn.PointSpawn;
import Display.Art;
import Display.IRenderer;
import GUI.Hud;
import Math.Vector2;
import Object.Entity;
import Object.EntityMap;
import Object.Weapon;

public class Level {

	private int width;
	private int height;
	private BufferedImage map = null;
	private String file;
	public Vector2[][] backgroundTiles; //Replace with Irenderer changes
	public Vector2[][] wallTiles; // ^^
	public Vector2[][] foregroundTiles; // ^^
	
	private Entity player;
	
	private Hud hud;

	private int counter = 0;
	
	public EntityMap eMap;

	public HashMap<Vector2, Entity> walls;
	public HashSet<Entity> entities;
	public HashSet<Entity> oldEntities;
	public HashSet<Entity> newEntities;
	
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
	}
	
	private void setTiles() {

		boolean noWall;
		float width = Art.wall.getWidth()/Art.wall.getFWidth();
		float height = Art.wall.getHeight()/Art.wall.getFHeight();
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
				
				//creating walls
				noWall = false;
				Entity wall = new Entity();
				MapGraphics wallG = null;
				switch (map.getRGB(x + (map.getWidth() / 3), y)) {
				case -1:
					noWall = true;
					break;
				case -3584:
					wallG = new MapGraphics(Art.wall, new Vector2(5.0f, 0.0f), x * width, y * height);
					break;
				case -14066:
					wallG = new MapGraphics(Art.wall, new Vector2(7.0f, 1.0f), x * width, y * height);
					break;
				case -20791:
					wallG = new MapGraphics(Art.wall, new Vector2(0.0f, 2.0f), x * width, y * height);
					break;
				case -32985:
					wallG = new MapGraphics(Art.wall, new Vector2(4.0f, 0.0f), x * width, y * height);
					break;
				case -1055568:
					wallG = new MapGraphics(Art.wall, new Vector2(6.0f, 1.0f), x * width, y * height);
					break;
				case -1237980:
					wallG = new MapGraphics(Art.wall, new Vector2(3.0f, 0.0f), x * width, y * height);
					break;
				case -3620889:
					wallG = new MapGraphics(Art.wall, new Vector2(2.0f, 1.0f), x * width, y * height);
					break;
				case -3947581:
					wallG = new MapGraphics(Art.wall, new Vector2(2.0f, 2.0f), x * width, y * height);
					break;
				case -4621737:
					wallG = new MapGraphics(Art.wall, new Vector2(1.0f, 2.0f), x * width, y * height);
					break;
				case -4856291:
					wallG = new MapGraphics(Art.wall, new Vector2(5.0f, 1.0f), x * width, y * height);
					break;
				case -6075996:
					wallG = new MapGraphics(Art.wall, new Vector2(1.0f, 1.0f), x * width, y * height);
					break;
				case -6694422:
					wallG = new MapGraphics(Art.wall, new Vector2(4.0f, 1.0f), x * width, y * height);
					break;
				case -7864299:
					wallG = new MapGraphics(Art.wall, new Vector2(2.0f, 0.0f), x * width, y * height);
					break;
				case -8355840:
					wallG = new MapGraphics(Art.wall, new Vector2(3.0f, 2.0f), x * width, y * height);
					break;
				case -8421505:
					wallG = new MapGraphics(Art.wall, new Vector2(1.0f, 0.0f), x * width, y * height);
					break;
				case -9399618:
					wallG = new MapGraphics(Art.wall, new Vector2(3.0f, 1.0f), x * width, y * height);
					break;
				case -14503604:
					wallG = new MapGraphics(Art.wall, new Vector2(6.0f, 0.0f), x * width, y * height);
					break;
				case -12629812:
					wallG = new MapGraphics(Art.wall, new Vector2(0.0f, 1.0f), x * width, y * height);
					break;
				case -16735512:
					wallG = new MapGraphics(Art.wall, new Vector2(7.0f, 0.0f), x * width, y * height);
					break;
				case -16777216:
					wallG = new MapGraphics(Art.wall, new Vector2(0.0f, 0.0f), x * width, y * height);
					break;
				default:
					System.out.println(map.getRGB(x + (map.getWidth() / 3), y));
				}
				
				if(!noWall){
					wall.addComponent(wallG);
					RigidCollision MC = new RigidCollision(wall);
					wall.addComponent(MC);
					entities.add(wall);
					walls.put(new Vector2(x,y), wall);
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
					System.out.println(map.getRGB(x + ((map.getWidth() / 3) * 2), y));
				}
			}
		}
		Art.irBack = new IRenderer(backgroundTiles, new Vector2(4.0f, 4.0f), 32.0f, 32.0f);
		Art.irWall = new IRenderer(walls, new Vector2(8.0f, 8.0f), 32.0f, 32.0f);
		Art.irFore = new IRenderer(foregroundTiles, new Vector2(4.0f, 4.0f), 32.0f, 32.0f);
	}

	private void addStuff() {
		walls = new HashMap<Vector2,Entity>();
		entities = new HashSet<Entity>();
		oldEntities = new HashSet<Entity>();
		newEntities = new HashSet<Entity>();
		
		//create player
		player = new Entity();
		PlayerGraphics g = new PlayerGraphics(player, Art.player, Art.base);
		PointSpawn s = new PointSpawn(g, new Vector2(480.0f, 480.0f), player);
		PlayerInventory i = new PlayerInventory(0, 1, 3);
		MageAttack a = new MageAttack(s, i, new Weapon(5, 100, 10, false, 1), 18, 100, 18, 18, 50, 18);
		RigidCollision c = new RigidCollision(player);
		BasicMovement m = new BasicMovement(player,c, g, 5);
		player.addComponent(new PlayerControl(player, g, a, m));
		player.addComponent(g);
		player.addComponent(s);
		player.addComponent(i);
		player.addComponent(a);
		player.addComponent(c);
		player.addComponent(m);
		
		//create HUD
		hud = new Hud(player);
		
		entities.add(player);
	}

	private void renderLowTiles() {
		Art.irBack.draw(Art.background.getID());
		Art.irWall.draw(Art.wall.getID());
	}

	private void renderHighTiles() {
		Art.irFore.draw(Art.foreground.getID());
	}

	public void update() {
		boolean collide = false;

		counter++;
		if (counter == 150) {
			Entity test = new Entity();
			AnimatedGraphics AG = new AnimatedGraphics(Art.player, Art.base, false);
			test.addComponent(AG);
			RigidCollision RC = new RigidCollision(test);
			test.addComponent(RC);
			test.addComponent(new BasicMovement(test, RC, AG, 5));
			Random rand = new Random();
			do {
				collide = false;
				((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).setX((float)rand.nextInt((backgroundTiles.length - 1) * 32));
				((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).setY(rand.nextInt((backgroundTiles[0].length - 1) * 32));

				for (Entity character : entities) {
					if ((((BaseMovement) test.getComponent(ComponentType.MOVEMENT)).doesCollide(test, character) != null)) {
						collide = true;
						break;
					}
				}
			} while (collide == true);
			
			//creating new Enemy
			Entity newEnemy = new Entity();
			AnimatedGraphics enemyGraphics;
			PointSpawn enemySpawn;
			MeleeAttack enemyAttack;
			AIControl enemyControl;
			RigidCollision enemyCollision;
			BaseMovement enemyMovement;
			
			int prob = rand.nextInt(3);
			if(prob == 0){
				enemyGraphics = new AnimatedGraphics(Art.enemy, Art.base, false); 
				enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
				enemyAttack = new MeleeAttack(enemySpawn, enemyGraphics, new Weapon(10, 100, 10, false, 1), 10, 100, 10);
				newEnemy.addComponent(enemyGraphics);
				enemyCollision = new RigidCollision(newEnemy);
				enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 2);
				enemyControl = new AIControl(enemyGraphics, enemyMovement);
			} else if(prob == 1){
				enemyGraphics = new AnimatedGraphics(Art.smallEnemy, Art.base, false); 
				enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
				enemyAttack = new MeleeAttack(enemySpawn, enemyGraphics, new Weapon(2, 100, 10, false, 1), 10, 100, 10);
				newEnemy.addComponent(enemyGraphics);
				enemyCollision = new RigidCollision(newEnemy);
				enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 7);
				enemyControl = new AIControl(enemyGraphics, enemyMovement);
			} else {
				enemyGraphics = new AnimatedGraphics(Art.flyingEnemy, Art.base, false); 
				enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
				enemyAttack = new MeleeAttack(enemySpawn, enemyGraphics, new Weapon(5, 100, 10, false, 1), 10, 100, 10);
				newEnemy.addComponent(enemyGraphics);
				enemyCollision = new RigidCollision(newEnemy);
				enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);
				enemyControl = new AIControl(enemyGraphics, enemyMovement);
			}			
			
			newEnemy.addComponent(enemySpawn);
			newEnemy.addComponent(enemyAttack);
			newEnemy.addComponent(enemyCollision);
			newEnemy.addComponent(enemyControl);
			newEnemy.addComponent(enemyMovement);
			
			entities.add(newEnemy);
			counter = 0;
		}

		Iterator<Entity> charIter = entities.iterator();
		while(charIter.hasNext()){
			Entity c = charIter.next();
			c.update();
		}
		
		Iterator<Entity> oldEntitiesIter = oldEntities.iterator();
		while(oldEntitiesIter.hasNext()){
			Entity n = oldEntitiesIter.next();
			ShootEmUp.currentLevel.eMap.removeEntity(((BaseCollision)n.getComponent(ComponentType.COLLISION)).getGridPos(), n);
			entities.remove(n);
		}
		oldEntities.clear();
		
		Iterator<Entity> newEntitiesIter = newEntities.iterator();
		while(newEntitiesIter.hasNext()){
			Entity n = newEntitiesIter.next();
			entities.add(n);
		}
		newEntities.clear();
		hud.update();
	}

	public void render() {
		renderLowTiles();
		renderHighTiles();

		for (Entity character : entities) {
			((BaseGraphics)character.getComponent(ComponentType.GRAPHICS)).render(character);
		}

		renderHighTiles();

		hud.render(Art.stat);

	}

	public Entity getPlayer() {
		return player;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	/*
	public Entity getWall(Vector2 vec){
		return  walls.get(vec);
	}
	*/
}
