package core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import loader.Loader;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import terrain.Chunk;

public class GameLoop {
	
	static MasterRenderer renderer;
	static Chunk testChunk;
	static Light light;
	static Camera camera;
	static List<Entity> entities = new ArrayList<Entity>();;
	
	public static void main(String[] args) {
		setup();
		loop();
		cleanUp();
	}
	
	static void setup() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		light = new Light(new Vector3f(0, 100, 100), new Vector3f(1, 1, 1));
		camera = new Camera(new Vector3f(16, 34, 16));
		testChunk = new Chunk();
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			camera.move();
			for (Entity entity : testChunk.getEntities()) {
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
			System.out.println(DisplayManager.getDeltaInMilliseconds());
		}
	}
	
	static void cleanUp() {
		renderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
