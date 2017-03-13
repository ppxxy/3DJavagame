package game.engine.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import Networking.Chat;
import Networking.ChatMessage;
import game.engine.textures.Texture;

public class ChatBox {
	private Vector2f position;
	private Vector2f size;
	private Interface ifce;
    private BufferedImage chatbox;
    private String message;
    private Chat chat;
    private Color fontcolor;
    private Font font;
    private Color backgroundcolor;
    private Color scrollbarbackground;
    private Color scrollbar;
    private int scrollpos;
    private int rows;

    public ChatBox(Chat chat){
    	this.chat=chat;
    	this.message="";
    	this.position=new Vector2f(0.0f,-0.65f);
    	this.size=new Vector2f(0.5f,0.25f);
    	this.chatbox=new BufferedImage(1000,600,BufferedImage.TYPE_INT_ARGB);
    	initialize();
    	ifce=new Interface(Texture.loadTexture(chatbox).nearestFiltering().load(),position,size);

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
    	this.backgroundcolor=new Color(119,136,153,150);
    	this.fontcolor=new Color(0,0,0);
    	this.font = new Font("DIALOG",Font.PLAIN,40);
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
    	rows=0;
		for(int cursorpos=0,msgindex=0,drawn=0;msgindex<99;cursorpos++,msgindex++){
			if(cursorpos+scrollpos<messages.size()){
				ChatMessage current=messages.get(msgindex+scrollpos);
				if(current.getRows()>1){
					if(drawn>0){
						cursorpos++;
					}
					drawString(current.getSecondline(), 20*current.getName().length(), cursorpos*-50-40+chatbox.getHeight());
					cursorpos++;
					drawString(current.getName()+":", 5, cursorpos*-50-40+chatbox.getHeight());
					drawString(current.geMessage(), 20*current.getName().length(), cursorpos*-50-40+chatbox.getHeight());
					rows+=2;
				}else{
					drawString(current.toString(),5,cursorpos*-50-40+chatbox.getHeight());
					rows+=1;
				}
			}
		}
    }

    public void drawString(String string, int x, int y){
    	Graphics g=chatbox.getGraphics();
    	g.setColor(fontcolor);
    	g.setFont(font);
    	g.drawString(string ,x ,y);
    	g.dispose();
    }
    public Interface getInterface(){
    	return this.ifce;
    }
    public void update(){
    	ifce.getTexture().delete();
    	ifce.setTexture(Texture.loadTexture(chatbox).nearestFiltering().load());
    }
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
