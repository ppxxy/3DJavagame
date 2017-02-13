package game.engine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.physics.MousePicker;

public class Camera {

	private Vector3f position;
	private float pitch = 45f, yaw=270f, roll;

	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.5f, FAR_PLANE = 400.0f;
	static final private float sensitivity = 0.05f;

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix = new Matrix4f();

	public Camera(Vector3f position){
		this.position = position;
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
		if(Mouse.isButtonDown(0)){
			float yChange = -(Mouse.getDX())*sensitivity%360;
			float xChange = (Mouse.getDY())*sensitivity%360;
			float xRotation = (pitch + (xChange > 0 ? xChange : 360+xChange))%360;
			yaw = (yaw + (yChange > 0 ? yChange : 360+yChange))%360;
			pitch = xRotation < 90 ? xRotation : (xRotation > 270 ? xRotation : (xRotation < 180 ? 90 : 270));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			float length = (pitch < 180 ? -1*(pitch/90)+1 : (pitch/90)-3);
			float zcalcs = (yaw < 180 ? -1*(yaw/90)+1 : (yaw/90)-3);
			float xcalcs = (yaw < 90 ? (yaw/90) : (yaw < 270 ? (-1*(yaw/90) +2) : (yaw/90)-4));
			//System.out.println(yaw +", " +zcalcs);
			position.z-= zcalcs*length*0.2f;
			position.x+= xcalcs*length*0.2f;
			position.y-= (pitch < 90 ? (pitch/90) : (pitch < 270 ? (-1*(pitch/90) +2) : (pitch/90)-4))*0.2f;
			//System.out.println(position.x +", " +position.y +", " +position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			float xcalcs = (yaw < 180 ? -1*(yaw/90)+1 : (yaw/90)-3);
			float zcalcs = (yaw < 90 ? (yaw/90) : (yaw < 270 ? (-1*(yaw/90) +2) : (yaw/90)-4));
			position.z+= zcalcs*0.2f;
			position.x+= xcalcs*0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			float xcalcs = (yaw < 180 ? -1*(yaw/90)+1 : (yaw/90)-3);
			float zcalcs = (yaw < 90 ? (yaw/90) : (yaw < 270 ? (-1*(yaw/90) +2) : (yaw/90)-4));
			//System.out.println(yaw +", " +zcalcs);
			position.z-= zcalcs*0.2f;
			position.x-= xcalcs*0.2f;
			//System.out.println(position.x +", " +position.y +", " +position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			float length = (pitch < 180 ? -1*(pitch/90)+1 : (pitch/90)-3);
			float zcalcs = (yaw < 180 ? -1*(yaw/90)+1 : (yaw/90)-3);
			float xcalcs = (yaw < 90 ? (yaw/90) : (yaw < 270 ? (-1*(yaw/90) +2) : (yaw/90)-4));
			//System.out.println(yaw +", " +zcalcs);
			position.z+= zcalcs*length*0.2f;
			position.x-= xcalcs*length*0.2f;
			position.y+= (pitch < 90 ? (pitch/90) : (pitch < 270 ? (-1*(pitch/90) +2) : (pitch/90)-4))*0.2f;
			//System.out.println(position.x +", " +position.y +", " +position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			position.y+= 0.2f;
			//System.out.println(position.x +", " +position.y +", " +position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			position.y-= 0.2f;
			//System.out.println(position.x +", " +position.y +", " +position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_X)){
			MousePicker.further = MousePicker.further ? false : true;
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
