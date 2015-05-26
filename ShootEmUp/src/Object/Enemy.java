package Object;

import java.util.ArrayList;
import java.util.HashSet;
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
			if (target.y() < posY) {
				if(target.y() - posY > -speed){
					movement.add(0.0f, ((1 / speed) * target.y() - posY));
				} else {
					movement.add(0.0f, -1.0f);
				}
			}
			if (target.x() < posX) {
				if(target.x() - posX > -speed){
					movement.add(((1 / speed) * target.x() - posX), 0.0f);
				} else {
					movement.add(-1.0f, 0.0f);
				}
			}
			if (target.y() > posY) {
				if(target.y() - posY < speed){
					movement.add(0.0f, ((1 / speed) * target.y() - posY));
				} else {
					movement.add(0.0f, 1.0f);
				}
			}
			if (target.x() > posX) {
				if(target.x() - posX < speed){
					movement.add(((1 / speed) * target.x() - posX), 0.0f);
				} else {
					movement.add(1.0f, 0.0f);
				}
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
		HashSet<Vector2> closed = new HashSet<Vector2>(); //list of already viewed tiles
		Tile start = new Tile((float)Math.floor(posX / 32),(float)Math.floor(posY / 32), null); //makes a tile for the enemy position
		Tile goal = new Tile((float)Math.floor(ShootEmUp.currentLevel.getPlayer().getX() / 32),(float)Math.floor(ShootEmUp.currentLevel.getPlayer().getY() / 32), null); // makes a tile for the player
		open.add(start);
		closed.add(new Vector2(start.getX(),start.getY()));
		
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
			Vector2 N = new Vector2(current.getX(), current.getY()-1);
			Vector2 NW = new Vector2(current.getX()-1, current.getY()-1);
			Vector2 W = new Vector2(current.getX()-1, current.getY());
			Vector2 SW = new Vector2(current.getX()-1, current.getY()+1);
			Vector2 SSW = new Vector2(current.getX()-1, current.getY()+2);
			Vector2 S = new Vector2(current.getX(), current.getY()+1);
			Vector2 SS = new Vector2(current.getX(), current.getY()+2);
			Vector2 SSE = new Vector2(current.getX()+1, current.getY()+2);
			Vector2 SSEE = new Vector2(current.getX()+2, current.getY()+2);
			Vector2 SE = new Vector2(current.getX()+1, current.getY()+1);
			Vector2 SEE = new Vector2(current.getX()+2, current.getY()+1);
			Vector2 EE = new Vector2(current.getX()+2, current.getY());
			Vector2 NEE = new Vector2(current.getX()+2, current.getY()-1);
			Vector2 E = new Vector2(current.getX()+1, current.getY());
			Vector2 NE = new Vector2(current.getX()+1, current.getY()-1);
			
			if(!closed.contains(N)){
				if(ShootEmUp.currentLevel.getWall(N) == null && ShootEmUp.currentLevel.getWall(NE) == null ){
					open.add(new Tile(current.getX(), current.getY() - 1, current));
					closed.add(N);
				}
			}
			if(!closed.contains(NW)){
				if(ShootEmUp.currentLevel.getWall(NW) == null && ShootEmUp.currentLevel.getWall(N) == null && ShootEmUp.currentLevel.getWall(W) == null && (ShootEmUp.currentLevel.getWall(SW) == null && ShootEmUp.currentLevel.getWall(NE) == null) ){
					open.add(new Tile(current.getX()-1, current.getY() - 1, current));
					closed.add(NW);
				}
			}
			if(!closed.contains(W)){
				if(ShootEmUp.currentLevel.getWall(W) == null && ShootEmUp.currentLevel.getWall(SW) == null ){
					open.add(new Tile(current.getX()-1, current.getY(), current));
					closed.add(W);
				}
			}
			if(!closed.contains(SW)){
				if(ShootEmUp.currentLevel.getWall(SW) == null && ShootEmUp.currentLevel.getWall(SSW) == null && ShootEmUp.currentLevel.getWall(SS) == null && (ShootEmUp.currentLevel.getWall(W) == null && ShootEmUp.currentLevel.getWall(SSE) == null) ){
					open.add(new Tile(current.getX()-1, current.getY() + 1, current));
					closed.add(SW);
				}
			}
			if(!closed.contains(S)){
				if(ShootEmUp.currentLevel.getWall(SS) == null && ShootEmUp.currentLevel.getWall(SSE) == null ){
					open.add(new Tile(current.getX(), current.getY() + 1, current));
					closed.add(S);
				}
			}
			if(!closed.contains(SE)){
				if(ShootEmUp.currentLevel.getWall(SSE) == null && ShootEmUp.currentLevel.getWall(SSEE) == null && ShootEmUp.currentLevel.getWall(SEE) == null && (ShootEmUp.currentLevel.getWall(EE) == null && ShootEmUp.currentLevel.getWall(SS) == null) ){
					open.add(new Tile(current.getX()+1, current.getY() + 1, current));
					closed.add(SE);
				}
			}
			if(!closed.contains(E)){
				if(ShootEmUp.currentLevel.getWall(EE) == null && ShootEmUp.currentLevel.getWall(SEE) == null ){
					open.add(new Tile(current.getX()+1, current.getY(), current));
					closed.add(E);
				}
			}
			if(!closed.contains(NE)){
				if(ShootEmUp.currentLevel.getWall(NE) == null && ShootEmUp.currentLevel.getWall(NEE) == null && ShootEmUp.currentLevel.getWall(EE) == null && (ShootEmUp.currentLevel.getWall(SEE) == null && ShootEmUp.currentLevel.getWall(N) == null) ){
					open.add(new Tile(current.getX()+1, current.getY() - 1, current));
					closed.add(NE);
				}
			}
			
			//check if children have been used before
			
		}
		
		return null;
	}
}
