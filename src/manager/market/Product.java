package manager.market;

public class Product {
	private static int id;
	private String name;
	private Category category;
	private double price;
	
	static {
		id = 0;
	}
	
	
	public Product(String name, Category category, double price) {
		this.name = name;
		this.category = category;
		this.price = price;
		
		id++;
	}
	
	public void delete() {
		id--;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Product.id = id;
	}

	@Override
	public String toString() {
		return String.format("Prodotto{Nome: %s, Categoria: %s, Prezzo: %.2f€ }", name,category,price);		
	}

}
