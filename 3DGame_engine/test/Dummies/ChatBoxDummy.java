package Dummies;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;
import Networking.Chat;
import Networking.ChatMessage;
import game.engine.textures.Texture;

public class ChatBoxDummy {
	private Vector2f position;
	private Vector2f size;
	//private Interface ifce;
    private BufferedImage chatbox;
    private String message;
    private ChatDummy chat;
    private Color fontcolor;
    private Font font;
    private Color backgroundcolor;
    private Color scrollbarbackground;
    private Color scrollbar;
    private int scrollpos;
    private int cursorpos;

    public ChatBoxDummy(ChatDummy chatDummy){
    	this.chat=chatDummy;
    	this.message="";
    	this.position=new Vector2f(0.0f,-0.65f);
    	this.size=new Vector2f(0.4f,0.25f);
    	this.chatbox=new BufferedImage(1000,300,BufferedImage.TYPE_INT_ARGB);
    	initialize();
    	//ifce=new Interface(Texture.loadTexture(chatbox).nearestFiltering().load(),position,size);

    }
    public void scrollUp(){
    	if(scrollpos<99){
    		scrollpos++;
    	}
    	chat.drawMessages();
    }
    public void scrollDown(){
    	if(scrollpos>0){
    		scrollpos--;
    	}
    	chat.drawMessages();
    }

    public void initialize(){
    	Graphics g=chatbox.getGraphics();
    	//this.backgroundcolor=new Color(119,136,153,150);
    	this.backgroundcolor=new Color(0,0,0,150);
    	this.fontcolor=new Color(153,204,255);
    	this.font = new Font("TAHOMA",Font.PLAIN,40);
    	this.scrollbar=new Color(120,120,120);
    	this.scrollbarbackground=new Color(0,0,0);
		g.setColor(backgroundcolor);
		g.fillRect(0, 0, chatbox.getWidth(), chatbox.getHeight());
		drawScrollbar(g);
    	g.dispose();
    }
    public void drawScrollbar(Graphics g){
    	g.setColor(scrollbarbackground);
    	g.fillRect(chatbox.getWidth()-40, 0, chatbox.getWidth(), chatbox.getHeight());
    	g.setColor(scrollbar);
    	g.fillRect(chatbox.getWidth()-40, chatbox.getHeight()-scrollpos*6-6, chatbox.getWidth(), 6);
    }
    public void clearchat(){
    	chatbox.flush();
    	chatbox=new BufferedImage(1000,600,BufferedImage.TYPE_INT_ARGB);
    	Graphics g=chatbox.getGraphics();
    	g.setColor(backgroundcolor);
		g.fillRect(0, 0, chatbox.getWidth(), chatbox.getHeight());
		drawScrollbar(g);
    	g.dispose();

    }
    public void drawMessages(ArrayList<ChatMessage> messages){
    	clearchat();
    	cursorpos=1;
    	for(ChatMessage msg:messages){
    		drawString(msg.toString());
    	}
    }

    public void drawString(String string){
    	Graphics g=chatbox.getGraphics();
    	g.setColor(fontcolor);
    	g.setFont(font);
    	FontMetrics metrics = g.getFontMetrics();
    	if(metrics.stringWidth(string)>chatbox.getWidth()-40){
    		ArrayList<String> strings=cutString(string,metrics);
    		for(int i=strings.size()-1;i>-1;i--){
    	    	g.drawString(strings.get(i) ,0 ,chatbox.getHeight()-metrics.getHeight()*cursorpos+scrollpos*metrics.getHeight());
    	    	cursorpos++;
    		}
    	}else{
    	if(cursorpos==1){
    		//System.out.println(metrics.stringWidth(string));
    	}
    	g.drawString(string ,0 ,chatbox.getHeight()-metrics.getHeight()*cursorpos+scrollpos*metrics.getHeight());
    	cursorpos++;
    	g.dispose();
    	}
    }
    public ArrayList<String> cutString(String str, FontMetrics fm){
    	ArrayList<String> strlist= new ArrayList();
    	String strcopy=str;
    	int counter=1;
    	while(fm.stringWidth(str)>chatbox.getWidth()-40){
    		str=str.substring(0,str.length()-1);
    		counter++;
    	}
    	strcopy=strcopy.substring(strcopy.length()-counter+1,strcopy.length());
    	strlist.add(str);
    	if(fm.stringWidth(strcopy)>chatbox.getWidth()-40){
    		strlist.addAll(cutString(strcopy,fm));
    	}else{
    		strlist.add(strcopy);
    	}
    	return strlist;
    }
    /*public Interface getInterface(){
    	return this.ifce;
    }
    /*public void update(){
    	ifce.getTexture().delete();
    	ifce.setTexture(Texture.loadTexture(chatbox).nearestFiltering().load());
    }*/
    public BufferedImage getImage(){
    	return this.chatbox;
    }
    public Vector2f getSize(){
    	return this.size;
    }
    public Vector2f getPosition(){
    	return this.position;
    }
}
