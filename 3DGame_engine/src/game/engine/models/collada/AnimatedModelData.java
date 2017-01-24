package game.engine.models.collada;

public class AnimatedModelData {

	private final JointsData jointsData;
	private final Mesh mesh;
	
	/**
	 * Collada object. Contains mesh data and joints data.
	 * @param mesh Mesh data that defines the look of our object.
	 * @param jointsData Contains the data of our object's joints.
	 */
	protected AnimatedModelData(Mesh mesh, JointsData jointsData){
		this.jointsData = jointsData;
		this.mesh = mesh;
	}
	
	public JointsData getJointsData(){
		return jointsData;
	}
	
	public Mesh getMesh(){
		return mesh;
	}
}
