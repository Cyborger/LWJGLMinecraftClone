package core;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
>>>>>>> b2558a61b5c4fad7217fc2e904e15d33bb080df6
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.Camera;
import entities.Light;
<<<<<<< HEAD
import loader.Loader;
=======
import entities.blocks.DirtBlock;
import guis.GuiRenderer;
import guis.GuiTexture;
import loader.Loader;
import loader.VAOLoader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;
>>>>>>> b2558a61b5c4fad7217fc2e904e15d33bb080df6
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
<<<<<<< HEAD
	
=======
	static Entity deathPig;
	static List<GuiTexture> guis = new ArrayList<GuiTexture>();
>>>>>>> b2558a61b5c4fad7217fc2e904e15d33bb080df6
	public static void main(String[] args) {
		setup();
		loop();
		cleanUp();
	}

	static void setup() {
		DisplayManager.CreateDisplay();
		renderer = new MasterRenderer();
		camera = new Camera(new Vector3f(0, Chunk.SIZE * 5 + 2, 0));
		light = new Light(new Vector3f(0, 300, 100), new Vector3f(0.75f, 0.75f, 0.75f));
		world = new World(7, 10, 7);
		frustum = new Frustum();
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());
<<<<<<< HEAD
=======
		Texture pigTexture = new Texture(Loader.loadTexture("tree"));
		RawModel cubeModel = Loader.loadOBJ("death");
		deathPig = new Entity(new TexturedModel(cubeModel, pigTexture), new Vector3f(20,16,20), 0, 0, 0, 0.25f);
		guis.add(new GuiTexture(Loader.loadTexture("health"), new Vector2f(Display.getHeight()/2, Display.getWidth() / 2), new Vector2f(0.5f, 0.5f)));
		guiRenderer = new GuiRenderer(new VAOLoader());
		
>>>>>>> b2558a61b5c4fad7217fc2e904e15d33bb080df6
	}

	static void loop() {
		while (!Display.isCloseRequested()) {
			// Update
			camera.move();
			mousePicker.update(world);

			// Render
			processBlockEntities();
			renderer.render(light, camera);
			guiRenderer.render(guis);
			DisplayManager.UpdateDisplay();
			System.out.println(DisplayManager.getDeltaInMilliseconds());
		}
	}

	static void processBlockEntities() {
		frustum.calculate(camera, renderer);
		for (Chunk chunk : world.getChunks()) {
			if (frustum.cubeInFrustum(chunk.x, chunk.y, chunk.z, chunk.x + Chunk.SIZE,
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
