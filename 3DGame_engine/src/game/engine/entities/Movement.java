package game.engine.entities;

import org.lwjgl.util.vector.Vector2f;

public class Movement {

	private int pos_index = 0;

	private Vector2f current;
	private Vector2f[] path;
	private float speed;

	private float rotation;

	private long previousFrame;

	public Movement(long time, Vector2f source, Vector2f target, float speed){
		this.previousFrame = time;
		this.current = source;
		this.path = new Vector2f[]{target};
		this.speed = speed;
	}

	public Movement(long time, Vector2f source, Vector2f[] path, float speed){
		this.previousFrame = time;
		this.current = source;
		this.path = path;
		this.speed = speed;
	}

	public Vector2f update(){
		if(pos_index >= path.length){
			return null;
		}
		Vector2f destination = path[pos_index];
		Vector2f dir_vector = new Vector2f(destination.x-current.x, destination.y-current.y);
		float dist = dir_vector.length();
		dir_vector.normalise();
		rotation = (float) (Math.toDegrees((dir_vector.y < 0 ? -135 : 0)+Math.atan(dir_vector.x/dir_vector.y)));
		//entity.setRotY((float) (Math.toDegrees((dir_vector.z < 0 ? -135 : 0)+Math.atan(dir_vector.x/dir_vector.z))));
		long currentTime = System.currentTimeMillis();
		float distance = speed*(currentTime-previousFrame)/100f;
		if(dist < distance){
			current = destination;
			pos_index++;
		}
		else{
			current.translate(dir_vector.x*distance, dir_vector.y*distance);
		}
		previousFrame = currentTime;
		//source.setY(getHeight(source.x, source.z));
		return current;
	}

	public float getRotation(){
		return this.rotation;
	}

}
