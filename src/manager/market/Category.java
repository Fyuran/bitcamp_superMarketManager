package manager.market;

import java.util.Locale;

public enum Category {
	FRUITS,
	VEGETABLES,
	MEAT,
	FISH,
	BAKERY,
	BEVERAGE,
	PASTA,
	HOUSEHOLDSUPPLIES,
	HEALTHCARE,
	DAIRY;
	
	private static final Category[] categories = Category.values();
	private static final int length = Category.values().length;
	
	public static int size() {
		return length;
	}
	
	public static void getCategoriesList() {
		int items = 0; //var used to display x values by row
		String fmt = "";
		
		for(int i = 0; i < categories.length; i++) {
			Category cat = categories[i];
			fmt += (i+1) + "-" + cat + " ";
			
			if(items < 3) {
				items++;
			} else {
				fmt += "\n"; //new line
				items = 0; //reset back to zero
			}
		}
		System.out.println(fmt);
	}
		
	
	@Override
	public String toString() {
		//https://www.oracle.com/java/technologies/javase/jdk21-suported-locales.html
		Locale currentLocale = Locale.getDefault();
		if(currentLocale.toLanguageTag().equals("it-IT")) { //Italian(Italy)
			switch(this) {
				case FRUITS:
					return "Frutta";
				case VEGETABLES:
					return "Vegetali";
				case BAKERY:
					return "Pane";
				case BEVERAGE:
					return "Bevande";
				case DAIRY:
					return "Latticini";
				case FISH:
					return "Pesce";
				case HEALTHCARE:
					return "Benessere";
				case HOUSEHOLDSUPPLIES:
					return "Generici";
				case MEAT:
					return "Carne";
				case PASTA:
					return "Pasta";
				default:
					return "Sconosciuto";
			}
		}
		return this.name();
	}
}
