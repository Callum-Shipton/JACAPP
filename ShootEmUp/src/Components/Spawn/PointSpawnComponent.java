package Components.Spawn;

import Main.ShootEmUp;
import Math.Vector2;
import Object.Entity;

public class PointSpawnComponent implements SpawnComponent {

	private Vector2 spawnLoc = new Vector2(480.0f, 480.0f);
	
	@Override
	public void spawn(Entity e) {
		e.setPosX(spawnLoc.x());
		e.setPosY(spawnLoc.y());
	}

}
