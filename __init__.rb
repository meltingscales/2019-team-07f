#
# This file is for helping other files use the root path of the
# Git repository, and open `variables.yml`.
#

require 'yaml'
require 'pathname'

# Get the absolute path of the repo, for convenience purposes.
def absolute_root_path
  File.dirname File.absolute_path(__FILE__)
end

# Makes sure a filepath is absolute, and if it isn't, make it.
def ensure_absolute(filepath)
  if Pathname.new(filepath).absolute?
    return filepath
  else
    return File.join(absolute_root_path, filepath)
  end
end

# @return [Object]
def get_variables
  YAML.load_file(File.join(absolute_root_path, 'variables.yml'))
end

# Tests if file is run directly.
puts absolute_root_path if $PROGRAM_NAME == __FILE__
