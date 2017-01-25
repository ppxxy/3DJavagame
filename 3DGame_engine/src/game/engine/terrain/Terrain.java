package game.engine.terrain;

public class Terrain{

	private static final int CHUNKS_WIDTH = 5, CHUNKS_HEIGHT = 5;
	private ChunkData[][] chunks;

	private int currentChunkX, currentChunkY;

	public Terrain(int x, int y){
		this.chunks = new ChunkData[CHUNKS_WIDTH][CHUNKS_HEIGHT];
		this.currentChunkX = x;
		this.currentChunkY = y;
	}

	public void loadChunks(){
		for(int x = 0; x < CHUNKS_WIDTH; x++){
			for(int y = 0; y < CHUNKS_HEIGHT; y++){
				chunks[x][y] = new ChunkData(x+currentChunkX-CHUNKS_WIDTH/2, y+currentChunkY-CHUNKS_HEIGHT/2);
			}
		}
	}

	public void setCurrentChunk(int x, int y){
		this.currentChunkX = x;
		this.currentChunkY = y;
	}

}
