package manager.market;

public class Product {
	private int id;
	private String name;
	private Category category;
	private double price;
	
	public Product(String name, Category category, double price) {
		id++;
		this.name = name;
		this.category = category;
		this.price = price;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return String.format("Prodotto{Nome: %s, Categoria: %s, Prezzo: %f }", name,category,price);		
	}
}
