package display;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

public class FloorRenderer extends IRenderer {

	private Vector2f[][] renderData;

	public FloorRenderer(Vector2f[][] renderData, Vector2f texMax, float width, float height) {
		super(texMax, width, height);
		this.renderData = renderData;
	}

	@Override
	public void initRenderData() {
		amount = renderData.length * renderData[0].length;
		instanceFloatBuffer = BufferUtils.createByteBuffer(amount * 4 * 4).asFloatBuffer();

		float[] texture = new float[2];
		float[] translation = new float[2];
		for (int i = 0; i < renderData.length; i++) {
			for (int j = 0; j < renderData[0].length; j++) {
				if (renderData[i][j] != null) {
					texture[0] = renderData[i][j].x() / texMax.x();
					texture[1] = renderData[i][j].y() / texMax.y();
					translation[0] = i * width;
					translation[1] = j * height;
				} else {
					texture[0] = texMax.x() - (1.0f / texMax.x());
					texture[1] = texMax.y() - (1.0f / texMax.y());
					translation[0] = 1000 * width;
					translation[1] = 1000 * width;
				}
				instanceFloatBuffer.put(translation);
				instanceFloatBuffer.put(texture);
			}
		}
		instanceFloatBuffer.flip();

	}

}
