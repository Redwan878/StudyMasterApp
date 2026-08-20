package com.porashona.studymaster.data.model

object StudyMusicLibrary {
    val tracks = listOf(
        MusicTrack(1, "Lofi Study Beats", "Focus Music", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", MusicCategory.LOFI),
        MusicTrack(2, "Piano Dreams", "Relaxing Piano", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3", MusicCategory.PIANO),
        MusicTrack(3, "Nature Sounds", "Forest Rain", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3", MusicCategory.NATURE),
        MusicTrack(4, "Classical Serenade", "Piano Classics", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3", MusicCategory.CLASSICAL),
        MusicTrack(5, "Jazz Cafe", "Smooth Jazz", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3", MusicCategory.JAZZ),
        MusicTrack(6, "Deep Focus", "Ambient Sounds", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3", MusicCategory.AMBIENT),
        MusicTrack(7, "Meditation Flow", "Calm Mind", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3", MusicCategory.MEDITATION),
        MusicTrack(8, "Rainy Days", "Gentle Rain", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3", MusicCategory.RAIN),
        MusicTrack(9, "Brain Waves", "Deep Focus", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3", MusicCategory.FOCUS),
        MusicTrack(10, "Study Helper", "Lofi Beats", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3", MusicCategory.LOFI),
        MusicTrack(11, "Creative Mind", "Piano & Keys", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-11.mp3", MusicCategory.PIANO),
        MusicTrack(12, "White Noise", "Pure White", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-12.mp3", MusicCategory.NATURE)
    )

    fun getTrackById(id: Int): MusicTrack? {
        return tracks.find { it.id == id }
    }

    fun getTracksByCategory(category: MusicCategory): List<MusicTrack> {
        return tracks.filter { it.category == category }
    }

    fun getAllTracks(): List<MusicTrack> = tracks
}