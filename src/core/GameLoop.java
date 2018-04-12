package core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.ModelTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import objConverter.OBJLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import shaders.StaticShader;

public class GameLoop {
	
	static MasterRenderer renderer;
	static RawModel cubeModel;
	static ModelTexture dirtTexture;
	static ModelTexture sandTexture;
	static TexturedModel dirtModel;
	static TexturedModel sandModel;
	static Light light;
	static Camera camera;
	static List<Entity> entities = new ArrayList<Entity>();;
	
	public static void main(String[] args) {
		initialize();
		loop();
		cleanUp();
	}
	
	static void initialize() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		ModelData modelData = OBJFileLoader.loadOBJ("cube");
		cubeModel = Loader.loadToVAO(modelData.getVertices(), modelData.getTextureCoords(),
				modelData.getNormals(), modelData.getIndices());
		dirtTexture = new ModelTexture(Loader.loadTexture("dirt"));
		sandTexture = new ModelTexture(Loader.loadTexture("sand"));
		dirtModel = new TexturedModel(cubeModel, dirtTexture);
		sandModel = new TexturedModel(cubeModel, sandTexture);
		entities.add(new Entity(dirtModel, new Vector3f(0, 0, 0), 0, 0, 0, 1));
		light = new Light(new Vector3f(0, 0, 20), new Vector3f(1, 1, 1));
		camera = new Camera();
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			camera.move();
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.UpdateDisplay();
		}
	}
	
	static void cleanUp() {
		renderer.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
