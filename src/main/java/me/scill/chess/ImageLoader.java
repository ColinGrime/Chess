package me.scill.chess;

import me.scill.chess.display.StretchIcon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ImageLoader extends Thread {

	private final ConcurrentHashMap<String, StretchIcon> images = new ConcurrentHashMap<>();

	@Override
	public void run() {
		long time = System.currentTimeMillis();

		File directory = new File("src/main/resources/images");
		File[] images = directory.listFiles();
		if (images == null) {
			System.out.println(directory.getAbsolutePath() + " does not exist!");
			return;
		}

		for (File file : images) {
			if (file.isDirectory()) {
				for (File f : Objects.requireNonNull(file.listFiles()))
					addImage(f, file.getName() + "/" + f.getName());
			}

			else
				addImage(file);
		}

		System.out.println("Image icons loaded (different thread). Time elapsed: " + (System.currentTimeMillis() - time) + "ms");
	}

	private void addImage(File file) {
		addImage(file, file.getName());
	}

	private void addImage(File file, String name) {
		if (file.isHidden())
			return;

		BufferedImage bufferedImage = null;
		try {
			InputStream inputStream = new FileInputStream(file);
			bufferedImage = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Returns false if the BufferedImage failed to read.
		if (bufferedImage == null) {
			System.out.println(file.getName() + " failed to load.");
			return;
		}

		StretchIcon icon = new StretchIcon(bufferedImage.getScaledInstance(512, 512, Image.SCALE_SMOOTH));
		images.put(name, icon);
	}

	public ConcurrentHashMap<String, StretchIcon> getImages() {
		return images;
	}
}
