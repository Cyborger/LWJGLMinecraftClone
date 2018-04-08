package core;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;

public class GameLoop {
	
	public static void main(String[] args) {
		initialize();
		loop();
		cleanUp();
	}
	
	static void initialize() {
		DisplayManager.CreateDisplay();
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			DisplayManager.UpdateDisplay();
		}
		DisplayManager.CloseDisplay();
	}
	
	static void cleanUp() {
		// Clean up textures and VAOs in memory
	}
}
