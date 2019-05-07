import java.util.Map;

public class TaxRate {
	private String country;
	private float rate;
	private String[] exempts;
	private Map<String, String> PRODUCT_CAT;

	public TaxRate(Map<String, String> productCat, String country, float rate, String[] exempts) {
		this.country = country;
		this.rate = rate;
		this.exempts = exempts;
		this.PRODUCT_CAT = productCat;
	}

	public float getRate() {
		return this.rate;
	}

	public boolean shouldExempt(String productName) {
		String productCat = PRODUCT_CAT.get(productName);

		for (int i=0; i<this.exempts.length ; i++) {
			if (this.exempts[i].equals(productCat)) {
				return true;
			}
		}
		return false;
	}
}