package core;

import org.lwjgl.opengl.Display;

import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;

public class GameLoop {
	
	static float[] vertices = {
		-0.5f, 0.5f, 0f,
		-0.5f, -0.5f, 0f,
		0.5f, -0.5f, 0f,
		0.5f, -0.5f, 0f,
		0.5f, 0.5f, 0f,
		-0.5f, 0.5f, 0f 
	};
	
	static RawModel model;
	
	public static void main(String[] args) {
		initialize();
		loop();
		cleanUp();
	}
	
	static void initialize() {
		DisplayManager.CreateDisplay();
		model = Loader.loadToVAO(vertices, 3);
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			Renderer.prepare();
			Renderer.render(model);
			DisplayManager.UpdateDisplay();
		}
	}
	
	static void cleanUp() {
		DisplayManager.CloseDisplay();
	}
}
