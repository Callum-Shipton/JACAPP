package display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import logging.Logger;

public class Shader {
	
	private int programID;
	private int vertexID;
	private int fragID;

	
	public Shader(String vertexShader, String fragShader, List<String> attr) {
		vertexID = loadShader(vertexShader, GL_VERTEX_SHADER);
		fragID = loadShader(fragShader, GL_FRAGMENT_SHADER);
		
		programID = glCreateProgram();
		
		glAttachShader(programID, vertexID);
		glAttachShader(programID, fragID);
		
		int i=0;
		for(String a: attr) {
			glBindAttribLocation(programID, i++, a);
		}
		
		glLinkProgram(programID);
		glValidateProgram(programID);
		
		int error = glGetError();

		if (error != GL_NO_ERROR) {
			Logger.error("OpenGL Error: " + error);
		}
	}
	
	private int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID;

		try {
			BufferedReader reader = new BufferedReader(

					new InputStreamReader(Shader.class.getResourceAsStream(filename)));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			Logger.error("Could not read shader file.");
			Logger.error(e);
			System.exit(-1);
		}

		shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);

		String infoLog = glGetShaderInfoLog(shaderID, glGetShaderi(shaderID, GL_INFO_LOG_LENGTH));

		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new RuntimeException("Failure in compiling " + filename + " shader. Error log:\n" + infoLog);
		}

		return shaderID;
	}

	public int getProgramID() {
		return programID;
	}
}
