package com.teamteem.util;

import java.util.Scanner;
import java.io.File;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;


class VideoToAudio{



    public void convertVideoToAudio(){
        Scanner s1=new Scanner(System.in);
        System.out.print("Enter input video path: ");
        String input=s1.next();
        System.out.print("Enter output audio path: ");
        String output=s1.next();
        File source = new File(input);
        File target = new File(output);

        IMediaReader reader = ToolFactory.makeReader(input);
        IMediaWriter writer = ToolFactory.makeWriter(output,reader);

        int sampleRate = 44100;
        int channels = 1;

        writer.addAudioStream(1, 0, ICodec.ID.CODEC_ID_MP3, channels, sampleRate);
        reader.addListener(writer);

        while (reader.readPacket() == null);
    }


    public static void main(String [] args){
        VideoToAudio vta = new VideoToAudio();
        try{
            vta.convertVideoToAudio();
        }
        catch(Exception e){
            System.out.println("Could not open video file");
        }
    }
}