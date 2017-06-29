package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector2 implements Vector<Vector2> {

	private static final FloatBuffer direct = BufferUtils.createFloatBuffer(2);

	private float x, y;

	public Vector2() {
		set(0, 0);
	}

	public Vector2(float v) {
		this(v, v);
	}

	public Vector2(float x, float y) {
		set(x, y);
	}

	public Vector2(Vector2 vec) {
		set(vec);
	}

	public Vector2(Vector3 vec) {
		set(vec);
	}

	public Vector2(Vector4 vec) {
		set(vec);
	}

	public Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	@Override
	public Vector2 add(Vector2 vec) {
		return add(vec.x, vec.y);
	}

	public double angle() {
		double a = Math.asin(this.x);
		if (this.y > 0) {
			a = Math.PI - a;
		} else if (this.x < 0) {
			a = (2 * Math.PI) + a;
		}
		return a * (180 / Math.PI);
	}

	@Override
	public Vector2 copy() {
		return new Vector2(this);
	}

	public float dist(Vector2 vector) {
		return (float) Math.sqrt(Math.pow(this.x() - vector.x(), 2) + Math.pow(this.y() - vector.y(), 2));
	}

	@Override
	public Vector2 divide(float f) {
		return divide(f, f);
	}

	public Vector2 divide(float x, float y) {
		this.x /= x;
		this.y /= y;
		return this;
	}

	@Override
	public Vector2 divide(Vector2 vec) {
		return divide(vec.x, vec.y);
	}

	public float dot(Vector2 vec) {
		return (this.x * vec.x) + (this.y * vec.y);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector2) {
			Vector2 v = (Vector2) o;
			return Float.compare(x, v.x) == 0 && Float.compare(y, v.y) == 0;
		}

		return false;
	}

	public void floor() {
		this.x = (float) Math.floor(this.x);
		this.y = (float) Math.floor(this.y);
	}

	@Override
	public int hashCode() {
		int hash = 139;
		hash = (int) ((467 * hash) + this.x);
		hash = (int) ((467 * hash) + this.y);
		return hash;
	}

	@Override
	public float length() {
		return (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
	}

	@Override
	public Vector2 mult(float f) {
		return mult(f, f);
	}

	public Vector2 mult(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}

	@Override
	public Vector2 mult(Vector2 vec) {
		return mult(vec.x, vec.y);
	}

	public Vector2 normalize() {
		float length = length();
		this.x /= length;
		this.y /= length;
		return this;
	}

	public Vector2 reset() {
		this.x = this.y = 0;
		return this;
	}

	public Vector2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2 set(Vector2 vec) {
		return set(vec.x, vec.y);
	}

	public Vector2 set(Vector3 vec) {
		return set(vec.x(), vec.y());
	}

	public Vector2 set(Vector4 vec) {
		return set(vec.x(), vec.y());
	}

	public Vector2 sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	@Override
	public Vector2 sub(Vector2 vec) {
		return sub(vec.x, vec.y);
	}

	@Override
	public FloatBuffer toBuffer() {
		direct.clear();
		direct.put(this.x).put(this.y);
		direct.flip();
		return direct;
	}

	public float x() {
		return this.x;
	}

	public Vector2 x(float x) {
		this.x = x;
		return this;
	}

	public float y() {
		return this.y;
	}

	public Vector2 y(float y) {
		this.y = y;
		return this;
	}
}