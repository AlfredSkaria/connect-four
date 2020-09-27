package com.pratilipi.test.connect4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratilipi.test.connect4.api.ConnectFourApi;
import com.pratilipi.test.connect4.service.CoinDropService;

@RestController
public class ConnectFourController implements ConnectFourApi{
	
	@Autowired
	CoinDropService coinDropService;

	@Override
	public ResponseEntity<String> getMoves() {

		return null;
	}

	@Override
	public ResponseEntity<String> dropCoin(String column, String playerId) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(coinDropService.dropACoin(column, playerId)));
	}

	@Override
	public ResponseEntity<String> startGame() throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(coinDropService.startNewGame()));
	}

}
