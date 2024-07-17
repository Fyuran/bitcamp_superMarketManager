package manager.market;

public class NoCommandsAvailableException extends RuntimeException {

	private static final long serialVersionUID = -6835078274576580078L;

	public NoCommandsAvailableException() {
        super("No commands available");
    }

}
