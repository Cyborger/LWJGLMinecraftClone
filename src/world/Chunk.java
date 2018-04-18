package world;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.DirtBlock;

public class Chunk {

	public static final int SIZE = 16;
	public int x;
	public int y;
	public int z;
	
	public boolean hasXPNeighbor;
	public boolean hasXMNeighbor;
	public boolean hasYPNeighbor;
	public boolean hasYMNeighbor;
	public boolean hasZPNeighbor;
	public boolean hasZMNeighbor;
	
	private Block[][][] blockArray = new Block[SIZE][SIZE][SIZE];
	private ArrayList<Block> blocksToRender = new ArrayList<Block>();
	
	public Chunk(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		// fill();
	}
	
	private void fill() {
		for (int x = 0; x < SIZE; ++x) {
			for (int y = 0; y < SIZE; ++y) {
				for (int z = 0; z < SIZE; ++z) {
					Vector3f blockPosition = new Vector3f(x + this.x, y + this.y, z + this.z);
					addBlock(new DirtBlock(blockPosition));
				}
			}
		}
	}
	
	public void addBlock(Block block) {
		int x_index = (int) block.getPosition().x - this.x;
		int y_index = (int) block.getPosition().y - this.y;
		int z_index = (int) block.getPosition().z - this.z;
		blockArray[x_index][y_index][z_index] = block;
		updateBlockNeighbors(x_index, y_index, z_index);
	}
	
	public void removeBlock(int x, int y, int z) {
		blocksToRender.remove(getBlock(x, y, z));
		blockArray[x][y][z] = null;
		updateBlockNeighbors(x, y, z);
	}
	
	public Block getBlock(int x, int y, int z) {
		try {
			return blockArray[x][y][z];
		} catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public boolean positionWithinChunk(Vector3f position) {
		if (position.x >= this.x && position.x < this.x + SIZE &&
				position.y >= this.y && position.y < this.y + SIZE &&
				position.z >= this.z && position.z < this.z + SIZE) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Block> getBlocksToRender() {
		return blocksToRender;
	}
	
	private void updateBlockNeighbors(int x, int y, int z) {
		updateBlockFlags(x, y, z);
		updateBlockFlags(x + 1, y, z);
		updateBlockFlags(x - 1, y, z);
		updateBlockFlags(x, y + 1, z);
		updateBlockFlags(x, y - 1, z);
		updateBlockFlags(x, y, z + 1);
		updateBlockFlags(x, y, z - 1);
	}
	
	private void updateBlockFlags(int x, int y, int z) {
		Block block = getBlock(x, y, z);
		if (block != null) {
			block.hasXPNeighbor = (getBlock(x + 1, y, z) != null) ? true : false;
			block.hasXMNeighbor = (getBlock(x - 1, y, z)) != null ? true : false;
			block.hasYPNeighbor = (getBlock(x, y + 1, z)) != null ? true : false;
			block.hasYMNeighbor = (getBlock(x, y - 1, z)) != null ? true : false;
			block.hasZPNeighbor = (getBlock(x, y, z + 1)) != null ? true : false;
			block.hasZMNeighbor = (getBlock(x, y, z - 1)) != null ? true : false;
			determineIfBlockShouldBeRendered(block);
		}
	}
	
	private void determineIfBlockShouldBeRendered(Block block) {
		if (block.shouldRender() && !blocksToRender.contains(block)) {
			blocksToRender.add(block);
		} else if (!block.shouldRender() && blocksToRender.contains(block)) {
			blocksToRender.remove(block);
		}
	}
	
	private boolean onBorder(int x, int y, int z) {
		if (x == -1 || x == SIZE || y == -1 || y == SIZE || z == -1 || z == SIZE) {
			return true;
		} else {
			return false;
		}
	}
}
