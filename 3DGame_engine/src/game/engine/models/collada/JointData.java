package game.engine.models.collada;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

public class JointData {

	public final int index;
	public final String name;
	public final Matrix4f bindLocalTransform;
	
	public final List<JointData> children = new ArrayList<JointData>();
	
	/**
	 * Joint object
	 * @param index Determine the amount of parent joints.
	 * @param name Name of this joint.
	 * @param bindLocalTransform Transformation of this joint. Transformation, rotation and scale.
	 */
	protected JointData(int index, String name, Matrix4f bindLocalTransform){
		this.index = index;
		this.name = name;
		this.bindLocalTransform = bindLocalTransform;
	}
	
	/**
	 * Method used to add child joint for this joint.
	 * @param child Child node to add.
	 */
	public void addChild(JointData child){
		children.add(child);
	}
	
}
