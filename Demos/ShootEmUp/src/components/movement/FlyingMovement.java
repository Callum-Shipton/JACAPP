package components.movement;

public class FlyingMovement extends GroundMovement {

	public FlyingMovement(int speed) {
		super(speed);
	}

	public FlyingMovement(FlyingMovement flyingMovement) {
		this(flyingMovement.speed);
	}
}
