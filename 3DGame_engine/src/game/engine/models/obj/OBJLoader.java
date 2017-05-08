package game.engine.models.obj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.engine.main.Main;
import game.engine.models.VAO;

public class OBJLoader {

    private String path;

    public OBJLoader(String path){
        this.path = path;
    }

    public List<VAO> loadModel(){
        List<VAO> parts = new ArrayList<VAO>();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(path)));
            String line;
            List<Vector3f> vertices = new ArrayList<Vector3f>();
            List<Vector2f> textures = new ArrayList<Vector2f>();
            List<Vector3f> normals = new ArrayList<Vector3f>();
            List<Integer> indices = new ArrayList<Integer>();
            while((line = reader.readLine()) != null){
                String[] data = line.split(" ");
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(data[1]),
                            Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                    vertices.add(vertex);
                }
                else if(line.startsWith("vt ")){
                    Vector2f texture = new Vector2f(Float.parseFloat(data[1]),
                            Float.parseFloat(data[2]));
                    textures.add(texture);
                }
                else if(line.startsWith("vn ")){
                    Vector3f normal = new Vector3f(Float.parseFloat(data[1]),
                            Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                    normals.add(normal);
                }
                else if(line.startsWith("f ")){
                    float[] textureArray = new float[vertices.size()*2];
                    float[] normalsArray = new float[vertices.size()*3];
                    while(line != null && line.startsWith("f ")){
                        data = line.split(" ");
                        String[] vertex1 = data[1].split("/");
                        String[] vertex2 = data[2].split("/");
                        String[] vertex3 = data[3].split("/");

                        processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                        processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                        processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                        line = reader.readLine();
                        if(line != null && line.startsWith("s ")){
                            line = reader.readLine();
                        }
                    }
                    float[] verticesArray = new float[vertices.size()*3];

					int vertexPointer = 0;
					for(Vector3f vertex : vertices){
						verticesArray[vertexPointer++] = vertex.x;
						verticesArray[vertexPointer++] = vertex.y;
						verticesArray[vertexPointer++] = vertex.z;
					}

					int[] indicesArray = new int[indices.size()];
					for(int i = 0; i < indicesArray.length; i++){
						indicesArray[i] = indices.get(i);
					}

					indices.clear();
                    parts.add(new Mesh(verticesArray, textureArray, normalsArray, indicesArray).storeToVAO());
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return parts;
    }

    private static void processVertex(String[] vertexData, List<Integer> indices,
            List<Vector2f> textures, List<Vector3f> normals, float[] textureArray,
            float[] normalsArray){
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
        textureArray[currentVertexPointer*2] = currentTex.x;
        textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
        normalsArray[currentVertexPointer*3] = currentNorm.x;
        normalsArray[currentVertexPointer*3+1] = currentNorm.y;
        normalsArray[currentVertexPointer*3+2] = currentNorm.z;
    }

    private static class Mesh{

        private float[] vertices, textureCoords, normals;
        private int[] indices;

        private Mesh(float[] vertices, float[] textureCoords, float[] normals, int[] integers){
        	this.vertices = vertices;
        	this.textureCoords = textureCoords;
        	this.normals = normals;
        	this.indices = integers;
        }

		private VAO storeToVAO(){
			VAO vao = VAO.create();
			vao.bind();
			vao.createIndexBuffer(indices);
			vao.createAttribute(0, vertices, 3);
			vao.createAttribute(1, textureCoords, 2);
			vao.createAttribute(2, normals, 3);
			vao.unbind();
			return vao;
		}
    }
}