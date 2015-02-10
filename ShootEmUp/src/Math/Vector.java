package Math;

import java.nio.FloatBuffer;

public interface Vector<V> {
	V add(V v);
	
	V sub(V v);
	
	V mult(V v);
	
	V mult(float f);
	
	V divide(V v);
	
	V divide(float f);
	
	float length();
	
	V copy();
	
	FloatBuffer toBuffer();
}