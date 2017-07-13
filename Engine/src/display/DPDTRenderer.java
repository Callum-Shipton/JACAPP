package display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import loop.Loop;
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
		this.shaderProgramID = pID;
		initRenderData();
	}

	public void draw(Image Texid, Vector2 pos, Vector2 size, float rotate, Vector2 texPos, Vector2 maxFrame) {
		if(!visible(pos,size)) return;
		glUseProgram(this.shaderProgramID);
		glBindVertexArray(this.VAO);
		glBindTexture(GL_TEXTURE_2D, Texid.getID());

		this.model.clearToIdentity();
		this.model.translate(pos.x(), pos.y(), 0.0f);
		this.model.translate(0.5f * size.x(), 0.5f * size.y(), 0.0f);
		this.model.rotateDeg(rotate, 0.0f, 0.0f, 1.0f);
		this.model.translate(-0.5f * size.x(), -0.5f * size.y(), 0.0f);
		this.model.scale(size.x(), size.y(), 1.0f);

		this.texture.clearToIdentity();
		this.texture.translate(texPos.x() / maxFrame.x(), texPos.y() / maxFrame.y(), 0.0f);
		this.texture.scale(1 / maxFrame.x(), 1 / maxFrame.y(), 1.0f);

		this.matrix44Buffer = this.model.toBuffer();

		glUniformMatrix4fv(this.modelMatrixLocation, false, this.matrix44Buffer);

		this.matrix44Buffer.clear();

		this.matrix44Buffer = this.texture.toBuffer();

		glUniformMatrix4fv(this.textureMatrixLocation, false, this.matrix44Buffer);

		// glEnableVertexAttribArray(0);
		// glEnableVertexAttribArray(1);

		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);

		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindVertexArray(0);
		glUseProgram(0);

	}

	private boolean visible(Vector2 pos, Vector2 size) {
		if(shaderProgramID == ImageProcessor.ShaderStat)return true;
		
		//TODO: Update to use rotation (even though I don't think anything uses it atm.)
		else return Loop.getDisplay().getCamera().isVisible(pos,size);


	}

	public int getVAO() {
		return this.VAO;
	}

	public void initRenderData() {

		this.matrix44Buffer = BufferUtils.createFloatBuffer(16);
		this.model = new Matrix4();
		this.texture = new Matrix4();

		glUseProgram(this.shaderProgramID);

		this.modelMatrixLocation = glGetUniformLocation(this.shaderProgramID, "modelMatrix");
		this.textureMatrixLocation = glGetUniformLocation(this.shaderProgramID, "textureMatrix");

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

		this.VAO = glGenVertexArrays();
		glBindVertexArray(this.VAO);

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
}
