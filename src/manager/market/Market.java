package manager.market;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Market {

	private String name;
	private List<Offer> offers;
	private List<Product> products;
	
	//Console text colors
	private static final String ANSI_RESET;
	private static final String ANSI_RED;
	private static final String ANSI_GREEN;
	private static final String ANSI_YELLOW;
	private static final String ANSI_CYAN;
	
	private static Scanner scanner;
	private final static DateTimeFormatter dateFmt;
	private final static String datePattern;
	
	static {
		scanner = new Scanner(System.in);
		datePattern = "dd/MM/yyyy";
		dateFmt = DateTimeFormatter.ofPattern(datePattern);
		
		ANSI_RESET = "\u001B[0m";
		ANSI_RED = "\u001B[31m";
		ANSI_GREEN = "\u001B[32m";
		ANSI_YELLOW = "\u001B[33m";
		ANSI_CYAN = "\u001B[36m";
	}
	
	public static void main(String[] args) {
		Market market = new Market("Supermercato Test");
		System.out.println(market.name);
		
		MenuUI productsMenu = new MenuUI("Menu Prodotti");
		productsMenu.addCmd("Aggiungi Prodotto", ()->{market.addProduct();});
		//productsMenu.addCmd("Rimuovi Prodotto", ()->{market.delProduct();});
		productsMenu.addCmd("Visualizza Prodotti", ()->{market.viewProducts();});
		productsMenu.addCmd("Cerca Prodotto", ()->{market.findProduct();});
		
		MenuUI offersMenu = new MenuUI("Menu Offerte");
		offersMenu.addCmd("Aggiungi Offerta", ()->{market.addOffer();});
		offersMenu.addCmd("Rimuovi Offerta", ()->{market.delOffer();});
		offersMenu.addCmd("Visualizza Offerte", ()->{market.viewOffers();});
		offersMenu.addCmd("Cerca Offerta", ()->{market.findOffer();});
		
		MenuUI mainMenu = new MenuUI("Menu principale");
		mainMenu.addCmd("Menu Prodotti", () -> {productsMenu.showCmds();});
		mainMenu.addCmd("Menu Offerte", () -> {offersMenu.showCmds();});
		
		mainMenu.showCmds();
		
		System.out.println(ANSI_YELLOW + "Arrivederci" + ANSI_RESET);
	}
	
	Market(String name) {
		this.name = name;
		offers = new ArrayList<Offer>();
		products = new ArrayList<Product>();
		
	}
	
	
	public int findByName(String name) { //search indexOf element by product's String name
		for(int i = 0; i < products.size(); i++)
			if(products.get(i).getName().equals(name)) return i;
		
		for(int i = 0; i < products.size(); i++)
			if(offers.get(i).getProduct().getName().equals(name)) return i;
		
		return -1;
	}
	
	public static <E extends Enum<E>> E getEnumConstant(Class<E> enumeration, int index) {
		E[] enums = enumeration.getEnumConstants();
		for(int i = 0; i < enums.length ; i++)
			if(enums[index].equals(enums[i])) return enums[i];

		return null;
	}
	
	//-------------------------------------------------------------------------------
	//Product(String name, Category category, double price)
	public Product addProduct() { 
		System.out.println("\nInserisci il nome del prodotto: ");
		String name = scanner.next();
		
		//check if product already exists
		int indexOfProduct = findByName(name);
		if(indexOfProduct > -1) {
			System.out.println(ANSI_CYAN + "Prodotto con lo stesso nome trovato" + ANSI_RESET);
			return products.get(indexOfProduct);
		}
		
			
		//Category input
		int indexOfCategory = -1;
		while(true) {
			System.out.println(ANSI_GREEN + "--=Categorie Prodotti=--" + ANSI_RESET);
			Category.getCategoriesList(); //list starting from 1 to Category.size()
			System.out.println("\nInserisci la categoria del prodotto:");
			
			if(scanner.hasNextInt()) {
				indexOfCategory = scanner.nextInt();
				
				if(indexOfCategory >= 1 &&  indexOfCategory < Category.size())
					break;
			}
			System.out.println(ANSI_RED + "*Inserisci una categoria valida*" + ANSI_RESET);
			scanner.next();
		}
		
		//Price input
		double price = 0;
		while(true) {
			System.out.println("Inserisci Prezzo:");
			
			if(scanner.hasNextDouble()) {
				price = scanner.nextDouble();
				break;
			}
			System.out.println(ANSI_RED + "*Inserisci un prezzo valido*" + ANSI_RESET);
			scanner.next();
		}
		
		Product product = new Product(name, getEnumConstant(Category.class, indexOfCategory-1), price);	
		products.add(product);
		System.out.println(ANSI_GREEN + "Prodotto aggiunto: " + product + ANSI_RESET);
		
		return product;
	}
	
	public void delProduct(Product product) {
		if(!products.contains(product)) {
			System.out.println("Prodotto non trovato");
			return;
		}		
		products.remove(product);
		System.out.println("Prodotto rimosso con successo");
	}
	public void viewProducts() {
		
	}
	public void editProduct(Product newProduct, Product productToSubstitute) {					
		if(!products.contains(productToSubstitute)) {
			System.out.println("Prodotto non trovato");
			return;
		}		
		products.set(Product.getId(), newProduct);
		System.out.println("Prodotto modificato con successo");
	}
	public void findProduct() {
		
	}
	
	//-------------------------------------------------------------------------------
	//Offer(Product product, double discount, LocalDate dateStart, LocalDate dateEnd)
	public Offer addOffer() {
		Product product = addProduct();
		
		//Discount input
		double discount = 0;
		while(true) {
			System.out.println("Inserisci uno sconto da 1% a 99%:");
			
			if(scanner.hasNextDouble()) {
				discount = scanner.nextDouble();
				if(discount >= 1 && discount <= 99)
					break;
			}
			System.out.println(ANSI_RED + "*Inserisci uno sconto valido*" + ANSI_RESET);
			scanner.next();
		}
		
		//dateEnd input
		LocalDate dateNow = LocalDate.now();
		LocalDate dateStart = LocalDate.EPOCH;
		LocalDate dateEnd = LocalDate.EPOCH;
		
		boolean isValidInput = false;
		while(!isValidInput) {

			try {			
				System.out.println("Inserisci la data di inizio nel formato " + datePattern);
				String date = scanner.nextLine();	
				dateStart = LocalDate.parse(scanner.next(), dateFmt);	
				if(dateStart.isBefore(dateNow)) //check if startDate is before than now()
					throw new DateTimeParseException("La data di inizio è antecedente alla corrente", date, 5);
				
				System.out.println("Inserisci la data di termine nel formato " + datePattern);
				date = scanner.nextLine();
				dateEnd = LocalDate.parse(date, dateFmt);	
				if(dateEnd.isBefore(dateNow)) //check if endDate is before than now()
					throw new DateTimeParseException("La data di termine è antecedente alla corrente", date, 5);
				
				isValidInput = true;
			}catch (Exception e) {
				System.out.println(ANSI_RED + "*Inserisci una data valida* " + e.getMessage() + ANSI_RESET);
			}
		}
		
		Offer offer = new Offer(product, discount, dateStart, dateEnd);
		offers.add(offer);
		System.out.println(ANSI_GREEN + "Offerta aggiunta: " + offer + ANSI_RESET);
		
		return offer;
		
	}


	public void delOffer() {
		
	}
	public void viewOffers() {
		
	}
	public void findOffer() {

	}
	
}
