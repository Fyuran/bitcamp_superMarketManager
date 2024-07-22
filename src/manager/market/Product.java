package manager.market;

/**
* A class holding some shop's product data
*
*
* @author Mauro Gariazzo
* @see Category
* @see java.lang.String
* @since 1.0
*/
public class Product extends Item{
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
