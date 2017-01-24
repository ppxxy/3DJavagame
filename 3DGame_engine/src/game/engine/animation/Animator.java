package game.engine.animation;

import game.engine.models.AnimatedModel;
import game.engine.models.Joint;
import game.engine.rendering.DisplayManager;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;

public class Animator {

	private float animationTime = 0;
	private Animation animation;
	
	private AnimatedModel model;
	
	public Animator(AnimatedModel model){
		this.model = model;
	}
	
	/**
	 * Makes the entity carry out this animation. Sets the animation timer to 0.
	 * @param animation Animation to carry out.
	 */
	public void doAnimation(Animation animation){
		this.animationTime = 0;
		this.animation = animation;
	}
	
	public void update(){
		if(animation == null){
			return;
		}
		increaseTime();
		Map<String, Matrix4f> pose = calculateCurrentPose();
		applyPoseToJoints(pose, model.getRootJoint(), new Matrix4f());
	}
	
	private void increaseTime(){
		animationTime += DisplayManager.getFrameTime();
		if(animationTime > animation.getLength()){
			this.animationTime %= animation.getLength();
		}
	}
	
	private Map<String, Matrix4f> calculateCurrentPose(){
		KeyFrame[] frames = getFrames();
		float progression = calculateProgression(frames[0], frames[1]);
		return interpolatePoses(frames[0], frames[1], progression);
	}
	
	/**
	 * Method to get previous and upcoming KeyFrames.
	 * @return Previous and upcoming KeyFrames.
	 */
	private KeyFrame[] getFrames(){
		KeyFrame previous = null;
		KeyFrame next = null;
		for(KeyFrame frame : animation.getKeyFrames()){
			if(frame.getTimeStamp() > animationTime){
				next = frame;
				break;
			}
			previous = frame;
		}
		previous = previous == null ? next : previous;
		next = next == null ? previous : next;
		return new KeyFrame[]{previous, next};
	}
	
	private void applyPoseToJoints(Map<String, Matrix4f> currentPose, Joint joint, Matrix4f parentTransform) {
		Matrix4f currentLocalTransform = currentPose.get(joint.name);
		Matrix4f currentTransform = Matrix4f.mul(parentTransform, currentLocalTransform, null);
		for (Joint childJoint : joint.children) {
			applyPoseToJoints(currentPose, childJoint, currentTransform);
		}
		Matrix4f.mul(currentTransform, joint.getInverseBindTransform(), currentTransform);
		joint.setAnimationTransform(currentTransform);
	}
	
	private float calculateProgression(KeyFrame previous, KeyFrame next){
		float timeDifference = next.getTimeStamp() - previous.getTimeStamp();
		return (animationTime - previous.getTimeStamp()) / timeDifference;
	}
	
	private Map<String, Matrix4f> interpolatePoses(KeyFrame previous, KeyFrame next, float progression){
		Map<String, Matrix4f> currentPose = new HashMap<String, Matrix4f>();
		for(String jointName : previous.getJointKeyFrames().keySet()){
			JointTransform previousTransform = previous.getJointKeyFrames().get(jointName);
			JointTransform nextTransform = next.getJointKeyFrames().get(jointName);
			JointTransform interpolatedTransform = JointTransform.intepolate(previousTransform, nextTransform, progression);
			currentPose.put(jointName, interpolatedTransform.getLocalTransform());
		}
		return currentPose;
	}
}
