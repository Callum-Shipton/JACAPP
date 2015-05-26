package Object;

import java.util.ArrayList;
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
		if (checkDead()) return;
		
		target = ai();
		
		if(target != null){
			Vector2 movement = new Vector2(0.0f, 0.0f);
			if (target.y() - posY < (float)(speed * -1)) {
				movement.add(0.0f, -1.0f);
			}
			if (target.x() - posX < (float)(speed * -1)) {
				movement.add(-1.0f, 0.0f);
			}
			if (target.y() - posY > (float)speed) {
				movement.add(0.0f, 1.0f);
			}
			if (target.x() - posX > (float)speed) {
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
		}
		counter++;
		if (counter == 30){
			weapon.shoot(posX, posY, direction, team);
			counter = 0;
		}
	}
	
	public boolean checkDead() {
		if (health <= 0) {
			destroy = true;
			ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
			ShootEmUp.currentLevel.experience.add(new Exp(posX, posY));
			ShootEmUp.currentLevel.coins.add(new Coin(posX + 32, posY + 32));
			return true;
		}
		return false;
	}
	
	public Vector2 ai(){
		PriorityQueue<Tile> open = new PriorityQueue<Tile>(); //queue for tiles to be looked at
		ArrayList<Tile> closed = new ArrayList<Tile>(); //list of already viewed tiles
		ArrayList<Tile> children = new ArrayList<Tile>();
		Tile start = new Tile((float)Math.floor(posX / 32),(float)Math.floor(posY / 32), null); //makes a tile for the enemy position
		Tile goal = new Tile((float)Math.floor(ShootEmUp.currentLevel.getPlayer().getX() / 32),(float)Math.floor(ShootEmUp.currentLevel.getPlayer().getY() / 32), null); // makes a tile for the player
		open.add(start);
		
		while(open.size() != 0){
			Tile current = open.poll(); //Tile currently being checked
			
			if((current.getX() == goal.getX()) && (current.getY() == goal.getY())){ //if goal is reached
				Tile tile = current;
				while(true){
					if(tile.getParent() == start){
						return tile.getPositionVector();
					} else {
						tile = tile.getParent();
					}
				}
			} 
			
			//add children
			children.add(new Tile(current.getX() + 1, current.getY(), current)); 
			children.add(new Tile(current.getX() + 1, current.getY() + 1, current));
			children.add(new Tile(current.getX(), current.getY() + 1, current));
			children.add(new Tile(current.getX() - 1, current.getY() + 1, current));
			children.add(new Tile(current.getX() - 1, current.getY(), current));
			children.add(new Tile(current.getX() - 1, current.getY() - 1, current));
			children.add(new Tile(current.getX(), current.getY() - 1, current));
			children.add(new Tile(current.getX() + 1, current.getY() - 1, current));
			
			//check if children have been used before
			boolean used;
			for(Tile child : children){
				used = false;
				for(Tile tile : closed){
					if((tile.getX() == child.getX()) && (tile.getY() == child.getY())){
						used = true;
					}
				}
				for(Tile tile : open){
					if((tile.getX() == child.getX()) && (tile.getY() == child.getY())){
						used = true;
					}
				}
				if(used != true){
					if((ShootEmUp.currentLevel.wallTiles[(int)child.getX()][(int)child.getY()] == null) && 
							(ShootEmUp.currentLevel.wallTiles[(int)child.getX()+1][(int)child.getY()] == null) && 
							(ShootEmUp.currentLevel.wallTiles[(int)child.getX()+1][(int)child.getY()+1] == null) &&
							(ShootEmUp.currentLevel.wallTiles[(int)child.getX()][(int)child.getY()+1] == null)){
						open.add(child);
					}
				}
			}
			
			children.clear(); //empties children 
			
			closed.add(current); //adds the current tile to the used tiles list
		}
		
		return null;
	}
}
