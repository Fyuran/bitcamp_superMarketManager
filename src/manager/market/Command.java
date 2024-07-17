package manager.market;

public record Command(String name, Callback fnc){

	public void execute() {
		this.fnc.call();
	}
}
