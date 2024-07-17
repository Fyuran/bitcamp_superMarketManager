package manager.market;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuUI {
	//Console text colors
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_CYAN = "\u001B[36m";

	private List<Command> commands;
	private String name;
	private static Scanner scanner = new Scanner(System.in);
	
	MenuUI(String name) {
		this.name = name;
		commands = new ArrayList<Command>();
	}
	
	public void addCmd(Command cmd) {
		commands.add(cmd);
	}
	public void addCmd(String name, Callback fnc) {
		commands.add(new Command(name, fnc));
	}
	
	public void removeCmd(String name) {
		for(int i = 0; i < commands.size(); i++)
			if(commands.get(i).name().equalsIgnoreCase(name))
				commands.remove(i);
			else
				System.out.println("Could not find " + name);
	}
	
	public void showCmds() throws NoCommandsAvailableException {
		if(commands.size() == 0) {
			throw new NoCommandsAvailableException();
		}
		while(true) {
	        System.out.println(ANSI_CYAN + "-=-=-=-=" + name + "=-=-=-=-" + ANSI_RESET);
	        for(int i = 0; i < commands.size(); i++) {
	        	System.out.println((i+1) + ". " + commands.get(i).name());
	        }
	        System.out.println((commands.size() + 1) + ". Esci");
	        System.out.println("Fai la tua scelta");
	        
	        if(scanner.hasNextInt()) {
	        	int choice = scanner.nextInt();
	        	scanner.nextLine(); //advance buffer
	        	if(choice == (commands.size() + 1)) {
	        		System.out.println(ANSI_CYAN + "Uscito da " + name + ANSI_RESET);
	        		break;
	        	}		        	
	        	try {
	        		Command cmd = commands.get(choice-1);
	        		System.out.println(ANSI_CYAN + "Selezionato: " + cmd.name() + ANSI_RESET);
	        		cmd.execute();
	        	} catch (IndexOutOfBoundsException e) {
	        		System.out.println(ANSI_RED + "*Scelta non valida*" + ANSI_RESET);
	        	}
	        } else {
	        	System.out.println(ANSI_RED + "*Inserisci un numero valido*" + ANSI_RESET);
	        	scanner.nextLine(); //advance buffer
	        }
	        	
		}
	}
}
