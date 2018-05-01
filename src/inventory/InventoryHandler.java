package inventory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

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
		
		
	}

	public Block getHeldBlock() {
		if(inventory.containsKey(BlockHandler.getBlockFromID(currentSelection).getClass())) {
			Class<? extends Block> constructor = BlockHandler.getBlockFromID(currentSelection).getClass();
			Block blockSelected;
			try {
				blockSelected = constructor.getDeclaredConstructor(Vector3f.class)
						.newInstance(new Vector3f(0, 0, 0));
				if(inventory.get(constructor) >= 1 && !inventory.get(constructor).equals(0)) {
					inventory.put(constructor, inventory.get(constructor) - 1);
				} else{
					inventory.remove(constructor);
				}
				System.out.println(Arrays.asList(inventory));
				return blockSelected;
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		return null;
		
		
	}
	
	public void update() {
		for (int i = 0; i < inventoryKeys.length; i++) {
			if (Keyboard.isKeyDown(inventoryKeys[i])) {
				currentSelection = i;
			}
		}
	}
}
