package com.dicegame.repository

import com.dicegame.dto.PlayerWinnings
import com.dicegame.model.Transaction
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long>
{

    fun findByPlayerId(playerId: Long): List<Transaction>
    @Query("SELECT new com.dicegame.dto.PlayerWinnings(t.playerId, SUM(t.amount)) FROM Transaction t WHERE t.transactionType = 'WIN' GROUP BY t.playerId ORDER BY SUM(t.amount) DESC")
    fun findPlayersTotalWinnings(pageable: Pageable): List<PlayerWinnings>

}
