package utilities;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Block;
import entities.Camera;
import inventory.InventoryHandler;
import world.World;

public class MousePicker {

	private Vector3f currentRay;

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;

	private boolean leftMouseButtonPressed;
	private boolean rightMouseButtonPressed;
	private float intervalUpdateSize = 0.001f;

	public MousePicker(Camera camera, Matrix4f projection) {
		this.camera = camera;
		this.projectionMatrix = projection;
	}

	public void update(World world, InventoryHandler inventoryHandler) {
		if (Mouse.isButtonDown(0) && !leftMouseButtonPressed) {
			for (float i = 0; i < 8; i += intervalUpdateSize) {
				int[] blockCoords = getBlockCoords(i);
				if (blockCoords != null) {
					Block blockSelected = world.getBlock(blockCoords[0], blockCoords[1], blockCoords[2]);
					if (world.removeBlock(blockCoords[0], blockCoords[1], blockCoords[2])){
						try {
							Class<? extends Block> constructor = blockSelected.getClass();
							blockSelected = constructor.getDeclaredConstructor(Vector3f.class).newInstance(new Vector3f(blockCoords[0], blockCoords[1], blockCoords[2]));
						} catch (Exception e) {
							e.printStackTrace();
						} 
						inventoryHandler.addToInventory(blockSelected);
						break;
					}
				}
			}
		}
		if (Mouse.isButtonDown(1) && !rightMouseButtonPressed) {
			for (float i = 0; i < 8; i += intervalUpdateSize) {
				int[] blockCoords = getBlockCoords(i);
				if (world.getBlock(blockCoords[0], blockCoords[1], blockCoords[2]) != null) {
					blockCoords = getBlockCoords(i - intervalUpdateSize);
					try {
						Class<? extends Block> constructor = inventoryHandler.getBlockAtIndex().getClass();
						Block blockToPlace = constructor.getDeclaredConstructor(Vector3f.class).
								newInstance(new Vector3f(blockCoords[0], blockCoords[1], blockCoords[2]));
						world.placeBlock(blockToPlace);
					} catch (Exception e) {
					} 
					break;
				}
			}
		}
		updateMouseFlags();
	}

	private int[] getBlockCoords(float rayDistance) {
		Vector3f blockCoords = getTerrainPoint(rayDistance);
		if (blockCoords != null) {
			int blockX = Math.round(blockCoords.x);
			int blockY = Math.round(blockCoords.y);
			int blockZ = Math.round(blockCoords.z);
			return new int[] { blockX, blockY, blockZ };
		}
		return null;
	}

	private void updateMouseFlags() {
		leftMouseButtonPressed = Mouse.isButtonDown(0);
		rightMouseButtonPressed = Mouse.isButtonDown(1);
	}

	private Vector3f getTerrainPoint(float lengthOfRay) {
		viewMatrix = MatrixMath.createViewMatrix(camera);
		currentRay = calculateMouseRay();
		return getPointOnRay(currentRay, lengthOfRay);
	}

	private Vector3f calculateMouseRay() {
		float mouseX = Display.getWidth() / 2f;
		float mouseY = Display.getHeight() / 2f;
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
