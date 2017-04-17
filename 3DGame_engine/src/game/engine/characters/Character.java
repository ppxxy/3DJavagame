package game.engine.characters;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.entities.AnimatedEntity;
import game.engine.entities.Movement;
import game.engine.main.Main;
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

    public Character(int id, Vector3f position, float rotX, float rotY, float rotZ, float scale, AnimatedModel animatedModel) {
        super(id, position, rotX, rotY, rotZ, scale, animatedModel);
    }

    @Override
    public void update(){
        super.update();
        if(this.movement != null){
        	Vector2f move = this.movement.update();
        	if(move == null){
        		this.movement = null;
        		return;
        	}
        	this.position.set(move.x, Main.getGameView().getHeightAt(move.x, move.y), move.y);
        	this.setRotY(movement.getRotation());
        }
    }

    public static Character loadCharacter(int id, String modelFile, String textureFile){
        AnimatedModelData entityData = ModelLoader.loadColladaModel(modelFile, GeneralSettings.MAX_WEIGHTS);
        VAO vao = createVao(entityData.getMesh());
        Texture texture = loadTexture(textureFile);
        JointsData skeletonData = entityData.getJointsData();
        Joint rootJoint = createJoints(skeletonData.rootJoint);
        AnimatedModel model = new AnimatedModel(vao, texture, rootJoint, skeletonData.jointCount);
        return new Character(id, new Vector3f(1, 1, 1), 0, 0, 0, 1, model);
    }

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	public Vector2f getLocation(){
		return new Vector2f(this.position.x, this.position.z);
	}

	public void setMoveTo(long startTime, Vector2f destination, float speed) {
		this.movement = new Movement(startTime, getLocation(), destination, speed);
	}

}
