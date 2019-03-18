require 'net/http'

require '../../__init__'

VARIABLES = get_variables

begin
  Net::HTTP.start(VARIABLES['local']['host'], VARIABLES['local']['port']) do |http|
    response = http.head('searchable-video-library')

    puts response.code
    if response.code != 200
      puts "Didn't get 200 OK! Failure!"
      puts response.body
      puts response
      exit(1)
    end
  end
rescue Errno::ECONNREFUSED
  puts "Couldn't connect to web server at #{VARIABLES['local']['host']}:#{VARIABLES['local']['port']}/#{VARIABLES['local']['uri']}"
  exit 1
end
