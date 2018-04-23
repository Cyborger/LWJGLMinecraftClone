package core;

<<<<<<< HEAD
import java.util.Arrays;

import org.lwjgl.input.Keyboard;
=======
>>>>>>> 158e33065587620c3b89c38ead5c134d09d535c0
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.Light;
import loader.Loader;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import utilities.Frustum;
import world.Chunk;
import world.World;

public class GameLoop {

	static MasterRenderer renderer;
	static Light light;
	static Camera camera;
	static World world;
	static Frustum frustum;
	
	public static void main(String[] args) {
		setup();
		loop();
		cleanUp();
	}

	static void setup() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		camera = new Camera(new Vector3f(-5, 0, 0));
		light = new Light(new Vector3f(0, 300, 100), new Vector3f(0.75f, 0.75f, 0.75f));
		world = new World(2, 1, 2);
		frustum = new Frustum();
	}

	static void loop() {
<<<<<<< HEAD
		int counter = 0;
		boolean done = false;
		while (!Display.isCloseRequested()) {
			// Update
			camera.move();
			mousePicker.update();			
			while(Keyboard.next()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_G) {
					if(!done) {
						if(world.blockToBreak(mousePicker.getTerrainPoint()) != new int[] {-1, -1, -1}) {
							done = true;
							int[] test = world.blockToBreak(mousePicker.getTerrainPoint());
							world.removeBlock(test[0], test[1], test[2]);
						} else {
							done = true;
						}
					} else if(done && !Keyboard.isKeyDown(Keyboard.KEY_W)) {
						done = false;
					}
					
				} else if (Keyboard.getEventKey() == Keyboard.KEY_H) {
					world.placeBlock(new DirtBlock(new Vector3f(7, 7, --counter)));
				}
			}
=======
		while (!Display.isCloseRequested()) {
			// Update
			camera.move();

>>>>>>> 158e33065587620c3b89c38ead5c134d09d535c0
			// Render
			processBlockEntities();
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
<<<<<<< HEAD
			//System.out.println(DisplayManager.getDeltaInMilliseconds());
=======
>>>>>>> 158e33065587620c3b89c38ead5c134d09d535c0
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
