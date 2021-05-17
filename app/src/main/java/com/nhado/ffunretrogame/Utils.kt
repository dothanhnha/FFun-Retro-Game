package com.nhado.ffunretrogame

import android.content.res.Resources
import android.util.TypedValue


fun Float.dpToPx(r: Resources): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        r.displayMetrics
    )

class Utils {

}