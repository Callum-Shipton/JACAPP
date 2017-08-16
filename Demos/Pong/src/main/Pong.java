package main;
import game.Game;
import loop.GameLoop;

public class Pong implements Game{
	
	private static final float FPS = 60.0f;
	private static final int SCREEN_WIDTH = 512;
	private static final int SCREEN_HEIGHT = 512;
	
	public static void main(String[] args) {
		GameLoop loop = new GameLoop(new Pong(), FPS, SCREEN_WIDTH, SCREEN_HEIGHT);
		loop.run();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
