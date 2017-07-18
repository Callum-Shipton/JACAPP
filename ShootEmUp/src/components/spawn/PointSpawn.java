package components.spawn;

import org.joml.Vector2f;

import components.DataMessage;
import components.Message;
import components.MessageId;
import entity.Entity;

public class PointSpawn extends BaseSpawn implements SpawnComponent {

	private Vector2f spawnLoc;

	public PointSpawn(Vector2f spawnLoc) {
		this.spawnLoc = spawnLoc;
	}

	public void setSpawnLocation(Vector2f spawnLoc) {
		this.spawnLoc = spawnLoc;
	}

	@Override
	public void receive(Message m, Entity e) {
		if (m.getId() == MessageId.ENTITY_DIED) {
			spawn(e);
		}
	}

	@Override
	public void spawn(Entity e) {
		e.send(new DataMessage<Vector2f>(MessageId.ENTITY_SPAWN, spawnLoc));
		e.send(new Message(MessageId.ENTITY_MOVED));
	}
}
