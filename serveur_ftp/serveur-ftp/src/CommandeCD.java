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
			// On récupère l'élément
			String elementRecup = commandeArgs[0];
			
			// Si on tape ".." alors on retourne au répertoire principal
			if(elementRecup.equals(".."))
			{
				Main.currentDirectory = Main.currentDirectory.getParentFile();
				ps.println("0 : Le répertoire a changé !");
				return;
			}

			// On créé l'objet répertoire
			File repertoire;

			// Parcours de l'ensemble des noms des éléments du répertoire et on vérifie qu'il contient 
			for(String element : Main.currentDirectory.list())
			{
			
				if(elementRecup.equals(element))
				{
					//On transforme l'element en objet File pour utiliser la fonction isDirectory()
					repertoire = new File(Main.currentDirectory.getAbsolutePath() + "\\" + element);
					
					
					// Si l'élément est bien un dossier 
					if(repertoire.isDirectory())
					{
						// Par conséquent on change de répertoire
						Main.currentDirectory = repertoire;
						ps.println("0 : Le répertoire a changé ! ");
						return;
					}
					
					// Sinon on affiche un message
					else
					{
						ps.println("2 : L'élément présent n'est pas un dossier !");
						return;
					}
				}
			}
			// On précise le nom de l'élément inexistant
			ps.println("2 : Il n'y a aucun élément au nom de : " + elementRecup);
		}
		else
		{
			ps.println("2 : Il manque un argument pour l'utilisation de la commande 'cd'");
		}
	}

}
