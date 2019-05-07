import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
	public static Map<String, TaxRate> TAX_RATE_MAP;
	public static Map<String, String> PRODUCT_CAT;

	public static void initProductCat() {
		PRODUCT_CAT = new HashMap<String, String>();
		PRODUCT_CAT.put("potato chips", "food");
		PRODUCT_CAT.put("shirt", "clothing");
	}

	public static void initTaxRate() {
		TAX_RATE_MAP = new HashMap<String, TaxRate>();
		// init, better to be loaded from config file
		TAX_RATE_MAP.put("CA", new TaxRate(PRODUCT_CAT, "CA", 0.0975f, new String[] { "food" }));
		TAX_RATE_MAP.put("NY", new TaxRate(PRODUCT_CAT, "NY", 0.08875f, new String[] { "food", "clothing" }));
	}

	public static void main(String args[]) {
		initProductCat();
		initTaxRate();

		// load input from file
		JSONParser parser = new JSONParser();
		try {
			JSONArray inputs = (JSONArray) parser.parse(new FileReader("data.json"));
			// looping each use case
			for (int i=0; i<inputs.size(); i++) {
				JSONObject obj = (JSONObject) inputs.get(i);
				String country = (String) obj.get("location");
				TaxRate taxtRate = TAX_RATE_MAP.get(country);
				Cart cart = new Cart(taxtRate);
				JSONArray items = (JSONArray) obj.get("items");
				// looping each cart item
				for (int j=0; j<items.size(); j++) {
					JSONObject cartItemData = (JSONObject) items.get(j);
					String name = (String) cartItemData.get("name");
					float price = ((Double) cartItemData.get("price")).floatValue();
					int qty = (int) ((long) cartItemData.get("qty"));
					CartItem cartItem = new CartItem(name, price, qty);
					cart.addItem(cartItem);
				}
				cart.display();
			}
		} catch (FileNotFoundException e) {
			System.out.print("Cannot find data.json");
		} catch (IOException e) {
			System.out.print("IO Exception");
		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}
}