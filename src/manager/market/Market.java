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
		productsMenu.addCmd("Aggiungi Prodotto", ()->{market.addProduct(false);});
		productsMenu.addCmd("Modifica Prodotto", ()->{market.editProduct();});
		productsMenu.addCmd("Rimuovi Prodotto", ()->{market.delProduct();});
		productsMenu.addCmd("Cerca Prodotto", ()->{market.findProduct();});
		productsMenu.addCmd("Visualizza Prodotti", ()->{market.viewProducts();});
		
		MenuUI offersMenu = new MenuUI("Menu Offerte");
		offersMenu.addCmd("Aggiungi Offerta", ()->{market.addOffer(false);});
		offersMenu.addCmd("Modifica Offerta", ()->{market.editOffer();});
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
	
	
	public int findProductByName(String name) { //search indexOf element by product's String name
		for(int i = 0; i < products.size(); i++)
			if(products.get(i).getName().equals(name)) return i;
		
		return -1;
	}
	public int findOfferByName(String name) { //search indexOf element by offer's product String name	
		for(int i = 0; i < offers.size(); i++)
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
	public Product addProduct(boolean isEdit) { 
		System.out.println("\nInserisci il nome del prodotto: ");
		String name = scanner.nextLine();
		
		if(!isEdit) { //do not return if we need to edit object
			//check if product already exists
			int indexOfProduct = findProductByName(name);
			if(indexOfProduct != -1) {
				System.out.println(ANSI_CYAN + "Prodotto con lo stesso nome trovato" + ANSI_RESET);
				return products.get(indexOfProduct);
			}			
		}
		
			
		//Category input
		int indexOfCategory = -1;
		while(true) {
			System.out.println(ANSI_GREEN + "--=Categorie Prodotti=--" + ANSI_RESET);
			Category.getCategoriesList(); //list starting from 1 to Category.size()
			System.out.println("\nInserisci la categoria del prodotto:");
			
			if(scanner.hasNextInt()) {
				indexOfCategory = scanner.nextInt();
				scanner.nextLine(); //advance buffer
				
				if(indexOfCategory >= 1 &&  indexOfCategory <= Category.size())
					break;
			}
			System.out.println(ANSI_RED + "*Inserisci una categoria valida*" + ANSI_RESET);
			scanner.nextLine();
		}
		
		//Price input
		double price = 0;
		while(true) {
			System.out.println("Inserisci Prezzo:");
			
			if(scanner.hasNextDouble()) {
				price = scanner.nextDouble();
				scanner.nextLine();
				break;
			}
			System.out.println(ANSI_RED + "*Inserisci un prezzo valido*" + ANSI_RESET);
			scanner.nextLine();
		}
		
		Product product = new Product(name, getEnumConstant(Category.class, indexOfCategory-1), price);	
		
		if(!isEdit) { //do not edit if we need to edit object
			products.add(product);
			System.out.println(ANSI_GREEN + "Prodotto aggiunto: " + product + ANSI_RESET);
		}
		return product;
	}
	
	public void delProduct() {
		MenuUI choiceMenu = new MenuUI("Cerca il prodotto");
		
		choiceMenu.addCmd("Trova per nome prodotto", () -> {
			System.out.println("Inserisci il nome del prodotto da cancellare");
			String name = scanner.nextLine();
			try {
				products.remove(products.get(findProductByName(name)));
				
				System.out.println(ANSI_GREEN + "Prodotto cancellato" + ANSI_RESET);
			} catch (IndexOutOfBoundsException e) {
				System.out.println(ANSI_RED + "*Prodotto non esistente*" + ANSI_RESET);
			}
		});
		choiceMenu.addCmd("Trova per ID prodotto", () -> {
			System.out.println("Inserisci l'ID del prodotto dell'offerta da cancellare");
			int index = -1;
			if(scanner.hasNextInt()) {
				index = scanner.nextInt();
				scanner.nextLine();
				
				try {
					products.remove(index);
					System.out.println("Prodotto cancellato");
				} catch (IndexOutOfBoundsException e) {
					System.out.println(ANSI_RED + "*Prodotto non esistente*" + ANSI_RESET);
				}
			} else {
	        	System.out.println(ANSI_RED + "*Inserisci un numero valido*" + ANSI_RESET);
	        	scanner.nextLine();
			}
		});
		choiceMenu.showCmds();
	}
	
	public void viewProducts() {
		if(products.size() < 1) {
			System.out.println(ANSI_RED + "Nessun prodotto da visualizzare" + ANSI_RESET);
			return;
		}
		
		for(int i = 0; i < products.size(); i++) {
			 System.out.println(i + ". "  + products.get(i));
		}
	}
	
	public void editProduct() {					
		MenuUI choiceMenu = new MenuUI("Cerca il prodotto");
		choiceMenu.addCmd("Trova per nome prodotto", () -> {
			System.out.println("Inserisci il nome del prodotto che vuoi modificare");
			String name = scanner.nextLine();
			int indexOfProduct = findProductByName(name);
			
			if(indexOfProduct != -1) {				
				Product product = addProduct(true);
				products.set(indexOfProduct, product);	
				System.out.println(ANSI_GREEN + "Prodotto modificato correttamente" + ANSI_RESET);
			}
			
			else {
				System.out.println(ANSI_RED + "Prodotto non trovato" + ANSI_RESET);
			}			
		});
		choiceMenu.addCmd("Trova per ID prodotto", () -> {
			System.out.println("Inserisci l'ID del prodotto da modificare");
			int index = -1;
			if(scanner.hasNextInt()) {
				index = scanner.nextInt();
				scanner.nextLine();
				
				if(index != -1) {					
					Product product = addProduct(true);
					products.set(index, product);	
					System.out.println(ANSI_GREEN + "Prodotto modificato correttamente" + ANSI_RESET);
				}
				
				else {
					System.out.println(ANSI_RED + "Prodotto non trovato" + ANSI_RESET);
				}
				
			} else {
	        	System.out.println(ANSI_RED + "*Inserisci un numero valido*" + ANSI_RESET);
	        	scanner.nextLine();
			}
		});
		choiceMenu.showCmds();
	}
	
	public void findProduct() {
		MenuUI choiceMenu = new MenuUI("Cerca il prodotto");
		choiceMenu.addCmd("Trova per nome prodotto", () -> {
			System.out.println("Inserisci il nome del prodotto che vuoi cercare");
			String name = scanner.nextLine();
			int indexOfProduct = findProductByName(name);
			
			if(indexOfProduct != -1) {
				System.out.println(ANSI_GREEN + products.get(indexOfProduct) + ANSI_RESET);
			}
			
			else {
				System.out.println(ANSI_RED + "Prodotto non trovato" + ANSI_RESET);
			}			
		});
		choiceMenu.addCmd("Trova per ID prodotto", () -> {
			System.out.println("Inserisci l'ID del prodotto da visualizzare");
			int index = -1;
			if(scanner.hasNextInt()) {
				index = scanner.nextInt();
				scanner.nextLine();
				
				if(index != -1) {
					System.out.println(products.get(index));
				}
				
				else {
					System.out.println(ANSI_RED + "Prodotto non trovato" + ANSI_RESET);
				}
				
			} else {
	        	System.out.println(ANSI_RED + "*Inserisci un numero valido*" + ANSI_RESET);
	        	scanner.nextLine(); //advance buffer
			}
		});
		choiceMenu.showCmds();
	}
	
	//-------------------------------------------------------------------------------
	//Offer(Product product, double discount, LocalDate dateStart, LocalDate dateEnd)
	public Offer addOffer(boolean isEdit) {
		Product product = addProduct(isEdit);
		if(products.contains(product)) //remove from products arrayList to deny clones
			products.remove(product);
		
		//Discount input
		double discount = 0;
		while(true) {
			System.out.println("Inserisci uno sconto da 1% a 99%:");
			
			if(scanner.hasNextDouble()) {
				discount = scanner.nextDouble();
				scanner.nextLine(); //advance buffer
				if(discount >= 1 && discount <= 99)
					break;
			}
			System.out.println(ANSI_RED + "*Inserisci uno sconto valido*" + ANSI_RESET);
			scanner.nextLine();
		}
		
		//dateEnd input
		LocalDate dateStart = LocalDate.now();
		LocalDate dateEnd = LocalDate.EPOCH;
		
		boolean isValidInput = false;
		while(!isValidInput) {

			try {			
				System.out.println("Inserisci la data di inizio nel formato " + datePattern);
				String date = scanner.nextLine();	
				dateStart = LocalDate.parse(date, dateFmt);	
				if(dateStart.isBefore(LocalDate.now())) //check if startDate is before than now()
					throw new DateTimeParseException("La data di inizio è antecedente alla corrente", date, 5);
				
				System.out.println("Inserisci la data di termine nel formato " + datePattern);
				date = scanner.nextLine();
				dateEnd = LocalDate.parse(date, dateFmt);	
				if(dateEnd.isBefore(dateStart)) //check if endDate is before than dateStart
					throw new DateTimeParseException("La data di termine è antecedente alla corrente", date, 5);
				
				isValidInput = true;
			}catch (Exception e) {
				System.out.println(ANSI_RED + "*Inserisci una data valida* " + e.getMessage() + ANSI_RESET);
			}
		}
		
		Offer offer = new Offer(product, discount, dateStart, dateEnd);
		if(!isEdit) { //do not edit if we need to edit object
			offers.add(offer);
			System.out.println(ANSI_GREEN + "Offerta aggiunta: " + offer + ANSI_RESET);
		}
		
		return offer;
		
	}

	public void editOffer() {
		MenuUI choiceMenu = new MenuUI("Scelta Opzione");

		choiceMenu.addCmd("Modifica per nome", () -> {
			System.out.println("Inserisci il nome del prodotto dell'offerta da modificare");
			String name = scanner.nextLine();
			try {
				int indexOfOffer = findOfferByName(name);
				offers.get(indexOfOffer); //throw if not found
				Offer newOffer = addOffer(true);
				offers.set(indexOfOffer, newOffer);
				System.out.println(ANSI_GREEN + "Offerta modificata" + ANSI_RESET);
				
			} catch (IndexOutOfBoundsException e) {
				System.out.println(ANSI_RED + "*Offerta non esistente*" + ANSI_RESET);
			}
		});
		
		choiceMenu.addCmd("Modifica per indice", () -> {
			System.out.println("Inserisci l'indice del prodotto dell'offerta da modificare");
			int index = -1;
			if(scanner.hasNextInt()) {
				index = scanner.nextInt();
				scanner.nextLine(); //advance buffer
				try {
					Offer offer = offers.get(index);
					System.out.println(offer);
				} catch (IndexOutOfBoundsException e) {
					System.out.println(ANSI_RED + "*Offerta non esistente*" + ANSI_RESET);
				}
			} else {
	        	System.out.println(ANSI_RED + "*Inserisci un numero valido*" + ANSI_RESET);
	        	scanner.nextLine(); //advance buffer
			}
		});
		
		choiceMenu.showCmds();
	}
	
	public void delOffer() {
		MenuUI choiceMenu = new MenuUI("Scelta Opzione");
		
		choiceMenu.addCmd("Cancella per nome", () -> {
			System.out.println("Inserisci il nome del prodotto dell'offerta da cancellare");
			String name = scanner.nextLine();
			try {
				offers.remove(findOfferByName(name)); //subtract from id
				System.out.println("Offerta cancellata");
			} catch (IndexOutOfBoundsException e) {
				System.out.println(ANSI_RED + "*Prodotto non esistente*" + ANSI_RESET);
			}
		});
		
		choiceMenu.addCmd("Cancella per indice", () -> {
			System.out.println("Inserisci l'indice del prodotto dell'offerta da cancellare");
			int index = -1;
			if(scanner.hasNextInt()) {
				index = scanner.nextInt();
				scanner.nextLine(); //advance buffer
				try {
					offers.remove(index);
					System.out.println("Offerta cancellata");
				} catch (IndexOutOfBoundsException e) {
					System.out.println(ANSI_RED + "*Prodotto non esistente*" + ANSI_RESET);
				}
			} else {
	        	System.out.println(ANSI_RED + "*Inserisci un numero valido*" + ANSI_RESET);
	        	scanner.nextLine(); //advance buffer
			}
		});
		
		choiceMenu.showCmds();

	}
	
	public void viewOffers() {
		if(offers.size() == 0) {
			System.out.println(ANSI_RED + "*Nessuna offerta trovata*" + ANSI_RESET);
			return;
		}
			
		for(int i = 0; i < offers.size(); i++)
			System.out.println(i + ". " + offers.get(i));		
	}
	
	public void findOffer() {
		MenuUI choiceMenu = new MenuUI("Scelta Opzione");

		choiceMenu.addCmd("Trova per nome", () -> {
			System.out.println("Inserisci il nome del prodotto dell'offerta da trovare");
			String name = scanner.nextLine();
			try {
				Offer offer = offers.get(findOfferByName(name));
				System.out.println(offer);
			} catch (IndexOutOfBoundsException e) {
				System.out.println(ANSI_RED + "*Offerta non esistente*" + ANSI_RESET);
			}
		});
		
		choiceMenu.addCmd("Trova per indice", () -> {
			System.out.println("Inserisci l'indice del prodotto dell'offerta da trovare");
			int index = -1;
			if(scanner.hasNextInt()) {
				index = scanner.nextInt();
				scanner.nextLine(); //advance buffer
				try {
					Offer offer = offers.get(index);
					System.out.println(offer);
				} catch (IndexOutOfBoundsException e) {
					System.out.println(ANSI_RED + "*Offerta non esistente*" + ANSI_RESET);
				}
			} else {
	        	System.out.println(ANSI_RED + "*Inserisci un numero valido*" + ANSI_RESET);
	        	scanner.nextLine(); //advance buffer
			}
		});
		
		choiceMenu.showCmds();
	}
	
	
}
