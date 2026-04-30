package si.model;

public enum AlienType {
	A(9, 7, 150), B(8, 7,500), C(10, 8, 300);
	private int width;
	private int height;
	private int score;

	private AlienType(int w, int h, int s) {
		width = w;
		height = h;
		score = s;
	}

	public int getWidth() {
		return width;
	}

	public int getScore() {
		return score;
	}

	public int getHeight() {return height; }
}
