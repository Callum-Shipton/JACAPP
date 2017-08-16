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

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public abstract class IRenderer extends Renderer {

	protected Shader shader = ImageProcessor.ShaderInst;
	
	protected float width;
	protected float height;
	protected int amount;

	protected Vector2f texMax;

	protected FloatBuffer verticesFloatBuffer;
	protected ByteBuffer indicesBuffer;
	protected FloatBuffer instanceFloatBuffer;

	public IRenderer(Vector2f texMax, float width, float height) {
		this.width = width;
		this.height = height;
		this.texMax = texMax;
	}

	public void draw(int texid) {
		glUseProgram(shader.getProgramID());
		glBindTexture(GL11.GL_TEXTURE_2D, texid);
		glBindVertexArray(this.VAO);
		glDrawElementsInstanced(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0, this.amount);
		glBindVertexArray(0);
		glUseProgram(0);
	}

	public abstract void initRenderData();

	public void init() {

		initRenderData();

		createBufferData(texMax);

		bindRenderData();

		// GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		// GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	private void createBufferData(Vector2f texMax) {
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
		verticesFloatBuffer = BufferUtils.createByteBuffer(vertices.length * TexturedVertex.STRIDE).asFloatBuffer();
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

	public void bindRenderData() {
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