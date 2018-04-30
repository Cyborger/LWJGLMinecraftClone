package inventory;

import java.util.ArrayList;
import java.util.List;

import entities.Block;

public class InventoryHandler {
	
	private final List<Block> inventory = new ArrayList<Block>();
	
	public List<Block> getInventory() {
		return inventory;
	}

	public void addToInventory(Block block) {
		inventory.add(block);
		System.out.println(inventory.toString());
	}

	public Block getBlockAtIndex(int index) {
		return inventory.get(index);
	}
}
