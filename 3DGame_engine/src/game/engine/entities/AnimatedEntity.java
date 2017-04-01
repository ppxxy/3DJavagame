package game.engine.entities;

import game.engine.models.AnimatedModel;
import game.engine.models.Joint;
import game.engine.models.VAO;
import game.engine.models.collada.AnimatedModelData;
import game.engine.models.collada.JointData;
import game.engine.models.collada.JointsData;
import game.engine.models.collada.Mesh;
import game.engine.models.collada.ModelLoader;
import game.engine.textures.Texture;
import game.engine.tools.GeneralSettings;

import org.lwjgl.util.vector.Vector3f;

public class AnimatedEntity extends Entity{

	private AnimatedModel animatedModel;
	private int id;

	public AnimatedEntity(int id, Vector3f position, float rotX, float rotY, float rotZ, float scale, AnimatedModel animatedModel) {
		super(position, rotX, rotY, rotZ, scale);
		this.id = id;
		this.animatedModel = animatedModel;
	}

	public static AnimatedEntity loadEntity(int id, String modelFile, String textureFile){
		AnimatedModelData entityData = ModelLoader.loadColladaModel(modelFile, GeneralSettings.MAX_WEIGHTS);
		VAO vao = createVao(entityData.getMesh());
		Texture texture = loadTexture(textureFile);
		JointsData skeletonData = entityData.getJointsData();
		Joint rootJoint = createJoints(skeletonData.rootJoint);
		AnimatedModel model = new AnimatedModel(vao, texture, rootJoint, skeletonData.jointCount);
		return new AnimatedEntity(id, new Vector3f(1, 1, 1), 0, 0, 0, 1, model);
	}

	protected static Texture loadTexture(String path){
		Texture diffuseTexture = Texture.loadTexture(path).anisotropic().load();
		return diffuseTexture;
	}

	protected static Joint createJoints(JointData data){
		Joint joint = new Joint(data.index, data.name, data.bindLocalTransform);
		for(JointData child : data.children){
			joint.addChild(createJoints(child));
		}
		return joint;
	}

	public AnimatedModel getModel() {
		return this.animatedModel;
	}

	protected static VAO createVao(Mesh data){
		VAO vao = VAO.create();
		vao.bind();
		vao.createIndexBuffer(data.getIndices());
		vao.createAttribute(0, data.getVertices(), 3);
		vao.createAttribute(1, data.getTextureCoords(), 2);
		vao.createAttribute(2, data.getNormals(), 3);
		vao.createAttribute(3, data.getJointIds(), 3);
		vao.createAttribute(4, data.getVertexWeights(), 3);
		vao.unbind();
		return vao;
	}

	public void update() {
		this.animatedModel.update();
	}

	public int getId() {
		return id;
	}
}
