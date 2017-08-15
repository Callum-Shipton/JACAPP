package main;
import display.Art;
import game.Game;
import loop.Loop;

public class Pong implements Game{
	
	public static void main(String[] args) {
		Loop loop = new Loop(new Pong(), 60.0f, new Art());
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
