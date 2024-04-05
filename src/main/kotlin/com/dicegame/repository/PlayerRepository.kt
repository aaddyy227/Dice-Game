package com.dicegame.repository

import com.dicegame.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : JpaRepository<Player, Long> {
    fun findByUsername(username: String): Player?
}