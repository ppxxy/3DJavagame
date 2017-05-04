package game.engine.physics;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class ObjectBuffer {

	private int idBuffer, idData;

	public ObjectBuffer(){
		initialise();
	}

	public void cleanUp(){
        GL30.glDeleteFramebuffers(idBuffer);
    }

	private void initialise(){
		idBuffer = createIdBuffer();
		idData = createIdData(Display.getWidth(), Display.getHeight());

		unbind();
	}

	public void unbind(){
		GL11.glReadBuffer(GL11.GL_FRONT);

    	GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	public void bind(int width, int height){
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, idBuffer);
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, idData);
        GL11.glViewport(0, 0, width, height);
    }

	public int getId(int x, int y){
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, idBuffer);
        //GL15.glBindBuffer(ARBPixelBufferObject.GL_PIXEL_UNPACK_BUFFER_ARB, pboIds[index]);
        IntBuffer bytes = BufferUtils.createIntBuffer(1);
        GL11.glReadPixels(x, y, 1, 1, GL30.GL_RED_INTEGER, GL11.GL_INT, bytes); //Component & data type
        //GL15.glBindBuffer(ARBPixelBufferObject.GL_PIXEL_UNPACK_BUFFER_ARB, 0);
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, 0);
        return bytes.get();
    }

	private int createIdData(int width, int height){
		int id = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, id);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_R32I, width, height); //Component type
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_RENDERBUFFER, id); //Attachment type
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
		return id;
	}

	private int createIdBuffer() {
		int frameBuffer = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0); //Don't change this, it doesn't make a difference.
		return frameBuffer;
	}

}
