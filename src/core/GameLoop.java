package core;

import org.lwjgl.input.Mouse;
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
import utilities.MousePicker;

public class GameLoop {

	static MasterRenderer renderer;
	static Light light;
	static Camera camera;
	static World world;
	static Frustum frustum;
	static MousePicker mousePicker;
	static boolean leftMousePressed;

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
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			// Update
			camera.move();
			mousePicker.update();
			checkForMousePress();

			// Render
			processBlockEntities();
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
		}
	}

	static void checkForMousePress() {
		if (Mouse.isButtonDown(0) && !leftMousePressed) {
			Vector3f blockCoords = mousePicker.getTerrainPoint();
			if (blockCoords != null) {
				int blockX = (int) Math.floor(blockCoords.x);
				int blockY = (int) Math.floor(blockCoords.y);
				int blockZ = (int) Math.floor(blockCoords.z);
				world.removeBlock(blockX, blockY, blockZ);
			}
		}
		leftMousePressed = Mouse.isButtonDown(0);
	}

	static void processBlockEntities() {
		for (Chunk chunk : world.getChunks()) {
			if (Frustum.getFrustum(camera, renderer).cubeInFrustum(chunk.x, chunk.y, chunk.z, chunk.x + Chunk.SIZE,
					chunk.y + Chunk.SIZE, chunk.z + Chunk.SIZE)) {
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
