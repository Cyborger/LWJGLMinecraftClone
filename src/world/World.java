package world;

import java.util.ArrayList;
import java.util.List;

public class World {
	private List<Chunk> chunks = new ArrayList<Chunk>();

	public World(int chunks_wide, int chunks_deep) {
		createChunks(chunks_wide, chunks_deep);
	}
	
	public void createChunks(int width, int depth) {
		for (int x = 0; x < width; ++x) {
			for (int z = 0; z < depth; ++z) {
				chunks.add(new Chunk(x * Chunk.SIZE, z * Chunk.SIZE));
			}
		}
	}

	public List<Chunk> getChunks() {
		return chunks;
	}
}
