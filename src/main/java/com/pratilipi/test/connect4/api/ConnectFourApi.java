package com.pratilipi.test.connect4.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pratilipi.test.connect4.dto.PlayerMoves;

import io.swagger.annotations.ApiOperation;

public interface ConnectFourApi {
	
	@ApiOperation(value = "Get the moves performed by the user", nickname = "getMoves", response = PlayerMoves.class, 
			tags = {"connect-4" })
	@GetMapping(value = "/user/moves", produces = "application/json")
	ResponseEntity<PlayerMoves> getMoves(@RequestParam("playerId") String playerId);
	
	@ApiOperation(value = "Insert the coin into a column", nickname = "dropCoin", response = String.class, 
			tags = {"connect-4" })
	@PostMapping(value= "/move", produces = "application/json")
	ResponseEntity<String> dropCoin(@RequestParam("column") String column,@RequestParam("playerId") String playerId) throws JsonProcessingException;
	
	@ApiOperation(value = "Start a new game", nickname = "startGame", response = String.class, 
			tags = {"connect-4" })
	@PostMapping(value= "/start-game", produces = "application/json")
	ResponseEntity<String> startGame() throws JsonProcessingException;
	
	@ApiOperation(value = "Home controller", nickname = "getHome", response = String.class, 
			tags = {"connect-4" })
	@GetMapping("/")
	ResponseEntity<String> getHome() throws JsonProcessingException;

}
