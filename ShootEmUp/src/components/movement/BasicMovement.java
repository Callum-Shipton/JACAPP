package components.movement;

import java.util.Set;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.collision.HitCollision;
import components.graphical.BaseGraphics;
import entity.Entity;
import main.ShootEmUp;
import math.Vector2;
import math.Vector4;
import object.EntityMap;

public class BasicMovement extends BaseMovement {

	protected BaseGraphics baseGraphics;

	public BasicMovement(BaseCollision baseCollision, BaseGraphics baseGraphics, int speed) {
		this.baseGraphics = baseGraphics;
		this.BC = baseCollision;
		this.speed = speed;
		this.realSpeed = speed;
	}

	private Set<Vector2> reactToCollision(Vector4 collVec, Entity hitEntity, Entity currentEntity, Axis axis,
			EntityMap eMap) {
		Set<Vector2> newGrid = eMap.getGridPos(currentEntity);
		BaseCollision HC = hitEntity.getComponent(TypeComponent.COLLISION);
		if ((HC.getMoveBack()) && !(this.BC instanceof HitCollision)) {
			switch (axis) {
			case X:
				moveBackX(collVec);
				break;
			case Y:
				moveBackY(collVec);
			}
			newGrid = eMap.getGridPos(currentEntity);
		}
		if ((currentEntity.getComponent(TypeComponent.COLLISION) != null)) {
			this.BC.collision(currentEntity, hitEntity);
		}
		BaseCollision EC = hitEntity.getComponent(TypeComponent.COLLISION);
		if (EC != null) {
			EC.collision(hitEntity, currentEntity);
		}

		return newGrid;
	}

	public boolean checkCollision(Entity currentEntity, Axis axis) {
		EntityMap eMap = ShootEmUp.getGame().getCurrentLevel().geteMap();
		boolean collide = false;
		Set<Vector2> newGrid = eMap.getGridPos(currentEntity);
		Set<Entity> entities = eMap.getEntites(newGrid);
		Vector4 collVec;

		for (Entity entity : entities) {
			if (!entity.equals(currentEntity)) {
				collVec = doesCollide(currentEntity, entity);
				if (collVec != null) {
					collide = true;
					newGrid = reactToCollision(collVec, entity, currentEntity, axis, eMap);
				}
			}
		}

		eMap.removeEntity(BC.getGridPos(), currentEntity);
		if (!currentEntity.isDestroy()) {
			eMap.addEntity(newGrid, currentEntity);
		}
		BC.setGridPos(newGrid);
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
		Vector4 mov = baseGraphics.getBox();
		
		BaseGraphics CG = checked.getComponent(TypeComponent.GRAPHICS);
		Vector4 check = CG.getBox();

		return mov.contains(check);

	}

	@Override
	public void move(Entity e, Vector2 moveVec) {
		super.move(e, moveVec);
		if (Math.abs(moveVec.x()) > 0) {
			baseGraphics.setX(baseGraphics.getX() + Math.round(moveVec.x() * speed));
			checkCollision(e, Axis.X);
		}
		if (Math.abs(moveVec.y()) > 0) {
			baseGraphics.setY(baseGraphics.getY() + Math.round(moveVec.y() * speed));
			checkCollision(e, Axis.Y);
		}
	}

	public void moveBackX(Vector4 collVec) {
		if (Math.abs(collVec.x()) <= speed) {
			baseGraphics.setX(baseGraphics.getX() - collVec.x());
		} else if (Math.abs(collVec.z()) <= speed) {
			baseGraphics.setX(baseGraphics.getX() - collVec.z());
		}
	}

	public void moveBackY(Vector4 collVec) {
		if (Math.abs(collVec.y()) <= speed) {
			baseGraphics.setY(baseGraphics.getY() - collVec.y());
		} else if (Math.abs(collVec.w()) <= speed) {
			baseGraphics.setY(baseGraphics.getY() - collVec.w());
		}
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}

	public enum Axis {
		X, Y
	}
}
