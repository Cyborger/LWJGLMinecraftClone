package world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;

public class World {
	private Chunk[][][] chunkArray;
	private List<Chunk> chunks = new ArrayList<Chunk>();

	public World(int chunks_wide, int chunks_high, int chunks_deep) {
		chunkArray = new Chunk[chunks_wide][chunks_high][chunks_deep];
		createChunks(chunks_wide, chunks_high, chunks_deep);
		updateAllChunkSides();
	}
	
	public void placeBlock(Block block) {
		for (Chunk chunk : chunks) {
			if (chunk.positionWithinChunk(block.getPosition())) {
				chunk.addBlock(block);
			}
		}
	}
	
	public void removeBlock(int x, int y, int z) {
		for (Chunk chunk : chunks) {
			if (chunk.positionWithinChunk(new Vector3f(x, y, z))) {
				chunk.removeBlock(x - chunk.x, y - chunk.y, z - chunk.z);
			}
		}
	}
	
	public void createChunks(int width, int height, int depth) {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				for (int z = 0; z < depth; ++z) {
					Chunk newChunk = new Chunk(x * Chunk.SIZE, y * Chunk.SIZE, z * Chunk.SIZE);
					chunkArray[x][y][z] = newChunk;
					chunks.add(newChunk);
				}
			}
		}
	}
	
	public List<Chunk> getChunks() {
		return chunks;
	}
	
	private Chunk getChunk(int x, int y, int z) {
		try {
			return chunkArray[x][y][z];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private void updateAllChunkSides() {
		for (int x = 0; x < chunkArray.length; ++x) {
			for (int y = 0; y < chunkArray[0].length; ++y) {
				for (int z = 0; z < chunkArray[0][0].length; ++z) {
					updateChunkTopSide(getChunk(x, y, z), getChunk(x, y + 1, z));
					updateChunkBottomSide(getChunk(x, y, z), getChunk(x, y - 1, z));
					updateChunkRightSide(getChunk(x, y, z), getChunk(x + 1, y, z));
					updateChunkLeftSide(getChunk(x, y, z), getChunk(x - 1, y, z));
					updateChunkFrontSide(getChunk(x, y, z), getChunk(x, y, z + 1));
					updateChunkBackSide(getChunk(x, y, z), getChunk(x, y, z - 1));
				}
			}
		}
	}
	
	private void updateChunkTopSide(Chunk firstChunk, Chunk secondChunk) {
		if (secondChunk == null) {
			return;
		}
		for (int x = 0; x < Chunk.SIZE; ++x) {
			for (int z = 0; z < Chunk.SIZE; ++z) {
				Block blockToUpdate = firstChunk.getBlock(x, Chunk.SIZE - 1, z);
				if (blockToUpdate == null) {
					continue;
				}
				if (secondChunk.getBlock(x, 0, z) == null) {
					blockToUpdate.hasYPNeighbor = false;
				} else {
					blockToUpdate.hasYPNeighbor = true;
				}
				firstChunk.determineIfBlockShouldBeRendered(blockToUpdate);
			}
		}
	}
	
	private void updateChunkBottomSide(Chunk firstChunk, Chunk secondChunk) {
		if (secondChunk == null) {
			return;
		}
		for (int x = 0; x < Chunk.SIZE; ++x) {
			for (int z = 0; z < Chunk.SIZE; ++z) {
				Block blockToUpdate = firstChunk.getBlock(x, 0, z);
				if (blockToUpdate == null) {
					continue;
				}
				if (secondChunk.getBlock(x, Chunk.SIZE - 1, z) == null) {
					blockToUpdate.hasYMNeighbor = false;
				} else {
					blockToUpdate.hasYMNeighbor = true;
				}
				firstChunk.determineIfBlockShouldBeRendered(blockToUpdate);
			}
		}
	}
	
	private void updateChunkRightSide(Chunk firstChunk, Chunk secondChunk) {
		if (secondChunk == null) {
			return;
		}
		for (int y = 0; y < Chunk.SIZE; ++y) {
			for (int z = 0; z < Chunk.SIZE; ++z) {
				Block blockToUpdate = firstChunk.getBlock(Chunk.SIZE - 1, y, z);
				if (blockToUpdate == null) {
					continue;
				}
				if (secondChunk.getBlock(0, y, z) == null) {
					blockToUpdate.hasXPNeighbor = false;
				} else {
					blockToUpdate.hasXPNeighbor = true;
				}
				firstChunk.determineIfBlockShouldBeRendered(blockToUpdate);
			}
		}
	}
	
	private void updateChunkLeftSide(Chunk firstChunk, Chunk secondChunk) {
		if (secondChunk == null) {
			return;
		}
		for (int y = 0; y < Chunk.SIZE; ++y) {
			for (int z = 0; z < Chunk.SIZE; ++z) {
				Block blockToUpdate = firstChunk.getBlock(0, y, z);
				if (blockToUpdate == null) {
					continue;
				}
				if (secondChunk.getBlock(Chunk.SIZE - 1, y, z) == null) {
					blockToUpdate.hasXMNeighbor = false;
				} else {
					blockToUpdate.hasXMNeighbor = true;
				}
				firstChunk.determineIfBlockShouldBeRendered(blockToUpdate);
			}
		}
	}
	
	private void updateChunkFrontSide(Chunk firstChunk, Chunk secondChunk) {
		if (secondChunk == null) {
			return;
		}
		for (int x = 0; x < Chunk.SIZE; ++x) {
			for (int y = 0; y < Chunk.SIZE; ++y) {
				Block blockToUpdate = firstChunk.getBlock(x, y, Chunk.SIZE - 1);
				if (blockToUpdate == null) {
					continue;
				}
				if (secondChunk.getBlock(x, y, 0) == null) {
					blockToUpdate.hasZPNeighbor = false;
				} else {
					blockToUpdate.hasZPNeighbor = true;
				}
				firstChunk.determineIfBlockShouldBeRendered(blockToUpdate);
			}
		}
	}
	
	private void updateChunkBackSide(Chunk firstChunk, Chunk secondChunk) {
		if (secondChunk == null) {
			return;
		}
		for (int x = 0; x < Chunk.SIZE; ++x) {
			for (int y = 0; y < Chunk.SIZE; ++y) {
				Block blockToUpdate = firstChunk.getBlock(x, y, 0);
				if (blockToUpdate == null) {
					continue;
				}
				if (secondChunk.getBlock(x, y, Chunk.SIZE - 1) == null) {
					blockToUpdate.hasZMNeighbor = false;
				} else {
					blockToUpdate.hasZMNeighbor = true;
				}
				firstChunk.determineIfBlockShouldBeRendered(blockToUpdate);
			}
		}
	}
}
