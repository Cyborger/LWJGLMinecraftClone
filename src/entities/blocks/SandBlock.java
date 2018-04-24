package entities.blocks;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class SandBlock extends Block {

	static Texture dirtTexture = new Texture(Loader.loadTexture("sand"));
	static RawModel cubeModel = Loader.loadOBJ("block");
	static TexturedModel dirtModel = new TexturedModel(cubeModel, dirtTexture);

	protected SandBlock(TexturedModel model, Vector3f position) {
		super(model, position);
	}
}
