package game.engine.models;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VAO {
	
	private static final int BYTES_PER_FLOAT = 4;

	public final int id;
	private List<VBO> dataVBOs = new ArrayList<VBO>();
	private VBO indexVBO;
	private int indexCount;

	public static VAO create() {
		int id = GL30.glGenVertexArrays();
		return new VAO(id);
	}

	private VAO(int id) {
		this.id = id;
	}
	
	public int getIndexCount(){
		return indexCount;
	}

	public void bind(int... attributes){
		bind();
		for (int i : attributes) {
			GL20.glEnableVertexAttribArray(i);
		}
	}

	public void unbind(int... attributes){
		for (int i : attributes) {
			GL20.glDisableVertexAttribArray(i);
		}
		unbind();
	}
	
	public void createIndexBuffer(int[] indices){
		this.indexVBO = VBO.create(GL15.GL_ELEMENT_ARRAY_BUFFER);
		indexVBO.bind();
		indexVBO.storeData(indices);
		this.indexCount = indices.length;
	}

	public void createAttribute(int attribute, float[] data, int attrSize){
		VBO dataVBO = VBO.create(GL15.GL_ARRAY_BUFFER);
		dataVBO.bind();
		dataVBO.storeData(data);
		GL20.glVertexAttribPointer(attribute, attrSize, GL11.GL_FLOAT, false, attrSize * BYTES_PER_FLOAT, 0);
		dataVBO.unbind();
		dataVBOs.add(dataVBO);
	}
	
	public void createAttribute(int attribute, int[] data, int attrSize){
		VBO dataVBO = VBO.create(GL15.GL_ARRAY_BUFFER);
		dataVBO.bind();
		dataVBO.storeData(data);
		GL30.glVertexAttribIPointer(attribute, attrSize, GL11.GL_INT, attrSize * BYTES_PER_FLOAT, 0);
		dataVBO.unbind();
		dataVBOs.add(dataVBO);
	}
	
	public void delete() {
		GL30.glDeleteVertexArrays(id);
		for(VBO VBO : dataVBOs){
			VBO.delete();
		}
		indexVBO.delete();
	}

	private void bind() {
		GL30.glBindVertexArray(id);
	}

	private void unbind() {
		GL30.glBindVertexArray(0);
	}

}