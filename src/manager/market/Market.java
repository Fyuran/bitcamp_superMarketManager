package manager.market;
import java.util.ArrayList;

public class Market {	 
	private ArrayList<Product> products;
	
	public Market(ArrayList<Product> products) {
		this.products = products;
	}
	
	
	public void addProduct(Product product) {
		if(!products.contains(product)) {
			products.add(product);
		}
	}
	
	
	public void editProduct(Product newProduct, Product productToSubstitute) 
	{					
		if(!products.contains(productToSubstitute)) {
			System.out.println("Prodotto non trovato");
			return;
		}		
		products.set(productToSubstitute.getId(), newProduct);
		System.out.println("Prodotto modificato con successo");
	}
	
	public void deleteProduct(Product productToDelete) {
		if(!products.contains(productToDelete)) {
			System.out.println("Prodotto non trovato");
			return;
		}		
		products.remove(productToDelete);
		System.out.println("Prodotto rimosso con successo");
	}
	
	public ArrayList<Product> getAllProducts(){
		return products;
	}
	
}
