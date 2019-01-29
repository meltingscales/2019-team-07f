# What is this?

This is an example of using Jep to run Python code in Java.

# How to use this example

You'll need:

- Python 3.6.4 or greater
- A somewhat high (8,9,10,11) JDK version.
- To install jep (`pip install jep` or [build from source](https://github.com/ninia/jep/wiki/Getting-Started))
- To set a jep environment variable to tell (java's) jep which Python
  installation to use
- Install [Maven](https://maven.apache.org/install.html)

## Windows 'mc.exe' errors
If you get weird errors about EXE files missing (Esp. on Windows), during the 
install of jep, see these links.

https://pypi.org/project/jep/
https://www.microsoft.com/en-us/download/confirmation.aspx?id=48159
http://go.microsoft.com/fwlink/?LinkId=691126&fixForIE=.exe
https://github.com/ninia/jep/wiki/Windows#python-35-and-36-msvc-build-setup

You may also have to run the Visual C++ 2008/2013 Command Prompt to have the
proper environment variables during `pip install jep` or `pipenv install`.

## Tell jep's java part where Python is.

Add `PYTHON_INSTALL_DIR\Lib\site-packages\jep` to the `PATH` env var.

Java uses this to find Python.

Sucks to have to do this, could be automated. <!-- TODO do it! -->

# How the heck do I build this?

Probably Maven and Javac, but I used Intellij IDEA to build and test this
example.
