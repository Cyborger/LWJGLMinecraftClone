package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.BlockHandler;
import entities.Camera;
import entities.Light;
import entities.Player;
import entities.blocks.CrateBlock;
import entities.blocks.GrassBlock;
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
	static int blockHeld;

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
		inventoryHandler.addToInventory(new CrateBlock(null));
		inventoryHandler.addToInventory(new GrassBlock(null));
		BlockHandler.generateHashMap();
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			// Update
			player.update();
			camera.update();
			checkNumberKeysDown();
			mousePicker.update(world, inventoryHandler.getInventory().get(blockHeld));

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
		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			blockHeld = 0;
		else if (Keyboard.isKeyDown(Keyboard.KEY_2))
			blockHeld = 1;
		else if(Keyboard.isKeyDown(Keyboard.KEY_3))
			blockHeld = 2;
		else if(Keyboard.isKeyDown(Keyboard.KEY_4))
			blockHeld = 3;
		else if(Keyboard.isKeyDown(Keyboard.KEY_5))
			blockHeld = 4;
		else if(Keyboard.isKeyDown(Keyboard.KEY_6))
			blockHeld = 5;
		else if(Keyboard.isKeyDown(Keyboard.KEY_7))
			blockHeld = 6;
		else if(Keyboard.isKeyDown(Keyboard.KEY_8))
			blockHeld = 7;
		else if(Keyboard.isKeyDown(Keyboard.KEY_9))
			blockHeld = 9;
		
	}

	static void cleanUp() {
		renderer.cleanUp();
		guiRenderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
