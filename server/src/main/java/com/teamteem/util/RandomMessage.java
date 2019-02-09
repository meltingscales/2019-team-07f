package com.teamteem.util;

import javax.faces.bean.ManagedBean;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@SuppressWarnings("WeakerAccess")
@ManagedBean(name = "randomMessage")
public class RandomMessage {
    public static final List<String> messages = Arrays.asList("Try again.",
            "You win!",
            "You lose!",
            "sudo rm -rf /",
            "Download MEMZ for lots of fun!",
            "Delete C:\\Windows\\System32!",
            "You are likely to be eaten by a Grue.",
            "You were eaten by a Grue.",
            "All of these commands are horrible ideas.",
            "You can do it!",
            ":(){ :|: & };:",
            "You are standing in an open field west of a white house, with a boarded front door.");

    public String message() {
        return messages.get(new Random().nextInt(messages.size()));
    }
}
