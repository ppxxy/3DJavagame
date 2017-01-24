package game.engine.animation;

public class Animation {

	private final float length;
	private final KeyFrame[] keyFrames;
	
	public Animation(float length, KeyFrame[] frames){
		this.keyFrames = frames;
		this.length = length;
	}
	
	public float getLength(){
		return length;
	}
	
	public KeyFrame[] getKeyFrames(){
		return keyFrames;
	}
}
