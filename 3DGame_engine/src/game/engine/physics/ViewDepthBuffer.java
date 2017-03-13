package game.engine.physics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBPixelBufferObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import game.engine.camera.Camera;

public class ViewDepthBuffer {

    private int depthBuffer, depthData;

    private int[] pboIds;

    private int index = 0;

    public ViewDepthBuffer(){
        initialise();
    }

    public void cleanUp(){
        GL30.glDeleteFramebuffers(depthBuffer);
    }

    public void unbind(){
    	GL11.glReadBuffer(GL11.GL_FRONT);
    	GL15.glBindBuffer(ARBPixelBufferObject.GL_PIXEL_PACK_BUFFER_ARB, pboIds[(index+1)%pboIds.length]);
    	GL11.glReadPixels(0, 0, Display.getWidth(), Display.getHeight(), GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, 0);
    	GL15.glBindBuffer(ARBPixelBufferObject.GL_PIXEL_PACK_BUFFER_ARB, 0);
    	index = (index+1)%pboIds.length;

        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }

    public void bind(int width, int height){
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, depthBuffer);
        GL11.glViewport(0, 0, width, height);
    }

    public float getDepth(int x, int y){
        GL15.glBindBuffer(ARBPixelBufferObject.GL_PIXEL_UNPACK_BUFFER_ARB, pboIds[index]);
        FloatBuffer bytes = BufferUtils.createFloatBuffer(1);
        GL11.glReadPixels(x, y, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, bytes);
        GL15.glBindBuffer(ARBPixelBufferObject.GL_PIXEL_UNPACK_BUFFER_ARB, 0);
        return toLinearDepth(bytes.get());
    }

    private float toLinearDepth(float f){
    	return Camera.NEAR_PLANE*Camera.FAR_PLANE/(Camera.FAR_PLANE+f*(Camera.NEAR_PLANE-Camera.FAR_PLANE));
    }

    public int createFrameBuffer(){
        int frameBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        return frameBuffer;
    }

    private int createDepthData(int width, int height) {
		int id = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, id);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, id);
		return id;
	}

	private void initialise(){
        depthBuffer = createFrameBuffer();
        depthData = createDepthData(Display.getWidth(), Display.getHeight());
        createPbos(2);
        unbind();
    }

    private void createPbos(int amount){
        this.pboIds = new int[amount];
        for(int i = 0; i < amount; i++){
            pboIds[i] = GL15.glGenBuffers();
            GL15.glBindBuffer(ARBPixelBufferObject.GL_PIXEL_PACK_BUFFER_ARB, pboIds[i]);
            GL15.glBufferData(ARBPixelBufferObject.GL_PIXEL_PACK_BUFFER_ARB, Display.getWidth()*Display.getHeight()*Float.BYTES, ARBPixelBufferObject.GL_STREAM_READ_ARB);
            GL15.glBindBuffer(ARBPixelBufferObject.GL_PIXEL_PACK_BUFFER_ARB, 0);
        }
    }
}
