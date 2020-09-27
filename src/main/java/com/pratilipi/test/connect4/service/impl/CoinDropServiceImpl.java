package com.pratilipi.test.connect4.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pratilipi.test.connect4.constants.Connect4Constants;
import com.pratilipi.test.connect4.constants.Connect4ErrorMsg;
import com.pratilipi.test.connect4.constants.Connect4SuccessMsg;
import com.pratilipi.test.connect4.dto.PlayerMoves;
import com.pratilipi.test.connect4.entity.PlayerMoveMatrix;
import com.pratilipi.test.connect4.model.CoinModel;
import com.pratilipi.test.connect4.repository.PlayerMoveMatrixRepository;
import com.pratilipi.test.connect4.service.CoinDropService;

@Service
public class CoinDropServiceImpl implements CoinDropService {

	@Autowired
	PlayerMoveMatrixRepository playerMoveMatrixRepository;

	private static final int rowLength = 6;
	private static final int colLength = 7;

	@Override
	public String dropACoin(String col, String playerId) {

		int column = Integer.parseInt(col);
		if (!isValidCol(column)) {
			return Connect4ErrorMsg.INVALID_MOVE;
		}

		int row = -1;
		String color = Connect4Constants.PLAYER_YELLOW;
		Optional<PlayerMoveMatrix> optPlMat = playerMoveMatrixRepository.findById(playerId);
		if (optPlMat.isPresent()) {
			if(optPlMat.get().getGameStatus().equalsIgnoreCase(Connect4Constants.GAME_COMPLETED)) {
				return Connect4ErrorMsg.START_NEW_GAME;
			}
			String[][] matrix = getStoredMatrix(optPlMat);
			List<String> yellowPlayerMoves = null;
			List<String> redPlayerMoves = null;
			if (optPlMat.get().getYellowPlayerMoves() == null) {
				yellowPlayerMoves = new ArrayList<String>();
			} else {
				yellowPlayerMoves = new ArrayList<String>();
//				System.out.println(optPlMat.get().getYellowPlayerMoves());
//				String withoutBrackets = optPlMat.get().getYellowPlayerMoves().replaceAll("\\[", "").replaceAll("\\]","");
//				System.out.println("Yellow: " + withoutBrackets);
//				String yPlayerMoves[] = withoutBrackets.split(",");
//				for (String y : yPlayerMoves) {
//					if (y.equals(Connect4Constants.PLAYER_YELLOW)) {
//						System.out.println("y:" + y);
//						yellowPlayerMoves.add(y);
//					}
//
//				}

			}

			if (optPlMat.get().getRedPlayerMoves() == null) {
				redPlayerMoves = new ArrayList<String>();
			} else {
				redPlayerMoves = new ArrayList<String>();
//				String withoutBrackets = optPlMat.get().getRedPlayerMoves().replaceAll("\\[", "").replaceAll("\\]", "");
//				System.out.println("r: " + withoutBrackets);
//				String rPlayerMoves[] = withoutBrackets.split(",");
//				for (String r : rPlayerMoves) {
//					if (r.equals(Connect4Constants.PLAYER_RED)) {
//						redPlayerMoves.add(r);
//					}
//
//				}

			}

			if (checkDrawCondition(matrix)) {
				return Connect4SuccessMsg.GAME_DRAW_MESSAGE;
			}
			row = getRow(column, matrix);
			if (row == -1) {
				return Connect4ErrorMsg.INVALID_MOVE;
			}

			if (optPlMat.get().getPrevPlayer() == null) {
				color = Connect4Constants.PLAYER_YELLOW;
				yellowPlayerMoves.add(String.valueOf(column));
			} else if (optPlMat.get().getPrevPlayer().equals(Connect4Constants.PLAYER_YELLOW)) {
				color = Connect4Constants.PLAYER_RED;
				redPlayerMoves.add(String.valueOf(column));
			} else if (optPlMat.get().getPrevPlayer().equals(Connect4Constants.PLAYER_RED)) {
				color = Connect4Constants.PLAYER_YELLOW;
				yellowPlayerMoves.add(String.valueOf(column));
			}

			matrix[row][column] = color;

			System.out.println(Arrays.deepToString(matrix));

			String mtString = Arrays.deepToString(matrix);
			PlayerMoveMatrix plMt = new PlayerMoveMatrix();
			plMt.setUserPk(playerId);
			plMt.setMatrix(mtString);
			plMt.setPrevPlayer(color);
			plMt.setGameStatus(Connect4Constants.GAME_NOT_COMPLETED);
			plMt.setYellowPlayerMoves(yellowPlayerMoves.toString());
			plMt.setRedPlayerMoves(redPlayerMoves.toString());
			plMt.setRecordStatus(Connect4Constants.ACTIVE);
			PlayerMoveMatrix moveMatrix = playerMoveMatrixRepository.save(plMt);

			CoinModel coin = getCoinData(row, column, color);
			if (checkHorizontal(coin, matrix) || checkVertical(coin, matrix) || checkForwardDiagonal(coin, matrix)
					|| checkBackwardDiagonal(coin, matrix)) {
				moveMatrix.setGameStatus(Connect4Constants.GAME_COMPLETED);
				playerMoveMatrixRepository.save(moveMatrix);
				return color.equals(Connect4Constants.PLAYER_YELLOW) ? "Yellow " + Connect4SuccessMsg.GAME_WON_MESSAGE
						: "Red " + Connect4SuccessMsg.GAME_WON_MESSAGE;
			}
		} else {
			return Connect4ErrorMsg.START_NEW_GAME;
		}

		return Connect4SuccessMsg.VALID;
	}

	private int getRow(int col, String mat[][]) {

		for (int i = rowLength - 1; i >= 0; i--) {
			if (mat[i][col].equals("0")) {
				return i;
			}
		}
		return -1;
	}

	private String[][] getStoredMatrix(Optional<PlayerMoveMatrix> optPlMat) {
		// Split on this delimiter
		String[] rows = optPlMat.get().getMatrix().split("], \\[");
		for (int i = 0; i < rows.length; i++) {
			// Remove any beginning and ending braces and any white spaces
			rows[i] = rows[i].replace("[[", "").replace("]]", "").replaceAll(" ", "");
		}
		// Get the number of columns in a row
		int numberOfColumns = rows[0].split(",").length;
		String[][] matrix = new String[rows.length][numberOfColumns];
		for (int i = 0; i < rows.length; i++) {
			matrix[i] = rows[i].split(",");
		}
		return matrix;
	}

	private boolean checkDrawCondition(String mat[][]) {
		int r = 5;
		int c = 6;
		for (int i = 0; i <= r; i++) {
			for (int j = 0; j <= c; j++) {
				if (mat[i][j].equals("0")) {
					return false;
				}
			}
		}
		return true;
	}

	private CoinModel getCoinData(int focalRow, int focalCol, String color) {

		CoinModel coinModel = new CoinModel();
		coinModel.setFocalRow(focalRow);
		coinModel.setFocalCol(focalCol);
		coinModel.setMinRow(getMin(focalRow));
		coinModel.setMaxRow(getMax(focalRow, 5));
		coinModel.setMinCol(getMin(focalCol));
		coinModel.setMaxCol(getMax(focalCol, 6));
		coinModel.setColor(color);
		return coinModel;
	}

	private boolean checkHorizontal(CoinModel coin, String mat[][]) {
		String color = coin.getColor();
		for (int row = coin.getFocalRow(), col = coin.getMinCol(); col <= coin.getMaxCol(); col++) {
			if (isValidRowCol(row, col) && isValidRowCol(row, col + 1) && isValidRowCol(row, col + 2)
					&& isValidRowCol(row, col + 3)) {
				if (color.equals(mat[row][col]) && color.equals(mat[row][col + 1]) && color.equals(mat[row][col + 2])
						&& color.equals(mat[row][col + 3])) {
					return true;
				}
			}

		}
		return false;
	}

	private boolean checkVertical(CoinModel coin, String mat[][]) {
		String color = coin.getColor();
		for (int col = coin.getFocalCol(), row = coin.getMinRow(); row <= coin.getFocalRow(); row++) {
			if (isValidRowCol(row, col) && isValidRowCol(row + 1, col) && isValidRowCol(row + 2, col)
					&& isValidRowCol(row + 3, col)) {
				if (color.equals(mat[row][col]) && color.equals(mat[row + 1][col]) && color.equals(mat[row + 2][col])
						&& color.equals(mat[row + 3][col])) {
					return true;
				}
			}

		}
		return false;
	}

	private boolean checkForwardDiagonal(CoinModel coin, String mat[][]) {
		int row = coin.getFocalRow();
		int col = coin.getFocalRow();
		String color = coin.getColor();
		while (row > coin.getMinRow() && col > coin.getMinCol()) {
			row--;
			col--;
		}

		for (; row <= coin.getMaxRow() && col <= coin.getMaxCol(); row++, col++) {
			if (isValidRowCol(row, col) && isValidRowCol(row + 1, col + 1) && isValidRowCol(row + 2, col + 2)
					&& isValidRowCol(row + 3, col + 3)) {
				if (color.equals(mat[row][col]) && color.equals(mat[row + 1][col + 1])
						&& color.equals(mat[row + 2][col + 2]) && color.equals(mat[row + 3][col + 3])) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean checkBackwardDiagonal(CoinModel coin, String mat[][]) {
		int row = coin.getFocalRow();
		int col = coin.getFocalRow();
		String color = coin.getColor();

		while (row < coin.getMaxRow() && col > coin.getMinCol()) {
			row++;
			col++;
		}

		for (; row >= coin.getMinRow() && col <= coin.getMaxCol(); row--, col++) {
			if (isValidRowCol(row, col) && isValidRowCol(row - 1, col + 1) && isValidRowCol(row - 2, col + 2)
					&& isValidRowCol(row - 3, col + 3)) {
				if (color.equals(mat[row][col]) && color.equals(mat[row - 1][col + 1])
						&& color.equals(mat[row - 2][col + 2]) && color.equals(mat[row - 3][col + 3])) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isValidRowCol(int row, int col) {
		return row <= 5 && row >= 0 && col <= 6 && col >= 0;
	}

	private boolean isValidCol(int col) {
		return col <= 6 && col >= 0;
	}

	private int getMin(int num) {
		return Math.max(num - 3, 0);
	}

	private int getMax(int num, int max) {
		return Math.min(num, max);
	}

	@Override
	public String startNewGame() {
		int[][] mat = new int[rowLength][colLength];
		String playerId = UUID.randomUUID().toString();
		PlayerMoveMatrix plMt = new PlayerMoveMatrix();
		plMt.setUserPk(playerId);
		plMt.setMatrix(Arrays.deepToString(mat));
		plMt.setRecordStatus(Connect4Constants.ACTIVE);
		plMt.setPrevPlayer(null);
		plMt.setGameStatus(Connect4Constants.GAME_NOT_COMPLETED);
		playerMoveMatrixRepository.save(plMt);
		return playerId;
	}

	@Override
	public PlayerMoves getPlayerMoves(String playerId) {
		Optional<PlayerMoveMatrix> optPlayerMatrix = playerMoveMatrixRepository.findById(playerId);
		PlayerMoves playerMoves = null;
		if (optPlayerMatrix.isPresent()) {
			playerMoves = new PlayerMoves();
			playerMoves.setRedPlayerMoves(
					optPlayerMatrix.get().getRedPlayerMoves() != null ? optPlayerMatrix.get().getRedPlayerMoves()
							: null);
			playerMoves.setYellowPlayerMoves(
					optPlayerMatrix.get().getYellowPlayerMoves() != null ? optPlayerMatrix.get().getYellowPlayerMoves()
							: null);
		}
		return playerMoves;
	}
}
