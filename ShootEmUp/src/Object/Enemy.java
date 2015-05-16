package Object;

import java.util.PriorityQueue;

import Main.ShootEmUp;
import Math.Vector2;

public class Enemy extends Character {

	private int counter = 0;
	private Vector2 target = new Vector2();
	public Enemy(float x, float y) {
		super(x, y);
	}
	
	public void update() {
		super.update();
		checkDead();
		
		target = ai();
		Vector2 movement = new Vector2(0.0f, 0.0f);
		if (target.y() < posY) {
			movement.add(0.0f, -1.0f);
		}
		if (target.x() < posX) {
			movement.add(-1.0f, 0.0f);
		}
		if (target.y() > posY) {
			movement.add(0.0f, 1.0f);
		}
		if (target.x() > posX) {
			movement.add(1.0f, 0.0f);
		}
		if (movement.length() > 0) {
			if (movement.length() > 1)
				movement.normalize();
			animating = true;
			move(movement);
			direction = (int) (Math.round(movement.Angle()) / 45);
		}
		else animating = false;
		counter++;
		if (counter == 30){
			weapon.shoot(posX, posY, direction, team);
			counter = 0;
		}
	}
	
	public void checkDead() {
		if (health <= 0) {
			ShootEmUp.currentLevel.characters.remove(this);
			ShootEmUp.currentLevel.experience.add(new Exp(posX, posY));
			ShootEmUp.currentLevel.coins.add(new Coin(posX + 32, posY + 32));
		}
	}
	
	public Vector2 ai(){
		PriorityQueue<Tile> queue = new PriorityQueue<Tile>();
		Tile start = new Tile((float)Math.floor(posX/64), (float)Math.floor(posY/64));
		boolean flagged[][] = new boolean[81][81];
		Tile current = null;
		Tile next = null;
		boolean found = false;
		queue.add(start);
		while(!found){
			current = queue.poll();
			if(Math.abs(current.getX() - start.getX()) == 1 || Math.abs(current.getY() - start.getY()) == 1){
				next = new Tile(current);
			}
			if(current == null){
				System.out.println("Cannot Find Player");
				return new Vector2(0,0);
			}
			if(current.getX() > 0 && current.getX() < 81 && current.getY() > 0 && current.getY() < 81){
				if(ShootEmUp.currentLevel.wallTiles[(int)(current.getX() + 1)][(int)current.getY()] == null){
					if(flagged[(int) (current.getX() + 1)][(int) current.getY()] == false){
						queue.add(new Tile((current.getX() + 1),(current.getY())));
						flagged[(int) (current.getX() + 1)][(int) current.getY()] = true;
					}
				}
				if(ShootEmUp.currentLevel.wallTiles[(int)(current.getX() + 1)][(int)current.getY() + 1] == null){
					if(flagged[(int) (current.getX() + 1)][(int) current.getY() + 1] == false){
						queue.add(new Tile((current.getX() + 1),(current.getY() + 1)));
						flagged[(int) (current.getX() + 1)][(int) current.getY() + 1] = true;
					}
				} 
				if(ShootEmUp.currentLevel.wallTiles[(int)(current.getX())][(int)current.getY() + 1] == null){
					if(flagged[(int) (current.getX())][(int) current.getY() + 1] == false){
						queue.add(new Tile((current.getX() + 1),(current.getY() + 1)));
						flagged[(int) (current.getX())][(int) current.getY() + 1] = true;
					}
				}
				if(ShootEmUp.currentLevel.wallTiles[(int)(current.getX() - 1)][(int)current.getY() + 1] == null){
					if(flagged[(int) (current.getX() - 1)][(int) current.getY() + 1] == false){
						queue.add(new Tile((current.getX() - 1),(current.getY() + 1)));
						flagged[(int) (current.getX() - 1)][(int) current.getY() + 1] = true;
					}
				}
				if(ShootEmUp.currentLevel.wallTiles[(int)(current.getX() - 1)][(int)current.getY()] == null){
					if(flagged[(int) (current.getX() - 1)][(int) current.getY()] == false){
						queue.add(new Tile((current.getX() - 1),(current.getY())));
						flagged[(int) (current.getX() - 1)][(int) current.getY()] = true;
					}
				}
				if(ShootEmUp.currentLevel.wallTiles[(int)(current.getX() - 1)][(int)current.getY() - 1] == null){
					if(flagged[(int) (current.getX() - 1)][(int) current.getY() - 1] == false){
						queue.add(new Tile((current.getX() - 1),(current.getY() - 1)));
						flagged[(int) (current.getX() - 1)][(int) current.getY() - 1] = true;
					}
				}
				if(ShootEmUp.currentLevel.wallTiles[(int)(current.getX())][(int)current.getY() - 1] == null){
					if(flagged[(int) (current.getX())][(int) current.getY() - 1] == false){
						queue.add(new Tile((current.getX()),(current.getY() - 1)));
						flagged[(int) (current.getX())][(int) current.getY() -1] = true;
					}
				}
				if(ShootEmUp.currentLevel.wallTiles[(int)(current.getX() + 1)][(int)current.getY() - 1] == null){
					if(flagged[(int) (current.getX() + 1)][(int) current.getY() - 1] == false){
						queue.add(new Tile((current.getX() + 1),(current.getY() - 1)));
						flagged[(int) (current.getX() + 1)][(int) current.getY() - 1] = true;
					}
				}
			}
			if((current.getX() == (Math.floor(ShootEmUp.currentLevel.getPlayer().getX() / 64))) && (current.getY() == (Math.floor(ShootEmUp.currentLevel.getPlayer().getY() / 64)))){
				found = true;
			}
		}
		queue.clear();
		return new Vector2(next.getX()*64 + 32, next.getY()*64 + 32);
	}
}
