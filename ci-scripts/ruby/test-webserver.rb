require 'net/http'

require_relative '../../__init__'

VARIABLES = get_variables

begin
  Net::HTTP.start(VARIABLES['local']['host'], VARIABLES['local']['port']) do |http|
    response = http.head('searchable-video-library')

    puts response.code
    if response.code != 200
      puts response.body
      puts response
      raise "Didn't get 200 OK! Failure!"
    end
  end
rescue Errno::ECONNREFUSED
  raise "Couldn't connect to web server at #{VARIABLES['local']['host']}:#{VARIABLES['local']['port']}/#{VARIABLES['local']['uri']}! Failure!"
end
