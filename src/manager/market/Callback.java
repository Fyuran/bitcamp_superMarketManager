package manager.market;

/**
* Used as a callback functionality in MenuUI
*
* @author Daniel Camuffo
* @see MenuUI
* @since 1.0
*/
@FunctionalInterface
public interface Callback {
	abstract void call();
}
