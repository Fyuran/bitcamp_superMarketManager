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
