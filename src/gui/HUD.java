package gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import loader.Loader;

public class HUD {

	private List<GUIElement> elements = new ArrayList<GUIElement>();
	private GUIElement crosshair;

	public HUD() {
		crosshair = new GUIElement(Loader.loadTexture("crosshair"), new Vector2f(0, 0), new Vector2f(0.02f, 0.035f));
		elements.add(crosshair);
	}

	public List<GUIElement> getElementsToRender() {
		return elements;
	}
}
