package game.engine.terrain;

public class SurfacePoint {

	private float height, weight;

	public SurfacePoint(float height, float weight){
		this.height = height;
		this.weight = weight;
	}

	public float getHeight(){
		return this.height;
	}

	public float getWeight(){
		return this.weight;
	}

}
