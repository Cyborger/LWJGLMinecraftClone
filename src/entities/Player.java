package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Player {

	private Vector3f position;
	private float viewYaw;
	private float viewPitch;
	private float speed = 0.2f;

	private float moveDX;
	private float moveDY;
	private float moveDZ;

	public Player(Vector3f startPosition) {
		this.position = startPosition;
	}

	public void update() {
		updateView();
		checkForKeyPresses();
		moveAndCollide();
	}

	public float getViewPitch() {
		return viewPitch;
	}

	public float getViewYaw() {
		return viewYaw;
	}

	public Vector3f getPosition() {
		return position;
	}

	private void updateView() {
		viewYaw += Mouse.getDX() / 10.0f;
		viewPitch -= Mouse.getDY() / 10.0f;
	}

	private void checkForKeyPresses() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			moveDX -= (float) Math.cos(Math.toRadians(viewYaw + 90)) * speed;
			moveDZ -= (float) Math.sin(Math.toRadians(viewYaw + 90)) * speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			moveDX += (float) Math.cos(Math.toRadians(viewYaw + 90)) * speed;
			moveDZ += (float) Math.sin(Math.toRadians(viewYaw + 90)) * speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			moveDX += (float) Math.cos(Math.toRadians(viewYaw)) * speed;
			moveDZ += (float) Math.sin(Math.toRadians(viewYaw)) * speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			moveDX -= (float) Math.cos(Math.toRadians(viewYaw)) * speed;
			moveDZ -= (float) Math.sin(Math.toRadians(viewYaw)) * speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			moveDY += speed;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			moveDY -= speed;
	}

	private void moveAndCollide() {
		position.x += moveDX;
		position.y += moveDY;
		position.z += moveDZ;
		moveDX = 0;
		moveDY = 0;
		moveDZ = 0;
	}
}
