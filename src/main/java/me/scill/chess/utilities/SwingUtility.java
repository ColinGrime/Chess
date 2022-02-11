package me.scill.chess.utilities;

import me.scill.chess.enums.Fonts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SwingUtility {

	public static Image getImage(String path, int width, int height) {
		BufferedImage bufferedImage = null;

		try {
			// Retrieves the path of the image, and its InputStream.
			String imagePath = "/images/" + path;
			InputStream inputStream = SwingUtility.class.getResourceAsStream(imagePath);

			// Returns if the InputStream is invalid.
			if (inputStream == null)
				return null;

			// Reads in the InputStream and retrieves the BufferedImage.
			bufferedImage = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Returns if the BufferedImage failed to read.
		if (bufferedImage == null)
			return null;

		// Scales the image, then returns a StretchIcon to account for window re-sizing.
		return bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	/**
	 * Creates a custom font.
	 * @param fontType any available font type.
	 * @param size any size.
	 * @return Font object if fontType is an existing font type.
	 */
	public static Font createFont(Fonts fontType, float size) {
		Font font = null;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, FileUtility.getStream(fontType.path()));
			font = font.deriveFont(size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		return font;
	}
}
