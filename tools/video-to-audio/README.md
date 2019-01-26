# What is this?

This is a subcomponent of the project.

It can be used however you want, though!

It is a program that takes a stream of data from a pipe which encodes any video file, and returns a stream of data back
that encodes a corresponding audio file.

# What does it use?

Python 3 as a language and `Pipenv` to manage Python dependencies. 

# Why pipes?

To ensure as few side effects as possible (no reliance on command line tools), and also that you can run as many
instances of this as you want to!

Also, it's a flexible way to do inter-process communication. Pipes are ubiquitous.

# How do I run the tests?

1. Install Python 3.6 or greater.
2. In this folder, run `setup.[bat|sh]`
3. In this folder, run `run-tests.[bat|sh]`