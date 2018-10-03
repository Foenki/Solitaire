import java.io.IOException;
import java.util.Scanner;

public class Lanceur {
	
	/* La methode principale est solveLargeur. En revanche, les autres methodes (solveMulti et solveProfondeur) sont implementees et peuvent etre testees en enlevant les balises
	 * de commentaire des lignes correspondantes.
	 * SolveMulti: Resolution en profondeur avec multithreading (modifier nombre iterations).
	 * SolveProfondeur: Resolution en profondeur sans multithreading.
	 * SolveLargeur: Resolution en largeur sans multithreading.
	 */

	public static void main(String[] args) throws InterruptedException, IOException{
		Scanner UserInput= new Scanner(System.in);
		System.out.println("Choisir un solitaire: ");
		String nom= "data/boards/" + UserInput.nextLine() + ".sol";
		System.out.println("Choisir un nombre d'iteration: ");
		int iteration= UserInput.nextInt();
		System.out.println("Choisir un pas de depart (valeur recommandee: 20): ");
		int pas= UserInput.nextInt();
		System.out.println("Choisir une taille de faisceau de depart (valeur minimale recommandee: 10000): ");
		int faisceau= UserInput.nextInt();
		UserInput.close();
		Solitaire s= new Solitaire(nom);
		s.solveLargeur(iteration ,pas, faisceau);
		//s.solveMulti(iteration, pas, faisceau);
		//s.solveProfondeur(iteration, pas, faisceau);
	}
	
}