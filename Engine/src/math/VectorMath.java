package math;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class VectorMath {
	static Vector2f axis = new Vector2f(0.0f,1.0f);
	public static Vector4f contains(Vector4f vec1, Vector4f vec2) {
		float cx = vec2.x;
		float cy = vec2.y;
		float cz = vec2.z;
		float cw = vec2.w;

		if ((vec1.x < (cx + cz)) && ((vec1.x + vec1.z) > cx) && (vec1.y < (cy + cw)) && ((vec1.y + vec1.w) > cy)) {
			return new Vector4f(vec1.x - (cx + cz), vec1.y - (cy + cw), (vec1.x + vec1.z) - cx, (vec1.y + vec1.w) - cy);
		}
		return null;
	}
	
	public static float angle(Vector2f vec) {
		return (float) Math.toDegrees(axis.angle(vec))+180;
	}
}
