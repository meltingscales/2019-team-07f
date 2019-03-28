
import java.io.File;
import java.util.Scanner;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

public class Convert {

	public static void  main(String[] args)  {
		Scanner s1=new Scanner(System.in);
		System.out.println("Enter input video path");
		String input=s1.next();
		System.out.println("Enter output audio path");
		String output=s1.next();
		File source = new File(input);
		File target = new File(output);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
	    try {
	        encoder.encode(source, target, attrs);
	    } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	    } catch (InputFormatException e) {
	        e.printStackTrace();
	    } catch (EncoderException e) {
	        e.printStackTrace();
	    }
	    
	    System.out.println("Audio successfully Dumped");
	}
}