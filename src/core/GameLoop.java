package core;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.ModelTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;

public class GameLoop {
	
	static float[] vertices = {
		-0.5f, 0.5f, 0f,
		-0.5f, -0.5f, 0f,
		0.5f, -0.5f, 0f,
		0.5f, 0.5f, 0f,
	};
	
	static int[] indices = {
			0, 1, 3,
			3, 1, 2
	};
	
	static float[] textureCoords = {
			0, 0,
			0, 1,
			1, 1,
			1, 0
	};
	
	static RawModel rawModel;
	static ModelTexture texture;
	static TexturedModel texturedModel;
	static Entity block1;
	static Entity block2;
	static StaticShader shader;
	
	public static void main(String[] args) {
		initialize();
		loop();
		cleanUp();
	}
	
	static void initialize() {
		DisplayManager.CreateDisplay();
		rawModel = OBJFileLoader.loadOBJ("cube");
		texture = new ModelTexture(Loader.loadTexture("dirt"));
		texturedModel = new TexturedModel(rawModel, texture);
		block1 = new Entity(texturedModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		shader = new StaticShader();
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			Renderer.prepare();
			shader.start();
			Renderer.render(block1, shader);
			shader.stop();
			DisplayManager.UpdateDisplay();
		}
	}
	
	static void cleanUp() {
		shader.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
