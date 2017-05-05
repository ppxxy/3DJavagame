package game.engine.terrain;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.lwjgl.util.vector.Vector3f;

import game.engine.models.VAO;
import game.engine.textures.Texture;

/**
 * Terrain class contains data of surrounding chunks {@link ChunkData}.
 * @author Kim
 *
 */
public class Terrain{

	/**
	 * Determines how many chunks are loaded.
	 */
	private static final int CHUNKS_WIDTH = 10, CHUNKS_HEIGHT = 10;
	private ChunkData[][] chunks;
	private Texture texture;

	private int currentChunkX, currentChunkZ;

	/**
	 * Creates terrain object with CenterChunk(x, z)
	 * @param x Coordinate of the center chunk.
	 * @param z Coordinate of the center chunk.
	 */
	public Terrain(int x, int z){
		this.chunks = new ChunkData[CHUNKS_WIDTH][CHUNKS_HEIGHT];
		this.currentChunkX = x;
		this.currentChunkZ = z;
	}

	/**
	 * Method loads all the surrounding chunks.
	 */
	public void loadChunks(){
		BufferedImage image = new BufferedImage(CHUNKS_WIDTH*ChunkData.WIDTH, CHUNKS_HEIGHT*ChunkData.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < CHUNKS_WIDTH; x++){
			for(int z = 0; z < CHUNKS_HEIGHT; z++){
				try {
					ObjectInputStream chunkLoader = new ObjectInputStream(Class.class.getResourceAsStream("/res/chunks/" +(x+10*z) +".chk"));
					ChunkData chunk = (ChunkData) chunkLoader.readObject();
					chunks[x][z] = chunk;
					image.setRGB(x*ChunkData.WIDTH, z*ChunkData.HEIGHT, ChunkData.WIDTH, ChunkData.HEIGHT, chunk.getTexture(), 0, ChunkData.WIDTH);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		texture = Texture.loadTexture(image).anisotropic().load();
	}

	/**
	 * Method that is used to load terrain model. Creates vertices array containing every vertex point as well as indices array
	 * containing every line connecting different vertices.
	 * @return TerrainModel object that represents mesh data of the terrain.
	 */
	public TerrainModel loadModel(){
		int w = (CHUNKS_WIDTH*ChunkData.WIDTH-CHUNKS_WIDTH+1), h = (CHUNKS_HEIGHT*ChunkData.HEIGHT-CHUNKS_HEIGHT+1);
		float[] vertices = new float[3*w*h];
		float[] normals = new float[3*w*h];
		float[][] heightMap = new float[w][h];
		float[] textureCoords = new float[2*w*h];
		int[] indices = new int[CHUNKS_WIDTH*CHUNKS_HEIGHT*(ChunkData.WIDTH-1)*(ChunkData.HEIGHT-1)*6];
		//System.out.println(vertices.length +", " +indices.length);
		for(int i = 0; i < vertices.length/3; i++){
			int x = i%w;
			int z = i/w;
			float y = chunks[x/ChunkData.WIDTH][z/ChunkData.HEIGHT].getHeight(x%ChunkData.WIDTH, z%ChunkData.HEIGHT);
			vertices[i*3] = x; //x
			vertices[i*3+1] = y; //y
			vertices[i*3+2] = z; //z
			Vector3f normal = chunks[x/ChunkData.WIDTH][z/ChunkData.HEIGHT].getNormal(x%ChunkData.WIDTH, z%ChunkData.HEIGHT);
			normals[i*3] = normal.x;
			normals[i*3 + 1] = normal.y;
			normals[i*3 + 2] = normal.z;
			heightMap[x][z] = y;
			textureCoords[i*2] = (float)x/(float)w;
			textureCoords[i*2+1] = (float)z/(float)h;
		}
		for(int i = 0; i < indices.length/6; i++){
			int a = i+i/(CHUNKS_WIDTH*ChunkData.WIDTH-CHUNKS_WIDTH);
			indices[i*6]=a;
			indices[i*6+1] = a+1;
			int b = a+(CHUNKS_WIDTH*ChunkData.WIDTH-CHUNKS_WIDTH+1);
			indices[i*6+2] = b;
			indices[i*6+3] = b;
			indices[i*6+4] = a+1;
			indices[i*6+5] = b+1;
		}
		return new TerrainModel(createVao(vertices, textureCoords, normals, indices), heightMap, texture);
	}

	/**
	 * Saves Terrain model into a VAO.
	 * @param vertices Vertices array containing TerrainModel vertices.
	 * @param indices Indices array containing TerrainModel indices.
	 * @return VAO that Terrain model data is saved to.
	 */
	private VAO createVao(float[] vertices, float[] textureCoords, float[] normals, int[] indices){
		VAO vao = VAO.create();
		vao.bind();
		vao.createIndexBuffer(indices);
		vao.createAttribute(0, vertices, 3);
		vao.createAttribute(1, textureCoords, 2);
		vao.createAttribute(2, normals, 3);
		vao.unbind();
		return vao;
	}

	/**
	 * Sets the current chunk of this terrain. Terrain data should be reloaded after call to this method.
	 * @param x X coordinate of the new center chunk.
	 * @param z Z coordinate of the new center chunk.
	 */
	public void setCurrentChunk(int x, int z){
		this.currentChunkX = x;
		this.currentChunkZ = z;
	}

}
