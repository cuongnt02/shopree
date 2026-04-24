package com.ntc.shopree.core.ui.utils

import java.text.NumberFormat
import java.util.Locale

private val vndFormatter = NumberFormat.getNumberInstance(Locale("vi", "VN"))

fun formatVnd(amount: Long): String = "${vndFormatter.format(amount)} đ"

fun formatVnd(amount: Double): String = formatVnd(amount.toLong())
