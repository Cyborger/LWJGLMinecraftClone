package terrain;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {
	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	public void createChunks(int width, int height) {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++ y) {
				chunks.add(new Chunk(x * 32, y * 32));
			}
		}
	}
	
	public List<Chunk> getChunks() {
		return chunks;
	}
}
