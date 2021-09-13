# http4s-db-example

http4s + H2 + Doobie + Quill + flywaydb + Cats Effect

## DB

3 tables: video, tags, and a videotag join table

## Video Endpoints

GET /video

Show all videos

GET /video/[id]

Show certain video

GET /video/tags/[id]

Show tags for certain video

POST /video/tags/[id]?tags=1,2,3

Set tags for certain video

POST /video/[name]

Create a new video

## Tag Endpoints

GET /tag

Show all tags

GET /tag/[id]

Show certain tag

POST /tag/[name]

Create a new tag

## VideoTag Endpoints

GET /videotag

Show all videotags

GET /videotag/[id]

Show certain videotag

POST /videotag/[videoid]/[tagid]

Create a new videotag (may well be easier to use the batch POST /video/tags/[id]?tags=1,2,3 call)
