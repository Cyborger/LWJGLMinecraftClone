package world;

import java.util.ArrayList;
import java.util.List;

import entities.Block;

public class World {
	private List<Chunk> chunks = new ArrayList<Chunk>();

	public World(int chunks_wide, int chunks_deep) {
		createChunks(chunks_wide, chunks_deep);
	}
	
	public void placeBlock(Block block, int x, int y, int z) {
		for (Chunk chunk : chunks) {
			if (chunk.positionWithinChunk(x, y, z)) {
				chunk.addBlock(block, x - chunk.chunk_x, y - chunk.chunk_y, z - chunk.chunk_z);
			}
		}
	}
	
	public void createChunks(int width, int depth) {
		for (int x = 0; x < width; ++x) {
			for (int z = 0; z < depth; ++z) {
				chunks.add(new Chunk(x * Chunk.SIZE, 0, z * Chunk.SIZE));
			}
		}
	}

	public List<Chunk> getChunks() {
		return chunks;
	}
}
