package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.Light;
import entities.Player;
import gui.GUIRenderer;
import gui.HUD;
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
	static int blockHeld = 1;

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
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			// Update
			player.update();
			camera.update();
			if (Keyboard.isKeyDown(Keyboard.KEY_1))
				blockHeld = 1;
			else if (Keyboard.isKeyDown(Keyboard.KEY_2))
				blockHeld = 2;
			mousePicker.update(world, blockHeld);

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

	static void cleanUp() {
		renderer.cleanUp();
		guiRenderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
