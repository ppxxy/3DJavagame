package game.engine.models.collada;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.models.xml.XmlNode;

public class JointsData {

	public final int jointCount;
	public final JointData rootJoint;
	
	/**
	 * Joint data of the model.
	 * @param jointCount Number of joints on the model. 
	 * @param rootJoint Model's root joint.
	 */
	public JointsData(int jointCount, JointData rootJoint) {
		this.jointCount = jointCount;
		this.rootJoint = rootJoint;
	}

	public static class JointsLoader{
		
		private XmlNode armatureData;
		private List<String> boneOrder;
		
		private int jointCount = 0;
		
		/**
		 * Final value used to translate Y-axis to point upwards. On Blender Z-axis is pointing up.
		 */
		private static final Matrix4f CORRECTION = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));
		
		/**
		 * JointsLoader is used to load Joint data from XmlNode.
		 * @param visualSceneNode Node to read data from.
		 * @param boneOrder List of joint names.
		 */
		public JointsLoader(XmlNode visualSceneNode, List<String> boneOrder){
			this.armatureData = visualSceneNode.getChild("visual_scene").getChildWithAttribute("node", "id", "Armature");
			this.boneOrder = boneOrder;
		}
		
		/**
		 * Load joint data of the whole model.
		 * @return JointsData object containing joint count and root joint.
		 */
		public JointsData extractBoneData(){
			XmlNode headNode = armatureData.getChild("node");
			JointData rootJoint = loadJointData(headNode, true);
			return new JointsData(jointCount, rootJoint);
		}
		
		/**
		 * Loads joint data of each joint on this model. Contains recursive structure for easy parenting!
		 * @param jointNode Xml node containing data to transform into joint object.
		 * @param isRoot True only if this is the root node. The only one with no parents.
		 * @return Joint object. (recursive approach)
		 */
		private JointData loadJointData(XmlNode jointNode, boolean isRoot){
			JointData joint = extractMainJointData(jointNode, isRoot);
			for(XmlNode childNode : jointNode.getChildren("node")){
				joint.addChild(loadJointData(childNode, false));
			}
			return joint;
		}
		
		/**
		 * Translates XmlNode data into Joint object.
		 * @param jointNode XmlNode containing joint data.
		 * @param isRoot Boolean to define root joint.
		 * @return Joint object that is created using the Xml data.
		 */
		private JointData extractMainJointData(XmlNode jointNode, boolean isRoot){
			String name = jointNode.getAttribute("id");
			int index = boneOrder.indexOf(name);
			String[] matrixData = jointNode.getChild("matrix").getData().split(" ");
			Matrix4f matrix = new Matrix4f();
			matrix.load(convertData(matrixData));
			matrix.transpose();
			if(isRoot){
				//Change Y axis to point upwards. In model data Z axis points upwards.
				Matrix4f.mul(CORRECTION, matrix, matrix);
			}
			jointCount++;
			return new JointData(index, name, matrix);
		}
		
		/**
		 * Method used to transform Xml matrix data from String[] to FloatBuffer to use in Matrix4f.
		 * @param rawData String array containing joint's Transformation, rotation and scale.
		 * @return FloatBuffer containing joint's matrix data.
		 */
		private FloatBuffer convertData(String[] rawData){
			float[] matrixData = new float[16];
			for(int i=0;i<matrixData.length;i++){
				matrixData[i] = Float.parseFloat(rawData[i]);
			}
			FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
			buffer.put(matrixData);
			buffer.flip();
			return buffer;
		}
	}
}
