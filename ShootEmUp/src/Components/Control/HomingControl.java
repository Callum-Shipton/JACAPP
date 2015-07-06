package Components.Control;

import Components.ComponentType;
import Components.Message;
import Components.Graphical.AnimatedGraphics;
import Components.Graphical.BaseGraphics;
import Components.Movement.BaseMovement;
import Main.ShootEmUp;
import Math.Vector2;
import Object.Entity;

public class HomingControl extends BaseControl{
	
	private BaseMovement BM;
	private AnimatedGraphics AG;
	
	private int counter = 0;
	private Entity target;
	
	//private Vector2 target;
	
	public HomingControl(AnimatedGraphics AG, BaseMovement BM){
		this.AG = AG;
		this.BM = BM;
	}
	
	public void update(Entity e) {		
		target = ShootEmUp.currentLevel.getPlayer();
		float y = ((BaseGraphics)target.getComponent(ComponentType.GRAPHICS)).getY();
		float x = ((BaseGraphics)target.getComponent(ComponentType.GRAPHICS)).getX();
		
		if(target != null){
			Vector2 movement = new Vector2(0.0f, 0.0f);
			if (y < AG.getY()) {
				if(y - AG.getY() > -BM.getSpeed()){
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - AG.getY())));
				} else {
					movement.add(0.0f, -1.0f);
				}
			}
			if (x < AG.getX()) {
				if(x - AG.getX() > -BM.getSpeed()){
					movement.add(((1.0f / BM.getSpeed()) * (x - AG.getX())), 0.0f);
				} else {
					movement.add(-1.0f, 0.0f);
				}
			}
			if (y > AG.getY()) {
				if(y - AG.getY() < BM.getSpeed()){
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - AG.getY())));
				} else {
					movement.add(0.0f, 1.0f);
				}
			}
			if (x > AG.getX()) {
				if(x - AG.getX() < BM.getSpeed()){
					movement.add(((1.0f / BM.getSpeed()) * (x - AG.getX())), 0.0f);
				} else {
					movement.add(1.0f, 0.0f);
				}
			}
			if (movement.length() > 0) {
				if (movement.length() > 1)
					movement.normalize();
				AG.setAnimating(true);
				BM.move(e, movement);
				AG.setDirection((int) (Math.round(movement.Angle()) / 45));
			}
			else AG.setAnimating(false);
		}
		counter++;
		if (counter == 30){
			//weapon.attack(AG.getX(), AG.getY(), getDirection(), getTeam());
			counter = 0;
		}
	}
	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
}