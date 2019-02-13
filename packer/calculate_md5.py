import hashlib
import os

"""
This file is just a utility to calculate hashes of ISO files.
"""

# Folder to get hashes of files in.
isopath = "./iso/"

# File extensions to hash.
file_ext = ".iso"

# List of hash functions.
hash_fns = [hashlib.md5, hashlib.sha1, hashlib.sha256, hashlib.sha512]


print("Calculates all the [{}] hashes of '{}*{}'.".format(",".join([fn.__name__ for fn in hash_fns]), isopath, file_ext))

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
        
        # If it ends with what we want it to,
        if filename.endswith(file_ext):
            print(filename+":")
            
            # For all hash functions,
            for hash_fn in hash_fns:
                print("{type:20s} = {digest}".format(digest=hash_any(filepath, hash_algorithm=hash_fn), type=hash_fn.__name__))
            
            print()
