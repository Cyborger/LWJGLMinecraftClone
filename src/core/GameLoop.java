package core;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.DirtBlock;
import entities.Light;
import loader.Loader;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import utilities.MousePicker;
import world.Chunk;
import world.World;

public class GameLoop {

	static MasterRenderer renderer;
	static Light light;
	static Camera camera;
	static World world;
	static MousePicker mousePicker;
	
	public static void main(String[] args) {
		setup();
		loop();
		cleanUp();
	}

	static void setup() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		
		camera = new Camera(new Vector3f(0, 0, 0));
		light = new Light(new Vector3f(0, 300, 100), new Vector3f(0.75f, 0.75f, 0.75f));
		world = new World(2, 2, 2);
		world.removeBlock(22, 7, 19);
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix(), world);
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			// Update
			camera.move();
			mousePicker.update();
			
			// Render
			processBlockEntities();
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
		}
	}

	static void processBlockEntities() {
		for (Chunk chunk : world.getChunks()) {
			for (Block block : chunk.getBlocksToRender()) {
				renderer.processEntity(block);
			}
		}
	}
	
	static void cleanUp() {
		renderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
