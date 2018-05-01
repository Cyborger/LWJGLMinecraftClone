package entities.blocks;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class DirtBlock extends Block {

	static final int ID = 0;
	static Texture dirtTexture = new Texture(Loader.loadTexture("dirt"));
	static RawModel cubeModel = Loader.loadOBJ("block");
	static TexturedModel dirtModel = new TexturedModel(cubeModel, dirtTexture);

	public DirtBlock(Vector3f position) {
		super(dirtModel, position);
	}

	@Override
	public int GetID() {
		return ID;
	}
}
