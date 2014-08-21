package nl.concipit.webstore.exception;


public class InvalidCartException extends RuntimeException {

	private static final long serialVersionUID = 4653583527418129296L;

	private String cartId;
	
	public InvalidCartException(String cartId) {
		this.cartId = cartId;
	}
	
	public String getCartId() {
		return cartId;
	}
}
