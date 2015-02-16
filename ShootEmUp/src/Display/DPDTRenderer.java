package Display;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Math.Matrix4;
import Math.Vector2;


public class DPDTRenderer implements Renderer {
	
	private int shaderProgramID;
	private int VAO;
	private int VBO;
	private int EBO;
	private int modelMatrixLocation;
	private int textureMatrixLocation;
	private FloatBuffer matrix44Buffer;
	
	public DPDTRenderer(int pID){
		shaderProgramID = pID;
		initRenderData();
	}
	
	public void draw(int Texid, Vector2 pos, Vector2 size, float rotate, Vector2 texPos, Vector2 texMax){
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Texid);
		
		
		Matrix4 model = new Matrix4();
		model.clearToIdentity();
		model.translate(pos.x(), pos.y(), 0.0f);
		model.translate(0.5f*size.x(), 0.5f*size.y(), 0.0f);
		model.rotateDeg(rotate, 0.0f, 0.0f, 1.0f);
		model.translate(-0.5f*size.x(), -0.5f*size.y(), 0.0f);
		model.scale(size.x(), size.y(), 1.0f);

		model.transpose();
		
		Matrix4 texture = new Matrix4();
		texture.clearToIdentity();
		texture.translate(texPos.x()/texMax.x(), texPos.y()/texMax.y(), 0.0f);
		texture.scale(1/texMax.x(), 1/texMax.y(), 1.0f);

		texture.transpose();
		
		//model.m03 += pos.x;
		//model.m13 += pos.y;
		//model.m23 += -5.0f;
		
		/*
		 * 
		double timeValue = GLFW.glfwGetTime();
		double greenValue = ((Math.sin(timeValue) / 2) + 0.5);
		int vertexColorLocation = GL20.glGetUniformLocation(shaderProgramID, "ourColor");
		GL20.glUniform2f(vertexColorLocation, 0.0f, (float) greenValue);
		
		*/
        
        
        matrix44Buffer = model.toBuffer();
		
		
		GL20.glUniformMatrix4(modelMatrixLocation, true, matrix44Buffer);

		matrix44Buffer.clear();
		
		matrix44Buffer = texture.toBuffer();
		
		GL20.glUniformMatrix4(textureMatrixLocation, true, matrix44Buffer);
		

		
        
       // GL20.glEnableVertexAttribArray(0);
       // GL20.glEnableVertexAttribArray(1);
        
      //  GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
        
        GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
     //   GL30.glBindVertexArray(0);
	}
	private void initRenderData(){
		
		matrix44Buffer = BufferUtils.createFloatBuffer(16);
		
		GL20.glUseProgram(shaderProgramID);
		
		modelMatrixLocation = GL20.glGetUniformLocation(shaderProgramID, "modelMatrix");
		textureMatrixLocation = GL20.glGetUniformLocation(shaderProgramID, "textureMatrix");
		
		GL20.glUseProgram(0);
		
		TexturedVertex vertices[];
		
		TexturedVertex v0 = new TexturedVertex(); 
        v0.setXY(0.0f, 1.0f);  v0.setST(0.0f, 1.0f);
        TexturedVertex v1 = new TexturedVertex(); 
        v1.setXY(0.0f, 0.0f);  v1.setST(0.0f, 0.0f);
        TexturedVertex v2 = new TexturedVertex(); 
        v2.setXY(1.0f, 0.0f);  v2.setST(1.0f, 0.0f);
        TexturedVertex v3 = new TexturedVertex(); 
        v3.setXY(1.0f, 1.0f);  v3.setST(1.0f, 1.0f);
        
        vertices = new TexturedVertex[] {v0,v1,v2,v3};
        FloatBuffer verticesFloatBuffer = BufferUtils.createByteBuffer(vertices.length * TexturedVertex.stride).asFloatBuffer();
        for (int i = 0; i < vertices.length; i++) {
            // Add position, color and texture floats to the buffer
            verticesFloatBuffer.put(vertices[i].getElements());
        }
       verticesFloatBuffer.flip();
        byte[] indices = {
                0, 1, 2,
                2, 3, 0
        };
        
        int indicesCount = indices.length;
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

		    setVAO(GL30.glGenVertexArrays());
		    GL30.glBindVertexArray(getVAO());
		    
		    VBO = GL15.glGenBuffers();
		    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesFloatBuffer, GL15.GL_DYNAMIC_DRAW);
	        
		    GL20.glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL11.GL_FLOAT, 
	                false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
		    
	        GL20.glEnableVertexAttribArray(0);
		    
	        // Put the texture coordinates in attribute list 1
	        GL20.glVertexAttribPointer(1, TexturedVertex.textureElementCount, GL11.GL_FLOAT, 
	                false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
	        
	        GL20.glEnableVertexAttribArray(1);
		    
		    EBO = GL15.glGenBuffers();
		    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
	        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, 
	                GL15.GL_STATIC_DRAW);
	        
		   // GL30.glBindVertexArray(0);
		    
			//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			//GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int getVAO() {
		return VAO;
	}

	private void setVAO(int vAO) {
		VAO = vAO;
	}
}