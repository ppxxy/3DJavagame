package game.engine.terrain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.imageio.ImageIO;

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
	private int[] texture;

	public ChunkData(int x, int y){
		this.x = x;
		this.y = y;
		this.points = new float[WIDTH][HEIGHT];
	}

	public void addPoint(int x, int y, float value){
		this.points[x][y] = 100f*value;
	}

	public float getHeight(int x, int z) {
		return points[x][z];
	}

	public int[] getTexture(){
		return this.texture;
	}

	public void loadTexture(){
		int[] image = new int[WIDTH*HEIGHT];
		for(int y = 0; y < HEIGHT; y++){
			for(int x = 0; x < WIDTH; x++){
				image[x+y*WIDTH] = new Color(0f, 0f, 0f, 1f).getRGB();
			}
		}
		this.texture = image;
	}

	public void loadTexture(BufferedImage img){
		int[] image = new int[WIDTH*HEIGHT];
		for(int y = 0; y < HEIGHT; y++){
			for(int x = 0; x < WIDTH; x++){
				image[x+y*WIDTH] = img.getRGB(x+this.x*WIDTH, y+this.y*HEIGHT);
			}
		}
		this.texture = image;
	}
}
