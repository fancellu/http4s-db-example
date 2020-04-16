package object example {

  case class Video(id: Int, name: String)

  case class Tag(id: Int, name: String)

  case class VideoTag(id: Int, videoId: Int, tagId: Int)

}
