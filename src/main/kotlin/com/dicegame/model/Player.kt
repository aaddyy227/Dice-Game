package com.dicegame.model

import jakarta.annotation.Nullable
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
open class Player(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long?,
    open var name: String,
    open var surname: String,
    open var username: String,
    @Nullable
    @Embedded
    open var wallet: Wallet = Wallet()
)

@Embeddable
open class Wallet(open var balance: Int = 1000)