package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public abstract class Block extends Entity {

	public boolean hasXPNeighbor;
	public boolean hasXMNeighbor;
	public boolean hasYPNeighbor;
	public boolean hasYMNeighbor;
	public boolean hasZPNeighbor;
	public boolean hasZMNeighbor;

	private int ID = -1;
	
	protected Block(TexturedModel model, Vector3f position) {
		super(model, position, 0, 0, 0, 1);
	}

	public boolean shouldRender() {
		if (!(hasXPNeighbor && hasXMNeighbor && hasYPNeighbor && hasYMNeighbor && hasZPNeighbor && hasZMNeighbor)) {
			return true;
		} else {
			return false;
		}
	}

	public int GetID() {
		return ID;
	}
}
