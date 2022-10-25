# http4s-db-example

http4s + H2 + Doobie + Quill + flywaydb + Cats Effect

## To run

Either run `MyMain` or `sbt run`

It should be on localhost:8080

## DB

3 tables: video, tags, and a videotag join table

## Video Endpoints

GET http://localhost:8080/video

Show all videos

GET http://localhost:8080/video/1

Show certain video

GET http://localhost:8080/video/tags/1

Show tags for certain video

POST http://localhost:8080/video/tags/[id]?tags=1,2,3

Set tags for certain video

POST http://localhost:8080/video/[name]

Create a new video

## Tag Endpoints

GET http://localhost:8080/tag

Show all tags

GET http://localhost:8080/tag/1

Show certain tag

POST http://localhost:8080/tag/[name]

Create a new tag

## VideoTag Endpoints

GET http://localhost:8080/videotag

Show all videotags

GET http://localhost:8080/videotag/1

Show certain videotag

POST http://localhost:8080/videotag/[videoid]/[tagid]

Create a new videotag (may well be easier to use the batch POST /video/tags/[id]?tags=1,2,3 call)
