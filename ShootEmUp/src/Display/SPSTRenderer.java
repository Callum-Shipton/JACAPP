package Display;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import Math.Matrix4;
import Math.Vector2;

public class SPSTRenderer {

	private int shaderProgramID;
	private int VAO;
	private int VBO;
	private int EBO;
	private int modelMatrixLocation;
	private FloatBuffer matrix44Buffer;

	public SPSTRenderer(int pID) {
		shaderProgramID = pID;
		initRenderData();
	}

	public void draw(int Texid, Vector2 pos, Vector2 size, float rotate) {

		glBindTexture(GL_TEXTURE_2D, Texid);

		Matrix4 model = new Matrix4();
		model.clearToIdentity();
		model.translate(pos.x(), pos.y(), 0.0f);
		model.translate(0.5f * size.x(), 0.5f * size.y(), 0.0f);
		model.rotateDeg(rotate, 0.0f, 0.0f, 1.0f);
		model.translate(-0.5f * size.x(), -0.5f * size.y(), 0.0f);
		model.scale(size.x(), size.y(), 1.0f);

		model.transpose();

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

		matrix44Buffer = model.toBuffer();

		glUniformMatrix4(modelMatrixLocation, true, matrix44Buffer);

		matrix44Buffer.clear();

		// glEnableVertexAttribArray(0);
		// glEnableVertexAttribArray(1);

		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		// glBindVertexArray(0);
	}

	private void initRenderData() {

		matrix44Buffer = BufferUtils.createFloatBuffer(16);

		glUseProgram(shaderProgramID);

		modelMatrixLocation = glGetUniformLocation(shaderProgramID, "modelMatrix");

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
		FloatBuffer verticesFloatBuffer = BufferUtils.createByteBuffer(vertices.length * TexturedVertex.stride).asFloatBuffer();
		for (int i = 0; i < vertices.length; i++) {
			// Add position, color and texture floats to the buffer
			verticesFloatBuffer.put(vertices[i].getElements());
		}
		verticesFloatBuffer.flip();
		byte[] indices = { 0, 1, 2, 2, 3, 0 };

		int indicesCount = indices.length;
		ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();

		setVAO(glGenVertexArrays());
		glBindVertexArray(getVAO());

		VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, verticesFloatBuffer, GL_DYNAMIC_DRAW);

		glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.positionByteOffset);

		glEnableVertexAttribArray(0);

		// Put the texture coordinates in attribute list 1
		glVertexAttribPointer(1, TexturedVertex.textureElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.textureByteOffset);

		glEnableVertexAttribArray(1);

		EBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

		// glBindVertexArray(0);

		// glBindBuffer(GL_ARRAY_BUFFER, 0);
		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int getVAO() {
		return VAO;
	}

	private void setVAO(int vAO) {
		VAO = vAO;
	}
}