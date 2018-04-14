package loader;

import models.RawModel;

public class Loader {
	private TextureLoader textureLoader = new TextureLoader();
	private VAOLoader vaoLoader = new VAOLoader();
	private OBJLoader objLoader = new OBJLoader();

	public int loadTexture(String fileName) {
		System.out.println("Loading texture");
		return textureLoader.loadTexture(fileName);
	}
	
	public RawModel loadOBJ(String fileName) {
		System.out.println("Loading obj");
		ModelData modelData = objLoader.loadOBJ(fileName);
		System.out.println("Loading complete");
		return vaoLoader.loadToVAO(modelData.getVertices(), modelData.getTextureCoords(), 
				modelData.getNormals(), modelData.getIndices());	
	}
	
	public void cleanUp() {
		textureLoader.cleanUp();
		vaoLoader.cleanUp();
	}
}
