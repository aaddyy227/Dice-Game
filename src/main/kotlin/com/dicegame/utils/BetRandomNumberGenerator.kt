package com.dicegame.utils

import kotlin.random.Random

class BetRandomNumberGenerator : RandomNumberGenerator {
    override fun nextInt(from: Int, until: Int): Int = Random.nextInt(from, until)
}