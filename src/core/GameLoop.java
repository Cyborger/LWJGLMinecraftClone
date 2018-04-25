package core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.blocks.DirtBlock;
import guis.GuiRenderer;
import guis.GuiTexture;
import loader.Loader;
import loader.VAOLoader;
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
	static GuiRenderer guiRenderer;
	static boolean leftMousePressed;
	static boolean rightMousePressed;
	static Entity deathPig;
	static List<GuiTexture> guis = new ArrayList<GuiTexture>();
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
		world = new World(2, 2, 2);
		frustum = new Frustum();
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
		Texture pigTexture = new Texture(Loader.loadTexture("tree"));
		RawModel cubeModel = Loader.loadOBJ("death");
		deathPig = new Entity(new TexturedModel(cubeModel, pigTexture), new Vector3f(20,16,20), 0, 0, 0, 0.25f);
		guis.add(new GuiTexture(Loader.loadTexture("health"), new Vector2f(Display.getHeight()/2, Display.getWidth() / 2), new Vector2f(0.5f, 0.5f)));
		guiRenderer = new GuiRenderer(new VAOLoader());
		
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
			guiRenderer.render(guis);
			DisplayManager.UpdateDisplay();
		}
	}

	static void checkForMousePress() {
		if (Mouse.isButtonDown(0) && !leftMousePressed) {
			for (float x = 0; x < 8; x += 0.1) {
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
		if(Mouse.isButtonDown(1) && !rightMousePressed) {
			for (float x = 8; x > 1; x -= 0.1) {
				Vector3f blockCoords = mousePicker.getTerrainPoint(x);
				if (blockCoords != null) {
					int blockX = Math.round(blockCoords.x);
					int blockY = Math.round(blockCoords.y);
					int blockZ = Math.round(blockCoords.z);
					Block blockToAdd = new DirtBlock(new Vector3f(blockX, blockY, blockZ));
					if(world.placeBlock(blockToAdd)) {
						rightMousePressed = Mouse.isButtonDown(1);
						return;
					}
					
				}
			}
		}
		leftMousePressed = Mouse.isButtonDown(0);
		rightMousePressed = Mouse.isButtonDown(1);
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
		guiRenderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
