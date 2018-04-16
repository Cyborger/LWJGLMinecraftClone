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
					Block dirtBlock = new DirtBlock(new Vector3f(chunk_x + x * 2, y * 2, chunk_z + z * 2));
					addBlock(dirtBlock, x, y, z);
				}
			}
		}
	}

	public void addBlock(Block block, int x, int y, int z) {
		blockArray[x][y][z] = block;
		updateBlockNeighborFlags(x, y, z);
		updateBlockNeighborFlags(x + 1, y, z);
		updateBlockNeighborFlags(x - 1, y, z);
		updateBlockNeighborFlags(x, y + 1, z);
		updateBlockNeighborFlags(x, y - 1, z);
		updateBlockNeighborFlags(x, y, z + 1);
		updateBlockNeighborFlags(x, y, z - 1);

	}

	private void updateBlockNeighborFlags(int x, int y, int z) {
		if (!spotTaken(x, y, z))
			return;
		Block block = blockArray[x][y][z];
		if (spotTaken(x + 1, y, z))
			block.hasXPNeighbor = true;
		if (spotTaken(x - 1, y, z))
			block.hasXMNeighbor = true;
		if (spotTaken(x, y + 1, z))
			block.hasYPNeighbor = true;
		if (spotTaken(x, y - 1, z))
			block.hasYMNeighbor = true;
		if (spotTaken(x, y, z + 1))
			block.hasZPNeighbor = true;
		if (spotTaken(x, y, z - 1))
			block.hasZMNeighbor = true;
		if (block.shouldRender()) {
			blocksToRender.add(block);
		}
	}

	private boolean spotTaken(int x, int y, int z) {
		if (!validIndexOfBlockArray(x, y, z)) {
			return false;
		}
		if (blockArray[x][y][z] == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean validIndexOfBlockArray(int x, int y, int z) {
		if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || z < 0 || z >= SIZE) {
			return false;
		} else {
			return true;
		}
	}

	public List<Block> getBlocksToRender() {
		return blocksToRender;
	}
}
