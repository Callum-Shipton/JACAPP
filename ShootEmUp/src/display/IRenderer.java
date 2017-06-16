package display;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import components.TypeComponent;
import components.graphical.MapGraphics;
import math.Vector2;
import object.Entity;

public class IRenderer extends Renderer {

	private float width;
	private float height;
	private int amount;
	FloatBuffer verticesFloatBuffer;
	ByteBuffer indicesBuffer;
	FloatBuffer instanceFloatBuffer;

	public IRenderer(Map<Vector2, Entity> textures, Vector2 texMax, float width, float height) {
		this.width = width;
		this.height = height;
		initRenderData(textures, texMax);
	}

	public IRenderer(Vector2[][] textures, Vector2 texMax, float width, float height) {
		this.width = width;
		this.height = height;
		initRenderData(textures, texMax);
	}

	public void draw(int texid) {
		glUseProgram(Art.ShaderInst);
		glBindTexture(GL11.GL_TEXTURE_2D, texid);
		glBindVertexArray(this.VAO);
		glDrawElementsInstanced(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0, this.amount);
		glBindVertexArray(0);
		glUseProgram(0);
	}

	public void initRenderData(Map<Vector2, Entity> textures, Vector2 texMax) {

		this.amount = textures.size();
		instanceFloatBuffer = BufferUtils.createByteBuffer(this.amount * 4 * 4).asFloatBuffer();

		float[] texture = new float[2];
		float[] translation = new float[2];
		Iterator<Entry<Vector2, Entity>> iterator = textures.entrySet().iterator();
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
		
		createBufferData(texMax);
		
		bindRenderData();

		// GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		// GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void initRenderData(Vector2[][] textures, Vector2 texMax) {

		this.amount = textures.length * textures[0].length;
		instanceFloatBuffer = BufferUtils.createByteBuffer(this.amount * 4 * 4).asFloatBuffer();
		
		float[] texture = new float[2];
		float[] translation = new float[2];
		for (int i = 0; i < textures.length; i++) {
			for (int j = 0; j < textures[0].length; j++) {
				if (textures[i][j] != null) {
					texture[0] = textures[i][j].x() / texMax.x();
					texture[1] = textures[i][j].y() / texMax.y();
					translation[0] = i * this.width;
					translation[1] = j * this.height;
				} else {
					texture[0] = texMax.x() - (1.0f / texMax.x());
					texture[1] = texMax.y() - (1.0f / texMax.y());
					translation[0] = 1000 * this.width;
					translation[1] = 1000 * this.width;
				}
				instanceFloatBuffer.put(translation);
				instanceFloatBuffer.put(texture);
			}
		}
		instanceFloatBuffer.flip();
		
		createBufferData(texMax);
		
		bindRenderData();

		// GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		// GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	private void createBufferData(Vector2 texMax){
		TexturedVertex vertices[];

		TexturedVertex v0 = new TexturedVertex();
		v0.setXY(0.0f, 1.0f * this.height);
		v0.setST(0.0f, 1.0f / texMax.y());
		TexturedVertex v1 = new TexturedVertex();
		v1.setXY(0.0f, 0.0f);
		v1.setST(0.0f, 0.0f);
		TexturedVertex v2 = new TexturedVertex();
		v2.setXY(1.0f * this.width, 0.0f);
		v2.setST(1.0f / texMax.x(), 0.0f);
		TexturedVertex v3 = new TexturedVertex();
		v3.setXY(1.0f * this.width, 1.0f * this.height);
		v3.setST(1.0f / texMax.x(), 1.0f / texMax.y());

		vertices = new TexturedVertex[] { v0, v1, v2, v3 };
		verticesFloatBuffer = BufferUtils.createByteBuffer(vertices.length * TexturedVertex.STRIDE)
				.asFloatBuffer();
		for (TexturedVertex vertice : vertices) {
			// Add position, color and texture floats to the buffer
			verticesFloatBuffer.put(vertice.getElements());
		}
		verticesFloatBuffer.flip();
		byte[] indices = { 0, 1, 2, 2, 3, 0 };

		int indicesCount = indices.length;
		indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();
	}
	
	public void bindRenderData(){
		this.VAO = glGenVertexArrays();
		glBindVertexArray(this.VAO);
		
		this.VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.VBO);
		glBufferData(GL_ARRAY_BUFFER, verticesFloatBuffer, GL_STATIC_DRAW);
		
		this.EBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, TexturedVertex.POSITION_ELEMENT_COUNT, GL_FLOAT, false, TexturedVertex.STRIDE,
				TexturedVertex.POSITION_BYTE_OFFSET);

		glEnableVertexAttribArray(0);

		
		glVertexAttribPointer(1, TexturedVertex.TEXTURE_ELEMENT_COUNT, GL_FLOAT, false, TexturedVertex.STRIDE,
				TexturedVertex.TEXTURE_BYTE_OFFSET);

		glEnableVertexAttribArray(1);
		
		int IVBO = glGenBuffers();
		glBindBuffer(GL15.GL_ARRAY_BUFFER, IVBO);
		glBufferData(GL15.GL_ARRAY_BUFFER, instanceFloatBuffer, GL_STATIC_DRAW);
		
		
		glVertexAttribPointer(2, TexturedVertex.POSITION_ELEMENT_COUNT, GL_FLOAT, false, TexturedVertex.STRIDE,
				TexturedVertex.POSITION_BYTE_OFFSET);
		
		glEnableVertexAttribArray(2);
		

		glVertexAttribPointer(3, TexturedVertex.TEXTURE_ELEMENT_COUNT, GL_FLOAT, false, TexturedVertex.STRIDE,
				TexturedVertex.TEXTURE_BYTE_OFFSET);
		
		glEnableVertexAttribArray(3);
		
		glVertexAttribDivisor(2, 1);
		glVertexAttribDivisor(3, 1);
		
		glBindVertexArray(0);
	}
}