package entities.blocks;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class CrateBlock extends Block {

	static final int ID = 0;
	static Texture crateTexture = new Texture(Loader.loadTexture("crate"));
	static RawModel cubeModel = Loader.loadOBJ("block");
	static TexturedModel crateModel = new TexturedModel(cubeModel, crateTexture);

	public CrateBlock(Vector3f position) {
		super(crateModel, position);
	}
	
	
	@Override
	public int GetID() {
		return ID;
	}
}

