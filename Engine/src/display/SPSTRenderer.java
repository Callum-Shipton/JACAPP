package display;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

//TODO: Clean the hell out of this class (nothing uses it) 

public class SPSTRenderer {

	private int shaderProgramID;
	private int VAO;
	private int VBO;
	private int EBO;
	private int modelMatrixLocation;
	private FloatBuffer matrix44Buffer;

	public SPSTRenderer(int pID) {
		this.shaderProgramID = pID;
		initRenderData();
	}

	public void draw(int Texid, Vector2f pos, Vector2f size, float rotate) {

		glBindTexture(GL_TEXTURE_2D, Texid);

		Matrix4f model = new Matrix4f().translate(pos.x(), pos.y(), 0.0f)
				.translate(0.5f * size.x(), 0.5f * size.y(), 0.0f)
				.rotate((float) Math.toRadians(rotate), 0.0f, 0.0f, 1.0f)
				.translate(-0.5f * size.x(), -0.5f * size.y(), 0.0f).scale(size.x(), size.y(), 1.0f).transpose();

		// model.m03 += pos.x;
		// model.m13 += pos.y;
		// model.m23 += -5.0f;

		/*
		 *
		 * double timeValue = GLFW.glfwGetTime(); double greenValue =
		 * ((Math.sin(timeValue) / 2) + 0.5); int vertexColorLocation =
		 * glGetUniformLocation(shaderProgramID, "ourColor");
		 * glUniform2f(vertexColorLocation, 0.0f, (float) greenValue);
		 */

		this.matrix44Buffer = model.get(matrix44Buffer);

		glUniformMatrix4fv(this.modelMatrixLocation, true, this.matrix44Buffer);

		this.matrix44Buffer.clear();

		// glEnableVertexAttribArray(0);
		// glEnableVertexAttribArray(1);

		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		// glBindVertexArray(0);
	}

	public int getVAO() {
		return this.VAO;
	}

	private void initRenderData() {

		this.matrix44Buffer = BufferUtils.createFloatBuffer(16);

		glUseProgram(this.shaderProgramID);

		this.modelMatrixLocation = glGetUniformLocation(this.shaderProgramID, "modelMatrix");

		glUseProgram(0);

		TexturedVertex vertices[];

		TexturedVertex v0 = new TexturedVertex();
		v0.setXY(0.0f, 1.0f);
		v0.setST(0.0f, 1.0f);
		TexturedVertex v1 = new TexturedVertex();
		v1.setXY(0.0f, 0.0f);
		v1.setST(0.0f, 0.0f);
		TexturedVertex v2 = new TexturedVertex();
		v2.setXY(1.0f, 0.0f);
		v2.setST(1.0f, 0.0f);
		TexturedVertex v3 = new TexturedVertex();
		v3.setXY(1.0f, 1.0f);
		v3.setST(1.0f, 1.0f);

		vertices = new TexturedVertex[] { v0, v1, v2, v3 };
		FloatBuffer verticesFloatBuffer = BufferUtils.createByteBuffer(vertices.length * TexturedVertex.STRIDE)
				.asFloatBuffer();
		for (TexturedVertex vertice : vertices) {
			// Add position, color and texture floats to the buffer
			verticesFloatBuffer.put(vertice.getElements());
		}
		verticesFloatBuffer.flip();
		byte[] indices = { 0, 1, 2, 2, 3, 0 };

		int indicesCount = indices.length;
		ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();

		setVAO(glGenVertexArrays());
		glBindVertexArray(getVAO());

		this.VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.VBO);
		glBufferData(GL_ARRAY_BUFFER, verticesFloatBuffer, GL_DYNAMIC_DRAW);

		glVertexAttribPointer(0, TexturedVertex.POSITION_ELEMENT_COUNT, GL_FLOAT, false, TexturedVertex.STRIDE,
				TexturedVertex.POSITION_BYTE_OFFSET);

		glEnableVertexAttribArray(0);

		// Put the texture coordinates in attribute list 1
		glVertexAttribPointer(1, TexturedVertex.TEXTURE_ELEMENT_COUNT, GL_FLOAT, false, TexturedVertex.STRIDE,
				TexturedVertex.TEXTURE_BYTE_OFFSET);

		glEnableVertexAttribArray(1);

		this.EBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

		// glBindVertexArray(0);

		// glBindBuffer(GL_ARRAY_BUFFER, 0);
		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	private void setVAO(int vAO) {
		this.VAO = vAO;
	}
}