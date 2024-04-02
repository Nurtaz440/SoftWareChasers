package demo.app.demotechno.constants

import demo.app.demotechno.R
import demo.app.demotechno.model.CategoriesModel
import demo.app.demotechno.model.EbooksModel
import demo.app.demotechno.model.IntroSlide

object Constants {
    fun getOnBoardingList(): ArrayList<IntroSlide> {
        return arrayListOf(
            IntroSlide(
                "Explore new topics",
                "Here you can find various online" + "lessons in just one click",
                R.drawable.on_boarding
            ),
            IntroSlide(
                "Use e-books with VR",
                "VR will help you understand topics by visualizing " + "them and bring fun to your learning experience. ",
                R.drawable.books
            ),
            IntroSlide(
                "AI-Mate",
                "Just ask a question on any subject and you'll find the answer.",
                R.drawable.chat
            ),
            IntroSlide(
                "Augmented Reality",
                "Virtual laboratory for your various research," +
                        "mix ready elements and have fun.",
                R.drawable.ar
            ) ,
            IntroSlide(
                "News",
                "Stay up to date on new news " +
                        "and opportunities in education with us.",
                R.drawable.desktop
            )
        )
    }

    fun getIcons(): ArrayList<CategoriesModel> {
        return arrayListOf(
            CategoriesModel(R.drawable.e_books, R.color.e_books_color, "E-BOOKS"),
            CategoriesModel(R.drawable.vr_book, R.color.exchange_color, "VR BOOKS"),
            CategoriesModel(R.drawable.vr, R.color.vr_color, "VR LAB"),
            CategoriesModel(R.drawable.news, R.color.news_color, "NEWS"),
            CategoriesModel(R.drawable.courses, R.color.courses_color, "COURSES"),
            CategoriesModel(R.drawable.ai_mate, R.color.ai_mate_color, "AI-MATE")
        )
    }
}