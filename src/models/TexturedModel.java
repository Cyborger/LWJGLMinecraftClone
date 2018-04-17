package models;

public class TexturedModel {

	private RawModel model;
	private Texture texture;

	public TexturedModel(RawModel model, Texture texture) {
		super();
		this.model = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return model;
	}

	public Texture getTexture() {
		return texture;
	}

}
