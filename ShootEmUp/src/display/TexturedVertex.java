package display;

public class TexturedVertex {

	/* Vertex data: [pos.x,pos.y,tex.s,tex.t] */

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

	// Vertex data
	private float[] xy = new float[] { 0.0f, 0.0f };
	private float[] st = new float[] { 0.0f, 0.0f };

	// Getters
	public float[] getElements() {
		float[] out = new float[TexturedVertex.elementCount];
		int i = 0;

		// Insert XY elements
		out[i++] = this.xy[0];
		out[i++] = this.xy[1];

		// Insert ST elements
		out[i++] = this.st[0];
		out[i++] = this.st[1];

		return out;
	}

	public float[] getST() {
		return new float[] { this.st[0], this.st[1] };
	}

	public float[] getXY() {
		return new float[] { this.xy[0], this.xy[1] };
	}

	public void setST(float s, float t) {
		this.st = new float[] { s, t };
	}

	// Setters
	public void setXY(float x, float y) {
		this.xy = new float[] { x, y };
	}
}