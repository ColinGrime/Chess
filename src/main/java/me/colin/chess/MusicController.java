package me.colin.chess;

import me.colin.chess.enums.Song;
import me.colin.chess.utils.ResourceUtility;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Controls what music is being played.
 *
 * Different music plays depending on where
 * the user is in the game.
 *
 * Home music occurs on the main menu.
 * Game music occurs when you're in the game.
 * Intense music occurs once the first check happens.
 *
 * Switching doesn't occur right away.
 * It will eventually switch once the current music ends.
 */
public class MusicController extends Thread {

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

	/**
	 * Plays a random song.
	 * If it just got played, it looks for another song.
	 *
	 * @param justPlayed song that was just played
	 */
	public void playMusic(Song justPlayed) {
		Song song = switch (status) {
			case HOME -> HOME_MUSIC[rng.nextInt(HOME_MUSIC.length)];
			case NORMAL -> GAME_MUSIC[rng.nextInt(GAME_MUSIC.length)];
			case CLIMAX -> INTENSE_MUSIC[rng.nextInt(INTENSE_MUSIC.length)];
		};

		// If the song just played, find another.
		if (song != Song.Lunokhod && song == justPlayed) {
			playMusic(justPlayed);
			return;
		}

		playSong(song);
	}

	/**
	 * Plays the song entered.
	 *
	 * When the song starts to play, the
	 * thread stops until it's finished.
	 *
	 * After it's finished, the thread resumes
	 * and {@link #playMusic(Song)} is called.
	 *
	 * @param song any song
	 */
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

	/**
	 * Sets the status of the music.
	 *
	 * @param status any status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
}
