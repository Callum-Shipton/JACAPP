package components.control;

import object.Entity;
import main.ShootEmUp;
import math.Vector2;
import components.TypeComponent;
import components.Message;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;

public class AIControl extends BaseControl{
	
	private BaseMovement BM;
	private BaseGraphics BG;
	private BaseAttack BA;
	
	private int counter = 0;
	private Entity target;
	
	//private Vector2 target;
	
	public AIControl(BaseGraphics BG, BaseAttack BA, BaseMovement BM){
		this.BA = BA;
		this.BG = BG;
		this.BM = BM;
	}
	
	public void update(Entity e) {		
		target = ShootEmUp.currentLevel.getPlayer();
		float y = ((BaseGraphics)target.getComponent(TypeComponent.GRAPHICS)).getY();
		float x = ((BaseGraphics)target.getComponent(TypeComponent.GRAPHICS)).getX();
		
		if(target != null){
			Vector2 movement = new Vector2(0.0f, 0.0f);
			if (y < BG.getY()) {
				if(y - BG.getY() > -BM.getSpeed()){
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - BG.getY())));
				} else {
					movement.add(0.0f, -1.0f);
				}
			}
			if (x < BG.getX()) {
				if(x - BG.getX() > -BM.getSpeed()){
					movement.add(((1.0f / BM.getSpeed()) * (x - BG.getX())), 0.0f);
				} else {
					movement.add(-1.0f, 0.0f);
				}
			}
			if (y > BG.getY()) {
				if(y - BG.getY() < BM.getSpeed()){
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - BG.getY())));
				} else {
					movement.add(0.0f, 1.0f);
				}
			}
			if (x > BG.getX()) {
				if(x - BG.getX() < BM.getSpeed()){
					movement.add(((1.0f / BM.getSpeed()) * (x - BG.getX())), 0.0f);
				} else {
					movement.add(1.0f, 0.0f);
				}
			}
			if (movement.length() > 0) {
				if (movement.length() > 1)
					movement.normalize();
				if(BG instanceof AnimatedGraphics) ((AnimatedGraphics) BG).setAnimating(true);
				BM.move(e, movement);
				if(BG instanceof AnimatedGraphics) ((AnimatedGraphics) BG).setDirection((int) (Math.round(movement.Angle()) / 45));
			}
			else if(BG instanceof AnimatedGraphics) ((AnimatedGraphics) BG).setAnimating(false);
		}
		counter++;
		if (counter == 30){
			BA.attack(e, (BG instanceof AnimatedGraphics) ? ((AnimatedGraphics) BG).getDirection() : 0);
			counter = 0;
		}
	}
	/*
	public Vector2 ai(){
		PriorityQueue<Entity> open = new PriorityQueue<Entity>(); //queue for tiles to be looked at
		HashSet<Vector2> closed = new HashSet<Vector2>(); //list of already viewed tiles
		Entity start = new Tile((float)Math.floor(AG.getX() / 32),(float)Math.floor(AG.getY() / 32), null); //makes a tile for the enemy position
		Entity goal = new Tile((float)Math.floor(((BaseGraphics)ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.GRAPHICS)).getX() / 32),(float)Math.floor(((BaseGraphics)ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.GRAPHICS)).getY() / 32), null); // makes a tile for the player
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
		System.out.println("cannot find player");
		return null;
	}
	
	*/
	@Override
	public void receive(Message m, Entity e) {
		
	}
}
