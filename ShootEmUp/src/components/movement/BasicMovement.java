package components.movement;

import java.util.Set;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.collision.HitCollision;
import components.graphical.BaseGraphics;
import main.Loop;
import math.Vector2;
import math.Vector4;
import object.Entity;
import object.EntityMap;

public class BasicMovement extends BaseMovement {

	protected BaseGraphics baseGraphics;

	public BasicMovement(BaseCollision baseCollision, BaseGraphics baseGraphics, int speed) {
		this.baseGraphics = baseGraphics;
		this.BC = baseCollision;
		this.speed = speed;
		this.realSpeed = speed;
	}

	public boolean checkCollisionX(Entity e) {
		EntityMap eMap = Loop.getCurrentLevel().geteMap();
		boolean collide = false;
		Set<Vector2> newGrid = eMap.getGridPos(e);
		Set<Entity> entities = eMap.getEntites(newGrid);
		Vector4 collVec;
		Entity hit;
		for (Entity character : entities) {
			if (character != e) {
				collVec = doesCollide(e, character);
				if (collVec != null) {
					collide = true;
					hit = character;
					BaseCollision HC = hit.getComponent(TypeComponent.COLLISION);
					if ((HC.getMoveBack()) && !(this.BC instanceof HitCollision)) {
						moveBackX(collVec);
						newGrid = eMap.getGridPos(e);
					}
					if ((e.getComponent(TypeComponent.COLLISION) != null)) {
						this.BC.collision(e, hit);
					}
					BaseCollision EC = hit.getComponent(TypeComponent.COLLISION);
					if (EC != null) {
						EC.collision(hit, e);
					}
				}
			}
		}

		eMap.removeEntity(this.BC.getGridPos(), e);
		if (!e.isDestroy()) {
			eMap.addEntity(newGrid, e);
		}
		this.BC.setGridPos(newGrid);
		return collide;
	}

	public boolean checkCollisionY(Entity e) {
		EntityMap eMap = Loop.getCurrentLevel().geteMap();
		boolean collide = false;
		Set<Vector2> newGrid = eMap.getGridPos(e);
		Set<Entity> entities = eMap.getEntites(newGrid);
		Vector4 collVec;
		Entity hit;
		for (Entity character : entities) {
			if (character != e) {
				collVec = doesCollide(e, character);
				if (collVec != null) {
					collide = true;
					hit = character;
					BaseCollision HC = hit.getComponent(TypeComponent.COLLISION);
					if ((HC.getMoveBack()) && !(this.BC instanceof HitCollision)) {
						moveBackY(collVec);
						newGrid = eMap.getGridPos(e);
					}
					if ((e.getComponent(TypeComponent.COLLISION) != null)) {
						this.BC.collision(e, hit);
					}
					BaseCollision EC = hit.getComponent(TypeComponent.COLLISION);
					if (EC != null) {
						EC.collision(hit, e);
					}
				}
			}
		}

		eMap.removeEntity(this.BC.getGridPos(), e);
		if (!e.isDestroy()) {
			eMap.addEntity(newGrid, e);
		}
		this.BC.setGridPos(newGrid);
		return collide;
	}

	@Override
	public Vector4 collideFunction(BaseGraphics BG, float x, float y) {
		if (((x >= BG.getX()) && (x <= (BG.getX() + BG.getWidth())))
				&& ((y >= BG.getY()) && (y <= (BG.getY() + BG.getHeight())))) {
			return new Vector4(x - BG.getX(), y - BG.getY(), x - (BG.getX() + BG.getWidth()),
					y - (BG.getY() + BG.getHeight()));
		}
		return null;
	}

	@Override
	public Vector4 doesCollide(Entity moving, Entity checked) {
		BaseGraphics CG = checked.getComponent(TypeComponent.GRAPHICS);
		float x = this.baseGraphics.getX();
		float y = this.baseGraphics.getY();
		float w = this.baseGraphics.getWidth();
		float h = this.baseGraphics.getHeight();

		float cx = CG.getX();
		float cy = CG.getY();
		float cw = CG.getWidth();
		float ch = CG.getHeight();

		if (((x < (cx + cw)) && ((x + w) > cx) && (y < (cy + ch)) && ((y + h) > cy))) {
			return new Vector4(x - (cx + cw), y - (cy + ch), (x + w) - cx, (y + h) - cy);
		}
		return null;

	}

	@Override
	public void move(Entity e, Vector2 moveVec) {
		super.move(e, moveVec);
		if (Math.abs(moveVec.x()) > 0) {
			this.baseGraphics.setX(this.baseGraphics.getX() + Math.round(moveVec.x() * this.speed));
			checkCollisionX(e);
		}
		if (Math.abs(moveVec.y()) > 0) {
			this.baseGraphics.setY(this.baseGraphics.getY() + Math.round(moveVec.y() * this.speed));
			checkCollisionY(e);
		}
	}

	public void moveBackX(Vector4 collVec) {
		if (Math.abs(collVec.x()) <= this.speed) {
			this.baseGraphics.setX(this.baseGraphics.getX() - collVec.x());
		} else if (Math.abs(collVec.z()) <= this.speed) {
			this.baseGraphics.setX(this.baseGraphics.getX() - collVec.z());
		}
	}

	public void moveBackY(Vector4 collVec) {
		if (Math.abs(collVec.y()) <= this.speed) {
			this.baseGraphics.setY(this.baseGraphics.getY() - collVec.y());
		} else if (Math.abs(collVec.w()) <= this.speed) {
			this.baseGraphics.setY(this.baseGraphics.getY() - collVec.w());
		}
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}
}
