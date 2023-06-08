package demo.app.demotechno.constants

import demo.app.demotechno.R
import demo.app.demotechno.model.CategoriesModel
import demo.app.demotechno.model.EbooksModel
import demo.app.demotechno.model.IntroSlide

object Constants {
    fun getOnBoardingList(): ArrayList<IntroSlide> {
        return arrayListOf(
            IntroSlide(
                "Learn new and" + " useful topics",
                "Here you can find various online" + "lessons in just one click",
                R.drawable.on_boarding
            ),
            IntroSlide(
                "Analyze the statistics of your school",
                "Explore the best school in your city" + "or find your school in given graph ",
                R.drawable.onboarding4
            ),
            IntroSlide(
                "Use e-books at school",
                "Use e-books instead of actual books" + "at school  ",
                R.drawable.books
            ),
            IntroSlide(
                "AI-Mate",
                "Use artificial intelligence in order to learn something new or as messenger.",
                R.drawable.on_boarding4
            ),
            IntroSlide(
                "Find answers to any question",
                "Just write down question related to any" + "school subject in order to find answer",
                R.drawable.onboarding2
            ),
            IntroSlide(
                "Virtual Reality",
                "Virtual laboratory for your various research," +
                        "mix ready elements and have fun.",
                R.drawable.ai_mate2
            )
        )
    }

    fun getIcons(): ArrayList<CategoriesModel> {
        return arrayListOf(
            CategoriesModel(R.drawable.e_books, R.color.e_books_color, "E-BOOKS"),
            CategoriesModel(R.drawable.exchange, R.color.exchange_color, "EXCHANGE"),
            CategoriesModel(R.drawable.vr, R.color.vr_color, "VR"),
            CategoriesModel(R.drawable.news, R.color.news_color, "NEWS"),
            CategoriesModel(R.drawable.courses, R.color.courses_color, "COURSES"),
            CategoriesModel(R.drawable.ai_mate, R.color.ai_mate_color, "AI-MATE")
        )
    }
}