package com.teamteem.util;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;


class VideoToAudio{



    public void convertVideoToAudio(){
        IMediaReader reader = ToolFactory.makeReader("C://Users//Administrator//Downloads//video.mp4");
        IMediaWriter writer = ToolFactory.makeWriter("C://Users//Administrator//Downloads//video.mp3",reader);

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