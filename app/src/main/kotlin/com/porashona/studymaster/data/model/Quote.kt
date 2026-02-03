package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val textEn: String,
    val textBn: String,
    val author: String = "",
    val authorBn: String = "",
    val category: String = "motivation",
    val isFavorite: Boolean = false,
    val isCustom: Boolean = false,
    val shownCount: Int = 0,
    val lastShownAt: Long? = null
)

object DefaultQuotes {
    val quotes = listOf(
        Quote(
            textEn = "Success is not final, failure is not fatal: it is the courage to continue that counts.",
            textBn = "সাফল্য চূড়ান্ত নয়, ব্যর্থতা মারাত্মক নয়: চালিয়ে যাওয়ার সাহসই গুরুত্বপূর্ণ।",
            author = "Winston Churchill",
            authorBn = "উইনস্টন চার্চিল"
        ),
        Quote(
            textEn = "The only way to do great work is to love what you do.",
            textBn = "মহান কাজ করার একমাত্র উপায় হল তুমি যা করো তা ভালোবাসা।",
            author = "Steve Jobs",
            authorBn = "স্টিভ জবস"
        ),
        Quote(
            textEn = "Education is the most powerful weapon which you can use to change the world.",
            textBn = "শিক্ষা হল সবচেয়ে শক্তিশালী অস্ত্র যা তুমি বিশ্ব পরিবর্তন করতে ব্যবহার করতে পারো।",
            author = "Nelson Mandela",
            authorBn = "নেলসন ম্যান্ডেলা"
        ),
        Quote(
            textEn = "The future belongs to those who believe in the beauty of their dreams.",
            textBn = "ভবিষ্যৎ তাদের যারা তাদের স্বপ্নের সৌন্দর্যে বিশ্বাস করে।",
            author = "Eleanor Roosevelt",
            authorBn = "এলিনর রুজভেল্ট"
        ),
        Quote(
            textEn = "Hard work beats talent when talent doesn't work hard.",
            textBn = "কঠোর পরিশ্রম প্রতিভাকে হারায় যখন প্রতিভা কঠোর পরিশ্রম করে না।",
            author = "Tim Notke",
            authorBn = "টিম নটকে"
        ),
        Quote(
            textEn = "Don't watch the clock; do what it does. Keep going.",
            textBn = "ঘড়ির দিকে তাকাও না; ঘড়ি যা করে তাই করো। চলতে থাকো।",
            author = "Sam Levenson",
            authorBn = "স্যাম লেভেনসন"
        ),
        Quote(
            textEn = "It's Time To Give Your Parents Something Special An Extraordinary Result.",
            textBn = "এটাই সময় তোমার বাবা-মাকে একটা অসাধারণ ফলাফল উপহার দেয়ার ।",
            author = "Arif Rayhan Sir",
            authorBn = "আরিফ রায়হান স্যার"
        ),
        Quote(
            textEn = "A little progress each day adds up to big results.",
            textBn = "প্রতিদিন একটু একটু অগ্রগতি বড় ফলাফল এনে দেয়।",
            author = "Satya Nani",
            authorBn = "সত্য নানী"
        ),
        Quote(
            textEn = "Study hard, for the well is deep, and our brains are shallow.",
            textBn = "কঠোর পড়াশোনা করো, কারণ কূপ গভীর, এবং আমাদের মস্তিষ্ক অগভীর।",
            author = "Richard Baxter",
            authorBn = "রিচার্ড ব্যাক্সটার"
        ),
        Quote(
            textEn = "The expert in anything was once a beginner.",
            textBn = "যেকোনো বিষয়ে বিশেষজ্ঞ একসময় শিক্ষানবিস ছিল।",
            author = "Helen Hayes",
            authorBn = "হেলেন হেইস"
        ),
        Quote(
            textEn = "Dreams don't work unless you do.",
            textBn = "স্বপ্ন কাজ করে না যদি না তুমি কাজ করো।",
            author = "John C. Maxwell",
            authorBn = "জন সি. ম্যাক্সওয়েল"
        ),
        Quote(
            textEn = "There are no shortcuts to any place worth going.",
            textBn = "যোগ্য কোনো জায়গায় যাওয়ার কোনো শর্টকাট নেই।",
            author = "Beverly Sills",
            authorBn = "বেভারলি সিলস"
        ),
        Quote(
            textEn = "পড়াশোনায় কোনো বিকল্প নেই।",
            textBn = "পড়াশোনায় কোনো বিকল্প নেই।",
            author = "প্রবাদ",
            authorBn = "প্রবাদ"
        ),
        Quote(
            textEn = "Chose Your Destination Win Or Die",
            textBn = "তোমার লক্ষ্য নির্ধারণ করো (জিতবে না হারবে ?)।",
            author = "Redwan",
            authorBn = "রেদোয়ান"
        ),
        Quote(
            textEn = "জ্ঞানই শক্তি।",
            textBn = "জ্ঞানই শক্তি।",
            author = "ফ্রান্সিস বেকন",
            authorBn = "ফ্রান্সিস বেকন"
        )
    )
}