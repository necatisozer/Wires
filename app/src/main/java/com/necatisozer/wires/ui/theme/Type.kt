/*
 * Copyright 2021 Necati Sozer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.necatisozer.wires.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.necatisozer.wires.R

val nunitoFontFamily = FontFamily(
    Font(R.font.nunito_extralight, FontWeight.ExtraLight),
    Font(R.font.nunito_extralight_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.nunito, FontWeight.Normal),
    Font(R.font.nunito_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold),
    Font(R.font.nunito_extrabold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.nunito_black, FontWeight.Black),
    Font(R.font.nunito_black_italic, FontWeight.Black, FontStyle.Italic),
)

val Typography = Typography(
    defaultFontFamily = nunitoFontFamily,
    h1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        letterSpacing = 0.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        lineHeight = 24.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.15.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        letterSpacing = 1.sp
    )
)
