package Components.Movement;

import Components.Graphical.BaseGraphics;
import Math.Vector2;
import Math.Vector4;
import Object.Entity;

public interface MovementComponent {

    void move(Entity e, Vector2 moveVec);
	Vector4 doesCollide(Entity moving, Entity checked);
	Vector4 collideFunction(BaseGraphics BG, float x, float y);

}

