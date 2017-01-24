package game.engine.models.collada;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.animation.Animation;
import game.engine.animation.KeyFrame;
import game.engine.models.xml.XmlNode;

public class AnimationData {
	
	public final float length;
	public final KeyFrame[] keyFrames;
	
	/**
	 * Holds data of animation, total duration of animation and all animation keyFrames.
	 * @param duration Total length of animation cycle in seconds.
	 * @param keyFrames Array containing each keyFrame.
	 */
	public AnimationData(float duration, KeyFrame[] keyFrames) {
		this.length = duration;
		this.keyFrames = keyFrames;
	}
	
	public float getLength(){
		return length;
	}
	
	public KeyFrame[] getKeyFrames(){
		return keyFrames;
	}

	private static final Matrix4f CORRECTION = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

	public static class AnimationLoader{
		
		private XmlNode animationData, jointHierarchy;
		
		/**
		 * Contains XmlNodes that are used to create animation.
		 * @param animationData Animation XmlNode. Contains data of KeyFrames.
		 * @param jointsNode Joint XmlNode. Contains data of model's joints.
		 */
		public AnimationLoader(XmlNode animationData, XmlNode jointsNode){
			this.animationData = animationData;
			this.jointHierarchy = jointsNode;
		}
		
		/**
		 * Creates animation object from Xml data.
		 * @return Animation.
		 */
		public Animation extractAnimation(){
			String rootJoint = getRootJoint();
			float[] timeStamps = getKeyTimes();
			float duration = timeStamps[timeStamps.length-1];
			//First we need to create key frames at defined times
			KeyFrameData[] keyFrames = initKeyFrames(timeStamps);
				
			//Then we apply transformation data to each keyFrame.
			List<XmlNode> animationNodes = animationData.getChildren("animation");
			for(XmlNode jointNode : animationNodes){
				loadTransformationFromNode(keyFrames, jointNode, rootJoint);
			}
			
			KeyFrame[] frames = new KeyFrame[keyFrames.length];
			
			for(int i = 0; i < frames.length; i++){
				frames[i] = keyFrames[i].changeForm();
			}
			return new Animation(duration, frames);
		}
		
		/**
		 * Get key time points from Animation XmlNode 
		 * @return Float array containing animation KeyFrame times.
		 */
		private float[] getKeyTimes(){
			XmlNode timeData = animationData.getChild("animation").getChild("source").getChild("float_array");
			String[] rawTimes = timeData.getData().split(" ");
			float[] times = new float[rawTimes.length];
			for(int i = 0; i < times.length; i++){
				times[i] = Float.parseFloat(rawTimes[i]);
			}
			return times;
		}
		
		/**
		 * Create KeyFrames based on time stamps.
		 * @param times Timestamps used to create KeyFrames.
		 * @return Array of animation KeyFrames.
		 */
		private KeyFrameData[] initKeyFrames(float[] times){
			KeyFrameData[] frames = new KeyFrameData[times.length];
			for(int i = 0; i < frames.length; i++){
				frames[i] = new KeyFrameData(times[i]);
			}
			return frames;
		}
		
		/**
		 * Load transformation data for each KeyFrame.
		 * @param keyFrames Animation KeyFrames that contain Transformation data at given times.
		 * @param jointNode Node containing joint data.
		 * @param rootJoint Model's root joint.
		 */
		private void loadTransformationFromNode(KeyFrameData[] keyFrames, XmlNode jointNode, String rootJoint){
			String jointName = getJointName(jointNode);
			String dataId = getDataId(jointNode);
			XmlNode transform = jointNode.getChildWithAttribute("source", "id", dataId);
			String[] rawData = transform.getChild("float_array").getData().split(" ");
			processTransforms(jointName, rawData, keyFrames, jointName.equals(rootJoint));
		}
		
		/**
		 * Creates joint transformations based on rawData. Created transformations are then added to keyFrames.
		 * @param jointName Name of the joint that is being transformed.
		 * @param rawData String array containing transformation data from Xml.
		 * @param keyFrames Array of animation's KeyFrames.
		 * @param root Model's root joint.
		 */
		private void processTransforms(String jointName, String[] rawData, KeyFrameData[] keyFrames, boolean root){
			FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
			float[] matrixData = new float[16];
			for(int i=0;i<keyFrames.length;i++){
				for(int j=0;j<16;j++){
					matrixData[j] = Float.parseFloat(rawData[i*16 + j]);
				}
				buffer.clear();
				buffer.put(matrixData);
				buffer.flip();
				Matrix4f transform = new Matrix4f();
				transform.load(buffer);
				transform.transpose();
				if(root){
					//because up axis in Blender is different to up axis in game
					Matrix4f.mul(CORRECTION, transform, transform);
				}
				keyFrames[i].addJointTransform(new JointTransformData(jointName, transform));
			}
		}
		
		/**
		 * Get output data id of jointNode.
		 * @param jointNode jointNode which
		 * @return
		 */
		private String getDataId(XmlNode jointNode){
			XmlNode node = jointNode.getChild("sampler").getChildWithAttribute("input", "semantic", "OUTPUT");
			return node.getAttribute("source").substring(1);
		}
		
		/**
		 * Get joint name from XmlNode.
		 * @param jointNode JointNode to extract joint name from.
		 * @return Joint name.
		 */
		private String getJointName(XmlNode jointNode){
			String data = jointNode.getChild("channel").getAttribute("target");
			return data.split("/")[0];
		}
		
		/**
		 * Get id of root joint from joint Xml.
		 * @return Id of root joint.
		 */
		private String getRootJoint(){
			XmlNode skeleton = jointHierarchy.getChild("visual_scene").getChildWithAttribute("node", "id", "Armature");
			return skeleton.getChild("node").getAttribute("id");
		}
	}
	
}
