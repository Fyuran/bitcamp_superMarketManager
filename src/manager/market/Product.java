package manager.market;

public class Product {
	private String name;
	private Category category;
	private double price;

	
	public Product(String name, Category category, double price) {
		this.name = name;
		this.category = category;
		this.price = price;
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

	@Override
	public String toString() {
		return String.format("{Nome: %s, Categoria: %s, Prezzo: %.2f€}", name,category,price);		
	}

}
