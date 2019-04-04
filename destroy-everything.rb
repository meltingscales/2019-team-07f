require_relative '__init__'

# Change directory to path of __init__.rb
Dir.chdir absolute_root_path

require 'optparse'

# Global variable to not actually delete files.
# True prevents files from being deleted.
DRY_RUN = TRUE

puts "Operating in this path:"
puts Dir.pwd

def destroy_vagrant_boxes
  cmd = "vagrant destroy -f"

  puts "Executing `#{cmd}`..."
  sleep(5)

  if not DRY_RUN
    system(cmd)
  else
    puts "Not actually going to run `#{cmd}` as DRY_RUN=#{DRY_RUN}."
  end
end

def destroy_packer_output(path)

  path = File.absolute_path path

  puts "About to DEL '#{File.absolute_path path}'!"

  Dir.glob path do |boxfile|
    puts "DEL #{boxfile}"

    if not DRY_RUN
      File.delete boxfile
    else
      puts "Not actually going to delete as DRY_RUN=#{DRY_RUN}."
    end

  end
end


options = {}
OptionParser.new do |opts|
  opts.banner = "Usage: #{__FILE__} [options]"

  opts.on("-f", "--force", "Destroy everything without prompts.") do |v|
    options[:force] = v
  end
end.parse!

p options
p ARGV

if options[:force]
  puts "Forcefully removing ALL vagrant boxes AND packer artifacts..."
  puts "You have 5 seconds to cancel (CTRL-C!)"
  sleep(5)
end

if not options[:force]
  puts("Hold on! You're about to destroy ALL vagrant boxes and ALL packer artifacts!")
  puts("This could take hours to undo!")
  puts("Are you sure you want to do this? (yes/no)")
  print (" > ")

  inp = gets.chomp

  if inp == "yes"
    puts "Continuing!"
  else
    puts "Aborting!"
    exit(1)
  end

end

destroy_vagrant_boxes

destroy_packer_output(File.join absolute_root_path, "packer/output/*.box")