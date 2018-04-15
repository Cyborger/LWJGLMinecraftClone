package loader;

import loader.objLoader.ModelData;
import loader.objLoader.OBJLoader;
import models.RawModel;

public class Loader {
	
	private static TextureLoader textureLoader = new TextureLoader();
	private static VAOLoader vaoLoader = new VAOLoader();
	private static OBJLoader objLoader = new OBJLoader();

	public static int loadTexture(String fileName) {
		return textureLoader.loadTexture(fileName);
	}
	
	public static RawModel loadOBJ(String fileName) {
		ModelData modelData = objLoader.loadOBJ(fileName);
		return vaoLoader.loadToVAO(modelData.getVertices(), modelData.getTextureCoords(), 
				modelData.getNormals(), modelData.getIndices());	
	}
	
	public static void cleanUp() {
		textureLoader.cleanUp();
		vaoLoader.cleanUp();
	}
}
