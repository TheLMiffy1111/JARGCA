package thelm.jargca.compat.agricraft.api.agriplants;

public class Product {

	private int min = 5;
	private int range = 0;
	private double chance = 0.99;
	private String format;

	public Product setMin(int min) {
		this.min = min;
		return this;
	}

	public int getMin() {
		return min;
	}

	public Product setRange(int range) {
		this.range = range;
		return this;
	}

	public int getRange() {
		return range;
	}

	public Product setChance(double chance) {
		this.chance = chance;
		return this;
	}

	public double getChance() {
		return chance;
	}

	public Product setFormat(String format) {
		this.format = format;
		return this;
	}

	public String getFormat() {
		return format;
	}
}
