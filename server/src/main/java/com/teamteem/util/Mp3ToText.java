package com.teamteem.util;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import javax.faces.bean.ManagedBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/* Imports the Google Cloud client library */

@ManagedBean (name = "mp3ToText")

public class Mp3ToText {


    public static void main(String[] args) throws Exception {

            // The path to the audio file to transcribe
            String fileName = "/mnt/nfs_videos/videos/person/31/Potato.mp4";
            //String fileName = "C:\\Temp\\Demo.mp3";


        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File("test.wav"));

        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        recognizer.stopRecognition();
    }

}
