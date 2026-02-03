-keepattributes Signature
-keepattributes *Annotation*

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class com.porashona.studymaster.data.model.** { *; }

# MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }