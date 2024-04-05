package com.dicegame.dto

data class BetResult(
    val playerId: Long,
    val oldBalance: Int,
    val newBalance: Int,
    val betAmount: Int,
    val chosenNumber: Int,
    val generatedNumber: Int,
    val winAmount: Int,
    val betOutcome: String // "WIN" or "LOSS"
)
