package components.control;

import components.Message;
import components.TypeComponent;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class HomingControl extends BaseControl {

	private BaseMovement BM;
	private AnimatedGraphics AG;

	private int counter = 0;
	private Entity target;

	// private Vector2 target;

	public HomingControl(AnimatedGraphics AG, BaseMovement BM) {
		this.AG = AG;
		this.BM = BM;
	}

	@Override
	public void update(Entity e) {
		target = ShootEmUp.currentLevel.getPlayer();
		BaseGraphics BG = ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS);
		float y = BG.getY();
		float x = BG.getX();

		if (target != null) {
			Vector2 movement = new Vector2(0.0f, 0.0f);
			if (y < AG.getY()) {
				if ((y - AG.getY()) > -BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - AG.getY())));
				} else {
					movement.add(0.0f, -1.0f);
				}
			}
			if (x < AG.getX()) {
				if ((x - AG.getX()) > -BM.getSpeed()) {
					movement.add(((1.0f / BM.getSpeed()) * (x - AG.getX())), 0.0f);
				} else {
					movement.add(-1.0f, 0.0f);
				}
			}
			if (y > AG.getY()) {
				if ((y - AG.getY()) < BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - AG.getY())));
				} else {
					movement.add(0.0f, 1.0f);
				}
			}
			if (x > AG.getX()) {
				if ((x - AG.getX()) < BM.getSpeed()) {
					movement.add(((1.0f / BM.getSpeed()) * (x - AG.getX())), 0.0f);
				} else {
					movement.add(1.0f, 0.0f);
				}
			}
			if (movement.length() > 0) {
				if (movement.length() > 1) {
					movement.normalize();
				}
				AG.setAnimating(true);
				BM.move(e, movement);
				AG.setDirection((int) (Math.round(movement.Angle()) / 45));
			} else {
				AG.setAnimating(false);
			}
		}
		counter++;
		if (counter == 30) {
			// weapon.attack(AG.getX(), AG.getY(), getDirection(), getTeam());
			counter = 0;
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}
}