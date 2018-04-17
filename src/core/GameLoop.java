package core;

import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
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

	static Random random = new Random();
	
	static float r = 0.75f;
	static float g = 0.75f;
	static float b = 0.75f;
	
	public static void main(String[] args) {
		setup();
		loop();
		cleanUp();
	}

	static void setup() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		
		camera = new Camera(new Vector3f(0, 0, 0));
		world = new World(1, 1);
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			camera.move();
			//r = random.nextFloat();
			//g = random.nextFloat();  
			//b = random.nextFloat();
			light = new Light(new Vector3f(0, 300, 100), new Vector3f(r, g, b));
			mousePicker.update();
			System.out.println(mousePicker.getCurrentRay());
			for (Chunk chunk : world.getChunks()) {
				for (Block block : chunk.getBlocksToRender()) {
					renderer.processEntity(block);
				}
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
