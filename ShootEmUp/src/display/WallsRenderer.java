package display;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.BufferUtils;

import components.TypeComponent;
import components.graphical.MapGraphics;
import entity.Entity;
import math.Vector2;

public class WallsRenderer extends IRenderer {

	private Map<Vector2, Entity> renderData;
	
	public WallsRenderer(Map<Vector2,Entity> renderData, Vector2 texMax, float width, float height){
		super(texMax, width, height);
		this.renderData = renderData;
	}
	
	@Override
	public void initRenderData() {
		
		this.amount = renderData.size();
		instanceFloatBuffer = BufferUtils.createByteBuffer(this.amount * 4 * 4).asFloatBuffer();

		float[] texture = new float[2];
		float[] translation = new float[2];
		Iterator<Entry<Vector2, Entity>> iterator = renderData.entrySet().iterator();
		while (iterator.hasNext()) {
			Entity wall = iterator.next().getValue();
			MapGraphics MG = wall.getComponent(TypeComponent.GRAPHICS);
			Vector2 textured = MG.getMapPos();
			Vector2 pos = new Vector2(MG.getX(), MG.getY());
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
