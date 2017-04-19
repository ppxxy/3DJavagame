package Networking;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

public class WhiteboardConnection {

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

	public static RenderedImage dataUrlToImage(String dataUrl) {
	    String data = dataUrl.substring(dataUrl.indexOf(',')+1);
	    byte[] bytes = DatatypeConverter.parseBase64Binary(data);

	    try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
	        return ImageIO.read(in);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

}
