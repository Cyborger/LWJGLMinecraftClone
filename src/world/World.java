package world;

import java.util.ArrayList;
import java.util.List;

import entities.Block;

public class World {
	private List<Chunk> chunks = new ArrayList<Chunk>();

	public World(int chunks_wide, int chunks_high, int chunks_deep) {
		createChunks(chunks_wide, chunks_high, chunks_deep);
	}
	
	public void placeBlock(Block block) {
		for (Chunk chunk : chunks) {
			if (chunk.positionWithinChunk(block.getPosition())) {
				chunk.addBlock(block);
			}
		}
	}
	
	public void createChunks(int width, int height, int depth) {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				for (int z = 0; z < depth; ++z) {
					chunks.add(new Chunk(x * Chunk.SIZE, y * Chunk.SIZE, z * Chunk.SIZE));
				}
			}
		}
	}

	public List<Chunk> getChunks() {
		return chunks;
	}
}
