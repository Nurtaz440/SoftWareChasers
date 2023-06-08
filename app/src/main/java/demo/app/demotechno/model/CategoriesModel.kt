package demo.app.demotechno.model

import androidx.annotation.DrawableRes

data class CategoriesModel(
    @DrawableRes
    val image : Int? = null,
    val color : Int? = null,
    val title : String? = null
)