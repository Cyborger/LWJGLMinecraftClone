package entities;

import org.lwjgl.util.vector.Vector3f;

import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class DirtBlock extends Block {

	static Texture dirtTexture = new Texture(Loader.loadTexture("dirtBlockTexture"));
	static RawModel cubeModel = Loader.loadOBJ("block");
	static TexturedModel dirtModel = new TexturedModel(cubeModel, dirtTexture);

	public DirtBlock(Vector3f position) {
		super(dirtModel, position);
	}

}
