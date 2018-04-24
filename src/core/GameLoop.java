package core;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.Entity;
import entities.Light;
import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;
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
	static Frustum frustum;
	static MousePicker mousePicker;
	static boolean leftMousePressed;
	static Entity deathPig;
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
		Texture pigTexture = new Texture(Loader.loadTexture("tree"));
		RawModel cubeModel = Loader.loadOBJ("death");
		deathPig = new Entity(new TexturedModel(cubeModel, pigTexture), new Vector3f(20,16,20), 0, 0, 0, 0.25f);
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			// Update
			camera.move();
			checkForMousePress();

			// Render
			processBlockEntities();
			renderer.processEntity(deathPig);
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
		}
	}

	static void checkForMousePress() {
		if (Mouse.isButtonDown(0) && !leftMousePressed) {
			for (float x = 0; x < 8; x += 0.5) {
				Vector3f blockCoords = mousePicker.getTerrainPoint(x);
				if (blockCoords != null) {
					int blockX = Math.round(blockCoords.x);
					int blockY = Math.round(blockCoords.y);
					int blockZ = Math.round(blockCoords.z);
					if(world.removeBlock(blockX, blockY, blockZ)) {
						leftMousePressed = Mouse.isButtonDown(0);
						return;
					}
				}
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
