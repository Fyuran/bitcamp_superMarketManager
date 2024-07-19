package manager.market;
import java.time.LocalDate;

/**
* A class holding some shop's offer data plus one instance of Product used in Market class
*
* @author Daniel Camuffo
* @see Product
* @see java.time.LocalDate
* @see java.lang.String
* @since 1.0
*/
public class Offer {

	private Product product;
	private double discount;

	private LocalDate dateStart;
	private LocalDate dateEnd;

	public Offer(Product product, double discount, LocalDate dateStart, LocalDate dateEnd) {
		this.product = product;
		this.discount = discount;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

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
		return "{Prodotto = " + product +
					", Sconto = " + discount + "%" +
						", Data inizio = " + dateStart +
							", Data termine = "+ dateEnd + "}";
	}
}
