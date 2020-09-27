package com.pratilipi.test.connect4.model;

public class CoinModel {

	private int focalRow;
	private int focalCol;
	private int minRow;
	private int minCol;
	private int maxRow;
	private int maxCol;
	private String color;

	public CoinModel() {
	}

	public CoinModel(int focalRow, int focalCol, int minRow, int minCol, int maxRow, int maxCol, String color) {
		super();
		this.focalRow = focalRow;
		this.focalCol = focalCol;
		this.minRow = minRow;
		this.minCol = minCol;
		this.maxRow = maxRow;
		this.maxCol = maxCol;
		this.color = color;
	}

	public int getFocalRow() {
		return focalRow;
	}

	public void setFocalRow(int focalRow) {
		this.focalRow = focalRow;
	}

	public int getFocalCol() {
		return focalCol;
	}

	public void setFocalCol(int focalCol) {
		this.focalCol = focalCol;
	}

	public int getMinRow() {
		return minRow;
	}

	public void setMinRow(int minRow) {
		this.minRow = minRow;
	}

	public int getMinCol() {
		return minCol;
	}

	public void setMinCol(int minCol) {
		this.minCol = minCol;
	}

	public int getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	public int getMaxCol() {
		return maxCol;
	}

	public void setMaxCol(int maxCol) {
		this.maxCol = maxCol;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
