package math;

import java.io.Serializable;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector4 implements Vector<Vector4>, Serializable {

	private static final long serialVersionUID = -5855797334341847882L;

	private static final FloatBuffer direct = BufferUtils.createFloatBuffer(4);

	private float x, y, z, w;

	public Vector4() {
		set(0, 0, 0, 0);
	}

	public Vector4(float v) {
		this(v, v, v, v);
	}

	public Vector4(float x, float y, float z, float w) {
		set(x, y, z, w);
	}

	public Vector4(Vector2 vec) {
		set(vec);
	}

	public Vector4(Vector2 vec, float z, float w) {
		set(vec, z, w);
	}

	public Vector4(Vector3 vec) {
		set(vec);
	}

	public Vector4(Vector3 vec, float w) {
		set(vec, w);
	}

	public Vector4(Vector4 vec) {
		set(vec);
	}

	public Vector4 add(float x, float y, float z, float w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}

	@Override
	public Vector4 add(Vector4 vec) {
		return add(vec.x, vec.y, vec.z, vec.w);
	}

	@Override
	public Vector4 copy() {
		return new Vector4(this);
	}

	@Override
	public Vector4 divide(float f) {
		return divide(f, f, f, f);
	}

	public Vector4 divide(float x, float y, float z, float w) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		this.w /= w;
		return this;
	}

	@Override
	public Vector4 divide(Vector4 vec) {
		return divide(vec.x, vec.y, vec.z, vec.w);
	}

	public float dot(Vector4 vec) {
		return (this.x * vec.x) + (this.y * vec.y) + (this.z * vec.z) + (this.w * vec.w);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector4) {
			Vector4 v = (Vector4) o;

			return new Vector2(this.x, this.y).equals(new Vector2(v.x, v.y))
					&& new Vector2(this.z, this.w).equals(new Vector2(v.z, v.w));
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 139;
		hash = (int) ((467 * hash) + this.x);
		hash = (int) ((467 * hash) + this.y);
		hash = (int) ((467 * hash) + this.z);
		hash = (int) ((467 * hash) + this.w);
		return hash;
	}

	@Override
	public float length() {
		return (float) Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z) + (this.w * this.w));
	}

	@Override
	public Vector4 mult(float f) {
		return mult(f, f, f, f);
	}

	public Vector4 mult(float x, float y, float z, float w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;
		return this;
	}

	@Override
	public Vector4 mult(Vector4 vec) {
		return mult(vec.x, vec.y, vec.z, vec.w);
	}

	public Vector4 normalize() {
		float length = length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;
		return this;
	}

	public Vector4 reset() {
		this.x = this.y = this.z = this.w = 0;
		return this;
	}

	public Vector4 set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public Vector4 set(Vector2 vec) {
		return set(vec, 0, 0);
	}

	public Vector4 set(Vector2 vec, float z, float w) {
		return set(vec.x(), vec.y(), z, w);
	}

	public Vector4 set(Vector3 vec) {
		return set(vec, 0);
	}

	public Vector4 set(Vector3 vec, float w) {
		return set(vec.x(), vec.y(), vec.z(), w);
	}

	public Vector4 set(Vector4 vec) {
		return set(vec.x, vec.y, vec.z, vec.w);
	}

	public Vector4 sub(float x, float y, float z, float w) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
		return this;
	}

	@Override
	public Vector4 sub(Vector4 vec) {
		return sub(vec.x, vec.y, vec.z, vec.w);
	}

	@Override
	public FloatBuffer toBuffer() {
		direct.clear();
		direct.put(this.x).put(this.y).put(this.z).put(this.w);
		direct.flip();
		return direct;
	}

	public float w() {
		return this.w;
	}

	public Vector4 w(float w) {
		this.w = w;
		return this;
	}

	public float x() {
		return this.x;
	}

	public Vector4 x(float x) {
		this.x = x;
		return this;
	}

	public float y() {
		return this.y;
	}

	public Vector4 y(float y) {
		this.y = y;
		return this;
	}

	public float z() {
		return this.z;
	}

	public Vector4 z(float z) {
		this.z = z;
		return this;
	}
	
	public Vector4 contains(Vector4 test) {
		float cx = test.x;
		float cy = test.y;
		float cz = test.z;
		float cw = test.w;
		
		if ((x < (cx + cz)) && ((x + z) > cx) && (y < (cy + cw)) && ((y + w) > cy)) {
			return new Vector4(x - (cx + cz), y - (cy + cw), (x + z) - cx, (y + w) - cy);
		}
		return null;
	}
}