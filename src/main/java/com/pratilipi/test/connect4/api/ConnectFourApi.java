package com.pratilipi.test.connect4.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ConnectFourApi {
	
	@GetMapping(value = "/user/moves", produces = "application/json")
	ResponseEntity<String> getMoves();
	
	@PostMapping(value= "/move", produces = "application/json")
	ResponseEntity<String> dropCoin(@RequestParam("column") String column,@RequestParam("playerId") String playerId) throws JsonProcessingException;
	
	@PostMapping(value= "/start-game", produces = "application/json")
	ResponseEntity<String> startGame() throws JsonProcessingException;

}
