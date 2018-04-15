package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public abstract class Block extends Entity {
	protected Block(TexturedModel model, Vector3f position) {
		super(model, position, 0, 0, 0, 1);
	}
}
