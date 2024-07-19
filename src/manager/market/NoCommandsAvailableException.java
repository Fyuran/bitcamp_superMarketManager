package manager.market;

/**
* An exception for MenuUI class
*
* @author Daniel Camuffo
* @see MenuUI
* @since 1.1
*/
public class NoCommandsAvailableException extends RuntimeException {

	private static final long serialVersionUID = -6835078274576580078L;

	public NoCommandsAvailableException() {
        super("No commands available");
    }

}
