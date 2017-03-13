package game.engine.camera;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	protected Vector3f position;
	protected float pitch = 45f, yaw=270f, roll;

	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.5f, FAR_PLANE = 400.0f;

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix = new Matrix4f();

	public Camera(){
		this.projectionMatrix = createProjectionMatrix();
		updateViewMatrix();
	}

	public Camera(Vector3f position){
		this.position = position;
		this.projectionMatrix = createProjectionMatrix();
		updateViewMatrix();
	}

	private static Matrix4f createProjectionMatrix() {
		Matrix4f projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}

	protected void updateViewMatrix() {
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
	}

	public Matrix4f getProjectionViewMatrix(){
		return Matrix4f.mul(projectionMatrix, viewMatrix, null);
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

	public Matrix4f getProjectionMatrix() {
		return this.projectionMatrix;
	}

	public Matrix4f getViewMatrix(){
		return this.viewMatrix;
	}

	public float getFOV() {
		return (float) Math.toRadians(FOV/2);
	}

}
