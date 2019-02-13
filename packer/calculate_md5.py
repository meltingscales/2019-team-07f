import hashlib
import os

isopath = "./iso/"
file_ext = ".iso"
hash_fn = hashlib.sha256

print("Calculate all the {} hashes of '{}{}'.".format(hash_fn.__name__, isopath, file_ext))

def hash_any(fname, hash_algorithm=hashlib.md5):
    """
    Avoids reading entire file into memory.
    Takes a filepath as an argument, and a hash function from hashlib.
    Uses MD5 hash by default.
    """

    hash_object = hash_algorithm()

    with open(fname, "rb") as f:

        for chunk in iter(lambda: f.read(4096), b""):
            hash_object.update(chunk)

    return hash_object.hexdigest()


if __name__ == "__main__":

    print(isopath)
    
    for file in os.listdir(isopath):

        filename = os.fsdecode(file)
        filepath = os.path.join(isopath, filename)

        if filename.endswith(file_ext):
            print(filename+":")

            print(hash_any(filepath, hash_algorithm=hashlib.sha256))

            print()
