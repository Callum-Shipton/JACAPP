package components.collision;

import java.util.Random;

import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.movement.BaseMovement;
import main.ShootEmUp;
import object.Element;
import object.Entity;
import object.Weapon;

public class HitCollision extends BaseCollision {

	Weapon weapon;

	public HitCollision(Entity e, Weapon weapon) {
		this.weapon = weapon;
		moveBack = false;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
	}

	@Override
	public void collision(Entity e, Entity hit) {

		BaseAttack hitAttack = hit.getComponent(TypeComponent.ATTACK);
		BaseMovement hitMove = hit.getComponent(TypeComponent.MOVEMENT);
		if (hitAttack != null) {
			if (hitAttack.getWeapon().getTeam() == weapon.getTeam()) {
				return;
			}
		}
		if (hit.getComponent(TypeComponent.COLLISION) instanceof RigidCollision) {
			e.destroy();
			if (hitAttack != null) {
				hitAttack.damage(weapon.getDamage(), hit);
				if (weapon.getElement() == Element.FIRE) {
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if (prob == 0) {
						hitAttack.setFire(true);
					}
				}
				if (weapon.getElement() == Element.ICE) {
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if (prob == 0) {
						hitMove.setFrost(true);
					}
				}
				if (weapon.getElement() == Element.EARTH) {
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if (prob == 0) {
						hitAttack.setPoison(true);
					}
				}
			}
		}
		/*
		 * if (hit != null && getTeam() != hit.getTeam()) {
		 * hit.damage(weapon.getDamage()); }
		 */
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}

}
