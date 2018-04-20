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
				determineIfBlockIsOnSide(chunk, block);
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
	
	private void determineIfBlockIsOnSide(Chunk chunk, Block block) {
		// Check for bordering right side
		if (block.getPosition().x == chunk.x + Chunk.SIZE - 1) {
			Chunk neighborChunk = getChunkNeighbor(chunk, 1, 0, 0);
			if (neighborChunk != null) {
				System.out.println("Updating right side of chunk");
				int blockY = chunk.getLocalCoordinates(block)[1];
				int blockZ = chunk.getLocalCoordinates(block)[2];
				block.hasXPNeighbor = neighborChunk.getBlock(0, blockY, blockZ) != null;
				neighborChunk.getBlock(0, blockY, blockZ).hasXMNeighbor = block != null;
			}
		}
		// Check for bordering left side
		if (block.getPosition().x == chunk.x) {
			Chunk neighborChunk = getChunkNeighbor(chunk, -1, 0, 0);
			if (neighborChunk != null) {
				System.out.println("Updating left side of chunk");
				int blockY = chunk.getLocalCoordinates(block)[1];
				int blockZ = chunk.getLocalCoordinates(block)[2];
				block.hasXMNeighbor = neighborChunk.getBlock(Chunk.SIZE - 1, blockY, blockZ) != null;
				neighborChunk.getBlock(Chunk.SIZE - 1, blockY, blockZ).hasXPNeighbor = block != null;
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
}
