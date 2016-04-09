package math;

import java.nio.FloatBuffer;

public interface Vector<V> {

	V add(V v);

	V copy();

	V divide(float f);

	V divide(V v);

	float length();

	V mult(float f);

	V mult(V v);

	V sub(V v);

	FloatBuffer toBuffer();
}