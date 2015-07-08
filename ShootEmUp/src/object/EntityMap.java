package object;

import java.util.HashMap;
import java.util.HashSet;

import math.Vector2;
import components.ComponentType;
import components.collision.RigidCollision;
import components.graphical.BaseGraphics;

public class EntityMap {

	public HashMap<Integer,HashMap<Integer,HashSet<Entity>>> map;
	
	public EntityMap(int w, int h){
		map = new HashMap<Integer, HashMap<Integer, HashSet<Entity>>>();
		for(int i = 0; i < (w/6)+1; i++){
			HashMap<Integer,HashSet<Entity>> mapi = new HashMap<Integer, HashSet<Entity>>();
			map.put(i, mapi);
			for(int j = 0; j < (h/6)+1; j++){
				mapi.put(j,new HashSet<Entity>());
			}
		}
	}
	
	public HashSet<Entity> getEntites(HashSet<Vector2> gridPos){
		HashSet<Entity> result = new HashSet<Entity>();
		for(Vector2 gridPosi : gridPos)
		result.addAll(map.get((int)gridPosi.x()).get((int)gridPosi.y()));
		return result;
	}
	
	public HashSet<Entity> getRigidEntites(HashSet<Vector2> gridPos){
		HashSet<Entity> result = new HashSet<Entity>();
		for(Vector2 gridPosi : gridPos){
			for(Entity e : map.get((int)gridPosi.x()).get((int)gridPosi.y())){
				if(e.getComponent(ComponentType.COLLISION) instanceof RigidCollision)result.add(e);
			}
		}
		return result;
	}
	
	public HashSet<Vector2> getGridPos(Entity e){
		HashSet<Vector2> gridPos = new HashSet<Vector2>();
		BaseGraphics BG = (BaseGraphics) e.getComponent(ComponentType.GRAPHICS);
		for(int i = (int) Math.floor((BG.getX()/32)/6); i <= Math.floor(((BG.getX()+BG.getWidth())/32)/6); i++){
			for(int j = (int) Math.floor((BG.getY()/32)/6); j <= Math.floor(((BG.getY()+BG.getHeight())/32)/6); j++ ){
				gridPos.add(new Vector2(i,j));
			}
		}
		return gridPos;
	}
	
	public void addEntity(HashSet<Vector2> gridPos, Entity e){
		for(Vector2 gridPosi : gridPos) {
			HashMap<Integer, HashSet<Entity>> x = map.get((int)gridPosi.x());
			HashSet<Entity> ents = x.get((int)gridPosi.y());
			ents.add(e);
		}
	}
	
	public void removeEntity(HashSet<Vector2> gridPos, Entity e){
		for(Vector2 gridPosi : gridPos) {
			((map.get((int)gridPosi.x())).get((int)gridPosi.y())).remove(e);
		}
	}
}
