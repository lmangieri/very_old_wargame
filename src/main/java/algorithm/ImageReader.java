package algorithm;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReader {
	public static Image getFormatedImage(String path, Double resize){
		File f = new File(ImageReader.class.getClass()
				.getResource(path).getPath());
		try {
			BufferedImage img = ImageIO.read(f);
			return img.getScaledInstance((int)(img.getWidth()/resize) , (int)(img.getHeight() /resize), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro no carregamento de imagens");
		}
	}
}
