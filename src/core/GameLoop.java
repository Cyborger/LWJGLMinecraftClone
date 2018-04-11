package core;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.ModelTexture;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;

public class GameLoop {
	
    static float[] vertices = {            
            -0.5f,0.5f,0,   
            -0.5f,-0.5f,0,  
            0.5f,-0.5f,0,   
            0.5f,0.5f,0,        
             
            -0.5f,0.5f,1,   
            -0.5f,-0.5f,1,  
            0.5f,-0.5f,1,   
            0.5f,0.5f,1,
             
            0.5f,0.5f,0,    
            0.5f,-0.5f,0,   
            0.5f,-0.5f,1,   
            0.5f,0.5f,1,
             
            -0.5f,0.5f,0,   
            -0.5f,-0.5f,0,  
            -0.5f,-0.5f,1,  
            -0.5f,0.5f,1,
             
            -0.5f,0.5f,1,
            -0.5f,0.5f,0,
            0.5f,0.5f,0,
            0.5f,0.5f,1,
             
            -0.5f,-0.5f,1,
            -0.5f,-0.5f,0,
            0.5f,-0.5f,0,
            0.5f,-0.5f,1
             
    };
     
    static float[] textureCoords = {
             
            0,0,
            0,1,
            1,1,
            1,0,            
            0,0,
            0,1,
            1,1,
            1,0,            
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0

             
    };
     
    static int[] indices = {
            0,1,3,  
            3,1,2,  
            4,5,7,
            7,5,6,
            8,9,11,
            11,9,10,
            12,13,15,
            15,13,14,   
            16,17,19,
            19,17,18,
            20,21,23,
            23,21,22

    };
	
	static RawModel cubeModel;
	static ModelTexture dirtTexture;
	static ModelTexture sandTexture;
	static TexturedModel dirtModel;
	static TexturedModel sandModel;
	static Entity dirtEntity1;
	static Entity dirtEntity2;
	static Entity sandEntity1;
	static Entity sandEntity2;
	static Camera camera;
	static StaticShader shader;
	
	public static void main(String[] args) {
		initialize();
		loop();
		cleanUp();
	}
	
	static void initialize() {
		DisplayManager.CreateDisplay();
		shader = new StaticShader();
		Renderer.initialize(shader);
		cubeModel = Loader.loadToVAO(vertices, textureCoords, indices);
		dirtTexture = new ModelTexture(Loader.loadTexture("dirt"));
		sandTexture = new ModelTexture(Loader.loadTexture("sand"));
		dirtModel = new TexturedModel(cubeModel, dirtTexture);
		sandModel = new TexturedModel(cubeModel, sandTexture);
		dirtEntity1 = new Entity(dirtModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		dirtEntity2 = new Entity(dirtModel, new Vector3f(1, 0, 1), 0, 0, 0, 1);
		sandEntity1 = new Entity(sandModel, new Vector3f(1, 0, 0), 0, 0, 0, 1);
		sandEntity2 = new Entity(sandModel, new Vector3f(0, 0, 1), 0, 0, 0, 1);
		camera = new Camera();
	}
	
	static void loop() {
		while(!Display.isCloseRequested()) {
			camera.move();
			Renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			Renderer.render(dirtEntity1, shader);
			Renderer.render(dirtEntity2, shader);
			Renderer.render(sandEntity1, shader);
			Renderer.render(sandEntity2, shader);
			shader.stop();
			DisplayManager.UpdateDisplay();
		}
	}
	
	static void cleanUp() {
		shader.cleanUp();
		Loader.cleanUp();
		DisplayManager.CloseDisplay();
	}
}
