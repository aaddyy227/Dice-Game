package com.dicegame.dto

data class BetRequest(
    val playerId: Long,
    val betAmount: Int,
    val chosenNumber: Int
)