package thelm.jargca.compat.agricraft.api.agriplants;

public class Condition {
	
	private int amount = 1;
	private int x1 = 0;
	private int y1 = -2;
	private int z1 = 0;
	private int x2 = 0;
	private int y2 = -2;
	private int z2 = 0;
	private String format;

	public Condition setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	public int getAmount() {
		return amount;
	}

	public Condition setX1(int x1) {
		this.x1 = x1;
		return this;
	}

	public int getX1() {
		return x1;
	}

	public Condition setY1(int y1) {
		this.y1 = y1;
		return this;
	}

	public int getY1() {
		return y1;
	}

	public Condition setZ1(int z1) {
		this.z1 = z1;
		return this;
	}

	public int getZ1() {
		return z1;
	}

	public Condition setX2(int x2) {
		this.x2 = x2;
		return this;
	}

	public int getX2() {
		return x2;
	}

	public Condition setY2(int y2) {
		this.y2 = y2;
		return this;
	}

	public int getY2() {
		return y2;
	}

	public Condition setZ2(int z2) {
		this.z2 = z2;
		return this;
	}

	public int getZ2() {
		return z2;
	}

	public Condition setFormat(String format) {
		this.format = format;
		return this;
	}

	public String getFormat() {
		return format;
	}
}
