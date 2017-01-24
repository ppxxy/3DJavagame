package game.engine.models.collada;

import game.engine.models.collada.SkinningData.VertexSkinData;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class Vertex {

	/**
	 * Default values of texturedIndex and normalIndex are -1.
	 */
	private static final int NO_INDEX = -1;
	
	private Vector3f position;
	private int textureIndex = NO_INDEX, normalIndex = NO_INDEX;
	/**
	 * Duplicate vertices are vertices that share same position, but have different texture and normal data.
	 */
	private Vertex duplicateVertex = null;
	private int index;
	private float length;
	private List<Vector3f> tangents = new ArrayList<Vector3f>();
	private Vector3f averagedTangent = new Vector3f(0, 0, 0);
	
	private VertexSkinData skinningData;
	
	/**
	 * Vertex object represents one Vertex point on collada model object.
	 * @param index Index of this vertex. Running number on model.
	 * @param position Vertex position in three dimensional space.
	 * @param weightsData Weight values with different joints.
	 */
	public Vertex(int index, Vector3f position, VertexSkinData skinningData){
		this.index = index;
		this.skinningData = skinningData;
		this.position = position;
		this.length = position.length();
	}
	
	/**
	 * Method used to receive skinning data of this vertex.
	 * @return Skinning data.
	 */
	public VertexSkinData getSkinData(){
		return skinningData;
	}
	
	/**
	 * Method used to add this vertex new tangent vector.
	 * @param tangent Vector to be added.
	 */
	public void addTangent(Vector3f tangent){
		tangents.add(tangent);
	}
	
	/**
	 * Updates average tangent using current tangent vectors.
	 */
	public void averageTangents(){
		if(tangents.isEmpty()){
			return;
		}
		for(Vector3f tangent : tangents){
			Vector3f.add(averagedTangent, tangent, averagedTangent);
		}
		averagedTangent.normalise();
	}
	
	/**
	 * Used to receive current average tangent. To update average tangent, see averageTangents();
	 * @return Averaged tangent vector.
	 */
	public Vector3f getAverageTangent(){
		return averagedTangent;
	}
	
	/**
	 * Method to get this vertice's index in the mesh that it belongs to.
	 * @return Index of this vertex.
	 */
	public int getIndex(){
		return index;
	}
	
	/**
	 * The length of this vertices position vector. In other words, the distance from the origin.
	 * @return distance from the origin.
	 */
	public float getLength(){
		return length;
	}
	
	/**
	 * Method used to define, if vertex has been applied texture and normal data.
	 * @return True, if vertex has texture and index data applied. False otherwise.
	 */
	public boolean isSet(){
		return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
	}
	
	/**
	 * Check if vertex has given texture and normal indices.
	 * @param textureIndexOther Texture index to compare with.
	 * @param normalIndexOther Normal index to compare with.
	 * @return True if texture and normal indices match, false otherwise.
	 */
	public boolean compareTextureAndNormal(int textureIndexOther, int normalIndexOther){
		return textureIndexOther == textureIndex && normalIndexOther == normalIndex;
	}
	
	/**
	 * Method to set this vertex's texture index.
	 * @param textureIndex Texture index this vector points to.
	 */
	public void setTextureIndex(int textureIndex){
		this.textureIndex = textureIndex;
	}
	
	/**
	 * Method to set this vertex's normal index.
	 * @param normalIndex Normal index this vector points to.
	 */
	public void setNormalIndex(int normalIndex){
		this.normalIndex = normalIndex;
	}
	
	/**
	 * Method to get this vertex's position.
	 * @return Position of this vertex.
	 */
	public Vector3f getPosition(){
		return position;
	}
	
	/**
	 * Method to get texture point this vertex refers to.
	 * @return Index of texture point this vertex refers to.
	 */
	public int getTextureIndex(){
		return textureIndex;
	}
	
	/**
	 * Method to get normal this vertex refers to.
	 * @return Index of normal this vertex refers to.
	 */
	public int getNormalIndex(){
		return normalIndex;
	}
	
	/**
	 * Method to get this vertex's duplicate vertex. Duplicate vertex share the same location, but has different Texture and Normal data.
	 * All vertices that share the same location are stacked using duplicateVertex values.
	 * @return Duplicate vertex of this vertex.
	 */
	public Vertex getDuplicateVertex(){
		return duplicateVertex;
	}
	
	/**
	 * Method to set this vertex's duplicate vertex. Duplicate vertex share the same location, but has different Texture and Normal data.
	 * All vertices that share the same location are stacked using duplicateVertex values.
	 * @param duplicateVertex Duplicate vertex to set.
	 */
	public void setDuplicateVertex(Vertex duplicateVertex){
		this.duplicateVertex = duplicateVertex;
	}
}
