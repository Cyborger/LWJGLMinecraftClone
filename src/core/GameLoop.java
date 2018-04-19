package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.Light;
import loader.Loader;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import utilities.Frustum;
import utilities.MousePicker;
import world.Chunk;
import world.World;

public class GameLoop {

	static MasterRenderer renderer;
	static Light light;
	static Camera camera;
	static World world;
	static MousePicker mousePicker;
	static Frustum frustum;
	
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
		world = new World(6, 6, 6);
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix(), world);
		frustum = new Frustum();
	}

	static void loop() {
		int counter = 0;
		while (!Display.isCloseRequested()) {
			// Update
			camera.move();
			while (Keyboard.next()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_G) {
					world.removeBlock(8, 8, counter);
					++counter;
				}
			}
			
			// mousePicker.update();
			
			// Render
			processBlockEntities();
			Frustum.getFrustum(camera, renderer);
			renderer.render(light, camera);
			
			
			DisplayManager.UpdateDisplay();
			System.out.println(DisplayManager.getDeltaInMilliseconds());
		}
		
	}

	static void processBlockEntities() {
		for (Chunk chunk : world.getChunks()) {
			if(Frustum.getFrustum(camera, renderer).cubeInFrustum(chunk.x, chunk.y, chunk.z, chunk.x + Chunk.SIZE, chunk.y + Chunk.SIZE, chunk.z + Chunk.SIZE)) {
				for (Block block : chunk.getBlocksToRender()) {
					renderer.processEntity(block);
				}
			}
		}
	}
	
	static void cleanUp() {
		renderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
