package entities;

import java.util.ArrayList;
import java.util.List;

public class BlockHandler {

	public static int getBlockID(Block block) {
		if(block != null) {
			int id = block.GetID();
			return id;
		}
		return -1;
		
	}
	
	
}
