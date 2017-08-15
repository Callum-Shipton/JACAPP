package display;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import components.TypeComponent;
import components.graphical.MapGraphics;
import entity.Entity;

public class WallsRenderer extends IRenderer {

	private Map<Vector2i, Entity> renderData;

	public WallsRenderer(Map<Vector2i, Entity> renderData, Vector2f texMax, float width, float height) {
		super(texMax, width, height);
		this.renderData = renderData;
	}

	@Override
	public void initRenderData() {

		amount = renderData.size();
		instanceFloatBuffer = BufferUtils.createByteBuffer(amount * 4 * 4).asFloatBuffer();

		float[] texture = new float[2];
		float[] translation = new float[2];
		Iterator<Entry<Vector2i, Entity>> iterator = renderData.entrySet().iterator();
		while (iterator.hasNext()) {
			Entity wall = iterator.next().getValue();
			MapGraphics graphicsComponents = wall.getComponent(TypeComponent.GRAPHICS);
			Vector2f textured = graphicsComponents.getMapPos();
			Vector2f pos = new Vector2f(graphicsComponents.getX(), graphicsComponents.getY());
			texture[0] = textured.x() / texMax.x();
			texture[1] = textured.y() / texMax.y();
			translation[0] = pos.x();
			translation[1] = pos.y();
			instanceFloatBuffer.put(translation);
			instanceFloatBuffer.put(texture);
		}

		instanceFloatBuffer.flip();
	}

}
