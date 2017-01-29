package game.engine.models.collada;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import game.engine.models.collada.SkinningData.VertexSkinData;
import game.engine.models.xml.XmlNode;

public class Mesh{

	private static final int DIMENSIONS = 3;

	private float[] vertices, textureCoords, normals, vertexWeights;
	private int[] indices, jointIds;
	private float furthestPoint;

	/**
	 * Mesh data of collada object
	 * @param vertices Vertices array.
	 * @param textureCoords Texture coodrinates array.
	 * @param normals Normals array.
	 * @param indices Indices array.
	 * @param jointIds JointIds array.
	 * @param vertexWeights Vertex weights array.
	 * @param furthestPoint Furthest point of the model.
	 */
	public Mesh(float[] vertices, float[] textureCoords, float[] normals, int[] indices, int[] jointIds, float[] vertexWeights, int furthestPoint){
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.jointIds = jointIds;
		this.vertexWeights = vertexWeights;
		this.furthestPoint = furthestPoint;
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public float[] getVertexWeights() {
		return vertexWeights;
	}

	public int[] getIndices() {
		return indices;
	}

	public int[] getJointIds() {
		return jointIds;
	}

	public float getFurthestPoint() {
		return furthestPoint;
	}

	public int getVertexCount(){
		return vertices.length / DIMENSIONS;
	}

	public static class Loader{

		private final XmlNode meshNode;
		private final List<VertexSkinData> vertexWeights;

		private float[] verticesArray, normalsArray, texturesArray, weightsArray;
		private int[] indicesArray, jointIdsArray;

		private List<Vertex> vertices = new ArrayList<Vertex>();
		private List<Vector3f> normals = new ArrayList<Vector3f>();
		private List<Vector2f> textures = new ArrayList<Vector2f>();
		private List<Integer> indices = new ArrayList<Integer>();

		private Matrix4f correction = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0,0));

		/**
		 * Creates mesh loader using the given node and VertexSkinData.
		 * @param geometryNode Node containing mesh geometry.
		 * @param verticesSkinData List containing vertex-joint connections.
		 */
		public Loader(XmlNode geometryNode, List<VertexSkinData> verticesSkinData) {
			this.meshNode = geometryNode.getChild("geometry").getChild("mesh");
			this.vertexWeights = verticesSkinData;
		}

		/**
		 * Creates Mesh object.
		 * @return Mesh object containing model geometry.
		 */
		public Mesh extractModelData(){
			readRawData();
			createPolygons();
			removeUnusedVertices();
			initArrays();
			saveDataToArrays();
			convertIndicesListToArray();
			return new Mesh(verticesArray, texturesArray, normalsArray, indicesArray, jointIdsArray, weightsArray, 1);
		}

		/**
		 * Reads model's vertices, normals and texture coordinates.
		 */
		private void readRawData(){
			readVertices();
			readNormals();
			readTextureCoords();
		}

		/**
		 * Reads vertice data of the mesh, creates Vertice objects and adds them to vertices -list.
		 */
		private void readVertices(){
			String verticeId = meshNode.getChild("vertices").getChild("input").getAttribute("source").substring(1);
			XmlNode verticesData = meshNode.getChildWithAttribute("source", "id", verticeId).getChild("float_array");
			int count = Integer.parseInt(verticesData.getAttribute("count"));
			String[] vertData = verticesData.getData().split(" ");
			for(int i = 0; i < count/DIMENSIONS; i++){
				float x = Float.parseFloat(vertData[i * DIMENSIONS]);
				float y = Float.parseFloat(vertData[i * DIMENSIONS + 1]);
				float z = Float.parseFloat(vertData[i * DIMENSIONS + 2]);
				Vector4f position = new Vector4f(x, y, z, 1);
				Matrix4f.transform(correction, position, position);
				vertices.add(new Vertex(vertices.size(), new Vector3f(position.x, position.y, position.z), vertexWeights.get(vertices.size())));
			}
		}

		/**
		 * Reads normals data of the mesh, creates normal vectors and adds them to normals -list.
		 */
		private void readNormals(){
			String normalsId = meshNode.getChild("polylist").getChildWithAttribute("input", "semantic", "NORMAL").getAttribute("source").substring(1);
			XmlNode normalsData = meshNode.getChildWithAttribute("source", "id", normalsId).getChild("float_array");
			int count = Integer.parseInt(normalsData.getAttribute("count"));
			String[] normData = normalsData.getData().split(" ");
			for(int i = 0; i < count/DIMENSIONS; i++){
				float x = Float.parseFloat(normData[i * DIMENSIONS]);
				float y = Float.parseFloat(normData[i * DIMENSIONS + 1]);
				float z = Float.parseFloat(normData[i * DIMENSIONS + 2]);
				Vector4f norm = new Vector4f(x, y, z, 0f);
				Matrix4f.transform(correction, norm, norm);
				normals.add(new Vector3f(norm.x, norm.y, norm.z));
			}
		}
		/**
		 * Reads textures data of the mesh, creates texture vectors and adds them to textures -list.
		 */
		private void readTextureCoords(){
			String texCoordsId = meshNode.getChild("polylist").getChildWithAttribute("input", "semantic", "TEXCOORD").getAttribute("source").substring(1);
			XmlNode textCoordsData = meshNode.getChildWithAttribute("source", "id", texCoordsId).getChild("float_array");
			int count = Integer.parseInt(textCoordsData.getAttribute("count"));
			String[] textData = textCoordsData.getData().split(" ");
			//Textures are 2Dimensional so final variable DIMENSIONS can't be used here.
			for(int i = 0; i < count/2; i++){
				float u = Float.parseFloat(textData[i * 2]);
				float v = Float.parseFloat(textData[i * 2 + 1]);
				textures.add(new Vector2f(u, v));
			}
		}

		/**
		 * Method assembles vertices, normals and texture coordinates.
		 */
		private void createPolygons(){
			XmlNode polygon = meshNode.getChild("polylist");
			int typeCount = polygon.getChildren("input").size();
			String[] indexData = polygon.getChild("p").getData().split(" ");
			for(int i = 0; i < indexData.length/typeCount; i++){
				int positionIndex = Integer.parseInt(indexData[i * typeCount]);
				int normalIndex = Integer.parseInt(indexData[i * typeCount + 1]);
				int textCoordIndex = Integer.parseInt(indexData[i * typeCount + 2]);
				processVertex(positionIndex, normalIndex, textCoordIndex);
			}
		}

		/**
		 * Link normal data and texture data to this vertex. If vertex already has texture and normal data linked to it, calls processAgain.
		 * @param posIndex Index of vertex to process.
		 * @param normIndex Index of normal to process.
		 * @param textIndex Index of texture data to process.
		 * @return Edited vertex.
		 */
		private Vertex processVertex(int posIndex, int normIndex, int textIndex){
			Vertex vertex = vertices.get(posIndex);
			//vertex at that position doesn't have any texture/normal data.
			if(!vertex.isSet()){
				vertex.setTextureIndex(textIndex);
				vertex.setNormalIndex(normIndex);
				indices.add(posIndex);
				return vertex;
			}
			//Atleast one vertex at those coordinates has texture & norm data.
			else{
				return processAgain(vertex, normIndex, textIndex);
			}
		}

		/**
		 * Converts indices list into primitive type int array.
		 * @return Created array.
		 */
		private int[] convertIndicesListToArray() {
			this.indicesArray = new int[indices.size()];
			for (int i = 0; i < indicesArray.length; i++) {
				indicesArray[i] = indices.get(i);
			}
			return indicesArray;
		}

		/**
		 * Saves vertices data into arrays.
		 * @return Distance of furthest vertex.
		 */
		private float saveDataToArrays(){
			float furthestPoint = 0;
			for(int i = 0; i < vertices.size(); i++){
				Vertex current = vertices.get(i);
				if(current.getLength() > furthestPoint){
					furthestPoint = current.getLength();
				}
				Vector3f position = current.getPosition();
				Vector2f textureCoord = textures.get(current.getTextureIndex());
				Vector3f normalVector = normals.get(current.getNormalIndex());
				verticesArray[i * 3] = position.x;
				verticesArray[i * 3 + 1] = position.y;
				verticesArray[i * 3 + 2] = position.z;
				texturesArray[i * 2] = textureCoord.x;
				texturesArray[i * 2 + 1] = 1 - textureCoord.y;
				normalsArray[i * 3] = normalVector.x;
				normalsArray[i * 3 + 1] = normalVector.y;
				normalsArray[i * 3 + 2] = normalVector.z;
				VertexSkinData weights = current.getSkinData();
				//Amount of joints connected to each vertex may vary.
				for(int j = 0; j < weights.jointIds.size(); j++){
					jointIdsArray[i * 3 + j] = weights.jointIds.get(j);
					weightsArray[i * 3 + j] = weights.weights.get(j);
				}
			}
			return furthestPoint;
		}

		/**
		 * Process vertex that has already been processed. Goes through all the duplicateVertices in recursive loop to see if
		 * vertice with same normData and textData exists. If not, creates new duplicateVertice. Note: Recursive method.
		 * @param vertex Vertex to check for identity with norm index and text index.
		 * @param normIndex Normal Index to link with this vertex. If vertex like that doesn't exist yet.
		 * @param textIndex Texture Index to link with this vertex. If vertex like that doesn't exist yet.
		 * @return Vertex with given normal index and texture index. Creates new duplicateVertex if equal vertex can't be found.
		 */
		private Vertex processAgain(Vertex vertex, int normIndex, int textIndex){
			//if this already has given norm & texture attached.
			if(vertex.compareTextureAndNormal(textIndex, normIndex)){
				indices.add(vertex.getIndex());
				return vertex;
			}
			//else this has other texture and normal added
			else{
				Vertex anotherVertex = vertex.getDuplicateVertex();
				//go through all the sub vertices to see if this one is already added.
				if(anotherVertex != null){
					return processAgain(anotherVertex, normIndex, textIndex);
				}
				//couldn't find vertex with given norm & texture data -> create new duplicate.
				else{
					Vertex duplicateVertex = new Vertex(vertices.size(), vertex.getPosition(), vertex.getSkinData());
					duplicateVertex.setTextureIndex(textIndex);
					duplicateVertex.setNormalIndex(normIndex);
					vertex.setDuplicateVertex(duplicateVertex);
					vertices.add(duplicateVertex);
					indices.add(duplicateVertex.getIndex());
					return duplicateVertex;
				}
			}
		}

		/**
		 * Initializes arrays according to amount of vertices.
		 */
		private void initArrays(){
			this.verticesArray = new float[vertices.size() * 3];
			this.texturesArray = new float[vertices.size() * 2];
			this.normalsArray = new float[vertices.size() * 3];
			this.jointIdsArray = new int[vertices.size() * 3];
			this.weightsArray = new float[vertices.size() * 3];
		}

		/**
		 * Updates average tangents of each vertex and resets unused vertices.
		 */
		private void removeUnusedVertices(){
			for(Vertex v : vertices){
				v.averageTangents();
				if(!v.isSet()){
					v.setTextureIndex(0);
					v.setNormalIndex(0);
				}
			}
		}

	}
}
