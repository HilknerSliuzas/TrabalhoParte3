package pt.c02classes.s01knowledge.s02app.app;

import java.util.Scanner;
import pt.c02classes.s01knowledge.s02app.app.OrchestratorInteractive;
import pt.c02classes.s01knowledge.s02app.app.Orchestrator;

public class OrquestratorInit {
	
	static void escolhedesafio () {
		System.out.println("Escolha o desafio:(A)nimals ou (M)aze");
		
		Scanner scanner = new Scanner(System.in);
		
		String Desafio = scanner.nextLine();
		
		while (!(Desafio.equalsIgnoreCase("A")) && !(Desafio.equalsIgnoreCase("M"))) {
			System.out.println("Desafio invalido, digite novamente.");
			Desafio = scanner.nextLine();
		}
		
		if (Desafio.equalsIgnoreCase("A"))
		    Orchestrator.main(null);
		else if (Desafio.equalsIgnoreCase("M"))
			OrchestratorInteractive.main(null);
		
		scanner.close();
	}
	
	public static void main(String[] args) {
		OrquestratorInit.escolhedesafio();
	}
}