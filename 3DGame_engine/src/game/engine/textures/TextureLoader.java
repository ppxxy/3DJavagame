package game.engine.textures;

import game.engine.textures.Texture.Loader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TextureLoader {

	/**
	 * Loads texture data from path to TextureData object.
	 * @param path Path to texture file.
	 * @return TextureData object containing data loaded from texture file.
	 */
	protected static TextureData decodeTextureFile(String path){
		int width = 0, height = 0;
		ByteBuffer buffer = null;
		InputStream in = Class.class.getResourceAsStream(path);
		try{
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.BGRA);
			buffer.flip();
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new TextureData(buffer, width, height);
	}
	
	/**
	 * Makes improvements to texture data and loads it to OpenGL.
	 * @param data Texture data to load on OpenGL.
	 * @param loader Loader to perform chosen improvements.
	 * @return Id of the given texture on the OpenGL.
	 */
	public static int saveToOpenGL(TextureData data, Loader loader) {
		int id = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		if (loader.isMipmap()) {
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			if (loader.isAnisotropic() && GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT,
						4.0f);
			}
		} else if (loader.isNearest()) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		}
		if (loader.isClampEdges()) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return id;
	}
	
	protected static class TextureData{
		
		private int width, height;
		private ByteBuffer buffer;
		
		/**
		 * Object holding texture data.
		 * @param buffer Buffer containing texture data.
		 * @param width Texture's width.
		 * @param height Texture's height.
		 */
		private TextureData(ByteBuffer buffer, int width, int height){
			this.buffer = buffer;
			this.width = width;
			this.height = height;
		}
		
		protected int getWidth(){
			return this.width;
		}
		
		protected int getHeight(){
			return this.height;
		}
		
		protected ByteBuffer getBuffer(){
			return buffer;
		}
	}
}
