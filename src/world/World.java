package world;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Block;
import entities.DirtBlock;

public class World {
	private Chunk[][][] chunkArray;
	private List<Chunk> chunks = new ArrayList<Chunk>();

	public World(int chunks_wide, int chunks_high, int chunks_deep) {
		chunkArray = new Chunk[chunks_wide][chunks_high][chunks_deep];
		createChunks(chunks_wide, chunks_high, chunks_deep);
	}
	
	public void placeBlock(Block block) {
		for (Chunk chunk : chunks) {
			if (chunk.positionWithinChunk(block.getPosition())) {
				chunk.addBlock(block);
				int[] localCoords = chunk.getLocalCoordinates(block);
				updateChunkSide(chunk, localCoords[0], localCoords[1], localCoords[2]);
			}
		}
	}
	
	public void removeBlock(int x, int y, int z) {
		for (Chunk chunk : chunks) {
			if (chunk.positionWithinChunk(new Vector3f(x, y, z))) {
				chunk.removeBlock(x - chunk.x, y - chunk.y, z - chunk.z);
				updateChunkSide(chunk, x - chunk.x, y - chunk.y, z - chunk.z);
			}
		}
	}
	
	public void createChunks(int width, int height, int depth) {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				for (int z = 0; z < depth; ++z) {
					Chunk chunk = new Chunk(x * Chunk.SIZE, y * Chunk.SIZE, z * Chunk.SIZE);
					chunkArray[x][y][z] = chunk;
					chunks.add(chunk);
					fillChunk(chunk);
				}
			}
		}
	}
	
	public List<Chunk> getChunks() {
		return chunks;
	}
	
	private void fillChunk(Chunk chunk) {
		for (int x = 0; x < Chunk.SIZE; ++x) {
			for (int y = 0; y < Chunk.SIZE; ++y) {
				for (int z = 0; z < Chunk.SIZE; ++z) {
					Vector3f blockPosition = new Vector3f(x + chunk.x, y + chunk.y, z + chunk.z);
					placeBlock(new DirtBlock(blockPosition));
				}
			}
		}
	}
	
	private void updateChunkSide(Chunk chunk, int indexX, int indexY, int indexZ) {
		Block block = chunk.getBlock(indexX, indexY, indexZ);
		// Check for bordering left side
		if (indexX == 0) {
			Chunk neighborChunk = getChunkNeighbor(chunk, -1, 0, 0);
			if (neighborChunk != null) {
				Block neighborBlock = neighborChunk.getBlock(Chunk.SIZE - 1, indexY, indexZ);
				block.hasXMNeighbor = neighborBlock != null;
				neighborBlock.hasXPNeighbor = block != null;
			}
		}
		// Check for bordering right side
		if (indexX == Chunk.SIZE - 1) {
			Chunk neighborChunk = getChunkNeighbor(chunk, 1, 0, 0);
			if (neighborChunk != null) {	
				Block neighborBlock = neighborChunk.getBlock(0, indexY, indexZ);
				block.hasXPNeighbor = neighborBlock != null;
				neighborBlock.hasXMNeighbor = block != null;
			}
		}
		// Check for bordering bottom side
		if (indexY == 0) {
			Chunk neighborChunk = getChunkNeighbor(chunk, 0, -1, 0);
			if (neighborChunk != null) {
				Block neighborBlock = neighborChunk.getBlock(indexX, Chunk.SIZE - 1, indexZ);
				block.hasYMNeighbor = neighborBlock != null;
				neighborBlock.hasYPNeighbor = block != null;
			}
		}
		// Check for bordering top side
		if (indexY == Chunk.SIZE - 1) {
			Chunk neighborChunk = getChunkNeighbor(chunk, 0, 1, 0);
			if (neighborChunk != null) {
				Block neighborBlock = neighborChunk.getBlock(0, indexY, indexZ);
				block.hasYPNeighbor = neighborBlock != null;
				neighborBlock.hasYMNeighbor = block != null;
			}
		}
		// Check for bordering front side
		if (indexZ == 0) {
			Chunk neighborChunk = getChunkNeighbor(chunk, 0, 0, -1);
			if (neighborChunk != null) {
				Block neighborBlock = neighborChunk.getBlock(indexX, indexY, Chunk.SIZE - 1);
				if (block != null) {
					block.hasZMNeighbor = neighborBlock != null;
				}
				if (neighborBlock != null) {
					neighborBlock.hasZPNeighbor = block != null;
				}
			}
		}
		// Check for bordering back side
		if (indexZ == Chunk.SIZE - 1) {
			Chunk neighborChunk = getChunkNeighbor(chunk, 0, 0, 1);
			if (neighborChunk != null) {
				Block neighborBlock = neighborChunk.getBlock(indexX, indexY, 0);
				if (block != null) {
					block.hasZPNeighbor = neighborBlock != null;
				}
				if (neighborBlock != null) {
					neighborBlock.hasZMNeighbor = block != null;
				}
			}
		}
	}
	
	private Chunk getChunkNeighbor(Chunk chunk, int dx, int dy, int dz) {
		int indexX = Math.round(chunk.x / Chunk.SIZE);
		int indexY = Math.round(chunk.y / Chunk.SIZE);
		int indexZ = Math.round(chunk.z / Chunk.SIZE);
		return getChunk(indexX + dx, indexY + dy, indexZ + dz);
	}
	
	private Chunk getChunk(int x, int y, int z) {
		try {
			return chunkArray[x][y][z];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public int[] blockToBreak(Vector3f terrainPoint) {
		for(Chunk chunk : chunks) {
			if(chunk.getBlock((int)terrainPoint.x, (int)terrainPoint.y, (int)terrainPoint.z) != null) {
				Block blocktoReturn = chunk.getBlock((int)Math.floor(terrainPoint.x), (int)Math.floor(terrainPoint.y), (int)Math.floor(terrainPoint.z));
				return new int[] {(int)blocktoReturn.getPosition().x, (int)blocktoReturn.getPosition().y, (int)blocktoReturn.getPosition().z};
			} 
		}
		return new int[] {-1, -1, -1};
	}
}
