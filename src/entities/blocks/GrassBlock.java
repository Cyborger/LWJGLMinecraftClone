package entities.blocks;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class GrassBlock extends Block {

	static final int ID = 1;
	static Texture grassTexture = new Texture(Loader.loadTexture("grass"));
	static RawModel cubeModel = Loader.loadOBJ("block");
	static TexturedModel grassModel = new TexturedModel(cubeModel, grassTexture);

	public GrassBlock(Vector3f position) {
		super(grassModel, position);
	}
	
	@Override
	public int GetID() {
		return ID;
	}
}
