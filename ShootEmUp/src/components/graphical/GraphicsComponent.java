package components.graphical;

import object.Entity;

public interface GraphicsComponent {
	
	void update(Entity e);
	
	void render(Entity e);
	
}
