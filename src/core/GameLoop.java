package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.BlockHandler;
import entities.Camera;
import entities.Light;
import entities.Player;
import gui.GUIRenderer;
import gui.HUD;
import inventory.InventoryHandler;
import loader.Loader;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import utilities.Frustum;
import utilities.MousePicker;
import world.Chunk;
import world.World;

public class GameLoop {

	static MasterRenderer renderer;
	static GUIRenderer guiRenderer;
	static HUD hud;
	static Light light;
	static Player player;
	static Camera camera;
	static World world;
	static Frustum frustum;
	static MousePicker mousePicker;
	static InventoryHandler inventoryHandler;

	public static void main(String[] args) {
		setup();
		loop();
		cleanUp();
	}

	static void setup() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		guiRenderer = new GUIRenderer();
		hud = new HUD();
		player = new Player(new Vector3f(0, Chunk.SIZE * 5 + 2, 0));
		camera = new Camera(player);
		light = new Light(new Vector3f(0, 300, 100), new Vector3f(0.75f, 0.75f, 0.75f));
		world = new World(7, 10, 7);
		frustum = new Frustum();
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
		inventoryHandler = new InventoryHandler();
		BlockHandler.generateHashMap();
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			// Update
			player.update();
			camera.update();
			checkNumberKeysDown();
			mousePicker.update(world, inventoryHandler);

			// Render
			processBlockEntities();
			renderer.render(light, camera);
			guiRenderer.render(hud.getElementsToRender());
			DisplayManager.UpdateDisplay();
		}
	}

	static void processBlockEntities() {
		frustum.calculate(camera, renderer);
		for (Chunk chunk : world.getChunks()) {
			if (frustum.cubeInFrustum(chunk.x, chunk.y, chunk.z, Chunk.SIZE)) {
				for (Block block : chunk.getBlocksToRender()) {
					renderer.processEntity(block);
				}
			}
		}
	}

	static void checkNumberKeysDown() {
		int[] keys = { Keyboard.KEY_1, Keyboard.KEY_2, Keyboard.KEY_3, Keyboard.KEY_4, Keyboard.KEY_5, Keyboard.KEY_6,
				Keyboard.KEY_7, Keyboard.KEY_8, Keyboard.KEY_9 };

		for (int i = 0; i < keys.length; i++) {
			if (Keyboard.isKeyDown(keys[i])) {
				inventoryHandler.blockHeld = i;
			}
		}

	}

	static void cleanUp() {
		renderer.cleanUp();
		guiRenderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
