package manager.market;

/**
* Used internally in MenuUI to hold a variable ArrayList of commands
*
* @author Daniel Camuffo
* @see MenuUI
* @see Callback
* @since 1.0
*/
public record Command(String name, Callback fnc){

	public void execute() {
		this.fnc.call();
	}
}
