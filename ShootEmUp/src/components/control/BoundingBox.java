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
	
	public float x(){
		return box.x();
	}
	
	public float y(){
		return box.y();
	}
	
	public float z(){
		return box.z();
	}
	
	public float w(){
		return box.w();
	}
	
	public void x(float x){
		box.x(x);
	}
	
	public void y(float y){
		box.y(y);
	}
	
	public void z(float z){
		box.z(z);
	}
	
	public void w(float w){
		box.w(w);
	}
}
