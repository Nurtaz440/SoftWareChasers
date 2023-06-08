package demo.app.demotechno.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class EbooksModel(
    @DrawableRes
    val image : Int? = null,
    @DrawableRes
    val number : Int? = null,
    @ColorRes
    val colorText : Int? = null,
    @ColorRes
    val color : Int? = null,
    val title : String? = null
)
