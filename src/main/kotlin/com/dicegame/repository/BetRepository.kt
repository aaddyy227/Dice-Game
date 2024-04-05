package com.dicegame.repository

import com.dicegame.model.Bet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BetRepository: JpaRepository<Bet, Long>