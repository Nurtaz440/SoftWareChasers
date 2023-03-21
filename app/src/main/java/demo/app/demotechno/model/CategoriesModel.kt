package demo.app.demotechno.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class CategoriesModel(
    @DrawableRes
    val image : Int? = null,
    @ColorRes
    val color : Int? = null,

    val title : String? = null
)