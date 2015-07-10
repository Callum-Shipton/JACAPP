package components.spawn;

import math.Vector2;
import components.Message;
import components.graphical.BaseGraphics;
import entities.Entity;

public class PointSpawn extends BaseSpawn implements SpawnComponent {

	private Vector2 spawnLoc = new Vector2(480.0f, 480.0f);
	private BaseGraphics BG;
	
	public PointSpawn(BaseGraphics BG, Vector2 spawnLoc, Entity e){
		this.BG = BG;
		this.spawnLoc = spawnLoc;
		spawn(e);
	}
	
	@Override
	public void spawn(Entity e) {
		BG.setX(spawnLoc.x());
		BG.setY(spawnLoc.y());
		e.send(Message.ENTITY_MOVED);
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
