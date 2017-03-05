package game.engine.characters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;

import game.engine.interfaces.Interface;
import game.engine.textures.Texture;

public class InventoryInterface {
	private String kuvaTiedosto;
	private Interface inv;
	private float x, y;
	private float leveys, korkeus;

	public InventoryInterface(String kuva, float x, float y, float leveys, float korkeus){
		this.kuvaTiedosto = kuva;
		this.inv = null;
		this.x = x;
		this.y = y;
		this.leveys = leveys;
		this.korkeus = korkeus;

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(kuvaTiedosto));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(img, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        this.inv = new Interface(Texture.loadTexture(image).load(), new Vector2f(x, y), new Vector2f(0.25f, 0.35f));
	}

	public Interface getInterface(){
		return this.inv;
	}
}
