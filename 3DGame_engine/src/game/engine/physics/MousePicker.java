package game.engine.physics;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import game.engine.camera.Camera;

public class MousePicker {

	private Vector3f currentRay;
	public static boolean further = true;

	private Matrix4f projectionMatrix, viewMatrix;
	private Camera camera;

	public MousePicker(Camera camera){
		this.camera = camera;
		this.projectionMatrix = camera.getProjectionMatrix();
		this.viewMatrix = camera.getViewMatrix();
	}

	public Vector3f getCurrentRay(){
		return currentRay;
	}

	public Vector2f update(float distance){
		//System.out.println(distance);
		this.projectionMatrix = camera.getProjectionMatrix();
		this.viewMatrix = camera.getViewMatrix();
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		Vector2f normalizedCoords = getNomalizedDeviceCoordinates(mouseX, mouseY);
		currentRay = calculateMouseRay(normalizedCoords);
		if(further){
			float pitch = (float) Math.toRadians(camera.getPitch());
			float yaw = (float) Math.toRadians(camera.getYaw());
			//System.out.println(currentRay.z);
			distance /= (Math.sin(pitch)*(currentRay.y)+Math.cos(pitch)*(Math.cos(yaw)*currentRay.z-Math.sin(yaw)*currentRay.x));
		}
		return getIntersectionPoint(distance);
	}

	private Vector2f getIntersectionPoint(float distance) {
		Vector3f camPos = camera.getPosition();
		return new Vector2f(camPos.x+currentRay.x*Math.abs(distance), camPos.z+currentRay.z*Math.abs(distance));
	}

	private Vector3f calculateMouseRay(Vector2f normalizedCoords) {
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldCoords = toWorldCoords(eyeCoords);
		return worldCoords;
	}

	private static float getPow(float f){
		return f*f;
	}

	private Vector3f toWorldCoords(Vector4f eyeCoords){
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayCoords = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayCoords.x, rayCoords.y, rayCoords.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords){
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vector2f getNomalizedDeviceCoordinates(float mouseX, float mouseY){
		float x = (2f*mouseX) / Display.getWidth()-1;
		float y = (2f*mouseY) / Display.getHeight()-1;
		return new Vector2f(x, y);
	}

}
