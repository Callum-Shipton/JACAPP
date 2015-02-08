import org.lwjgl.opengl.GL20;


public class Renderer {
	
	private int shaderProgramID;
	
	public void draw(Image i, TexturedVertex[] quad){
		GL20.glUseProgram(shaderProgramID);
		
	}

}
