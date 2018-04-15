package terrain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.DirtBlock;
import entities.Entity;

public class Chunk {
	
	private List<Entity> entities = new ArrayList<Entity>();
	private final int SIZE = 16;
	
	public Chunk() {
		generateBlocks();
	}
	
	private void generateBlocks() {
		for (int x = 0; x < SIZE; ++x) {
			for (int y = 0; y < SIZE; ++y) {
				for (int z = 0; z < SIZE; ++z) {
					entities.add(new DirtBlock(new Vector3f(x*2, y*2, z*2)));
				}
			}
		}
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
}
