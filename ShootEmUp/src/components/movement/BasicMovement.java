package components.movement;

import java.util.Set;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.collision.HitCollision;
import components.graphical.BaseGraphics;
import main.ShootEmUp;
import math.Vector2;
import math.Vector4;
import object.Entity;
import object.EntityMap;

public class BasicMovement extends BaseMovement {

	protected BaseGraphics BG;

	public BasicMovement(Entity e, BaseCollision BC, BaseGraphics BG, int speed) {
		this.BG = BG;
		this.BC = BC;
		this.speed = speed;
		this.realSpeed = speed;
	}

	public boolean checkCollisionX(Entity e, Vector2 moveVec) {
		EntityMap eMap = ShootEmUp.getCurrentLevel().geteMap();
		boolean collide = false;
		Set<Vector2> newGrid = eMap.getGridPos(e);
		Set<Entity> entities = eMap.getEntites(newGrid);
		Vector4 collVec = null;
		Entity hit = null;
		for (Entity character : entities) {
			if (character != e) {
				collVec = doesCollide(e, character);
				if (collVec != null) {
					collide = true;
					hit = character;
					BaseCollision HC = hit.getComponent(TypeComponent.COLLISION);
					if ((HC.getMoveBack() == true) && !(this.BC instanceof HitCollision)) {
						moveBackX(moveVec, collVec);
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

	public boolean checkCollisionY(Entity e, Vector2 moveVec) {
		EntityMap eMap = ShootEmUp.getCurrentLevel().geteMap();
		boolean collide = false;
		Set<Vector2> newGrid = eMap.getGridPos(e);
		Set<Entity> entities = eMap.getEntites(newGrid);
		Vector4 collVec = null;
		Entity hit = null;
		for (Entity character : entities) {
			if (character != e) {
				collVec = doesCollide(e, character);
				if (collVec != null) {
					collide = true;
					hit = character;
					BaseCollision HC = hit.getComponent(TypeComponent.COLLISION);
					if ((HC.getMoveBack() == true) && !(this.BC instanceof HitCollision)) {
						moveBackY(moveVec, collVec);
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
		float x = this.BG.getX();
		float y = this.BG.getY();
		float w = this.BG.getWidth();
		float h = this.BG.getHeight();

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
			this.BG.setX((this.BG.getX() + Math.round(moveVec.x() * this.speed)));
			checkCollisionX(e, moveVec);
		}
		if (Math.abs(moveVec.y()) > 0) {
			this.BG.setY(this.BG.getY() + Math.round(moveVec.y() * this.speed));
			checkCollisionY(e, moveVec);
		}
	}

	public void moveBackX(Vector2 moveVec, Vector4 collVec) {
		if (Math.abs(collVec.x()) <= this.speed) {
			this.BG.setX(this.BG.getX() - collVec.x());
		} else if (Math.abs(collVec.z()) <= this.speed) {
			this.BG.setX(this.BG.getX() - collVec.z());
		}
	}

	public void moveBackY(Vector2 moveVec, Vector4 collVec) {
		if (Math.abs(collVec.y()) <= this.speed) {
			this.BG.setY(this.BG.getY() - collVec.y());
		} else if (Math.abs(collVec.w()) <= this.speed) {
			this.BG.setY(this.BG.getY() - collVec.w());
		}
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}
}
