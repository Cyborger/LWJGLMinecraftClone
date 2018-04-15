package entities;

import org.lwjgl.util.vector.Vector3f;

import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class DirtBlock extends Block {

	static Texture dirtTexture = new Texture(Loader.loadTexture("grass"));
	static RawModel cubeModel = Loader.loadOBJ("cube");
	static TexturedModel dirtModel = new TexturedModel(cubeModel, dirtTexture);
	
	public DirtBlock(Vector3f position) {
		super(dirtModel, position);
	}

}
