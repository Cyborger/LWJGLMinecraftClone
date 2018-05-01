package entities;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position;
	private float pitch;
	private float yaw = 145f;
	private float roll;
	Player target;

	public Camera(Player target) {
		this.target = target;
	}

	public void update() {
		this.position = this.target.getPosition();
		this.pitch = this.target.getViewPitch();
		this.yaw = this.target.getViewYaw();
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}