package inventory;

import java.util.ArrayList;

import entities.Block;

public class InventoryHandler {
	
	private ArrayList<Block> inventory = new ArrayList<Block>();
	
	public ArrayList<Block> getInventory() {
		return inventory;
	}

	public void addToInventory(Block block) {
		this.inventory.add(block);
	}

	public Block getBlockAtIndex(int index) {
		return inventory.get(index);
	}
}
