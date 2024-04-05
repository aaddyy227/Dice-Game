package com.dicegame.controller

import com.dicegame.dto.LeaderboardView
import com.dicegame.service.LeaderboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class LeaderboardController(val leaderboardService: LeaderboardService) {

    @GetMapping("/leaderboard")
    fun getLeaderboard(): ResponseEntity<List<LeaderboardView>> {
        return ResponseEntity.ok(leaderboardService.getLeaderboard())
    }
}