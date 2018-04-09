package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderLoader {
	public static int loadShader(String file, int type) {
		String shaderSource = buildShaderSource(file);
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Failure to compile shader: " + file);
			System.exit(-1);
		}
		return shaderID;
	}
	
	private static String buildShaderSource(String file) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			do {
				line = reader.readLine();
				shaderSource.append(line).append("\n");
			} while(line != null);
			reader.close();
		} catch(IOException e) {
			System.err.println("Failure to loader shader file: " + file);
			e.printStackTrace();
			System.exit(-1);
		}
		return shaderSource.toString();
	}
}
