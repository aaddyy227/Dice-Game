package com.dicegame.service

import com.dicegame.dto.LeaderboardView
import com.dicegame.exception.NoBetsPlacedException
import com.dicegame.repository.PlayerRepository
import com.dicegame.repository.TransactionRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LeaderboardService(
    private val transactionRepository: TransactionRepository,
    private val playerRepository: PlayerRepository
) {
    @Transactional(readOnly = true)
    fun getLeaderboard(): List<LeaderboardView> {
        val topFive = PageRequest.of(0, 5)

        val playerWinnings = transactionRepository.findPlayersTotalWinnings(topFive)
        if (playerWinnings.isEmpty()) {
            throw NoBetsPlacedException("There are no winners yet!")
        } else {
            val userIds = playerWinnings.map { it.playerId }
            val users = playerRepository.findAllById(userIds).associateBy { it.id }

            return playerWinnings.mapNotNull { winnings ->
                users[winnings.playerId]?.username?.let { username ->
                    LeaderboardView(username, winnings.totalWinnings.toInt())
                }
            }
        }
    }
}
