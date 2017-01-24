package game.engine.animation;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class JointTransform {
	//COMMENT

	private final Vector3f position;
	private final Quaternion rotation;
	private final String name;
	
	public JointTransform(String name, Vector3f position, Quaternion rotation){
		this.name = name;
		this.position = position;
		this.rotation = rotation;
	}
	
	public JointTransform(String name, Matrix4f localTransform){
		this.name = name;
		this.position = new Vector3f(localTransform.m30, localTransform.m31, localTransform.m32);
		this.rotation = new Quaternion(localTransform);
	}
	
	protected Matrix4f getLocalTransform(){
		Matrix4f matrix = new Matrix4f();
		matrix.translate(position);
		Matrix4f.mul(matrix, rotation.toRotationMatrix(), matrix);
		return matrix;
	}
	
	protected static JointTransform intepolate(JointTransform frameA, JointTransform frameB, float progression){
		Vector3f pos = interpolate(frameA.position, frameB.position, progression);
		Quaternion rot = Quaternion.slerp(frameA.rotation, frameB.rotation, progression);
		return new JointTransform(frameA.getName(), pos, rot);
	}
	
	private static Vector3f interpolate(Vector3f start, Vector3f end, float progression){
		float x = start.x + (end.x - start.x) * progression;
		float y = start.y + (end.y - start.y) * progression;
		float z = start.z + (end.z - start.z) * progression;
		return new Vector3f(x, y, z);
	}

	public String getName() {
		return this.name;
	}
}
