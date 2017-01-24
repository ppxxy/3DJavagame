package game.engine.models.collada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.engine.models.xml.XmlNode;

/**
 * SkinningData contains data of vertex movement during animation.
 */

public class SkinningData {
	
	public final List<String> jointOrder;
	public final List<VertexSkinData> verticesSkinData;
	
	public SkinningData(List<String> joints, List<VertexSkinData> vertexWeights) {
		this.jointOrder = joints;
		this.verticesSkinData = vertexWeights;
	}

	public static class SkinLoader{
		
		private final XmlNode skinningData;
		private final int maxWeights;
		
		/**
		 * Create SkinLoader that loads Skin data.
		 * 
		 * @param node Node that contains the skin data.
		 * @param maxWeights Maximum amount of connected joints per vertex.
		 */
		public SkinLoader(XmlNode node, int maxWeights){
			this.skinningData = node.getChild("controller").getChild("skin");
			this.maxWeights = maxWeights;
		}
		
		/**
		 * Method used to transform skinning data from Xml to SkinningData object.
		 * Which contains data of every vertex's joint connection.
		 * @return SkinningData object, containing Joint, Vertex connections.
		 */
		
		public SkinningData extractSkinData() {
			XmlNode weightsDataNode = skinningData.getChild("vertex_weights");
			List<String> joints = loadJoints(weightsDataNode);
			float[] weights = loadWeights(weightsDataNode);
			int[] effectorJointCounts = getEffectiveJointsCounts(weightsDataNode);
			List<VertexSkinData> vertexWeights = getSkinData(weightsDataNode, effectorJointCounts, weights);
			return new SkinningData(joints, vertexWeights);
		}
		
		/**
		 * Method used to load joint data from the XMLdata inside the Collada file.
		 * @param weightsDataNode 
		 * 
		 * @param weightsDataNode XmlNode containing joints data
		 * 
		 * @return List of joints
		 */
		public List<String> loadJoints(XmlNode weightsDataNode){
			XmlNode inputNode = skinningData.getChild("vertex_weights");
			String jointId = inputNode.getChildWithAttribute("input", "semantic", "JOINT").getAttribute("source")
					.substring(1);
			XmlNode jointsNode = skinningData.getChildWithAttribute("source", "id", jointId).getChild("Name_array");
			return Arrays.asList(jointsNode.getData().split(" "));
		}
		
		/**
		 * Method used to load weights of different joints. Weight determines how much vertices
		 * are affected by each joint's movement.
		 * 
		 * @param weightsDataNode XmlNode containing weight data of each vertex. 
		 * 
		 * @return Weight values of each joint.
		 */
		
		public float[] loadWeights(XmlNode weightsDataNode){
			XmlNode inputNode = skinningData.getChild("vertex_weights");
			String weightsId = inputNode.getChildWithAttribute("input", "semantic", "WEIGHT").getAttribute("source")
					.substring(1);
			XmlNode weightsNode = skinningData.getChildWithAttribute("source", "id", weightsId).getChild("float_array");
			String[] rawData = weightsNode.getData().split(" ");
			float[] weights = new float[rawData.length];
			for(int i = 0; i < weights.length; i++){
				weights[i] = Float.parseFloat(rawData[i]);
			}
			return weights;
		}
		
		/**
		 * Method used to get the number of joints affecting each vertice's motion.
		 * 
		 * @param weightsDataNode XmlNode that contains weight data of each vertex.
		 * 
		 * @return Array containing number joints that affect each vertice.
		 */
		private int[] getEffectiveJointsCounts(XmlNode weightsDataNode){
			String[] rawData = weightsDataNode.getChild("vcount").getData().split(" ");
			int[] counts = new int[rawData.length];
			for(int i = 0; i < rawData.length; i++){
				counts[i] = Integer.parseInt(rawData[i]);
			}
			return counts;
		}
		
		/**
		 * Method that returns skin data of each vertex.
		 * 
		 * @param weightsDataNode Node that contains weight information for each vertex.
		 * @param counts The number of joints affecting each vertex.
		 * @param weights Weight value of each vertex, joint connection.
		 * @return List of skin data objects.
		 */
		private List<VertexSkinData> getSkinData(XmlNode weightsDataNode, int[] counts, float[] weights){
			//Data of each vertex
			String[] rawData = weightsDataNode.getChild("v").getData().split(" ");
			List<VertexSkinData> skinningData = new ArrayList<VertexSkinData>();
			int pointer = 0;
			for(int count : counts){
				VertexSkinData skinData = new VertexSkinData();
				for (int i = 0; i < count; i++) {
					int jointId = Integer.parseInt(rawData[pointer++]);
					int weightId = Integer.parseInt(rawData[pointer++]);
					skinData.addJointConnection(jointId, weights[weightId]);
				}
				skinData.evaluate(maxWeights);
				skinningData.add(skinData);
			}
			return skinningData;
		}
		
	}

	public static class VertexSkinData {
		
		final List<Integer> jointIds = new ArrayList<Integer>();
		final List<Float> weights = new ArrayList<Float>();
		
		/**
		 * Make connection between this vertex and joint.
		 * 
		 * @param jointId id of the connected joint.
		 * @param weight weight of the given joint. The amount joint's movement affects the movement of this vertex.
		 */
		private void addJointConnection(int jointId, float weight){
			//Orders connections so that the most meaningful weight comes always first in the list.
			for(int i = 0; i < weights.size(); i++){
				if(weight > weights.get(i)){
					jointIds.add(i, jointId);
					weights.add(i, weight);
					return;
				}
			}
			jointIds.add(jointId);
			weights.add(weight);
		}
		
		/**
		 * Limits amount of joints to max. Calculates the proportion of each weight
		 * making total weight 1.
		 * 
		 * @param max The maximum amount of joint connections for each joint.
		 */
		private void evaluate(int max){
			//remove excessive connections
			//Note: Saving to temporary variable is faster, because it allows to calculate total weight at the same time.
			if(jointIds.size() > max){
				float[] topWeights = new float[max];
				float total = 0;
				for(int i = 0; i < topWeights.length; i++){
					total += weights.get(i);
					topWeights[i] = weights.get(i);
				}
				while(jointIds.size() > max){
					jointIds.remove(jointIds.size()-1);
				}
			//calculate weight[i]/total for each
				weights.clear();
				for(float f : topWeights){
					//using Math.min here to make sure it doesn't round to over 1. That would lead to an error.
					weights.add(Math.min(f/total, 1));
				}
			}
			/*else if(jointIds.size() < max){
				while(jointIds.size() < max){
					jointIds.add(0);
					weights.add(0f);
				}
			}*/
		}
	}
}
