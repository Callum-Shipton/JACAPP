package components.spawn;

import org.joml.Vector2f;

import components.DataMessage;
import components.Message;
import components.MessageId;
import components.interfaces.SpawnComponent;
import loop.GameLoop;

public class SectionSpawn extends BaseSpawn implements SpawnComponent {

	private int section;

	public SectionSpawn() {
	}

	public SectionSpawn(SectionSpawn pointSpawn) {
		this.section = pointSpawn.section;
	}

	@Override
	public void receive(Message m) {
		if (m.getId() == MessageId.ENTITY_DIED) {
			spawn();
		}
	}

	@Override
	public void spawn() {
		Vector2f spawnLoc = null;
		switch(section) {
		case-1: spawnLoc = new Vector2f(40,0);
			break;
		case 1: spawnLoc = new Vector2f(GameLoop.getDisplay().getWidth()-40,0);
			break;
		default:
		case 0: spawnLoc = new Vector2f(GameLoop.getDisplay().getWidth()/2,0);
			break;
			
		}
		spawnLoc.add(0, GameLoop.getDisplay().getHeight()/2);
		
		getEntity().send(new DataMessage<Vector2f>(MessageId.ENTITY_SPAWN, spawnLoc));
		getEntity().send(new Message(MessageId.ENTITY_MOVED));
	}
}
