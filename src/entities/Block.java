package entities;

import org.lwjgl.util.vector.Vector3f;

import loader.Loader;
import models.RawModel;
import models.TexturedModel;

public abstract class Block extends Entity {
	
	protected static RawModel cubeModel = Loader.loadOBJ("cube");
	
	protected Block(TexturedModel model, Vector3f position) {
		super(model, position, 0, 0, 0, 1);
	}
}
