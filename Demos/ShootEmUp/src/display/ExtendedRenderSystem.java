package display;

import java.util.Arrays;

public class ExtendedRenderSystem extends BaseRenderSystem {
	
	public static IRenderer irBack;
	public static IRenderer irWall;
	public static IRenderer irFloor;
	
	
	@Override
	protected void initShaders() {
		// Create a new shader program that links both shaders
		ShaderBase = new Shader("/Shaders/CameraVertexShader.glsl","/Shaders/PlayerFadeFragmentShader.glsl",Arrays.asList("pos","tex"));
		ShaderInst = new Shader("/Shaders/CameraIVertexShader.glsl","/Shaders/PlayerFadeIFragmentShader.glsl",Arrays.asList("pos","tex","trans","text"));
		ShaderStat = new Shader("/Shaders/StaticVertexShader.glsl","/Shaders/BaseFragmentShader.glsl",Arrays.asList("pos","tex"));
	}
}
