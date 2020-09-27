package com.pratilipi.test.connect4.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.pratilipi.test.connect4.constants.Connect4Constants;

@Entity
@Table(name = "player_game_matrix", schema = Connect4Constants.SCHEMA)
@Where(clause = "record_status='A'")
public class PlayerMoveMatrix implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_pk")
	private String userPk;

	@Column(name = "matrix")
	private String matrix;

	@Column(name = "prev_player")
	private String prevPlayer;

	@Column(name = "record_status")
	private String recordStatus;

	public PlayerMoveMatrix() {
	}

	public PlayerMoveMatrix(String userPk, String matrix, String prevPlayer, String recordStatus) {
		super();
		this.userPk = userPk;
		this.matrix = matrix;
		this.prevPlayer = prevPlayer;
		this.recordStatus = recordStatus;
	}

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getMatrix() {
		return matrix;
	}

	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getPrevPlayer() {
		return prevPlayer;
	}

	public void setPrevPlayer(String prevPlayer) {
		this.prevPlayer = prevPlayer;
	}

}
