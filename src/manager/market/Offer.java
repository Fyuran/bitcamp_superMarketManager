package manager.market;
import java.time.LocalDate;

public class Offer {
	
	
	public Offer(Product product, double discount, LocalDate dateStart, LocalDate dateEnd) {
		this.product = product;
		this.discount = discount;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	
	private Product product;
	private double discount;
	
	private LocalDate dateStart;
	private LocalDate dateEnd;
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public LocalDate getDateStart() {
		return dateStart;
	}
	public void setDateStart(LocalDate dateStart) {
		this.dateStart = dateStart;
	}
	public LocalDate getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(LocalDate dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	@Override
	public String toString() {
		String discountFmt = new String(String.format("%d%%", discount));
		
		return "Offerta{Prodotto = " + product + 
					", Sconto = " + discountFmt +
						", Data inizio = " + dateStart + 
							", Data termine = "+ dateEnd + "}";
	}
	
	
	
}
