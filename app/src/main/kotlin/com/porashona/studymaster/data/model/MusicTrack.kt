package com.porashona.studymaster.data.model

data class MusicTrack(
    val id: Int,
    val title: String,
    val artist: String,
    val streamUrl: String,
    val coverUrl: String = "",
    val duration: Long = 0
)

object StudyMusicLibrary {

    // Free Lo-Fi / Study Music Streams (Royalty Free)
    val tracks = listOf(
        MusicTrack(
            id = 1,
            title = "Lo-Fi Study Beats",
            artist = "ChilledCow",
            streamUrl = "https://streams.ilovemusic.de/iloveradio17.mp3"
        ),
        MusicTrack(
            id = 2,
            title = "Relaxing Piano",
            artist = "Study Music",
            streamUrl = "https://stream.zeno.fm/f3wvbbqmdg8uv"
        ),
        MusicTrack(
            id = 3,
            title = "Nature Sounds",
            artist = "Ambient",
            streamUrl = "https://stream.zeno.fm/4d6622pd8g8uv"
        ),
        MusicTrack(
            id = 4,
            title = "Classical Focus",
            artist = "Mozart & Bach",
            streamUrl = "https://stream.zeno.fm/0r0xa792kwzuv"
        ),
        MusicTrack(
            id = 5,
            title = "Jazz Cafe",
            artist = "Smooth Jazz",
            streamUrl = "https://stream.zeno.fm/fyn8eh3h5f8uv"
        ),
        MusicTrack(
            id = 6,
            title = "Rain Sounds",
            artist = "White Noise",
            streamUrl = "https://stream.zeno.fm/tkyed9pc9vzuv"
        ),
        MusicTrack(
            id = 7,
            title = "Deep Focus",
            artist = "Electronic Ambient",
            streamUrl = "https://stream.zeno.fm/n07kcmdc9vzuv"
        ),
        MusicTrack(
            id = 8,
            title = "Meditation Music",
            artist = "Zen",
            streamUrl = "https://stream.zeno.fm/5auvcykfttzuv"
        )
    )

    fun getTrackById(id: Int): MusicTrack? = tracks.find { it.id == id }
}