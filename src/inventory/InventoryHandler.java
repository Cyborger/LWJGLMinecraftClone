package inventory;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import entities.Block;

public class InventoryHandler {

	private final List<Block> inventory = new ArrayList<Block>();
	private int currentSelection;
	int[] inventoryKeys = { Keyboard.KEY_1, Keyboard.KEY_2, Keyboard.KEY_3, 
			Keyboard.KEY_4, Keyboard.KEY_5, Keyboard.KEY_6,
			Keyboard.KEY_7, Keyboard.KEY_8, Keyboard.KEY_9 };

	public List<Block> getInventory() {
		return inventory;
	}

	public void addToInventory(Block block) {
		inventory.add(block);
		System.out.println("Adding block to inventory: " + block);
	}

	public Block getHeldBlock() {
		Block heldBlock = inventory.get(currentSelection);
		inventory.remove(heldBlock);
		return heldBlock;
	}
	
	public void update() {
		for (int i = 0; i < inventoryKeys.length; i++) {
			if (Keyboard.isKeyDown(inventoryKeys[i])) {
				currentSelection = i;
			}
		}
	}
}
