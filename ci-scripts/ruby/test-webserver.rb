require 'net/http'

require_relative '../../__init__'

def testResponse(response)
  case response
  when Net::HTTPSuccess
    puts("HTTP 2xx Success!")
    exit(0)
  when Net::HTTPRedirection
    puts("HTTP 302 Found (i.e. redirect).")
    exit(0)
  else
    response.error!
  end
end


VARIABLES = get_variables

url = URI("#{VARIABLES['local']['scheme']}://#{VARIABLES['local']['host']}:#{VARIABLES['local']['port']}/#{VARIABLES['local']['uri']}")

string_resp = Net::HTTP.get(url)
status_resp = Net::HTTP.get_response(url)

puts url
puts string_resp
puts status_resp
puts status_resp.class

testResponse(status_resp)