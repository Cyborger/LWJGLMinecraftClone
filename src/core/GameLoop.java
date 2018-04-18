package core;

<<<<<<< HEAD
=======
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
>>>>>>> 6a0a190fa87079aa44061ffbdf11cd11325803be
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.DirtBlock;
import entities.Light;
import loader.Loader;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import utilities.MousePicker;
import world.Chunk;
import world.World;

public class GameLoop {

	static MasterRenderer renderer;
	static Light light;
	static Camera camera;
	static World world;
	static MousePicker mousePicker;
	
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
		world = new World(1, 1);
<<<<<<< HEAD
		world.placeBlock(new DirtBlock(new Vector3f(9, 8, 9)), 9, 8, 9);
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
=======
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix(), world);
>>>>>>> 6a0a190fa87079aa44061ffbdf11cd11325803be
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			camera.move();
			mousePicker.update();
<<<<<<< HEAD
			System.out.println(mousePicker.getCurrentRay());
			
=======
			System.out.println(mousePicker.getTerrainPoint());
>>>>>>> 6a0a190fa87079aa44061ffbdf11cd11325803be
			for (Chunk chunk : world.getChunks()) {
				for (Block block : chunk.getBlocksToRender()) {
					renderer.processEntity(block);
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_C)) {
				world.getChunks().get(0).removeBlock((int)mousePicker.getTerrainPoint().x, (int)mousePicker.getTerrainPoint().y, (int)mousePicker.getTerrainPoint().z);
			}
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
			//System.out.println(DisplayManager.getDeltaInMilliseconds());
		}
	}

	static void cleanUp() {
		renderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
