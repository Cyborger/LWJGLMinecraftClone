package loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public class TextureLoader {
	private List<Integer> textures = new ArrayList<Integer>();

	public int loadTexture(String fileName) {
		org.newdawn.slick.opengl.Texture texture = null;
		String filePath = "res/textures/" + fileName + ".png";
		try {
			texture = org.newdawn.slick.opengl.TextureLoader.getTexture("PNG", new FileInputStream(filePath));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}

	public void cleanUp() {
		for (int texture : textures) {
			GL30.glDeleteVertexArrays(texture);
		}
	}
}
