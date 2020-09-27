package com.pratilipi.test.connect4.service;

import com.pratilipi.test.connect4.dto.PlayerMoves;

public interface CoinDropService {
	
	String dropACoin(String Coin, String playerId);
	
	String startNewGame();
	
	PlayerMoves getPlayerMoves(String playerId);
}
