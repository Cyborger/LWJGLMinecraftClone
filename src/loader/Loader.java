package loader;

import models.RawModel;

public class Loader {
	private static TextureLoader textureLoader = new TextureLoader();
	private static VAOLoader vaoLoader = new VAOLoader();
	private static OBJLoader objLoader = new OBJLoader();

	public static int loadTexture(String fileName) {
		System.out.println("Loading texture");
		return textureLoader.loadTexture(fileName);
	}
	
	public static RawModel loadOBJ(String fileName) {
		System.out.println("Loading obj");
		ModelData modelData = objLoader.loadOBJ(fileName);
		System.out.println("Loading complete");
		return vaoLoader.loadToVAO(modelData.getVertices(), modelData.getTextureCoords(), 
				modelData.getNormals(), modelData.getIndices());	
	}
	
	public static void cleanUp() {
		textureLoader.cleanUp();
		vaoLoader.cleanUp();
	}
}
