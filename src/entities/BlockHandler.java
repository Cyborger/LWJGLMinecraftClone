package entities;

import java.util.HashMap;

import entities.blocks.DirtBlock;
import entities.blocks.GrassBlock;
import entities.blocks.LeafBlock;
import entities.blocks.TreeBlock;


public class BlockHandler {

	public static HashMap<Block, Integer> IDs = new HashMap<Block, Integer>();
	
	
	
	public static int getIDFromBlock(Block block) {
		if(block != null) {
			int id = block.GetID();
			return id;
		}
		return -1;
	}
	
	public static Block getBlockFromID(int ID) {
		for(Block block : IDs.keySet()) {
			if(getIDFromBlock(block) == ID) {
				return block;
			}
		}
		return null;
	}
	
	public static void generateHashMap() {
		DirtBlock dirtblock = new DirtBlock(null);
		GrassBlock grassblock = new GrassBlock(null);
		LeafBlock leafblock = new LeafBlock(null);
		TreeBlock treeblock = new TreeBlock(null);
		IDs.put(dirtblock, getIDFromBlock(dirtblock));
		IDs.put(grassblock, getIDFromBlock(grassblock));
		IDs.put(leafblock, getIDFromBlock(leafblock));
		IDs.put(treeblock, getIDFromBlock(treeblock));
	}
	
	
}
