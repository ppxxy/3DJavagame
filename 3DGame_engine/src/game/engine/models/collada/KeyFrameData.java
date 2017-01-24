package game.engine.models.collada;

import game.engine.animation.KeyFrame;
import game.engine.animation.JointTransform;
import game.engine.animation.Quaternion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class KeyFrameData {

	public final float time;
	public final List<JointTransformData> jointTransforms = new ArrayList<JointTransformData>();
	
	protected KeyFrameData(float time){
		this.time = time;
	}
	
	protected void addJointTransform(JointTransformData transform){
		jointTransforms.add(transform);
	}

	public KeyFrame changeForm() {
		Map<String, JointTransform> map = new HashMap<String, JointTransform>();
		for (JointTransformData jointData : jointTransforms) {
			JointTransform jointTransform = createTransform(jointData);
			map.put(jointData.jointNameId, jointTransform);
		}
		return new KeyFrame(time, map);
	}
	
	private static JointTransform createTransform(JointTransformData data) {
		Matrix4f mat = data.jointLocalTransform;
		Vector3f translation = new Vector3f(mat.m30, mat.m31, mat.m32);
		Quaternion rotation = new Quaternion(mat);
		return new JointTransform(data.jointNameId, translation, rotation);
	}
}
