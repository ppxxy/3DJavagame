package game.engine.terrain;

import game.engine.models.VAO;

public class Terrain{

	private static final int CHUNKS_WIDTH = 10, CHUNKS_HEIGHT = 10;
	private ChunkData[][] chunks;

	private int currentChunkX, currentChunkZ;

	public Terrain(int x, int z){
		this.chunks = new ChunkData[CHUNKS_WIDTH][CHUNKS_HEIGHT];
		this.currentChunkX = x;
		this.currentChunkZ = z;
	}

	public void loadChunks(){
		for(int x = 0; x < CHUNKS_WIDTH; x++){
			for(int z = 0; z < CHUNKS_HEIGHT; z++){
				chunks[x][z] = new ChunkData(x+currentChunkX-CHUNKS_WIDTH/2, z+currentChunkZ-CHUNKS_HEIGHT/2);
			}
		}
	}

	public TerrainModel loadModel(){
		float[] vertices = new float[3*(CHUNKS_WIDTH*ChunkData.WIDTH-CHUNKS_WIDTH+1)*(CHUNKS_HEIGHT*ChunkData.HEIGHT-CHUNKS_HEIGHT+1)];
		int[] indices = new int[CHUNKS_WIDTH*CHUNKS_HEIGHT*(ChunkData.WIDTH-1)*(ChunkData.HEIGHT-1)*6];
		//System.out.println(vertices.length +", " +indices.length);
		for(int i = 0; i < vertices.length/3; i++){
			vertices[i*3] = i%(CHUNKS_WIDTH*ChunkData.WIDTH-CHUNKS_WIDTH+1)*10; //x
			vertices[i*3+1] = (float) (Math.random()*10f); //y
			vertices[i*3+2] = i/(CHUNKS_WIDTH*ChunkData.WIDTH-CHUNKS_WIDTH+1)*10; //z
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
		return new TerrainModel(createVao(vertices, indices));
	}

	private VAO createVao(float[] vertices, int[] indices){
		VAO vao = VAO.create();
		vao.bind();
		vao.createIndexBuffer(indices);
		vao.createAttribute(0, vertices, 3);
		vao.unbind();
		return vao;
	}

	public void setCurrentChunk(int x, int z){
		this.currentChunkX = x;
		this.currentChunkZ = z;
	}

}
