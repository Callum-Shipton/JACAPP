package components.collision;

import java.util.Random;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.movement.BaseMovement;
import entity.Entity;
import object.Element;

public class HitCollision extends BaseCollision {

	private Element element;
	private transient int team;
	private int damage;

	public HitCollision(Element element, int damage) {

		moveBack = false;

		this.element = element;
		this.damage = damage;
	}

	public HitCollision(HitCollision hitCollision) {
		this(hitCollision.element, hitCollision.damage);
	}

	@Override
	public void collision(Entity e, Entity hit) {

		BaseAttack hitAttack = hit.getComponent(TypeComponent.ATTACK);
		BaseMovement hitMove = hit.getComponent(TypeComponent.MOVEMENT);
		if (hitAttack != null) {
			if (hitAttack.getWeapon().getTeam() == team) {
				e.destroy();
				return;
			}
		}
		if (hit.getComponent(TypeComponent.COLLISION) instanceof RigidCollision) {
			e.destroy();
			if (hitAttack != null) {
				hitAttack.damage(damage, hit);
				Random rand = new Random();
				int prob = rand.nextInt(3);
				if (prob == 0 && element != null) {
					switch (element) {
					case FIRE:
						hitAttack.setFire(true);
						break;
					case ICE:
						hitMove.setFrost(true);
						break;
					case EARTH:
						hitAttack.setPoison(true);
						break;
					}
				}
			}
		}
	}

	public void setTeam(int team) {
		this.team = team;
	}

	@Override
	public void update() {
	}
}
