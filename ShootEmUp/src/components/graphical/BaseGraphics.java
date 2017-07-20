package components.graphical;

import org.joml.Vector2f;
import org.joml.Vector4f;

import components.Component;
import components.DataMessage;
import components.Message;
import components.MessageId;
import components.TypeComponent;
import display.Image;
import display.Renderer;
import entity.Entity;

public abstract class BaseGraphics extends Component implements GraphicsComponent {

	protected Renderer r;

	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected Image image;

	public BaseGraphics() {
	
	}

	@Override
	public void destroy(Entity e) {

	}

	public float getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.GRAPHICS;
	}

	public float getWidth() {
		return width;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void addToX(float increment) {
		x = x + increment;
	}

	public void addToY(float increment) {
		y = y + increment;
	}

	public void takeFromX(float decrement) {
		x = x - decrement;
	}

	public void takeFromY(float decrement) {
		y = y - decrement;
	}

	public Vector4f getBox() {
		return new Vector4f(x, y, width, height);
	}

	@Override
	public void receive(Message m, Entity e) {
		if (m instanceof DataMessage) {
			if (m.getId() == MessageId.ENTITY_SPAWN) {
				Vector2f position = ((DataMessage<Vector2f>) m).getData();
				x = position.x();
				y = position.y();
			}
		}
	}
}
