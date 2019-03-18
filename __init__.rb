#
# This file is for helping other files use the root path of the
# Git repository, and open `variables.yml`.
#

require 'yaml'

# Get the absolute path of the repo, for convenience purposes.
def absolute_root_path
  File.dirname File.absolute_path(__FILE__)
end

# @return [Object]
def get_variables
  YAML.load_file(File.join(absolute_root_path, 'variables.yml'))
end

# Tests if file is run directly.
puts absolute_root_path if $PROGRAM_NAME == __FILE__
