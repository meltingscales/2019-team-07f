require 'open3'

require '../../__init__'

VARIABLES = get_variables

# The files changed in the last commit.
Changed = `git show --name-only --oneline HEAD`

puts Changed

def removeIfChanged(buildfile, artifactfile, delete = false)

  buildfileabs = ensure_absolute buildfile
  artifactfileabs = ensure_absolute artifactfile

  if Changed.include? buildfile

    puts "Removing #{artifactfile} as it was changed in the most recent commit!"

    if delete #If we should delete the file,

      if File.exist?(artifactfileabs) # If it exists, delete it.
        puts "DEL #{artifactfileabs}"
        File.delete(artifactfileabs)
      else
        puts "DEL? #{artifactfileabs} but doesn't exist"
      end

    else # If not, notify them it would have been deleted.
      puts "TDEL Would have DELETED #{artifactfileabs}, but `delete` var was FALSE."
    end

  end
end

removeIfChanged "packer/ubuntu-mysql.json", "packer/output/ubuntu-mysql.box", true
removeIfChanged "packer/ubuntu-storage.json", "packer/output/ubuntu-storage.box", true
removeIfChanged "packer/ubuntu-webserver.json", "packer/output/ubuntu-webserver.box", true
removeIfChanged "Merge", "IWOULDBEDELETED.BOX"

puts "81d453b Merge branch 'master' of https://github.com/HenryFBP/2019-team-07f".include? "Merge"