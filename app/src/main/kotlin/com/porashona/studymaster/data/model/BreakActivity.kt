package com.porashona.studymaster.data.model

data class BreakActivity(
    val id: String,
    val title: String,
    val titleBn: String,
    val description: String,
    val descriptionBn: String,
    val duration: Int, // seconds
    val icon: String,
    val category: BreakCategory
)

enum class BreakCategory {
    STRETCHING,
    EYE_EXERCISE,
    MEDITATION,
    BREATHING,
    HYDRATION,
    WALK,
    SNACK
}

object BreakActivities {
    val activities = listOf(
        // Stretching
        BreakActivity(
            id = "neck_stretch",
            title = "Neck Stretch",
            titleBn = "ঘাড় স্ট্রেচ",
            description = "Slowly tilt your head to each side, hold for 15 seconds",
            descriptionBn = "ধীরে ধীরে মাথা প্রতিটি দিকে কাত করুন, ১৫ সেকেন্ড ধরে রাখুন",
            duration = 60,
            icon = "🧘",
            category = BreakCategory.STRETCHING
        ),
        BreakActivity(
            id = "shoulder_roll",
            title = "Shoulder Rolls",
            titleBn = "কাঁধ ঘোরানো",
            description = "Roll your shoulders forward and backward 10 times each",
            descriptionBn = "কাঁধ সামনে ও পেছনে ১০ বার করে ঘোরান",
            duration = 45,
            icon = "💪",
            category = BreakCategory.STRETCHING
        ),
        BreakActivity(
            id = "back_stretch",
            title = "Back Stretch",
            titleBn = "পিঠ স্ট্রেচ",
            description = "Stand up, reach for the ceiling, then touch your toes",
            descriptionBn = "দাঁড়ান, সিলিংয়ের দিকে হাত বাড়ান, তারপর পায়ের আঙুল স্পর্শ করুন",
            duration = 60,
            icon = "🙆",
            category = BreakCategory.STRETCHING
        ),

        // Eye Exercises
        BreakActivity(
            id = "20_20_20",
            title = "20-20-20 Rule",
            titleBn = "২০-২০-২০ নিয়ম",
            description = "Look at something 20 feet away for 20 seconds",
            descriptionBn = "২০ ফুট দূরে কিছুর দিকে ২০ সেকেন্ড তাকান",
            duration = 20,
            icon = "👁️",
            category = BreakCategory.EYE_EXERCISE
        ),
        BreakActivity(
            id = "eye_circles",
            title = "Eye Circles",
            titleBn = "চোখ ঘোরানো",
            description = "Slowly rotate your eyes in circles, 5 times each direction",
            descriptionBn = "ধীরে ধীরে চোখ বৃত্তাকারে ঘোরান, প্রতি দিকে ৫ বার",
            duration = 30,
            icon = "🔄",
            category = BreakCategory.EYE_EXERCISE
        ),
        BreakActivity(
            id = "palming",
            title = "Palming",
            titleBn = "পামিং",
            description = "Rub palms together, place over closed eyes for 30 seconds",
            descriptionBn = "হাতের তালু ঘষুন, বন্ধ চোখের উপর ৩০ সেকেন্ড রাখুন",
            duration = 45,
            icon = "🙌",
            category = BreakCategory.EYE_EXERCISE
        ),

        // Meditation
        BreakActivity(
            id = "quick_meditation",
            title = "Quick Meditation",
            titleBn = "দ্রুত ধ্যান",
            description = "Close your eyes, focus on your breath for 1 minute",
            descriptionBn = "চোখ বন্ধ করুন, ১ মিনিট শ্বাসে মনোযোগ দিন",
            duration = 60,
            icon = "🧘‍♂️",
            category = BreakCategory.MEDITATION
        ),
        BreakActivity(
            id = "body_scan",
            title = "Body Scan",
            titleBn = "বডি স্ক্যান",
            description = "Mentally scan your body from head to toe, release tension",
            descriptionBn = "মাথা থেকে পা পর্যন্ত মানসিকভাবে স্ক্যান করুন, টেনশন ছাড়ুন",
            duration = 120,
            icon = "✨",
            category = BreakCategory.MEDITATION
        ),

        // Breathing
        BreakActivity(
            id = "box_breathing",
            title = "Box Breathing",
            titleBn = "বক্স ব্রিদিং",
            description = "Inhale 4s, hold 4s, exhale 4s, hold 4s. Repeat 4 times",
            descriptionBn = "৪সে শ্বাস নিন, ৪সে ধরুন, ৪সে ছাড়ুন, ৪সে ধরুন। ৪ বার পুনরাবৃত্তি",
            duration = 64,
            icon = "🌬️",
            category = BreakCategory.BREATHING
        ),
        BreakActivity(
            id = "deep_breaths",
            title = "Deep Breaths",
            titleBn = "গভীর শ্বাস",
            description = "Take 5 slow, deep breaths",
            descriptionBn = "৫টি ধীর, গভীর শ্বাস নিন",
            duration = 30,
            icon = "💨",
            category = BreakCategory.BREATHING
        ),

        // Hydration
        BreakActivity(
            id = "drink_water",
            title = "Drink Water",
            titleBn = "পানি পান করুন",
            description = "Drink a glass of water to stay hydrated",
            descriptionBn = "হাইড্রেটেড থাকতে এক গ্লাস পানি পান করুন",
            duration = 30,
            icon = "💧",
            category = BreakCategory.HYDRATION
        ),

        // Walk
        BreakActivity(
            id = "short_walk",
            title = "Short Walk",
            titleBn = "সংক্ষিপ্ত হাঁটা",
            description = "Walk around for 2-3 minutes to boost circulation",
            descriptionBn = "রক্ত সঞ্চালন বাড়াতে ২-৩ মিনিট হেঁটে আসুন",
            duration = 180,
            icon = "🚶",
            category = BreakCategory.WALK
        ),

        // Snack
        BreakActivity(
            id = "healthy_snack",
            title = "Healthy Snack",
            titleBn = "স্বাস্থ্যকর স্ন্যাক",
            description = "Have a fruit or some nuts for energy",
            descriptionBn = "শক্তির জন্য একটি ফল বা কিছু বাদাম খান",
            duration = 120,
            icon = "🍎",
            category = BreakCategory.SNACK
        )
    )

    fun getRandomActivity(): BreakActivity = activities.random()

    fun getByCategory(category: BreakCategory): List<BreakActivity> =
        activities.filter { it.category == category }
}