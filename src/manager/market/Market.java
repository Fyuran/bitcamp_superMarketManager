package manager.market;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
* A class that executes the purpose of holding product and products in offer data inside of ArrayLists, supports bi-directional menus
* and supports adding, deleting, removing, searching, editing and viewing of both Offer and Product instances
* @author Daniel Camuffo
* @author Mauro Gariazzo
* @see java.util.ArrayList
* @see java.lang.String
* @see java.util.Scanner
* @see java.time.format.DateTimeFormatter
* @see java.time.LocalDate
* @see MenuUI
* @see Product
* @see Offer
* @since 1.0
* @version 1.2
*/
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

	//errorMsgs
	private static final String emptyInputMsg = ANSI_RED + "*Non hai inserito nulla*" + ANSI_RESET;
	
	private static final String noItemsInListMsg = ANSI_RED + "*Lista vuota*" + ANSI_RESET;
	private static final String invalidCategoryMsg = ANSI_RED + "*Inserisci una categoria valida*" + ANSI_RESET;
	
	private static final String productNotFoundMsg = ANSI_RED + "*Prodotto non trovato*" + ANSI_RESET;
	
	private static final String offerNotFoundMsg = ANSI_RED + "*Offerta non trovata*" + ANSI_RESET;
	
	private static final String invalidInputNumberMsg = ANSI_RED + "*Numero non valido*" + ANSI_RESET;
	private static final String invalidCurrencyFormatMsg = ANSI_RED + "*Inserire un prezzo valido*" + ANSI_RESET;
	
	//confirmMsgs
	private static final String productAddedMsg =  ANSI_GREEN + "Prodotto aggiunto" + ANSI_RESET;
	private static final String productDeletedMsg = ANSI_GREEN + "Prodotto cancellato" + ANSI_RESET;
	private static final String productModifiedMsg = ANSI_GREEN + "Prodotto modificato" + ANSI_RESET;
	
	private static final String offerAddedMsg =  ANSI_GREEN + "Offerta aggiunta" + ANSI_RESET;
	private static final String offerDeletedMsg = ANSI_GREEN + "Offerta cancellata" + ANSI_RESET;
	private static final String offerModifiedMsg = ANSI_GREEN + "Offerta modificata" + ANSI_RESET;
	
	public static void main(String[] args) {
		Market market;
		if(args.length > 0) {
			market = new Market("Supermercato " + args[0]);
		} else {
			market = new Market("Supermercato");
		}
			
		System.out.println(market.name);

		MenuUI productsMenu = new MenuUI("Menu Prodotti");
		productsMenu.addCmd("Aggiungi Prodotto", () -> market.addProduct(false));
		productsMenu.addCmd("Modifica Prodotto", () -> market.editProduct());
		productsMenu.addCmd("Rimuovi Prodotto", () -> market.delProduct());
		productsMenu.addCmd("Cerca Prodotto", () -> market.findProduct());
		productsMenu.addCmd("Visualizza Prodotti", () -> Market.viewItems(market.products));

		MenuUI offersMenu = new MenuUI("Menu Offerte");
		offersMenu.addCmd("Aggiungi Offerta", () -> market.addOffer(false));
		offersMenu.addCmd("Modifica Offerta", () -> market.editOffer());
		offersMenu.addCmd("Rimuovi Offerta", () -> market.delOffer());
		offersMenu.addCmd("Visualizza Offerte", () -> Market.viewItems(market.offers));
		offersMenu.addCmd("Cerca Offerta", () -> market.findOffer());

		MenuUI mainMenu = new MenuUI("Menu principale");
		mainMenu.addCmd("Menu Prodotti", () -> productsMenu.showCmds());
		mainMenu.addCmd("Menu Offerte", () -> offersMenu.showCmds());

		mainMenu.showCmds();

		System.out.println(ANSI_YELLOW + "Arrivederci" + ANSI_RESET);
	}

	Market(String name) {
		this.name = name;
		offers = new ArrayList<>();
		products = new ArrayList<>();

	}

	/**
	 * View items in list's items
	 * @param <T> Product or Offer
	 * @param <L> List of offers or products
	 * @param list
	 * @since 1.2
	 */
	public static <T extends Item, L extends List<T>> void viewItems(L list) {
		if(list.size() == 0) {
			System.out.println(noItemsInListMsg);
			return;
		}

		for(int i = 0; i < list.size(); i++) {
			System.out.println(i + ". " + list.get(i));
		}
	}
	
	/**
	 * @param <T> Product or Offer
	 * @param <L> List of offers or products
	 * @param name String to find
	 * @param list
	 * @return indexOf item
	 * @since 1.2
	 */
	public static <T extends Item, L extends List<T>> int getItemIndexByName(String name, L list) {
		if(list.size() == 0) return -1;
		
		for(int i = 0; i < list.size(); i++) {
			Item item = list.get(i);
			if(item instanceof Product) {
				Product product = (Product) item;
				if(product.getName().equalsIgnoreCase(name))					
					return i;
			}
			if(item instanceof Offer) {
				Offer offer = (Offer) item;
				if(offer.getProduct().getName().equalsIgnoreCase(name))
					return i;
			}
		}
		
		return -1;
	}

	/**
	 * @param <T> Product or Offer
	 * @param <L> List of offers or products
	 * @param name String to find
	 * @param list
	 * @return If item is inside list
	 * @since 1.2
	 */
	public static <T extends Item, L extends List<T>> boolean itemExists(String name, L list) {
		if(list.size() == 0) return false;
		
		for(Item item : list) {
			if(item instanceof Product) {
				Product product = (Product) item;
				if(product.getName().equalsIgnoreCase(name))					
					return true;
			}
			if(item instanceof Offer) {
				Offer offer = (Offer) item;
				if(offer.getProduct().getName().equalsIgnoreCase(name))
					return true;
			}
		}
		return false;
	}

	/**
	 * @param <T> Product or Offer
	 * @param <L> List of offers or products
	 * @param index index of item
	 * @param list
	 * @return If item is inside list
	 * @since 1.2
	 */
	public static <T extends Item, L extends List<T>> boolean itemExists(int index, L list) {
		if(list.size() == 0) return false;
		
		try {
			list.get(index);
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	
	private static <T extends Item, L extends List<T>> String getItemDisplayName(L list) {
		if(list.size() == 0) return null;
		
		try {
			T firstItem = list.getFirst();
			
			if(firstItem instanceof Product)
				return "del prodotto";
			
			if(firstItem instanceof Offer)
				return "dell'offerta";
			
		} catch(NoSuchElementException e) {
			return null;
		}
		return null;
	}

	/**
	 * @param <T> Product or Offer
	 * @param <L> List of offers or products
	 * @param list
	 * @return Object T
	 * @since 1.2
	 */
	public static <T extends Item, L extends List<T>> T getItemByName(L list) {
		if(list.size() == 0) {
			System.out.println(noItemsInListMsg);
			return null;
		};
		
		System.out.println("Inserisci il nome " + getItemDisplayName(list));
		String name = scanner.nextLine();
		try {
			T obj = list.get(getItemIndexByName(name, list));
			return obj;
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * @param <T> Product or Offer
	 * @param <L> List of offers or products
	 * @param list
	 * @return Object T
	 * @since 1.2
	 */
	public static <T extends Item, L extends List<T>> T getItemByIndex(L list) {
		if(list.size() == 0) {
			System.out.println(noItemsInListMsg);
			return null;
		};
		
		System.out.println("Inserisci l'indice del " + getItemDisplayName(list));
		while(true) {
			if(scanner.hasNextInt()) {
				int index = scanner.nextInt();
				scanner.nextLine();
				
				if(itemExists(index, list))
					return list.get(index);
				else
					break;
			} else {
				System.out.println(invalidInputNumberMsg);
			}
		}
		return null;
	}
	/**
	* Iterates through Enum constants to find desired constant index
	* @param enumeration Class name of enumeration to search through
	* @param index Which constant to return from enumeration
	* @param <E> Any enum
	* @return Enum's constant
	* @since 1.0
	*/
	public static <E extends Enum<E>> E getEnumConstant(Class<E> enumeration, int index) {
		E[] enums = enumeration.getEnumConstants();
		for (E enum1 : enums) {
			if(enums[index].equals(enum1)) {
				return enum1;
			}
		}

		return null;
	}

	//-------------------------------------------------------------------------------

	/**
	* Adds or if isEdit is true, edits an existing item
	* @param isEdit If it's a case of editing an existing item or not
	* @return Product with user inputed values
	* @since 1.0
	*/
	public Product addProduct(boolean isEdit) {
		System.out.println("\nInserisci il nome del prodotto: ");
		String name = scanner.nextLine();
		
		if(name.isBlank()) {
			System.out.println(emptyInputMsg);
			return null;
		}
		if(!isEdit) { //do not return if we need to edit item
			//check if product already exists
			int indexOfProduct = getItemIndexByName(name, products);
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

				if(indexOfCategory >= 1 &&  indexOfCategory <= Category.size()) {
					break;
				}
			}
			System.out.println(invalidCategoryMsg);
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
			System.out.println(invalidCurrencyFormatMsg);
			scanner.nextLine();
		}

		Product product = new Product(name, getEnumConstant(Category.class, indexOfCategory-1), price);

		if(!isEdit) { //do not edit if we need to edit item
			products.add(product);
			System.out.println(ANSI_GREEN + "Prodotto aggiunto: " + product + ANSI_RESET);
		}
		return product;
	}
	/**
	* Removes an existing item
	* @since 1.0
	*/
	public void delProduct() {
		MenuUI choiceMenu = new MenuUI("Cerca il prodotto");

		choiceMenu.addCmd("Cancella per nome", () -> {
			System.out.println("Inserisci il nome del prodotto da cancellare");
			String name = scanner.nextLine();
			try {
				products.remove(products.get(getItemIndexByName(name, products)));

				System.out.println(productDeletedMsg);
			} catch (IndexOutOfBoundsException e) {
				System.out.println(offerNotFoundMsg);
			}
		});
		choiceMenu.addCmd("Cancella per indice", () -> {
			System.out.println("Inserisci l'indice del prodotto dell'offerta da cancellare");
			int index = -1;
			if(scanner.hasNextInt()) {
				index = scanner.nextInt();
				scanner.nextLine();

				try {
					products.remove(index);
					System.out.println(productDeletedMsg);
				} catch (IndexOutOfBoundsException e) {
					System.out.println(offerNotFoundMsg);
				}
			} else {
	        	System.out.println(invalidInputNumberMsg);
	        	scanner.nextLine();
			}
		});
		choiceMenu.showCmds();
	}
	/**
	* Views items
	* @since 1.0
	*/
	public void viewProducts() {
		if(products.size() < 1) {
			System.out.println(ANSI_RED + "Nessun prodotto da visualizzare" + ANSI_RESET);
			return;
		}

		for(int i = 0; i < products.size(); i++) {
			 System.out.println(i + ". "  + products.get(i));
		}
	}
	/**
	* Edits an item
	* @since 1.0
	*/
	public void editProduct() {
		MenuUI choiceMenu = new MenuUI("Cerca il prodotto");
		choiceMenu.addCmd("Modifica per nome", () -> {
			System.out.println("Inserisci il nome del prodotto che vuoi modificare");
			String name = scanner.nextLine();
			int indexOfProduct = getItemIndexByName(name, products);

			if(indexOfProduct != -1) {
				Product product = addProduct(true);
				products.set(indexOfProduct, product);
				System.out.println(productModifiedMsg);
			}

			else {
				System.out.println(productNotFoundMsg);
			}
		});
		choiceMenu.addCmd("Modifica per indice", () -> {
			System.out.println("Inserisci l'indice del prodotto da modificare");
			int index = -1;
			if(scanner.hasNextInt()) {
				index = scanner.nextInt();
				scanner.nextLine();

				if(index != -1) {
					products.get(index);
					Product product = addProduct(true);
					products.set(index, product);
					System.out.println(productModifiedMsg);
				}

				else {
					System.out.println(productNotFoundMsg);
				}

			} else {
	        	System.out.println(invalidInputNumberMsg);
	        	scanner.nextLine();
			}
		});
		choiceMenu.showCmds();
	}
	/**
	* Finds an item
	* @since 1.0
	*/
	public void findProduct() {
		MenuUI choiceMenu = new MenuUI("Cerca il prodotto");
		choiceMenu.addCmd("Trova per nome prodotto", () -> {
			System.out.println("Inserisci il nome del prodotto che vuoi cercare");
			String name = scanner.nextLine();
			int indexOfProduct = getItemIndexByName(name, products);

			if(indexOfProduct != -1) {
				System.out.println(ANSI_GREEN + products.get(indexOfProduct) + ANSI_RESET);
			}

			else {
				System.out.println(productNotFoundMsg);
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
					System.out.println(productNotFoundMsg);
				}

			} else {
	        	System.out.println(invalidInputNumberMsg);
	        	scanner.nextLine(); //advance buffer
			}
		});
		choiceMenu.showCmds();
	}

	//-------------------------------------------------------------------------------
	//Offer(Product product, double discount, LocalDate dateStart, LocalDate dateEnd)
	/**
	* Adds or if isEdit is true, edits an existing item
	* @param isEdit If it's a case of editing an existing item or not
	* @return Offer with user inputed values
	* @since 1.0
	*/
	public Offer addOffer(boolean isEdit) {
		Product product = addProduct(isEdit);
		if(product == null) {
			return null;
		}
		if(products.contains(product)) { //remove from products arrayList to deny clones
			products.remove(product);
		}

		//Discount input
		double discount = 0;
		while(true) {
			System.out.println("Inserisci uno sconto da 1% a 99%:");

			if(scanner.hasNextDouble()) {
				discount = scanner.nextDouble();
				scanner.nextLine(); //advance buffer
				if(discount >= 1 && discount < 100) {
					break;
				} else {
					System.out.println(ANSI_RED + "*Inserisci uno sconto valido*" + ANSI_RESET);
				}
			} else {
	        	System.out.println(invalidInputNumberMsg);
	        	scanner.nextLine(); //advance buffer
			}
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
				if(dateStart.isBefore(LocalDate.now())) { //check if startDate is before than now()
					throw new DateTimeParseException("La data di inizio è antecedente alla corrente", date, 5);
				}

				System.out.println("Inserisci la data di termine nel formato " + datePattern);
				date = scanner.nextLine();
				dateEnd = LocalDate.parse(date, dateFmt);
				if(dateEnd.isBefore(dateStart)) { //check if endDate is before than dateStart
					throw new DateTimeParseException("La data di termine è antecedente alla corrente", date, 5);
				}

				isValidInput = true;
			}catch (Exception e) {
				System.out.println(ANSI_RED + "*Inserisci una data valida* " + e.getMessage() + ANSI_RESET);
			}
		}

		Offer offer = new Offer(product, discount, dateStart, dateEnd);
		if(!isEdit) { //do not edit if we need to edit item
			offers.add(offer);
			System.out.println(ANSI_GREEN + "Offerta aggiunta: " + offer + ANSI_RESET);
		}

		return offer;

	}
	/**
	* Edits an item
	* @since 1.0
	*/
	public void editOffer() {
		MenuUI choiceMenu = new MenuUI("Scelta Opzione");

		choiceMenu.addCmd("Modifica per nome", () -> {
			Offer offer = getItemByName(offers);
			if(offer != null) {
				Offer newOffer = addOffer(true);
				offers.set(offers.indexOf(offer), newOffer);
				System.out.println(offerModifiedMsg);
			} else {
				System.out.println(offerNotFoundMsg);
			}
		});

		choiceMenu.addCmd("Modifica per indice", () -> {
			Offer offer = getItemByIndex(offers);
			if(offer != null) {
				Offer newOffer = addOffer(true);
				offers.set(offers.indexOf(offer), newOffer);
				System.out.println(offerModifiedMsg);
			} else {
				System.out.println(offerNotFoundMsg);
			}
				
		});

		choiceMenu.showCmds();
	}
	/**
	* Deletes an item
	* @since 1.0
	*/
	public void delOffer() {
		MenuUI choiceMenu = new MenuUI("Scelta Opzione");

		choiceMenu.addCmd("Cancella per nome", () -> {
			Offer offer = getItemByName(offers);
			if(offer != null) {
				offers.remove(offer);
				System.out.println(offerDeletedMsg);
			} else {
				System.out.println(offerNotFoundMsg);
			}
		});

		choiceMenu.addCmd("Cancella per indice", () -> {
			Offer offer = getItemByIndex(offers);
			if(offer != null) {
				offers.remove(offer);
				System.out.println(offerDeletedMsg);
			} else {
				System.out.println(offerNotFoundMsg);
			}
		});

		choiceMenu.showCmds();

	}
	/**
	* Finds an item
	* @since 1.0
	*/
	public void findOffer() {
		MenuUI choiceMenu = new MenuUI("Scelta Opzione");

		choiceMenu.addCmd("Trova per nome", () -> {
			Offer offer = getItemByName(offers);
			System.out.println(offer != null ? offer : offerNotFoundMsg);
		});
		choiceMenu.addCmd("Trova per indice", () -> {
			Offer offer = getItemByIndex(offers);
			System.out.println(offer != null ? offer : offerNotFoundMsg);
		});

		choiceMenu.showCmds();
	}
}
