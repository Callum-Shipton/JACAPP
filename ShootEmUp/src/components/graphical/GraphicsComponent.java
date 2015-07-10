package components.graphical;

import entities.Entity;

public interface GraphicsComponent {
	
	void update(Entity e);
	
	void render(Entity e);
	
}
