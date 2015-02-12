package Display;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Main.ShootEmUp;
import Math.Matrix4;

public class Art {
	public static String grass = "res/img/grass.png";
	public static int grassID;

	public static String face = "res/img/awesomeface.png";
	public static int faceID;

	public static String player = "res/img/Player.png";
	public static int playerID;

	public static String particle = "res/img/Particle.png";
	public static int particleID;

	public static String level1 = "res/levels/level1.png";
	public static int levelID;
	
	public static int ShaderBase;

	private static void initShaders() {
		// Load the vertex shader
		int vsId = loadShader("src/Display/VertexShader.glsl",
				GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		int fsId = loadShader("src/Display/FragmentShader.glsl",
				GL20.GL_FRAGMENT_SHADER);

		// Create a new shader program that links both shaders
		ShaderBase = GL20.glCreateProgram();
		GL20.glAttachShader(ShaderBase, vsId);
		GL20.glAttachShader(ShaderBase, fsId);

		// Position information will be attribute 0
		GL20.glBindAttribLocation(ShaderBase, 0, "pos");
		// Textute information will be attribute 1
		GL20.glBindAttribLocation(ShaderBase, 1, "tex");

		GL20.glLinkProgram(ShaderBase);
		GL20.glValidateProgram(ShaderBase);

	}

	public static int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}

		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);

		String infoLog = GL20.glGetShaderInfoLog(shaderID,
				GL20.glGetShaderi(shaderID, GL20.GL_INFO_LOG_LENGTH));

		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Failure in compiling " + filename
					+ " shader. Error log:\n" + infoLog);
		else {
			System.out.print("Compiling " + filename + " shader successful.");
			if (infoLog != null && !(infoLog = infoLog.trim()).isEmpty())
				System.out.println(" Log:\n" + infoLog);
			else
				System.out.println();
		}

		return shaderID;
	}

	private static void initTextures() {

		faceID = GL11.glGenTextures();
		Image texIm = new Image(Art.face);
		ByteBuffer buf = texIm.byteBuffer();
		bindTexture(faceID, texIm, buf);
		
		
		faceID = GL11.glGenTextures();
		texIm = new Image(Art.player);
		buf = texIm.byteBuffer();
		bindTexture(playerID, texIm, buf);
		
		faceID = GL11.glGenTextures();
		texIm = new Image(Art.particle);
		buf = texIm.byteBuffer();
		bindTexture(particleID, texIm, buf);
		
		/* Template for other Texs
		
		faceID = GL11.glGenTextures();
		texIm = new Image(Art.player);
		buf = texIm.byteBuffer();
		bindTexture(playerID, texIm, buf);

		*/
	}

	private static void bindTexture(int ID, Image texIm, ByteBuffer buf) {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);

		// All RGB bytes are aligned to each other and each component is 1 byte
				GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

				// Upload the texture data and generate mip maps (for scaling)
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, texIm.getWidth(),
						texIm.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
				GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

				// Setup the ST coordinate system
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
						GL11.GL_REPEAT);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
						GL11.GL_REPEAT);

				// Setup what to do when the texture has to be scaled
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
						GL11.GL_LINEAR);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
						GL11.GL_LINEAR_MIPMAP_LINEAR);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		
	}

	private static void initShaderUniforms() {
		GL20.glUseProgram(ShaderBase);
		int projectionMatrixLocation = GL20.glGetUniformLocation(ShaderBase,
				"projectionMatrix");

		Matrix4 projectionMatrix = new Matrix4();
		projectionMatrix.clearToOrtho(0, ShootEmUp.WIDTH, ShootEmUp.HEIGHT, 0, -1.0f, 1.0f);
		projectionMatrix.transpose();
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		matrix44Buffer = projectionMatrix.toBuffer();

		GL20.glUniformMatrix4(projectionMatrixLocation, true, matrix44Buffer);

		GL20.glUseProgram(0);

	}

	public static void init() {
		initShaders();
		initShaderUniforms();
		initTextures();
	}

}
