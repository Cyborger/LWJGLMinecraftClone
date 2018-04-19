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
	
	private Block[][][] blockArray = new Block[SIZE][SIZE][SIZE];
	private ArrayList<Block> blocksToRender = new ArrayList<Block>();
	
	public Chunk(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		fill();
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
	
	private boolean outsideChunk(int value) {
		if (value == -1 || value == 16) {
			return true;
		}
		return false;
	}
	
	public void updateTopAndBottomSides(Chunk aboveChunk, Chunk belowChunk) {
		for (int x = 0; x < Chunk.SIZE; ++x) {
			for (int z = 0; z < Chunk.SIZE; ++z) {
				if (aboveChunk != null) {
					Block blockToUpdate = getBlock(x, Chunk.SIZE - 1, z);
					if (blockToUpdate != null) {
						if (aboveChunk.getBlock(x, 0, z) == null) {
							blockToUpdate.hasYPNeighbor = false;
						} else {
							blockToUpdate.hasYPNeighbor = true;
						}
						determineIfBlockShouldBeRendered(blockToUpdate);
					}
				}
				if (belowChunk != null) {
					Block blockToUpdate = getBlock(x, 0, z);
					if (blockToUpdate != null) {
						if (belowChunk.getBlock(x, Chunk.SIZE - 1, z) == null) {
							blockToUpdate.hasYMNeighbor = false;
						} else {
							blockToUpdate.hasYMNeighbor = true;
						}
						determineIfBlockShouldBeRendered(blockToUpdate);
					}
				}
				
			}
		}
	}
	
	public void updateRightAndLeftSides(Chunk rightChunk, Chunk leftChunk) {
		for (int y = 0; y < Chunk.SIZE; ++y) {
			for (int z = 0; z < Chunk.SIZE; ++z) {
				if (rightChunk != null) {
					Block blockToUpdate = getBlock(Chunk.SIZE - 1, y, z);
					if (blockToUpdate != null) {
						if (rightChunk.getBlock(0, y, z) == null) {
							blockToUpdate.hasXPNeighbor = false;
						} else {
							blockToUpdate.hasXPNeighbor = true;
						}
						determineIfBlockShouldBeRendered(blockToUpdate);
					}
				}
				
				if (leftChunk != null) {
					Block blockToUpdate = getBlock(0, y, z);
					if (blockToUpdate != null) {
						if (leftChunk.getBlock(Chunk.SIZE - 1, y, z) == null) {
							blockToUpdate.hasXMNeighbor = false;
						} else {
							blockToUpdate.hasXMNeighbor = true;
						}
						determineIfBlockShouldBeRendered(blockToUpdate);
					}
				}
			}
		}
	}
	
	public void updateFrontAndBackSides(Chunk frontChunk, Chunk backChunk) {
		for (int x = 0; x < Chunk.SIZE; ++x) {
			for (int y = 0; y < Chunk.SIZE; ++y) {
				if (frontChunk != null) {
					Block blockToUpdate = getBlock(x, y, Chunk.SIZE - 1);
					if (blockToUpdate != null) {
						if (frontChunk.getBlock(x, y, 0) == null) {
							blockToUpdate.hasZPNeighbor = false;
						} else {
							blockToUpdate.hasZPNeighbor = true;
						}
						determineIfBlockShouldBeRendered(blockToUpdate);
					}
				}
				
				if (backChunk != null) {
					Block blockToUpdate = getBlock(x, y, 0);
					if (blockToUpdate != null) {
						if (backChunk.getBlock(x, y, Chunk.SIZE - 1) == null) {
							blockToUpdate.hasZMNeighbor = false;
						} else {
							blockToUpdate.hasZMNeighbor = true;
						}	
						determineIfBlockShouldBeRendered(blockToUpdate);
					}
				}
			}
		}
	}
}
