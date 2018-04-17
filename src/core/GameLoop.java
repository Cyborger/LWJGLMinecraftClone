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
import terrain.Chunk;
import terrain.ChunkManager;

public class GameLoop {

	static MasterRenderer renderer;
	static Light light;
	static Camera camera;
	static ChunkManager chunkManager;

	public static void main(String[] args) {
		setup();
		loop();
		cleanUp();
	}

	static void setup() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		light = new Light(new Vector3f(0, 300, 100), new Vector3f(0.75f, 0.75f, 0.75f));
		camera = new Camera(new Vector3f(0, 0, 0));
		chunkManager = new ChunkManager();
		chunkManager.createChunks(1, 1);
		chunkManager.getChunks().get(0).removeBlock(5, 2, 2);
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			camera.move();
			for (Chunk chunk : chunkManager.getChunks()) {
				for (Block block : chunk.getBlocksToRender()) {
					renderer.processEntity(block);
				}
			}
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
			// System.out.println(DisplayManager.getDeltaInMilliseconds());
		}
	}

	static void cleanUp() {
		renderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
