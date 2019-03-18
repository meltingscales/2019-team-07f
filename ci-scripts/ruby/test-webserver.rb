require 'net/http'

response = nil

Net::HTTP.start('127.0.0.1', 8080) {|http|
    response = http.head('searchable-video-library')

    puts response.code
    if(response.code != 200) {
        puts "Didn't get 200 OK! Failure!"
        puts response.body
        puts response
        exit(1)
    }
}
