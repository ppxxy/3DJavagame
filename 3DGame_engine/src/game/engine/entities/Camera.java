package game.engine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0, 5, 5);
	private float pitch, yaw, roll;
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.2f;
	private static final float FAR_PLANE = 400;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix = new Matrix4f();
	
	public Camera(){
		this.projectionMatrix = createProjectionMatrix();
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
	
	private void updateViewMatrix() {
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
	}
	
	public Matrix4f getProjectionViewMatrix(){
		return Matrix4f.mul(projectionMatrix, viewMatrix, null);
	}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z-=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x+=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x-=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z+=0.02f;
		}
		updateViewMatrix();
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
