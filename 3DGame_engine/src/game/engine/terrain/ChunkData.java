package game.engine.terrain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Kim
 *
 */
public class ChunkData {

	public static final int WIDTH = 5, HEIGHT = 5;
	private final int x, y;
	private final SurfacePoint[][] points;

	public ChunkData(int x, int y){
		this.x = x;
		this.y = y;
		this.points = new SurfacePoint[WIDTH][HEIGHT];
	}

	public void addPoint(){

	}

	public static class ChunkLoader{

		private int chunkX, chunkY;
		private String file;

		private ChunkLoader(int chunkX, int chunkY, String file){
			this.chunkX = chunkX;
			this.chunkY = chunkY;
			this.file = file;
		}

		public ChunkData loadChunk(){
			ChunkData data = new ChunkData(chunkX, chunkY);
			BufferedReader reader = null;
			try{
				reader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(file)));
				String line;
				while((line = reader.readLine()) != null){
					String[] datas = line.split(", ");

				}
			} catch(IOException e){
				e.printStackTrace();
			}
			finally{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return data;
		}
	}
}
