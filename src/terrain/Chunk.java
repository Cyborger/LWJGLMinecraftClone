package terrain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.DirtBlock;
import entities.Entity;

public class Chunk {
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Vector3f> blockList = new ArrayList<Vector3f>();
	private final int SIZE = 16;
	
	public Chunk() {
		generateBlocks();
	}
	
	private void generateBlocks() {
		for (int x = 0; x < SIZE; ++x) {
			for (int y = 0; y < SIZE; ++y) {
				for (int z = 0; z < SIZE; ++z) {
					entities.add(new DirtBlock(new Vector3f(x*2, y*2, z*2)));
					blockList.add(new Vector3f(x*2, y*2, z*2));
					
					
				}
			}
		}
		System.out.println(blockList);
	}
	
	public List<Entity> getEntities() {
		return entities;
	}

	public List<Vector3f> getBlockList() {
		return blockList;
	}
	
	public boolean checkForSurroundingBlocks(int i) {
		Vector3f currentChunk = blockList.get(i);
		if(blockList.contains(new Vector3f(currentChunk.x/2, currentChunk.y/2, currentChunk.z/2)) &&
			blockList.contains(new Vector3f(currentChunk.x*2, currentChunk.y*2, currentChunk.z*2))) {
			return true;
		}
		return false;
	}
	
	
}
