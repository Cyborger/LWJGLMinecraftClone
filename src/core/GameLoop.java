package core;

import org.lwjgl.opengl.Display;

import models.RawModel;
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
	
	static RawModel model;
	static StaticShader shader;
	
	public static void main(String[] args) {
		initialize();
		loop();
		cleanUp();
	}
	
	static void initialize() {
		DisplayManager.CreateDisplay();
		float[] matt = {};
		float[] sucks = {};
		model = Loader.loadToVAO(vertices, matt, sucks, indices);
		shader = new StaticShader();
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			Renderer.prepare();
			shader.start();
			Renderer.render(model);
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
