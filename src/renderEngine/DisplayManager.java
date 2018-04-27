package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int FPS_CAP = 120;

	private static long currentFrameTime;
	private static long lastFrameTime;
	private static float delta;

	public static void CreateDisplay() {
		ContextAttribs attrib = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attrib);
			Display.setTitle("LWJGL Minecraft!");
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
		Mouse.setGrabbed(true);
	}

	public static void UpdateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		calculateDelta();
	}

	private static void calculateDelta() {
		currentFrameTime = getCurrentTime();
		delta = currentFrameTime - lastFrameTime;
		lastFrameTime = currentFrameTime;
	}

	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public static float getDeltaInMilliseconds() {
		return delta;
	}

	public static float getDeltaInSeconds() {
		return delta / 1000f;
	}

	public static void CloseDisplay() {
		Display.destroy();
	}
}
