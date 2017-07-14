package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector3 implements Vector<Vector3> {

	private final static FloatBuffer direct = BufferUtils.createFloatBuffer(3);

	private float x, y, z;

	public Vector3() {
		set(0, 0, 0);
	}

	public Vector3(float v) {
		this(v, v, v);
	}

	public Vector3(float x, float y, float z) {
		set(x, y, z);
	}

	public Vector3(Vector2 vec) {
		set(vec);
	}

	public Vector3(Vector2 vec, float z) {
		set(vec, z);
	}

	public Vector3(Vector3 vec) {
		set(vec);
	}

	public Vector3 add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	@Override
	public Vector3 add(Vector3 vec) {
		return add(vec.x, vec.y, vec.z);
	}

	@Override
	public Vector3 copy() {
		return new Vector3(this);
	}

	public Vector3 cross(Vector3 vec) {
		return new Vector3((this.y * vec.z) - (vec.y * this.z), (this.z * vec.x) - (vec.z * this.x),
				(this.x * vec.y) - (vec.x * this.y));
	}

	@Override
	public Vector3 divide(float f) {
		return divide(f, f, f);
	}

	public Vector3 divide(float x, float y, float z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}

	@Override
	public Vector3 divide(Vector3 vec) {
		return divide(vec.x, vec.y, vec.z);
	}

	public float dot(Vector3 vec) {
		return (this.x * vec.x) + (this.y * vec.y) + (this.z * vec.z);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector3) {
			Vector3 v = (Vector3) o;
			return Float.compare(this.x, v.x) == 0 && Float.compare(this.y, v.y) == 0
					&& Float.compare(this.z, v.z) == 0;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 139;
		hash = (int) ((467 * hash) + this.x);
		hash = (int) ((467 * hash) + this.y);
		hash = (int) ((467 * hash) + this.z);
		return hash;
	}

	@Override
	public float length() {
		return (float) Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
	}

	@Override
	public Vector3 mult(float f) {
		return mult(f, f, f);
	}

	public Vector3 mult(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	@Override
	public Vector3 mult(Vector3 vec) {
		return mult(vec.x, vec.y, vec.z);
	}

	public Vector3 normalize() {
		float length = length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}

	public Vector3 reset() {
		this.x = this.y = this.z = 0;
		return this;
	}

	public Vector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3 set(Vector2 vec) {
		return set(vec.x(), vec.y(), 0);
	}

	public Vector3 set(Vector2 vec, float z) {
		return set(vec.x(), vec.y(), z);
	}

	public Vector3 set(Vector3 vec) {
		return set(vec.x, vec.y, vec.z);
	}

	public Vector3 sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	@Override
	public Vector3 sub(Vector3 vec) {
		return sub(vec.x, vec.y, vec.z);
	}

	@Override
	public FloatBuffer toBuffer() {
		direct.clear();
		direct.put(this.x).put(this.y).put(this.z);
		direct.flip();
		return direct;
	}

	public float x() {
		return this.x;
	}

	public Vector3 x(float x) {
		this.x = x;
		return this;
	}

	public float y() {
		return this.y;
	}

	public Vector3 y(float y) {
		this.y = y;
		return this;
	}

	public float z() {
		return this.z;
	}

	public Vector3 z(float z) {
		this.z = z;
		return this;
	}
}