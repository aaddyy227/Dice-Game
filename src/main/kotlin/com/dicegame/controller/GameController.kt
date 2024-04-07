package com.dicegame.controller

import com.dicegame.dto.BetRequest
import com.dicegame.dto.BetResult
import com.dicegame.service.GameService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/game")
class GameController(val gameService: GameService) {

    @PostMapping("/placebet")
    fun placeBet(@Validated @RequestBody betRequest: BetRequest): ResponseEntity<BetResult> {
        val betResult = gameService.placeBet(betRequest)
        return ResponseEntity.ok(betResult)
    }
}