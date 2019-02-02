# What is this?

See the nontechnical report in [this file](./report.md) if you're interested.

This is a subcomponent of the project.

It can be used however you want, though!

It is a program that takes a stream of data from a pipe which encodes any video
file, and returns a stream of data back that encodes a corresponding audio file.

# What does it use?

Python 3 as a language and `Pipenv` to manage Python dependencies. 

# Why pipes?

To ensure as few side effects as possible (no reliance on command line tools),
and also that you can run as many instances of this as you want to!

# How do I run the tests and build everything?

## Using vagrant

Run `vagrant up` in this folder.

## Using your computer

### Setup

- Install Python 3.6 or greater.
- Install maven.
- Install JDK 8 or greater.
- Install pip.
- Install pipenv with `pip install pipenv`.
- In this folder, run `pipenv install --deploy --system`

#### jep setup
- If using Windows, install Microsoft C++ Build tools.
- If using Linux, install `python3.6-dev` with `apt`.
- If using Mac, ask Google because I don't know.
- Run `pip install jep` and pray.

After this, you'll need to add the folder that contains jep's DLL/SO file for linking to Java to an environment variable.

`PATH` or `LD_LIBRARY_PATH` both work.

For example, I added `C:\Python36\Lib\site-packages\jep` to my `PATH`.

See [this link](https://github.com/ninia/jep) for details.

### Running Python tests
- In this folder, run `run-tests.[bat|sh]`

That file just runs `python test.py` for you.

### Running Java/Python jep tests
In the `jep-example` folder, run:

- `mvn package`
- `mvn exec:java`
