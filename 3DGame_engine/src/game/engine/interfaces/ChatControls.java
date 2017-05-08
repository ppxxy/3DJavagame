package game.engine.interfaces;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

public class ChatControls extends Thread{
	private boolean active=false;
	private MessageBox msgbox;
	private ChatBox chatbox;
	private boolean[] keys = new boolean[65536];
	private volatile boolean running=true;
	private ArrayList<Integer> disallowedspecials;
	private Integer[] speciallist={26,39,1,15,29,42,54,56,58,59,60,61,62,63,64,65,66,67,68,69,70,87,88,100,101,102,103,104,105,112,113,121,123,148,149,150,151,156,157,181,183,184,196,197,199,203,205,207,201,209,210,211,219,220,221,222,223};

	public ChatControls(MessageBox msgbox,ChatBox chatbox){
		this.msgbox=msgbox;
		this.chatbox=chatbox;
		disallowedspecials=new ArrayList(Arrays.asList(speciallist));
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Keyboard.enableRepeatEvents(true);
	}

	public void run(){
		while(running){
	while(Keyboard.next()) {
		checkActive();
		if(active){
		if(Keyboard.getEventKeyState()){
			keys[Keyboard.getEventKey()]=true;
				if(keys[Keyboard.KEY_BACK]){
					msgbox.backspace();
				}else if(keys[Keyboard.KEY_UP]){
					chatbox.scrollUp();
				}else if(keys[Keyboard.KEY_DOWN]){
					chatbox.scrollDown();
				}else if(keys[Keyboard.KEY_SPACE]){
					msgbox.drawChar(Keyboard.getEventCharacter());
				}else if(keys[Keyboard.KEY_RETURN]){
				}else if(disallowedspecials.contains(Keyboard.getEventKey())){
					char key = Keyboard.getEventCharacter();
					if(key=='´'||key=='¨'||key=='`'||key=='^'||key=='~'){
						msgbox.drawChar(key);
						msgbox.drawChar(key);
					}
					keys[Keyboard.getEventKey()]=false;
				}else{
					msgbox.drawChar(Keyboard.getEventCharacter());
				}
		}else {
			keys[Keyboard.getEventKey()]=false;
		 }

	}
	}
		}
	}
	public void checkActive(){
		if(active){
			if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
				msgbox.send();
				msgbox.deactivate();
				active=false;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				active=false;
				msgbox.deactivate();
			}
		}else{
			if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
				active=true;
				msgbox.activate();
			}
		}
	}
	public void quit(){
		running=false;
	}
	}
