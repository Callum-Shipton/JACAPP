package components.graphical;

import object.Entity;
import components.Component;
import components.TypeComponent;
import display.Image;
import display.Renderer;

public abstract class BaseGraphics extends Component implements GraphicsComponent {

	protected TypeComponent type = TypeComponent.GRAPHICS;
	
	protected Renderer r;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected Image image;
	
	@Override
	public abstract void update(Entity e);
	
	@Override
	public abstract void render(Entity e);
	
	public void destroy(Entity e){
		
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public TypeComponent getType(){
		return type;
	}

}
