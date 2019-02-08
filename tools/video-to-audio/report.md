# Video2Audio Library
by Henry Post

## What's it do?

This is a subcomponent of our project that facilitates converting MP4 files
(videos) to MP3 files (audio).

It features a simple API (input a path to an MP4 file, get a path to an MP3
file) and examples, as well as good documentation.

## How does it do this? (What technologies?)

### Languages

It uses Python.

I wrote a Java example using a Java library (jep) that allows running Python
code to show how you can run Python code in Java.

### Libraries/frameworks

It takes advantage of a library for Python called `moviepy` that is meant for
video editing and effects, but it can open videos and extract the audio from it.