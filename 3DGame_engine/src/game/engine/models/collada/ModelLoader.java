package game.engine.models.collada;

import game.engine.animation.Animation;
import game.engine.models.xml.XmlNode;
import game.engine.models.xml.XmlParser;

public class ModelLoader {

	/**
	 * Loads collada model data from the given path.
	 * 
	 * @param Path to collada file.
	 * @param maxWeights Amount of maximum number of affecting joints per vertex.
	 * @return
	 */
	public static AnimatedModelData loadColladaModel(String path, int maxWeights){
		XmlNode node = XmlParser.loadXmlFile(path);
		
		//Skinning data of the model
		SkinningData skinningData = new SkinningData.SkinLoader(node.getChild("library_controllers"), maxWeights).extractSkinData();
		
		//Joint data of the model
		JointsData jointsData = new JointsData.JointsLoader(node.getChild("library_visual_scenes"), skinningData.jointOrder).extractBoneData();
	
		//Mesh data of the model
		Mesh mesh = new Mesh.Loader(node.getChild("library_geometries"), skinningData.verticesSkinData).extractModelData();
		
		return new AnimatedModelData(mesh, jointsData);
	}
	
	/**
	 * Loads animation data from Collada file.
	 * @param path Path of our Collada file.
	 * @return AnimationData object containing animation data.
	 */
	public static Animation loadColladaAnimation(String path){
		XmlNode node = XmlParser.loadXmlFile(path);
		XmlNode animNode = node.getChild("library_animations");
		XmlNode jointsNode = node.getChild("library_visual_scenes");
		Animation animation = new AnimationData.AnimationLoader(animNode, jointsNode).extractAnimation();
		return animation;
	}
	
	
}
