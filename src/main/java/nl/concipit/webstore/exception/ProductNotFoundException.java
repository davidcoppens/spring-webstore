package nl.concipit.webstore.exception;


public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4653583527418129296L;

	private String productId;
	
	public ProductNotFoundException(String productId) {
		this.productId = productId;
	}
	
	public String getProductId() {
		return productId;
	}
}
