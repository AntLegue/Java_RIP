import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute()
	{
		if(commandeArgs.length > 0)
		{
			// On r�cup�re l'�l�ment
			String elementRecup = commandeArgs[0];
			
			// Si on tape ".." alors on retourne au r�pertoire principal
			if(elementRecup.equals(".."))
			{
				Main.currentDirectory = Main.currentDirectory.getParentFile();
				ps.println("0 : Le r�pertoire a chang� !");
				return;
			}

			// On cr�� l'objet r�pertoire
			File repertoire;

			// Parcours de l'ensemble des noms des �l�ments du r�pertoire et on v�rifie qu'il contient 
			for(String element : Main.currentDirectory.list())
			{
			
				if(elementRecup.equals(element))
				{
					//On transforme l'element en objet File pour utiliser la fonction isDirectory()
					repertoire = new File(Main.currentDirectory.getAbsolutePath() + "\\" + element);
					
					
					// Si l'�l�ment est bien un dossier 
					if(repertoire.isDirectory())
					{
						// Par cons�quent on change de r�pertoire
						Main.currentDirectory = repertoire;
						ps.println("0 : Le r�pertoire a chang� ! ");
						return;
					}
					
					// Sinon on affiche un message
					else
					{
						ps.println("2 : L'�l�ment pr�sent n'est pas un dossier !");
						return;
					}
				}
			}
			// On pr�cise le nom de l'�l�ment inexistant
			ps.println("2 : Il n'y a aucun �l�ment au nom de : " + elementRecup);
		}
		else
		{
			ps.println("2 : Il manque un argument pour l'utilisation de la commande 'cd'");
		}
	}

}
