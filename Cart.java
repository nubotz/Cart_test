import java.util.ArrayList;

public class Cart {
	private TaxRate taxRate;
	private float subtotal = 0;
	private float tax = 0;
	private float total = 0;
	private ArrayList<CartItem> cartItems;

	public Cart(TaxRate taxRate) {
		this.taxRate = taxRate;
		this.subtotal = 0f;
		this.cartItems = new ArrayList<CartItem>();
	}

	// Round up to nearest 0.05
	public float rounding(float price) {
		float rounded = (float) Math.ceil(price * 20.0f) / 20.0f;
		return rounded;
	}

	public void addItem(CartItem item) {
		this.cartItems.add(item);
		// calculate the subtotal
		this.subtotal += item.price * item.qty;
		// calculate the tax
		if (!taxRate.shouldExempt(item.name)) {
			// System.out.println("Tax is " + item.price + " / " + item.qty + " / " + taxRate.getRate());
			this.tax += rounding(item.price * item.qty * taxRate.getRate());
		}
		this.total = this.subtotal + this.tax;
	}

	public float getSubtotal() {
		return this.subtotal;
	}

	public void display() {
		System.out.println("item\tprice\tqty\n");
		for (CartItem item : this.cartItems) {
			System.out.println(item.name + "\t" + item.price + "\t" + item.qty);
		}
		System.out.println("subtotal:\t  \t" + String.format("%.2f", this.subtotal));
		System.out.println("tax:\t  \t" + String.format("%.2f", this.tax));
		System.out.println("total:\t  \t" + String.format("%.2f", this.total));
		System.out.println();
	}

}