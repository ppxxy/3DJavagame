package game.engine.characters;

import org.lwjgl.util.vector.Vector3f;

import game.engine.entities.AnimatedEntity;
import game.engine.entities.Movement;
import game.engine.models.AnimatedModel;
import game.engine.models.Joint;
import game.engine.models.VAO;
import game.engine.models.collada.AnimatedModelData;
import game.engine.models.collada.JointsData;
import game.engine.models.collada.ModelLoader;
import game.engine.textures.Texture;
import game.engine.tools.GeneralSettings;

public class Character extends AnimatedEntity{

    private Movement movement;

    public Character(Vector3f position, float rotX, float rotY, float rotZ, float scale, AnimatedModel animatedModel) {
        super(position, rotX, rotY, rotZ, scale, animatedModel);
    }

    @Override
    public void update(){
        super.update();
        if(this.movement != null){
        	if(!this.movement.update()){
        		this.movement = null;
        	}
        }
    }

    public static Character loadCharacter(String modelFile, String textureFile){
        AnimatedModelData entityData = ModelLoader.loadColladaModel(modelFile, GeneralSettings.MAX_WEIGHTS);
        VAO vao = createVao(entityData.getMesh());
        Texture texture = loadTexture(textureFile);
        JointsData skeletonData = entityData.getJointsData();
        Joint rootJoint = createJoints(skeletonData.rootJoint);
        AnimatedModel model = new AnimatedModel(vao, texture, rootJoint, skeletonData.jointCount);
        return new Character(new Vector3f(1, 1, 1), 0, 0, 0, 1, model);
    }

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

}
