package game.engine.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.lwjgl.util.vector.Vector2f;

import Networking.Chat;
import Networking.ChatMessage;
import game.engine.textures.Texture;

public class MessageBox {
	private Vector2f position;
	private Vector2f size;
	private Interface ifce;
    private BufferedImage messagebox;
    private String message;
    private Chat chat;
    private Color fontcolor;
    private Font font;
    private Color backgroundcolor;
    private Color bordercolor;
    private final int maxsize=90;



    public MessageBox(Chat chat){
    	this.chat=chat;
    	this.message="";
    	this.position=new Vector2f(0.0f,-0.95f);
    	this.size=new Vector2f(0.5f,0.05f);
    	this.messagebox=new BufferedImage(1000,120,BufferedImage.TYPE_INT_ARGB);
    	initialize();
    	this.ifce=new Interface(Texture.loadTexture(messagebox).nearestFiltering().load(),position,size);

    }

    public void initialize(){
    	Graphics g=messagebox.getGraphics();
    	this.backgroundcolor=new Color(119,136,153,200);
    	this.fontcolor=new Color(0,0,0);
    	this.font = new Font("DIALOG",Font.PLAIN,40);
    	this.bordercolor=new Color(0,0,0,100);
		g.setColor(backgroundcolor);
		g.fillRect(0, 0, messagebox.getWidth(), messagebox.getHeight());
		g.setColor(bordercolor);
		g.fillRect(0, 0, messagebox.getWidth(), 20);
    	g.dispose();
    }
    public void drawChar(char character){
    	if(message.length()<maxsize){
    		message+=character;
    		clearInput();
    		drawString(message+"*",5,70);
    	}
    }
    public void activate(){;
    	drawString("*",5,70);
    }
    public void deactivate(){
    	message="";
    	clearInput();
    }

    public void backspace(){
    	if(message.length()>1){
    		message=message.substring(0, message.length()-1);
    		clearInput();
    		System.out.println(message.length());
    		drawString(message+"*",5,70);
    	}
    }
    public void send(){
    	if(message.length()>1){
    		System.out.println(message.length());
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
    	g.drawString(string ,x ,y);
    	g.dispose();
    }
    public void update(){
    	ifce.getTexture().delete();
    	ifce.setTexture(Texture.loadTexture(messagebox).nearestFiltering().load());
    }
    public BufferedImage getImage(){
    	return this.messagebox;
    }
    public Vector2f getSize(){
    	return this.size;
    }
    public Vector2f getPosition(){
    	return this.position;
    }
    public Interface getInterface(){
    	return this.ifce;
    }
}
