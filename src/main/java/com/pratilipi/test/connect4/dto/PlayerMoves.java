package com.pratilipi.test.connect4.dto;

public class PlayerMoves {

	String yellowPlayerMoves;
	String redPlayerMoves;

	public PlayerMoves() {
	}

	public PlayerMoves(String yellowPlayerMoves, String redPlayerMoves) {
		super();
		this.yellowPlayerMoves = yellowPlayerMoves;
		this.redPlayerMoves = redPlayerMoves;
	}

	public String getYellowPlayerMoves() {
		return yellowPlayerMoves;
	}

	public void setYellowPlayerMoves(String yellowPlayerMoves) {
		this.yellowPlayerMoves = yellowPlayerMoves;
	}

	public String getRedPlayerMoves() {
		return redPlayerMoves;
	}

	public void setRedPlayerMoves(String redPlayerMoves) {
		this.redPlayerMoves = redPlayerMoves;
	}

}
