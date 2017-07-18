package components.movement;

import java.util.Set;

import org.joml.Vector2f;
import org.joml.Vector4f;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.collision.HitCollision;
import components.graphical.BaseGraphics;
import entity.Entity;
import main.ShootEmUp;
import math.VectorMath;
import object.EntityMap;

public class GroundMovement extends BaseMovement {

	protected BaseGraphics baseGraphics;

	public GroundMovement(BaseCollision baseCollision, BaseGraphics baseGraphics, int speed) {
		this.baseGraphics = baseGraphics;
		this.BC = baseCollision;
		this.speed = speed;
		this.realSpeed = speed;
	}

	private Set<Vector2f> reactToCollision(Vector4f collVec, Entity hitEntity, Entity currentEntity, Axis axis,
			EntityMap eMap) {
		Set<Vector2f> newGrid = eMap.getGridPos(currentEntity);
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
		Set<Vector2f> newGrid = eMap.getGridPos(currentEntity);
		Set<Entity> entities = eMap.getEntites(newGrid);
		Vector4f collVec;

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
	public Vector4f collideFunction(BaseGraphics BG, float x, float y) {
		if (((x >= BG.getX()) && (x <= (BG.getX() + BG.getWidth())))
				&& ((y >= BG.getY()) && (y <= (BG.getY() + BG.getHeight())))) {
			return new Vector4f(x - BG.getX(), y - BG.getY(), x - (BG.getX() + BG.getWidth()),
					y - (BG.getY() + BG.getHeight()));
		}
		return null;
	}

	@Override
	public Vector4f doesCollide(Entity moving, Entity checked) {
		Vector4f mov = baseGraphics.getBox();

		BaseGraphics CG = checked.getComponent(TypeComponent.GRAPHICS);
		Vector4f check = CG.getBox();

		return VectorMath.contains(mov, check);

	}

	@Override
	public void move(Entity e, Vector2f moveVec) {
		super.move(e, moveVec);
		if (Math.abs(moveVec.x()) > 0) {
			baseGraphics.addToX(Math.round(moveVec.x() * speed));
			checkCollision(e, Axis.X);
		}
		if (Math.abs(moveVec.y()) > 0) {
			baseGraphics.addToY(Math.round(moveVec.y() * speed));
			checkCollision(e, Axis.Y);
		}
	}

	public void moveBackX(Vector4f collVec) {
		if (Math.abs(collVec.x()) <= speed) {
			baseGraphics.takeFromX(collVec.x());
		} else if (Math.abs(collVec.z()) <= speed) {
			baseGraphics.takeFromX(collVec.z());
		}
	}

	public void moveBackY(Vector4f collVec) {
		if (Math.abs(collVec.y()) <= speed) {
			baseGraphics.takeFromY(collVec.y());
		} else if (Math.abs(collVec.w()) <= speed) {
			baseGraphics.takeFromY(collVec.w());
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
