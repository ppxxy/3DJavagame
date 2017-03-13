package game.engine.interfaces;

import java.io.IOException;
import javax.imageio.ImageIO;
import org.lwjgl.util.vector.Vector2f;
import game.engine.interfaces.Interface;
import game.engine.main.Main;
import game.engine.textures.Texture;

public class Inventory extends Interface implements ActiveInterface{


	public Inventory(String kuva, float x, float y, float leveys, float korkeus) throws IOException{
		super(Texture.loadTexture(ImageIO.read(Main.class.getResourceAsStream(kuva))).load(), new Vector2f(x, y), new Vector2f(leveys, korkeus));
	}

	@Override
	public boolean MouseAction(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}
}
