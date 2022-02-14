package me.scill.chess.utilities;

import me.scill.chess.enums.Font;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ResourceUtility {

	public static Image getImage(String path, int width, int height) {
		BufferedImage bufferedImage = null;

		try {
			// Retrieves the path of the image, and its InputStream.
			String imagePath = "/images/" + path;
			InputStream inputStream = getStream(imagePath);

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
	public static java.awt.Font createFont(Font fontType, float size) {
		java.awt.Font font = null;

		try {
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, getStream(fontType.path()));
			font = font.deriveFont(size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		return font;
	}

	public static InputStream getStream(String path) {
		InputStream inputStream = ResourceUtility.class.getResourceAsStream(path);

		if (inputStream == null)
			System.out.println("Error: Path \"" + path + "\" does not exist.");

		return inputStream;
	}
}
