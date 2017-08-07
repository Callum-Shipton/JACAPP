package test.components;

import java.util.Set;

import org.joml.Vector2f;
import org.joml.Vector4f;

import components.TypeComponent;
import components.collision.BaseCollision;
import components.collision.HitCollision;
import components.graphical.BaseGraphics;
import components.movement.GroundMovement.Axis;
import entity.Entity;
import main.ShootEmUp;
import math.VectorMath;
import object.EntityMap;

public class GroundMovementTest extends BaseMovementTest{
	
	public GroundMovementTest(int speed) {
		super(speed);
	}
	
	public GroundMovementTest(GroundMovementTest gm) {
		this(gm.speed);
	}
	
	@Override
	public boolean equals(Object o) {
	
		return this.speed == ((GroundMovementTest) o).speed;
		
	}

	private Set<Vector2f> reactToCollision(Vector4f collVec, Entity hitEntity, Entity currentEntity, Axis axis,
			EntityMap eMap) {
		Set<Vector2f> newGrid = eMap.getGridPos(currentEntity);
		BaseCollision BC = currentEntity.getComponent(TypeComponent.COLLISION);
		BaseCollision HC = hitEntity.getComponent(TypeComponent.COLLISION);
		BaseGraphics BG = currentEntity.getComponent(TypeComponent.GRAPHICS);
		
		if ((HC.getMoveBack()) && !(BC instanceof HitCollision)) {
			switch (axis) {
			case X:
				moveBackX(collVec, BG);
				break;
			case Y:
				moveBackY(collVec, BG);
			}
			newGrid = eMap.getGridPos(currentEntity);
		}
		if (currentEntity.getComponent(TypeComponent.COLLISION) != null) {
			BC.collision(currentEntity, hitEntity);
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

		BaseCollision BC = currentEntity.getComponent(TypeComponent.COLLISION);
		
		eMap.removeEntity(BC.getGridPos(), currentEntity);
		if (!currentEntity.isDestroy()) {
			eMap.addEntity(newGrid, currentEntity);
		}
		BC.setGridPos(newGrid);
		return collide;
	}

	@Override
	public Vector4f doesCollide(Entity moving, Entity checked) {
		BaseGraphics BG = moving.getComponent(TypeComponent.GRAPHICS);
		Vector4f mov = BG.getBox();

		BaseGraphics CG = checked.getComponent(TypeComponent.GRAPHICS);
		Vector4f check = CG.getBox();

		return VectorMath.contains(mov, check);

	}

	@Override
	public void move(Entity e, Vector2f moveVec) {
		super.move(e, moveVec);

		BaseGraphics BG = e.getComponent(TypeComponent.GRAPHICS);

		if (Math.abs(moveVec.x()) > 0) {
			BG.addToX(Math.round(moveVec.x() * speed));
			checkCollision(e, Axis.X);
		}
		if (Math.abs(moveVec.y()) > 0) {
			BG.addToY(Math.round(moveVec.y() * speed));
			checkCollision(e, Axis.Y);
		}
	}

	public void moveBackX(Vector4f collVec, BaseGraphics BG) {
		if (Math.abs(collVec.x()) <= speed) {
			BG.takeFromX(collVec.x());
		} else if (Math.abs(collVec.z()) <= speed) {
			BG.takeFromX(collVec.z());
		}
	}

	public void moveBackY(Vector4f collVec, BaseGraphics BG) {
		if (Math.abs(collVec.y()) <= speed) {
			BG.takeFromY(collVec.y());
		} else if (Math.abs(collVec.w()) <= speed) {
			BG.takeFromY(collVec.w());
		}
	}

	public enum Axis {
		X, Y
	}
}