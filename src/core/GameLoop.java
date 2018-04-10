package core;

import org.lwjgl.opengl.Display;

import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

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
	
	static RawModel model;		
	static ModelTexture texture;
	static TexturedModel texturedModel;
	static StaticShader shader;
	
	public static void main(String[] args) {
		initialize();
		loop();
		cleanUp();
	}
	
	static void initialize() {
		DisplayManager.CreateDisplay();
		model = Loader.loadToVAO(vertices, textureCoords, indices);
		texture = new ModelTexture(Loader.loadTexture("dirt"));
		texturedModel = new TexturedModel(model, texture);
		shader = new StaticShader();
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			Renderer.prepare();
			shader.start();
			Renderer.render(texturedModel);
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
