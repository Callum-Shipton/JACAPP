package display;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import math.Vector2;
import object.Entity;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import components.TypeComponent;
import components.graphical.MapGraphics;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL33.*;

public class IRenderer extends Renderer {
	

	private float width;
	private float height;
	private int amount;
	
	
	public IRenderer(Vector2[][] Textures, Vector2 texMax, float width, float height){
		this.width = width;
		this.height = height;
		initRenderData(Textures, texMax);
	}
	
	public IRenderer(HashMap<Vector2, Entity> Textures, Vector2 texMax, float width, float height){
		this.width = width;
		this.height = height;
		initRenderData(Textures, texMax);
	}
	
	public void draw(int Texid){
		glUseProgram(Art.ShaderInst);
		glBindTexture(GL11.GL_TEXTURE_2D, Texid);
		glBindVertexArray(VAO);
        glDrawElementsInstanced(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0, amount);
        glBindVertexArray(0);
        glUseProgram(0);
	}
	public void initRenderData(Vector2[][] textures, Vector2 texMax){
			
		amount = textures.length * textures[0].length;
		
		float[] texture = new float[2];
		float[] translation = new float[2];
		FloatBuffer instanceFloatBuffer = BufferUtils.createByteBuffer(amount * 4 * 4).asFloatBuffer();
		for(int i = 0; i<textures.length; i++){
			for(int j = 0; j<textures[0].length; j++){
				if(textures[i][j] != null){
					texture[0] = textures[i][j].x()/texMax.x();
					texture[1] = textures[i][j].y()/texMax.y();
					translation[0] = i*width;
					translation[1] = j*height;
				}else{
					texture[0] = texMax.x()-1.0f/texMax.x();
					texture[1] = texMax.y()-1.0f/texMax.y();
					translation[0] = 1000*width;
					translation[1] = 1000*width;
				}
				instanceFloatBuffer.put(translation);
				instanceFloatBuffer.put(texture);
			}
		}
		instanceFloatBuffer.flip();
		
		TexturedVertex vertices[];
			
		TexturedVertex v0 = new TexturedVertex(); 
        v0.setXY(0.0f, 1.0f*height);  v0.setST(0.0f, 1.0f/texMax.y());
        TexturedVertex v1 = new TexturedVertex(); 
        v1.setXY(0.0f, 0.0f);  v1.setST(0.0f, 0.0f);
        TexturedVertex v2 = new TexturedVertex(); 
        v2.setXY(1.0f*width, 0.0f);  v2.setST(1.0f/texMax.x(), 0.0f);
        TexturedVertex v3 = new TexturedVertex(); 
        v3.setXY(1.0f*width, 1.0f*height);  v3.setST(1.0f/texMax.x(), 1.0f/texMax.y());
        
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
        VAO = glGenVertexArrays();
		glBindVertexArray(VAO);
			    
	    VBO = glGenBuffers();
	    glBindBuffer(GL_ARRAY_BUFFER, VBO);
	    glBufferData(GL_ARRAY_BUFFER, verticesFloatBuffer, GL_STATIC_DRAW);
			    
	    EBO = glGenBuffers();
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		        
	    glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
			    
        glEnableVertexAttribArray(0);
		    
        // Put the texture coordinates in attribute list 1
        glVertexAttribPointer(1, TexturedVertex.textureElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
	        
        glEnableVertexAttribArray(1);
			    
        int IVBO = glGenBuffers();
	    glBindBuffer(GL15.GL_ARRAY_BUFFER, IVBO);
	    glBufferData(GL15.GL_ARRAY_BUFFER, instanceFloatBuffer, GL_STATIC_DRAW);
			    
	    glVertexAttribPointer(2, TexturedVertex.positionElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
	    glEnableVertexAttribArray(2);
			    
	    glVertexAttribPointer(3, TexturedVertex.textureElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
	    glEnableVertexAttribArray(3);
			    
	    glVertexAttribDivisor(2, 1);
		glVertexAttribDivisor(3, 1);
		        
		glBindVertexArray(0);
			    
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		//GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	public void initRenderData(HashMap<Vector2, Entity> textures, Vector2 texMax){
		
		amount = textures.size();
		
		float[] texture = new float[2];
		float[] translation = new float[2];
		FloatBuffer instanceFloatBuffer = BufferUtils.createByteBuffer(amount * 4 * 4).asFloatBuffer();
		Iterator<Entry<Vector2, Entity>> iterator = textures.entrySet().iterator() ;
		while(iterator.hasNext()){
			Entity wall = iterator.next().getValue();
			MapGraphics MG = (MapGraphics) wall.getComponent(TypeComponent.GRAPHICS);
			Vector2 textured = MG.getMapPos();
			Vector2 pos = new Vector2(MG.getX(), MG.getY());
			texture[0] = textured.x()/texMax.x();
			texture[1] = textured.y()/texMax.y();
			translation[0] = pos.x();
			translation[1] = pos.y();
			instanceFloatBuffer.put(translation);
			instanceFloatBuffer.put(texture);
		}

		instanceFloatBuffer.flip();
		
		TexturedVertex vertices[];
			
		TexturedVertex v0 = new TexturedVertex(); 
        v0.setXY(0.0f, 1.0f*height);  v0.setST(0.0f, 1.0f/texMax.y());
        TexturedVertex v1 = new TexturedVertex(); 
        v1.setXY(0.0f, 0.0f);  v1.setST(0.0f, 0.0f);
        TexturedVertex v2 = new TexturedVertex(); 
        v2.setXY(1.0f*width, 0.0f);  v2.setST(1.0f/texMax.x(), 0.0f);
        TexturedVertex v3 = new TexturedVertex(); 
        v3.setXY(1.0f*width, 1.0f*height);  v3.setST(1.0f/texMax.x(), 1.0f/texMax.y());
        
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
        VAO = glGenVertexArrays();
		glBindVertexArray(VAO);
			    
	    VBO = glGenBuffers();
	    glBindBuffer(GL_ARRAY_BUFFER, VBO);
	    glBufferData(GL_ARRAY_BUFFER, verticesFloatBuffer, GL_STATIC_DRAW);
			    
	    EBO = glGenBuffers();
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		        
	    glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
			    
        glEnableVertexAttribArray(0);
		    
        // Put the texture coordinates in attribute list 1
        glVertexAttribPointer(1, TexturedVertex.textureElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
	        
        glEnableVertexAttribArray(1);
			    
        int IVBO = glGenBuffers();
	    glBindBuffer(GL15.GL_ARRAY_BUFFER, IVBO);
	    glBufferData(GL15.GL_ARRAY_BUFFER, instanceFloatBuffer, GL_STATIC_DRAW);
			    
	    glVertexAttribPointer(2, TexturedVertex.positionElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
	    glEnableVertexAttribArray(2);
			    
	    glVertexAttribPointer(3, TexturedVertex.textureElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
	    glEnableVertexAttribArray(3);
			    
	    glVertexAttribDivisor(2, 1);
		glVertexAttribDivisor(3, 1);
		        
		glBindVertexArray(0);
			    
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		//GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
}