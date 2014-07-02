package main;

/**
 * @author maurice
 */
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;

public class SoundGenerator {

	private static int refPitchLevel;
	private static final int MAX_PITCH_LEVEL = 2000, MIN_PITCH_LEVEL = 10,TONE_LENGTH = 100;

	private AudioFormat af;
	private byte sinusTone[];

	public SoundGenerator(int level) {

		af = new AudioFormat(44100, 16, 1, true, false);
		sinusTone = getSinusTone(level * refPitchLevel, af);
	}


	private byte[] getSinusTone(int frequency, AudioFormat af) {
		byte sample_size = (byte) (af.getSampleSizeInBits() / 8);
		byte[] data = new byte[TONE_LENGTH];
		double step_width = (2 * Math.PI) / af.getSampleRate();
		double x = 0;

		for (int i = 0; i < data.length; i += sample_size) {
			int sample_max_value = (int) Math.pow(2, af.getSampleSizeInBits()) / 2 - 1;
			int value = (int) (sample_max_value * Math.sin(frequency * x));
			for (int j = 0; j < sample_size; j++) {
				byte sample_byte = (byte) ((value >> (8 * j)) & 0xff);
				data[i + j] = sample_byte;
			}
			x += step_width;
		}
		return data;
	}

	public void play() {
		long t =System.currentTimeMillis();
		try {
			Clip c = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			c.open(af, sinusTone, 0, sinusTone.length);
			c.start();

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("TIME NEEDED: "+(System.currentTimeMillis()-t));
	}

	public byte[] getTone() {
		return sinusTone;
	}

	public void setTone(byte[] sinusTone) {
		this.sinusTone = sinusTone;
	}

	public static void setReferencePitchLevel(int maxPitchLevel) {
		refPitchLevel = (MAX_PITCH_LEVEL - MIN_PITCH_LEVEL) / maxPitchLevel;
	}

}
