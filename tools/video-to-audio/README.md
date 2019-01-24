# What is this?

This is a subcomponent of the project.

It can be used however you want, though!

It is web service that takes a stream of data which encodes any video file, and
returns a stream of data back that encodes a corresponding audio file.

# What does it use?

Python 3 as a language and `Pipenv` to manage Python dependencies. 

# Why a web service?

To ensure as few side effects as possible (no reliance on command line tools)
and also that you can run as many instances of this as you want to!

Also, it's a flexible way to do inter-process communication.

This should never be accessible on WAN or even LAN as it's unencrypted TLS
traffic because I'm too lazy to do any other IPC.