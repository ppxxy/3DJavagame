package Dummies;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.lwjgl.util.vector.Vector2f;

public class MessageBoxDummy {
	private Vector2f position;
	private Vector2f size;
	//private Interface ifce;
    private BufferedImage messagebox;
    private String message;
    private ChatDummy chat;
    private Color fontcolor;
    private Font font;
    private Color backgroundcolor;
    private Color bordercolor;
    private final int messagemaxsize=300;


    public MessageBoxDummy(ChatDummy chat){
    	this.chat=chat;
    	this.message="";
    	this.position=new Vector2f(0.0f,-0.95f);
    	this.size=new Vector2f(0.4f,0.05f);
    	this.messagebox=new BufferedImage(1000,300,BufferedImage.TYPE_INT_ARGB);
    	initialize();
    	//this.ifce=new Interface(Texture.loadTexture(messagebox).nearestFiltering().load(),position,size);

    }
    public String cutDisplayedMessage(String string, FontMetrics fm){
    	while(fm.stringWidth(string)>messagebox.getWidth()){
    		string=string.substring(1,string.length());
    	}
    	return string;
    }
    public void initialize(){
    	Graphics g=messagebox.getGraphics();
    	this.backgroundcolor=new Color(0,0,0,150);
    	this.fontcolor=new Color(0,0,0);
    	this.font = new Font("TAHOMA",Font.PLAIN,40);
    	this.bordercolor=new Color(0,0,0,100);
		g.setColor(backgroundcolor);
		g.fillRect(0, 0, messagebox.getWidth(), messagebox.getHeight());
		g.setColor(bordercolor);
		g.fillRect(0, 0, messagebox.getWidth(), 20);
    	g.dispose();
    }
    public void drawChar(char character){
    	if(message.length()<messagemaxsize){
    		message+=character;
    		clearInput();
    		drawString(message+"*",0,70);
    	}
    }
    public void activate(){;
		this.backgroundcolor=new Color(119,136,153,150);
    	drawString("*",0,70);
    }
    public void deactivate(){
    	this.backgroundcolor=new Color(0,0,0,150);
    	message="";
    	clearInput();
    }
    //testausta varten
    public void setMessage(String msg){
    	this.message=msg;
    }
    public void backspace(){
    	if(message.length()>1){
    		message=message.substring(0, message.length()-1);
    		clearInput();
    		drawString(message+"*",0,70);
    	}
    }
    public void send(){
    	if(message.length()>1){
    		chat.sendMessage(message.substring(1,message.length()));
    		message="";
    		clearInput();
    	}
    }

    public void clearInput(){
    	messagebox.flush();
    	messagebox=new BufferedImage(1000,120,BufferedImage.TYPE_INT_ARGB);
    	Graphics g=messagebox.getGraphics();
    	g.setColor(backgroundcolor);
		g.fillRect(0, 0, messagebox.getWidth(), messagebox.getHeight());
		g.setColor(bordercolor);
		g.fillRect(0, 0, messagebox.getWidth(), 20);
    	g.dispose();
    }
    public void drawString(String string, int x, int y){
    	Graphics g=messagebox.getGraphics();
    	g.setColor(fontcolor);
    	g.setFont(font);
    	FontMetrics fm = g.getFontMetrics();
    	if(fm.stringWidth(string)>messagebox.getWidth()){
    		string=cutDisplayedMessage(string,fm);
    	}
    	g.drawString(string ,x ,y);
    	g.dispose();
    }
    /*public void update(){
    	ifce.getTexture().delete();
    	ifce.setTexture(Texture.loadTexture(messagebox).nearestFiltering().load());
    }*/
    public BufferedImage getImage(){
    	return this.messagebox;
    }
    public Vector2f getSize(){
    	return this.size;
    }
    public Vector2f getPosition(){
    	return this.position;
    }/*
    public Interface getInterface(){
    	return this.ifce;
    }*/
}
