package com.dicegame.utils

interface RandomNumberGenerator {
    fun nextInt(from: Int, until: Int): Int
}