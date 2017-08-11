package components.spawn;

import org.joml.Vector2f;

import components.DataMessage;
import components.Message;
import components.MessageId;
import components.interfaces.SpawnComponent;

public class PointSpawn extends BaseSpawn implements SpawnComponent {

	private transient Vector2f spawnLoc;

	public PointSpawn() {
	}

	public PointSpawn(PointSpawn pointSpawn) {
	}

	public void setSpawnLocation(Vector2f spawnLoc) {
		this.spawnLoc = spawnLoc;
	}

	@Override
	public void receive(Message m) {
		if (m.getId() == MessageId.ENTITY_DIED) {
			spawn();
		}
	}

	@Override
	public void spawn() {
		getEntity().send(new DataMessage<Vector2f>(MessageId.ENTITY_SPAWN, spawnLoc));
		getEntity().send(new Message(MessageId.ENTITY_MOVED));
	}
}
