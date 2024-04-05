package com.dicegame.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
open class Bet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,
    open var playerId: Long,
    open var betAmount: Int,
    open var chosenNumber: Int,
    open var result: Int = 0
)