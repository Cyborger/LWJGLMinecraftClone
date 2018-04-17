package world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.DirtBlock;

public class Chunk {

	public int chunk_x;
	public int chunk_z;
	public static final int SIZE = 16;
	
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
					Block dirtBlock = new DirtBlock(new Vector3f(chunk_x + x, y, chunk_z + z));
					addBlock(dirtBlock, x, y, z);
				}
			}
		}
	}

	public List<Block> getBlocksToRender() {
		return blocksToRender;
	}
	
	public void addBlock(Block block, int x, int y, int z) {
		blockArray[x][y][z] = block;
		updateBlockAndNeighbors(x, y, z);
	}

	public void removeBlock(int x, int y, int z) {
		blocksToRender.remove(blockArray[x][y][z]);
		blockArray[x][y][z] = null;
		updateBlockAndNeighbors(x, y, z);
	}

	public void updateBlockAndNeighbors(int x, int y, int z) {
		updateNeighborFlags(x, y, z);
		updateNeighborFlags(x + 1, y, z);
		updateNeighborFlags(x - 1, y, z);
		updateNeighborFlags(x, y + 1, z);
		updateNeighborFlags(x, y - 1, z);
		updateNeighborFlags(x, y, z + 1);
		updateNeighborFlags(x, y, z - 1);
	}

	public void updateNeighborFlags(int x, int y, int z) {
		if (blockLocationOutOfBounds(x, y, z) || !spotInBlockArrayFilled(x, y, z)) {
			return;
		}

		Block block = blockArray[x][y][z];
		block.hasXPNeighbor = (spotInBlockArrayFilled(x + 1, y, z)) ? true : false;
		block.hasXMNeighbor = (spotInBlockArrayFilled(x - 1, y, z)) ? true : false;
		block.hasYPNeighbor = (spotInBlockArrayFilled(x, y + 1, z)) ? true : false;
		block.hasYMNeighbor = (spotInBlockArrayFilled(x, y - 1, z)) ? true : false;
		block.hasZPNeighbor = (spotInBlockArrayFilled(x, y, z + 1)) ? true : false;
		block.hasZMNeighbor = (spotInBlockArrayFilled(x, y, z - 1)) ? true : false;

		if (block.shouldRender() && !blocksToRender.contains(block)) {
			blocksToRender.add(block);
		} else if (!block.shouldRender() && blocksToRender.contains(block)) {
			blocksToRender.remove(block);
		}
	}

	private boolean spotInBlockArrayFilled(int x, int y, int z) {
		if (blockLocationOutOfBounds(x, y, z)) {
			return false;
		}
		if (blockArray[x][y][z] != null) {
			return true;
		} else {
			return false;
		}
	}
		
	private boolean blockLocationOutOfBounds(int x, int y, int z) {
		if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || z < 0 || z >= SIZE) {
			return true;
		} else {
			return false;
		}
	}
}
