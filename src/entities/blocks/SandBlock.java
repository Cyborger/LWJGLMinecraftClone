package entities.blocks;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class SandBlock extends Block {

	static final int ID = 3;
	static Texture sandTexture = new Texture(Loader.loadTexture("sand"));
	static RawModel cubeModel = Loader.loadOBJ("block");
	static TexturedModel sandModel = new TexturedModel(cubeModel, sandTexture);

	public SandBlock(Vector3f position) {
		super(sandModel, position);
	}
}
