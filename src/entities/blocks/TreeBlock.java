package entities.blocks;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import loader.Loader;
import models.RawModel;
import models.Texture;
import models.TexturedModel;

public class TreeBlock extends Block {

	static Texture treeTexture = new Texture(Loader.loadTexture("tree"));
	static RawModel cubeModel = Loader.loadOBJ("block");
	static TexturedModel treeModel = new TexturedModel(cubeModel, treeTexture);

	public TreeBlock(Vector3f position) {
		super(treeModel, position);
	}
}
