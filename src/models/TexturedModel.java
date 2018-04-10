package models;

public class TexturedModel {
	
	private RawModel model;
	private ModelTexture texture;
	
	public TexturedModel(RawModel model, ModelTexture texture) {
		super();
		this.model = model;
		this.texture = texture;
	}
	public RawModel getModel() {
		return model;
	}
	public ModelTexture getTexture() {
		return texture;
	}
	
	
}
