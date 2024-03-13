package demo.app.demotechno.utils

import demo.app.demotechno.R
import demo.app.demotechno.model.News

object Constants {
    fun getNewsItems() : ArrayList<News>{
        return arrayListOf(
            News(R.drawable.news,"If you want to build better societies, we should be mindful of what we teach our children."),
            News(R.drawable.image_cv,"The best AI tools to power your academic research."),
            News(R.drawable.news2,"Every year spent in education improves your life expectancy, new study finds"),
            News(R.drawable.image_cv2,"Are Students Math Futures Being Unwittingly Set By Tracking ?"),
            News(R.drawable.image_cv6,""),
            News(R.drawable.ai_mate,"")
        )
    }
}