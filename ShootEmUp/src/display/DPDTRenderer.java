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
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import math.Matrix4;
import math.Vector2;

public class DPDTRenderer extends Renderer {

	private int shaderProgramID;
	private int modelMatrixLocation;
	private int textureMatrixLocation;
	private FloatBuffer matrix44Buffer;
	private Matrix4 model;
	private Matrix4 texture;

	public DPDTRenderer(int pID) {
		shaderProgramID = pID;
		initRenderData();
	}

	public void draw(Image Texid, Vector2 pos, Vector2 size, float rotate, Vector2 texPos) {

		glUseProgram(shaderProgramID);
		glBindVertexArray(VAO);
		glBindTexture(GL_TEXTURE_2D, Texid.getID());

		model.clearToIdentity();
		model.translate(pos.x(), pos.y(), 0.0f);
		model.translate(0.5f * size.x(), 0.5f * size.y(), 0.0f);
		model.rotateDeg(rotate, 0.0f, 0.0f, 1.0f);
		model.translate(-0.5f * size.x(), -0.5f * size.y(), 0.0f);
		model.scale(size.x(), size.y(), 1.0f);

		texture.clearToIdentity();
		texture.translate(texPos.x() / Texid.getFWidth(), texPos.y() / Texid.getFHeight(), 0.0f);
		texture.scale(1 / Texid.getFWidth(), 1 / Texid.getFHeight(), 1.0f);

		matrix44Buffer = model.toBuffer();

		glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);

		matrix44Buffer.clear();

		matrix44Buffer = texture.toBuffer();

		glUniformMatrix4(textureMatrixLocation, false, matrix44Buffer);

		// glEnableVertexAttribArray(0);
		// glEnableVertexAttribArray(1);

		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindVertexArray(0);
		glUseProgram(0);
	}

	public void initRenderData() {

		matrix44Buffer = BufferUtils.createFloatBuffer(16);
		model = new Matrix4();
		texture = new Matrix4();

		glUseProgram(shaderProgramID);

		modelMatrixLocation = glGetUniformLocation(shaderProgramID, "modelMatrix");
		textureMatrixLocation = glGetUniformLocation(shaderProgramID, "textureMatrix");

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
		FloatBuffer verticesFloatBuffer = BufferUtils.createByteBuffer(vertices.length * TexturedVertex.stride)
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

		VAO = glGenVertexArrays();
		glBindVertexArray(VAO);

		VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, verticesFloatBuffer, GL_DYNAMIC_DRAW);

		glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL_FLOAT, false, TexturedVertex.stride,
				TexturedVertex.positionByteOffset);

		glEnableVertexAttribArray(0);

		// Put the texture coordinates in attribute list 1
		glVertexAttribPointer(1, TexturedVertex.textureElementCount, GL_FLOAT, false, TexturedVertex.stride,
				TexturedVertex.textureByteOffset);

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

	public void draw(Image Texid, Vector2 pos, Vector2 size, float rotate, Vector2 texPos, Vector2 maxFrame) {
		glUseProgram(shaderProgramID);
		glBindVertexArray(VAO);
		glBindTexture(GL_TEXTURE_2D, Texid.getID());

		model.clearToIdentity();
		model.translate(pos.x(), pos.y(), 0.0f);
		model.translate(0.5f * size.x(), 0.5f * size.y(), 0.0f);
		model.rotateDeg(rotate, 0.0f, 0.0f, 1.0f);
		model.translate(-0.5f * size.x(), -0.5f * size.y(), 0.0f);
		model.scale(size.x(), size.y(), 1.0f);

		texture.clearToIdentity();
		texture.translate(texPos.x() / maxFrame.x(), texPos.y() / maxFrame.y(), 0.0f);
		texture.scale(1 / maxFrame.x(), 1 / maxFrame.y(), 1.0f);

		matrix44Buffer = model.toBuffer();

		glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);

		matrix44Buffer.clear();

		matrix44Buffer = texture.toBuffer();

		glUniformMatrix4(textureMatrixLocation, false, matrix44Buffer);

		// glEnableVertexAttribArray(0);
		// glEnableVertexAttribArray(1);

		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindVertexArray(0);
		glUseProgram(0);

	}
}
