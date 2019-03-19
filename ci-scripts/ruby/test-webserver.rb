require 'net/http'

require_relative '../../__init__'

VARIABLES = get_variables

url = URI("http://#{VARIABLES['local']['host']}:#{VARIABLES['local']['port']}/#{VARIABLES['local']['uri']}")

string_resp = Net::HTTP.get(url)
status_resp = Net::HTTP.get_response(url)


  puts url
puts string_resp
puts status_resp
puts status_resp.class

case status_resp
when Net::HTTPSuccess
  puts("HTTP 2xx Success!")
  exit(0)
when Net::HTTPFound
  puts("HTTP 302 Found (i.e. redirect)")
  exit(0)
else
  raise("Not success or found...must be error! #{status_resp.class}")
end