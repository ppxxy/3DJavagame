package game.engine.terrain;

import java.io.Serializable;

/**
 *
 * @author Kim
 *
 */
public class ChunkData implements Serializable{
	private static final long serialVersionUID = 4250045384077626235L;

	public static final int WIDTH = 64, HEIGHT = 64;
	private final int x, y;
	private final float[][] points;

	public ChunkData(int x, int y){
		this.x = x;
		this.y = y;
		this.points = new float[WIDTH][HEIGHT];
	}

	public void addPoint(int x, int y, float value){
		this.points[x][y] = value;
	}

	public float getHeight(int x, int z) {
		return points[x][z];
	}
}
