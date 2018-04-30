package world;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.BlockHandler;

public class Chunk {

	public static final int SIZE = 16;
	public int x;
	public int y;
	public int z;

	private Block[][][] blockArray = new Block[SIZE][SIZE][SIZE];
	private ArrayList<Block> blocksToRender = new ArrayList<Block>();

	public Chunk(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void addBlock(Block block) {
		int[] indices = getLocalCoordinates(block);
		blockArray[indices[0]][indices[1]][indices[2]] = block;
		updateBlockNeighbors(indices[0], indices[1], indices[2]);
	}

	public boolean removeBlock(int x, int y, int z) {
		boolean blockExists = getBlock(x, y, z) != null;
		if(getBlock(x, y, z) != null) {
			int id = BlockHandler.getIDFromBlock(getBlock(x, y, z));
		}
		blocksToRender.remove(getBlock(x, y, z));
		blockArray[x][y][z] = null;
		updateBlockNeighbors(x, y, z);
		return blockExists;
	}

	public Block getBlock(int x, int y, int z) {
		try {
			return blockArray[x][y][z];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean positionWithinChunk(Vector3f position) {
		if (position.x >= this.x && position.x < this.x + SIZE && position.y >= this.y && position.y < this.y + SIZE
				&& position.z >= this.z && position.z < this.z + SIZE) {
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
			if (!outsideChunk(x + 1))
				block.hasXPNeighbor = (getBlock(x + 1, y, z) != null) ? true : false;
			if (!outsideChunk(x - 1))
				block.hasXMNeighbor = (getBlock(x - 1, y, z) != null) ? true : false;
			if (!outsideChunk(y + 1))
				block.hasYPNeighbor = (getBlock(x, y + 1, z) != null) ? true : false;
			if (!outsideChunk(y - 1))
				block.hasYMNeighbor = (getBlock(x, y - 1, z) != null) ? true : false;
			if (!outsideChunk(z + 1))
				block.hasZPNeighbor = (getBlock(x, y, z + 1) != null) ? true : false;
			if (!outsideChunk(z - 1))
				block.hasZMNeighbor = (getBlock(x, y, z - 1) != null) ? true : false;
			determineIfBlockShouldBeRendered(block);
		}
	}

	public void determineIfBlockShouldBeRendered(Block block) {
		if (block.shouldRender() && !blocksToRender.contains(block)) {
			blocksToRender.add(block);
		} else if (!block.shouldRender() && blocksToRender.contains(block)) {
			blocksToRender.remove(block);
		}
	}

	public int[] getLocalCoordinates(Block block) {
		int xIndex = (int) block.getPosition().x - this.x;
		int yIndex = (int) block.getPosition().y - this.y;
		int zIndex = (int) block.getPosition().z - this.z;
		int[] coords = { xIndex, yIndex, zIndex };
		return coords;
	}

	private boolean outsideChunk(int value) {
		if (value == -1 || value == Chunk.SIZE) {
			return true;
		}
		return false;
	}
}
