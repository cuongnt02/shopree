package com.ntc.shopree.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.ntc.shopree.R


val Roboto = FontFamily(
    Font(resId = R.font.robotoregular, weight = FontWeight.Normal),
    Font(resId = R.font.robotomedium, weight = FontWeight.Medium),
    Font(resId = R.font.robotobold, weight = FontWeight.Bold),
    Font(resId = R.font.robotoblack, weight = FontWeight.Black),
    Font(resId = R.font.robotolight, weight = FontWeight.Light),
    Font(resId = R.font.robotoextrabold, weight = FontWeight.ExtraBold),
    Font(resId = R.font.robotosemibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.robotoextralight, weight = FontWeight.ExtraLight),
    Font(resId = R.font.robotothin, weight = FontWeight.Thin),
    Font(resId = R.font.robotoitalic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resId = R.font.robotomediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(resId = R.font.robotobolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(resId = R.font.robotoblackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(resId = R.font.robotolightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(resId = R.font.robotothinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(
        resId = R.font.robotoextrabolditalic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.robotosemibolditalic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.robotoextralightitalic,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),
)


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 16.sp,
    )

)