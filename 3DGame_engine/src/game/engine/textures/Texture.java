package game.engine.textures;

import game.engine.textures.TextureLoader.TextureData;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

	public final int id, size;
	private final int type;

	/**
	 * Creates normal 2D texture with given id and size.
	 * @param id Id this texture is associated with.
	 * @param size Size of this texture.
	 */
	protected Texture(int id, int size){
		this.id = id;
		this.type = GL11.GL_TEXTURE_2D;
		this.size = size;
	}

	/**
	 * Creates normal 2D texture with given id and size.
	 * @param id Id this texture is associated with.
	 * @param type Texture type. GL11 has final variables for different texture types.
	 * @param size Size of this texture.
	 */
	protected Texture(int id, int type, int size){
		this.id = id;
		this.type = type;
		this.size = size;
	}

	/**
	 * Binds this texture to be used on OpenGL rendering.
	 * @param unit Unit value to bind this texture to.
	 */
	public void bindToUnit(int unit){
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(type, id);
	}

	/**
	 * Removes this texture from the memory.
	 */
	public void delete(){
		GL11.glDeleteTextures(id);
	}

	/**
	 * Load texture from the given path.
	 * @param path Path of texture file.
	 * @return Texture object.
	 */
	public static Loader loadTexture(String path){
		return new Loader(path);
	}

	public static Loader loadTexture(BufferedImage image){
		return new Loader(image);
	}

	public static class Loader{

		private boolean clampEdges = false, mipmap = false, anisotropic = true, nearest = false;

		private String path;
		private BufferedImage image;

		/**
		 * Texture loader to load texture from the given path using filters.
		 * @param path Path to texture file.
		 */
		private Loader(String path){
			this.path = path;
		}

		private Loader(BufferedImage image){
			this.image = image;
		}

		public boolean isClampEdges() {
			return clampEdges;
		}

		public boolean isMipmap() {
			return mipmap;
		}

		public boolean isAnisotropic() {
			return anisotropic;
		}

		public boolean isNearest() {
			return nearest;
		}

		/**
		 * Loads texture data from the path. Saves Texture to OpenGL and makes it into a Texture object.
		 * @return Texture object containing texture.
		 */
		public Texture load(){
			TextureData data;
			if(path != null){
				data = TextureLoader.decodeTextureFile(path);
			}
			else{
				return TextureLoader.loadFromBufferedImage(image, this);
			}
			int id = TextureLoader.saveToOpenGL(data, this);
			return new Texture(id, data.getWidth());
		}

		/**
		 * Clamp texture edges.
		 * @return This loader object with ClampEdges filter.
		 */
		public Loader clampEdges(){
			this.clampEdges = true;
			return this;
		}

		/**
		 * Make textures anisotropic
		 * @return This loader object with anisotropic filter.
		 */
		public Loader anisotropic(){
			this.mipmap = true;
			this.anisotropic = true;
			return this;
		}

		/**
		 * Use MipMap to make objects further away look better.
		 * @return This loader with normal MipMapping activated.
		 */
		public Loader normalMipMap(){
			this.mipmap = true;
			this.anisotropic = false;
			return this;
		}

		/**
		 * Nearest filtering makes pixels pop out more.
		 * @return This loader with nearestFiltering activated.
		 */
		public Loader nearestFiltering(){
			this.mipmap = false;
			this.anisotropic = false;
			this.nearest = true;
			return this;
		}
	}
}
