package display;

public class TexturedVertex {

	/* Vertex data: [pos.x,pos.y,tex.s,tex.t] */

	// Vertex data
	private float[] xy = new float[] { 0.0f, 0.0f };
	private float[] st = new float[] { 0.0f, 0.0f };

	// The amount of bytes an element has
	public static final int elementBytes = 4;

	// Elements per parameter
	public static final int positionElementCount = 2;
	public static final int textureElementCount = 2;

	// Bytes per parameter
	public static final int positionBytesCount = positionElementCount * elementBytes;
	public static final int textureByteCount = textureElementCount * elementBytes;

	// Byte offsets per parameter
	public static final int positionByteOffset = 0;
	public static final int textureByteOffset = positionByteOffset + positionBytesCount;

	// The amount of elements that a vertex has
	public static final int elementCount = positionElementCount + textureElementCount;
	// The size of a vertex in bytes
	public static final int stride = positionBytesCount + textureByteCount;

	// Setters
	public void setXY(float x, float y) {
		xy = new float[] { x, y };
	}

	public void setST(float s, float t) {
		st = new float[] { s, t };
	}

	// Getters
	public float[] getElements() {
		float[] out = new float[TexturedVertex.elementCount];
		int i = 0;

		// Insert XY elements
		out[i++] = xy[0];
		out[i++] = xy[1];

		// Insert ST elements
		out[i++] = st[0];
		out[i++] = st[1];

		return out;
	}

	public float[] getXY() {
		return new float[] { xy[0], xy[1] };
	}

	public float[] getST() {
		return new float[] { st[0], st[1] };
	}
}