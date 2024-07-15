package manager.market;

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
	
	public String getLocalized(String locale) {
		if(locale.equals("it-IT")) {
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
		return "";
	}
}
