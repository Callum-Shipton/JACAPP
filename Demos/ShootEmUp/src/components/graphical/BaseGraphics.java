package components.graphical;

import org.joml.Vector2f;
import org.joml.Vector4f;

import components.Component;
import components.DataMessage;
import components.Message;
import components.MessageId;
import components.TypeComponent;
import components.interfaces.GraphicsComponent;
import display.Art;
import display.Image;
import display.ImageProcessor;
import display.Renderer;

public abstract class BaseGraphics extends Component implements GraphicsComponent {

	protected transient Renderer r;

	protected transient float x;
	protected transient float y;
	protected transient float width;
	protected transient float height;
	protected transient Image image;
	protected String imageId;

	public BaseGraphics(String imageId, Renderer r) {
		this.imageId = imageId;
		this.r = r;

		image = Art.getImage(imageId);
		width = image.getWidth() / image.getFWidth();
		height = image.getHeight() / image.getFHeight();
	}

	@Override
	public void destroy() {

	}

	public float getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}

	public String getImageId() {
		return imageId;
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
	public void receive(Message m) {
		if (m instanceof DataMessage) {
			if (m.getId() == MessageId.ENTITY_SPAWN) {
				Vector2f position = ((DataMessage<Vector2f>) m).getData();
				x = position.x();
				y = position.y();
			}
		}
	}
}
