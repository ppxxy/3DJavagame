package game.engine.skybox;

import game.engine.models.VAO;

public class Skybox {

	private static final float SIZE = 150f;

	private static final float[] VERTICES = {
			-SIZE,  SIZE, -SIZE,
		    -SIZE, -SIZE, -SIZE,
		    SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		    -SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE
	};

	/*private static final int[] INDICES = {
		1, 2, 3,
		3, 4, 1,
		5, 2, 1,
		1, 6, 5,
		3, 7, 8,
		8, 4, 3,
		5, 6, 8,
		8, 7, 5,
		1, 4, 8,
		8, 6, 1,
		2, 5, 3,
		3, 5, 7
	};*/

	private static final VAO vao = loadToVAO(VERTICES);

	private int texture;


	public Skybox(int texture){
		this.texture = texture;
	}

	public static VAO loadToVAO(float[] positions){
		VAO vao = VAO.create();
		vao.bind();
		vao.createIndexBuffer(new int[36]);
		vao.createAttribute(0, positions, 3);
		vao.unbind();
		return vao;

	}

	public VAO getVAO() {
		return vao;
	}

	public int getTexture() {
		return this.texture;
	}
}
