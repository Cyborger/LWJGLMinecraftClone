package terrain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.DirtBlock;

public class Chunk {
	
	private final int SIZE = 16;
	private int chunk_x;
	private int chunk_z;
	private Block[][][] blockArray = new Block[SIZE][SIZE][SIZE];
	private List<Block> blocksToRender = new ArrayList<Block>();
	
	public Chunk(int x, int z) {
		this.chunk_x = x;
		this.chunk_z = z;
		generateBlocks();
	}
	
	private void generateBlocks() {
		for (int x = 0; x < SIZE; ++x) {
			for (int y = 0; y < SIZE; ++y) {
				for (int z = 0; z < SIZE; ++z) {
					Block dirtBlock =  new DirtBlock(new Vector3f(chunk_x + x*2, y*2, chunk_z + z*2));
					blockArray[z][y][x] = dirtBlock;
					if(z == 0 || x == 0 || y == 0 || z == SIZE - 1 || x == SIZE - 1 || y == SIZE - 1) {
						blocksToRender.add(dirtBlock);
					}
				}
			}
		}
	}
	
	public List<Block> getBlocksToRender() {
		return blocksToRender;
	}
}
