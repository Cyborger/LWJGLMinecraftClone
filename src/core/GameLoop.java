package core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import loader.Loader;
import models.Texture;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;

public class GameLoop {
	
	static MasterRenderer renderer;
	static Loader loader;
	static RawModel cubeModel;
	static Texture dirtTexture;
	static Texture sandTexture;
	static TexturedModel dirtModel;
	static TexturedModel sandModel;
	static Light light;
	static Camera camera;
	static List<Entity> entities = new ArrayList<Entity>();;
	
	public static void main(String[] args) {
		setup();
		loop();
		cleanUp();
	}
	
	static void setup() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		loader = new Loader();
		cubeModel = loader.loadOBJ("cube");
		dirtTexture = new Texture(loader.loadTexture("grass"));
		sandTexture = new Texture(loader.loadTexture("sand"));
		dirtModel = new TexturedModel(cubeModel, dirtTexture);
		sandModel = new TexturedModel(cubeModel, sandTexture);
		light = new Light(new Vector3f(0, 100, 100), new Vector3f(1, 1, 1));
		camera = new Camera(new Vector3f(16, 34, 16));
		generateTerrain();
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			camera.move();
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
			System.out.println(DisplayManager.getDeltaInMilliseconds());
		}
	}
	
	static void cleanUp() {
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
	
	static void generateTerrain() {
		int size = 16;
		for (int x = 0; x < size; ++x) {
			for (int y = 0; y < size; ++y) {
				for (int z = 0; z < size; ++z) {
					entities.add(new Entity(dirtModel, new Vector3f(x*2, y*2, z*2), 0, 0, 0, 1));
				}
			}
		}
	}
}
