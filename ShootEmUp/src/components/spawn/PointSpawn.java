package components.spawn;

import org.joml.Vector2f;

import components.Message;
import components.graphical.BaseGraphics;
import entity.Entity;

public class PointSpawn extends BaseSpawn implements SpawnComponent {

	private Vector2f spawnLoc = new Vector2f(480.0f, 480.0f);
	private BaseGraphics BG;

	public PointSpawn(BaseGraphics BG, Vector2f spawnLoc, Entity e) {
		this.BG = BG;
		this.spawnLoc = spawnLoc;
		spawn(e);
	}

	@Override
	public void receive(Message m, Entity e) {
		if (m == Message.ENTITY_DIED) {
			spawn(e);
		}
	}

	@Override
	public void spawn(Entity e) {
		BG.setX(spawnLoc.x());
		BG.setY(spawnLoc.y());
		e.send(Message.ENTITY_MOVED);
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}
}
