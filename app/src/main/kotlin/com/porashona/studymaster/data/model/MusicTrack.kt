package com.porashona.studymaster.data.model

enum class MusicCategory { LOFI, NATURE, CLASSICAL, AMBIENT, JAZZ }

data class MusicTrack(
    val id: Int,
    val title: String,
    val artist: String,
    val streamUrl: String,
    val category: MusicCategory = MusicCategory.AMBIENT,
    val coverUrl: String = "",
    val duration: Long = 0,
)

object StudyMusicLibrary {

    // Free Lo-Fi / Study Music Streams (Royalty Free)
    val tracks = listOf(
        MusicTrack(
            id = 1,
            title = "Lo-Fi Study Beats",
            artist = "ChilledCow",
            streamUrl = "https://streams.ilovemusic.de/iloveradio17.mp3",
            category = MusicCategory.LOFI,
        ),
        MusicTrack(
            id = 2,
            title = "Relaxing Piano",
            artist = "Study Music",
            streamUrl = "https://stream.zeno.fm/f3wvbbqmdg8uv",
            category = MusicCategory.CLASSICAL,
        ),
        MusicTrack(
            id = 3,
            title = "Forest & Rain",
            artist = "Ambient Nature",
            streamUrl = "https://stream.zeno.fm/4d6622pd8g8uv",
            category = MusicCategory.NATURE,
        ),
        MusicTrack(
            id = 4,
            title = "Classical Focus",
            artist = "Mozart & Bach",
            streamUrl = "https://stream.zeno.fm/0r0xa792kwzuv",
            category = MusicCategory.CLASSICAL,
        ),
        MusicTrack(
            id = 5,
            title = "Jazz Cafe",
            artist = "Smooth Jazz",
            streamUrl = "https://stream.zeno.fm/fyn8eh3h5f8uv",
            category = MusicCategory.JAZZ,
        ),
        MusicTrack(
            id = 6,
            title = "Rain Sounds",
            artist = "White Noise",
            streamUrl = "https://stream.zeno.fm/tkyed9pc9vzuv",
            category = MusicCategory.NATURE,
        ),
        MusicTrack(
            id = 7,
            title = "Deep Focus",
            artist = "Electronic Ambient",
            streamUrl = "https://stream.zeno.fm/n07kcmdc9vzuv",
            category = MusicCategory.AMBIENT,
        ),
        MusicTrack(
            id = 8,
            title = "Meditation Music",
            artist = "Zen",
            streamUrl = "https://stream.zeno.fm/5auvcykfttzuv",
            category = MusicCategory.AMBIENT,
        ),
        MusicTrack(
            id = 9,
            title = "Chillhop Radio",
            artist = "Chillhop Music",
            streamUrl = "https://streams.fluxfm.de/Chillhop/mp3-320/streams.fluxfm.de/",
            category = MusicCategory.LOFI,
        ),
        MusicTrack(
            id = 10,
            title = "Ocean Waves",
            artist = "Natural Sounds",
            streamUrl = "https://streaming.radionomy.com/Oceanwaves-RadioWhiteNoise",
            category = MusicCategory.NATURE,
        ),
        MusicTrack(
            id = 11,
            title = "Baroque Strings",
            artist = "Classical FM",
            streamUrl = "https://media-ice.musicradio.com/ClassicFMMP3",
            category = MusicCategory.CLASSICAL,
        ),
        MusicTrack(
            id = 12,
            title = "Smooth Bossa",
            artist = "Jazz24",
            streamUrl = "https://live.wostreaming.net/direct/ppm-jazz24aac-ibc1",
            category = MusicCategory.JAZZ,
        ),
    )

    fun getTrackById(id: Int): MusicTrack? = tracks.find { it.id == id }
}
