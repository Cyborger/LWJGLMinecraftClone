package terrain;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {
	private List<Chunk> chunks = new ArrayList<Chunk>();

	public void createChunks(int width, int depth) {
		for (int x = 0; x < width; ++x) {
			for (int z = 0; z < depth; ++z) {
				chunks.add(new Chunk(x * 32, z * 32));
			}
		}
	}

	public List<Chunk> getChunks() {
		return chunks;
	}
}
