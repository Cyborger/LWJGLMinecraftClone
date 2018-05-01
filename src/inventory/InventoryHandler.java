package inventory;

import java.util.Arrays;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import entities.Block;
import entities.BlockHandler;

public class InventoryHandler {

	private final HashMap<Class, Integer> inventory = new HashMap<Class, Integer>();
	private int currentSelection;
	int[] inventoryKeys = { Keyboard.KEY_1, Keyboard.KEY_2, Keyboard.KEY_3, 
			Keyboard.KEY_4, Keyboard.KEY_5, Keyboard.KEY_6,
			Keyboard.KEY_7, Keyboard.KEY_8, Keyboard.KEY_9 };

	public HashMap<Class, Integer> getInventory() {
		return inventory;
	}

	public void addToInventory(Class currentBlock, int amountToAdd) {
		if(inventory.containsKey(currentBlock)) {
			inventory.put(currentBlock, inventory.get(currentBlock) + 1);
			System.out.println("Adding block to inventory: " + currentBlock);
		} else if(!inventory.containsKey(currentBlock)) {
			inventory.put(currentBlock, 1);
		}
		
		System.out.println(Arrays.asList(inventory));
	}

	/*public Block getHeldBlock() {
		Class heldBlock = null;
		for(Class block : inventory.keySet()) {
			if(block == BlockHandler.getBlockFromID(currentSelection)) {
				heldBlock = BlockHandler.getBlockFromID(currentSelection);
				inventory.remove(heldBlock);
				
			}
		}
		return heldBlock;
		
	}*/
	
	public void update() {
		for (int i = 0; i < inventoryKeys.length; i++) {
			if (Keyboard.isKeyDown(inventoryKeys[i])) {
				currentSelection = i;
			}
		}
	}
}
