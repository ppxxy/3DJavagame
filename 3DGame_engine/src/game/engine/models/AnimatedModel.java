package game.engine.models;

import org.lwjgl.util.vector.Matrix4f;

import game.engine.animation.Animation;
import game.engine.animation.Animator;
import game.engine.textures.Texture;

public class AnimatedModel{
		
	private final VAO model;
	private final Texture texture;
	
	private final Joint rootJoint;
	private final int jointCount;
	
	private final Animator animator;
		
	public AnimatedModel(VAO model, Texture texture, Joint rootJoint, int jointCount){
		this.model = model;
		this.texture = texture;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
		rootJoint.calcInverseBindTransform(new Matrix4f());
	}	
	
	public VAO getModel(){
		return model;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public Joint getRootJoint(){
		return rootJoint;
	}
	
	public void delete(){
		model.delete();
		texture.delete();
	}
	
	public void doAnimation(Animation animation){
		animator.doAnimation(animation);
	}
	
	public void update(){
		animator.update();
	}
	
	public Matrix4f[] getJointTransforms(){
		Matrix4f[] jointMatrices = new Matrix4f[jointCount];
		addJointsToArray(rootJoint, jointMatrices);
		return jointMatrices;
	}
	
	private void addJointsToArray(Joint parentJoint, Matrix4f[] jointMatrices){
		jointMatrices[parentJoint.index] = parentJoint.getAnimatedTransform();
		for(Joint childJoint : parentJoint.children){
			addJointsToArray(childJoint, jointMatrices);
		}
	}
}
