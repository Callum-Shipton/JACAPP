package Components.Spawn;

import Components.Message;
import Components.Graphical.BaseGraphics;
import Math.Vector2;
import Object.Entity;

public class PointSpawn extends BaseSpawn implements SpawnComponent {

	private Vector2 spawnLoc = new Vector2(480.0f, 480.0f);
	private BaseGraphics BG;
	
	public PointSpawn(BaseGraphics BG){
		this.BG = BG;
	}
	
	@Override
	public void spawn(Entity e) {
		BG.setX(spawnLoc.x());
		BG.setY(spawnLoc.y());
	}

	@Override
	public void receive(Message m, Entity e) {
		if(m == Message.ENTITY_DIED){
			spawn(e);
		}
		
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
