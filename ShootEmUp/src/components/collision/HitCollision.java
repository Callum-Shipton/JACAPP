package components.collision;

import java.util.Random;

import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.movement.BaseMovement;
import entity.Entity;
import object.Element;
import object.Weapon;

public class HitCollision extends BaseCollision {

	private Weapon weapon;

	public HitCollision(Weapon weapon) {
		this.weapon = weapon;
		this.moveBack = false;
	}

	@Override
	public void collision(Entity e, Entity hit) {

		BaseAttack hitAttack = hit.getComponent(TypeComponent.ATTACK);
		BaseMovement hitMove = hit.getComponent(TypeComponent.MOVEMENT);
		if (hitAttack != null) {
			if (hitAttack.getWeapon().getTeam() == this.weapon.getTeam()) {
				e.destroy();
				return;
			}
		}
		if (hit.getComponent(TypeComponent.COLLISION) instanceof RigidCollision) {
			e.destroy();
			if (hitAttack != null) {
				hitAttack.damage(this.weapon.getDamage(), hit);
				if (this.weapon.getElement() == Element.FIRE) {
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if (prob == 0) {
						hitAttack.setFire(true);
					}
				}
				if (this.weapon.getElement() == Element.ICE) {
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if (prob == 0) {
						hitMove.setFrost(true);
					}
				}
				if (this.weapon.getElement() == Element.EARTH) {
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if (prob == 0) {
						hitAttack.setPoison(true);
					}
				}
			}
		}
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
