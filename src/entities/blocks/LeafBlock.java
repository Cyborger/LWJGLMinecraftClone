package entities.blocks;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class LeafBlock extends Block {
	
	static Texture leafTexture = new Texture(Loader.loadTexture("leaves"));
	static RawModel cubeModel = Loader.loadOBJ("block");
	static TexturedModel leafModel = new TexturedModel(cubeModel, leafTexture);
	
	public LeafBlock(Vector3f position) {
		super(leafModel, position);
	}
}
