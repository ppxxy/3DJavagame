package game.engine.entities;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.main.Main;
import game.engine.tools.Maths;

public class Movement {

	private Vector3f[] path;
	private Entity entity;
	private int pos_index;
	private float speed;
	private long previousFrame;

	public Movement(Entity entity, float speed, Vector3f destination){
		this.entity = entity;
		path = calcPath(destination);
		this.speed = speed;
		this.previousFrame = System.currentTimeMillis();
	}

	public Movement(Entity entity, float speed, Vector3f... path){
		this.entity = entity;
		this.speed = speed;
		this.path = path;
		this.previousFrame = System.currentTimeMillis();
	}

	public Vector3f[] calcPath(Vector3f destination){
		return path = new Vector3f[]{destination};
	}

	public boolean update(){
		Vector3f source = entity.getPosition();
		if(pos_index >= path.length){
			return false;
		}
		Vector3f destination = path[pos_index];
		Vector3f dir_vector = new Vector3f(destination.x-source.x, 0, destination.z-source.z);
		float dist = dir_vector.length();
		dir_vector.normalise();
		entity.setRotY((float) (Math.toDegrees((dir_vector.z < 0 ? -135 : 0)+Math.atan(dir_vector.x/dir_vector.z))));
		long currentTime = System.currentTimeMillis();
		float distance = speed*(currentTime-previousFrame)/700f;
		if(dist < distance){
			source = destination;
			pos_index++;
		}
		previousFrame = currentTime;
		source.translate(dir_vector.x*distance, 0, dir_vector.z*distance);
		source.setY(getHeight(source.x, source.z));
		return true;
	}

	private float getHeight(float x, float z){
		float[][] heightmap = Main.activeView.getTerrain().getHeightMap();
		Vector3f a, b, c;
		if(x%1 <= 1-z%1){
			a = new Vector3f((int) 0, heightmap[(int) x][(int) z], (int) 0);
		}
		else{
			a = new Vector3f((int) 1, heightmap[(int) x+1][(int) z+1], (int) 1);
		}
		b = new Vector3f((int) 0, heightmap[(int) x][(int) z+1], (int) 1);
		c = new Vector3f((int) 1, heightmap[(int) x+1][(int) z], (int) 0);
		return Maths.barryCentric(a, b, c, new Vector2f(x%1, z%1));
	}
}
