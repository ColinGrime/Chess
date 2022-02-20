package me.scill.chess;

import me.scill.chess.enums.Song;
import me.scill.chess.utilities.ResourceUtility;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Music extends Thread {

	public enum Status {
		HOME,
		NORMAL,
		CLIMAX,
	}

	private final Random rng = new Random();
	private final Song[] HOME_MUSIC = { Song.Lunokhod };
	private final Song[] GAME_MUSIC = { Song.ImpatientlyWaiting, Song.OrNot, Song.DevilPiano  };
	private final Song[] INTENSE_MUSIC = { Song.ThePercussionist, Song.ThinWallsPercussion, Song.BeforeTheFight };
	private Status status = Status.HOME;

	@Override
	public void run() {
		playMusic(null);
	}

	public void playMusic(Song justPlayed) {
		Song song = switch (status) {
			case HOME -> HOME_MUSIC[rng.nextInt(HOME_MUSIC.length)];
			case NORMAL -> GAME_MUSIC[rng.nextInt(GAME_MUSIC.length)];
			case CLIMAX -> INTENSE_MUSIC[rng.nextInt(INTENSE_MUSIC.length)];
		};

		// If the song just played, find another.
		if (song == justPlayed) {
			playMusic(justPlayed);
			return;
		}

		playSong(song);
	}

	private void playSong(Song song) {
		// Open an audio input stream.
		InputStream inputStream = ResourceUtility.getStream(song.path());
		CountDownLatch syncLatch = new CountDownLatch(1);

		try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(inputStream)) {
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();

			// When the song is finished, it finds another song to play!
			clip.addLineListener(e -> {
				if (e.getType() == LineEvent.Type.STOP) {
					clip.close();
					syncLatch.countDown();
				}
			});

			// Open audio clip and start playing.
			clip.open(audioIn);
			clip.start();

			String songName = song.name().replaceAll("(.)([A-Z])", "$1 $2");
			System.out.println("Song currently playing: " + songName);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		try {
			syncLatch.await();
			playMusic(song);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
