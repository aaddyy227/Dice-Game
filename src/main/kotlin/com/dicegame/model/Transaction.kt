package com.dicegame.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
open class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null,
    open var playerId: Long,
    open var amount: Int,
    open var transactionType: String,
    open var transactionTime: LocalDateTime = LocalDateTime.now()
)