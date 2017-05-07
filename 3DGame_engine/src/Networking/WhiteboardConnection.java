package Networking;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import javafx.scene.image.Image;

/**
* <h1>WhiteboardConnection</h1>
* This class handles the conversion of image to dataUrl and
* dataUrl to image so that it can be sent to the server.
* <p>

* @author  Tomi Lehto
* @version 1.0
*/

public class WhiteboardConnection {

	/**
	   * Converts an image to dataUrl String.
	   * @param img Image that is converted to dataUrl.
	   * @return String Returns the dataUrl String of the given image.
	   */
	public static String imageToDataUrl(RenderedImage img) {
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();

	    try {
	        ImageIO.write(img, "png", bytes);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }

	    String data = DatatypeConverter.printBase64Binary(bytes.toByteArray()),
	    //proper data url format
	    dataUrl = "data:image/png;base64," + data;

	    return dataUrl;
	}

	/**
	   * Converts a dataUrl String to an image.
	   * @param String DataUrl String that is to be converted to an image.
	   * @return Image Returns the image of the given dataUrl.
	   */
	public static Image dataUrlToImage(String dataUrl) {
	    String data = dataUrl.substring(dataUrl.indexOf(',')+1);
	    byte[] bytes = DatatypeConverter.parseBase64Binary(data);

	    try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
	        return new Image(in);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

}
