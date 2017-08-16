package main;
import java.util.HashSet;
import java.util.Set;

import components.TypeComponent;
import components.graphical.BaseGraphics;
import components.spawn.BaseSpawn;
import display.BaseRenderSystem;
import entity.Entity;
import game.Game;
import loop.GameLoop;

public class Pong implements Game{
	
	private static final float FPS = 60.0f;
	private static final int SCREEN_WIDTH = 512;
	private static final int SCREEN_HEIGHT = 512;
	private static final String GAME_NAME = "Pong";
	private static BaseRenderSystem renderSystem = new BaseRenderSystem();
	
	private static Set<Entity> entities = new HashSet<>();
	private static Entity ball;
	private static Entity background;
	private static Entity player;
	private static Entity enemy;
	
	public static void main(String[] args) {
		GameLoop loop = new GameLoop(new Pong(), FPS, SCREEN_WIDTH, SCREEN_HEIGHT, GAME_NAME);
		loop.run();
	}

	@Override
	public void init() {
		renderSystem.init();
		background = new Entity("Level","Objects","Background");
		ball = new Entity("Level","Objects","Ball");
		player = new Entity("Characters","Players","Player");
		enemy = new Entity("Characters","Players","Enemy");
		
		entities.add(ball);

		entities.add(player);

		entities.add(enemy);
		
		for(Entity e: entities) {
			BaseSpawn SC = e.getComponent(TypeComponent.SPAWN);
			if(SC != null) {
				SC.spawn();
			}
		}
	}

	@Override
	public void update() {
		background.update();
		for(Entity e: entities) {
			e.update();
		}
	}

	@Override
	public void render() {
		BaseGraphics graphics = background.getComponent(TypeComponent.GRAPHICS);
		graphics.render(background);
		for(Entity e: entities) {
			graphics = e.getComponent(TypeComponent.GRAPHICS);
			graphics.render(e);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public static Set<Entity> getEntites() {
		return entities;
	}

	public static Entity getBall() {
		return ball;
	}

}
