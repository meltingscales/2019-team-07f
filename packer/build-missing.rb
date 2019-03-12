# This script will build all packer boxes that are missing in serial.
#
# This script assumes your output artifacts are named the same as the json files that built them, excluding the file extension.

# Folder containing packer json files.
root_folder = File.absolute_path(File.dirname(__FILE__))

# Folder containing output .box files.
output_folder = File.join(root_folder, "output")


Dir.glob(File.join(root_folder, "*.json")).each {|jsonfile|

  # Just the filename, i.e. ubuntu-web
  filename = File.basename(jsonfile, ".json")

  # Box filename with output folder, i.e. output/ubuntu-web.box
  boxfile = File.join(output_folder, (filename + ".box"))

  if File.exist? boxfile
    puts "[ OK ] " + boxfile
  else
    puts "[MISS] " + boxfile

    command = "packer build -force #{jsonfile}"

    puts("Running `#{command}`...")

    system(command)


  end
}