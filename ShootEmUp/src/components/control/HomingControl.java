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
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Entity e) {
		this.target = ShootEmUp.getPlayer();
		BaseGraphics BG = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		float y = BG.getY();
		float x = BG.getX();

		if (this.target != null) {
			Vector2 movement = new Vector2(0.0f, 0.0f);
			if (y < this.AG.getY()) {
				if ((y - this.AG.getY()) > -this.BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / this.BM.getSpeed()) * (y - this.AG.getY())));
				} else {
					movement.add(0.0f, -1.0f);
				}
			}
			if (x < this.AG.getX()) {
				if ((x - this.AG.getX()) > -this.BM.getSpeed()) {
					movement.add(((1.0f / this.BM.getSpeed()) * (x - this.AG.getX())), 0.0f);
				} else {
					movement.add(-1.0f, 0.0f);
				}
			}
			if (y > this.AG.getY()) {
				if ((y - this.AG.getY()) < this.BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / this.BM.getSpeed()) * (y - this.AG.getY())));
				} else {
					movement.add(0.0f, 1.0f);
				}
			}
			if (x > this.AG.getX()) {
				if ((x - this.AG.getX()) < this.BM.getSpeed()) {
					movement.add(((1.0f / this.BM.getSpeed()) * (x - this.AG.getX())), 0.0f);
				} else {
					movement.add(1.0f, 0.0f);
				}
			}
			if (movement.length() > 0) {
				if (movement.length() > 1) {
					movement.normalize();
				}
				this.AG.setAnimating(true);
				this.BM.move(e, movement);
				this.AG.setDirection((int) (Math.round(movement.Angle()) / 45));
			} else {
				this.AG.setAnimating(false);
			}
		}
		this.counter++;
		if (this.counter == 30) {
			// weapon.attack(AG.getX(), AG.getY(), getDirection(), getTeam());
			this.counter = 0;
		}
	}
}