
create table IF NOT EXISTS video
(
id serial not null
  constraint video_pk
    primary key,
 name text not null
);

create table IF NOT EXISTS tag
(
    id serial not null
        constraint tag_pk
            primary key,

    name text not null
);

create table IF NOT EXISTS videotag
(
    id serial not null
        constraint videotag_pk
            primary key,
    videoId integer not null
        constraint videotag_video_id_fk
            references video on delete cascade,
    tagId integer not null
        constraint videotag_tag_id_fk
            references tag on delete cascade
);

create index IF NOT EXISTS videotag_tagId_index
    on videotag (tagId);

create index IF NOT EXISTS videotag_videoId_index
    on videotag (videoId);
