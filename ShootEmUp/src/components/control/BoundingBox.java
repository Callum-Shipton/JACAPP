package components.control;

import math.Vector2;
import math.Vector4;

public class BoundingBox {

	private Vector4 box;
	
	public BoundingBox(Vector4 box){
		this.box = box;
	}
	
	public boolean boxContains(Vector2 point){
		if(((point.x() > box.x()) && (point.x() < box.x()+box.z())) && ((point.y() > box.y()) && (point.y() < box.y()+box.w()))){
			return true;
		}
		return false;
	}
	
	public void addPoint(Vector2 point){
		if(point.x() < box.x()){
			box.x(point.x());
		}
		if(point.y() < box.y()){
			box.y(point.y());
		}
		if(point.x() > box.x() + box.z()){
			box.z(point.x()-box.x());
		}
		if(point.y() > box.y() + box.w()){
			box.w(point.y()-box.y());
		}
	}
}
