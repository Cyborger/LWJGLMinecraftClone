package core;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.Light;
import entities.blocks.LeafBlock;
import entities.blocks.TreeBlock;
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
		world = new World(2, 2, 2);
		createTree();
		frustum = new Frustum();
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			// Update
			camera.move();
			checkForMousePress();

			// Render
			processBlockEntities();
			renderer.render(light, camera);
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

	static void createTree() {
		world.placeBlock(new TreeBlock(new Vector3f(16, 16, 16)));
		world.placeBlock(new TreeBlock(new Vector3f(16, 17, 16)));
		world.placeBlock(new TreeBlock(new Vector3f(16, 18, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 18, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 18, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 18, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 18, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 18, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 18, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 18, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 18, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 18, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 18, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 18, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 18, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 18, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 18, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 18, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 18, 14)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 18, 14)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 18, 14)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 18, 14)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 18, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 18, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 18, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 18, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 18, 14)));
		world.placeBlock(new TreeBlock(new Vector3f(16, 19, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 19, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 19, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 19, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 19, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 19, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 19, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 19, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 19, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 19, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 19, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 19, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 19, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 19, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 19, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 19, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 19, 14)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 19, 14)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 19, 14)));
		world.placeBlock(new LeafBlock(new Vector3f(18, 19, 14)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 19, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 19, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 19, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 19, 18)));
		world.placeBlock(new LeafBlock(new Vector3f(14, 19, 14)));
		world.placeBlock(new TreeBlock(new Vector3f(16, 20, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 20, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 20, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 20, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 20, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 20, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 20, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 20, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 20, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(17, 21, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 21, 17)));
		world.placeBlock(new LeafBlock(new Vector3f(15, 21, 16)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 21, 15)));
		world.placeBlock(new LeafBlock(new Vector3f(16, 21, 16)));
	}
	
	static void cleanUp() {
		renderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
