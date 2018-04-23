package utilities;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import world.World;

public class MousePicker {
	
	private static final float RAY_RANGE = 5;
	
	private Vector3f currentRay;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;
	private World world;
	
	private Vector3f currentTerrainPoint;
	
	public MousePicker(Camera camera, Matrix4f projection, World world) {
		this.camera = camera;
		this.projectionMatrix = projection;
		this.world = world;
	}
	
	public Vector3f getCurrentRay() {
		return currentRay;
	}
	
	public Vector3f getTerrainPoint() {
		return currentTerrainPoint;
	}
	
	public void update() {
		viewMatrix = MatrixMath.createViewMatrix(camera);
		currentRay = calculateMouseRay();
		currentTerrainPoint = getPointOnRay(currentRay, RAY_RANGE);
		System.out.println(getPointOnRay(currentRay, RAY_RANGE)  );
	}

	private Vector3f calculateMouseRay() {
		float mouseX = Display.getWidth()/2f;
		float mouseY = Display.getHeight()/2f;
		Vector2f NDC = getNormalisedDeviceCoordinates(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(NDC.x, NDC.y, -1.0f, 1.0f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}
	
	private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
		float x = (2.0f * mouseX) / Display.getWidth() - 1;
		float y = (2.0f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}

	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}
	
	private Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f camPos = camera.getPosition();
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}
}
