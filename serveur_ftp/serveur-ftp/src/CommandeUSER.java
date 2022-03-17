import java.io.PrintStream;
import java.io.File;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute()
	{
		if(commandeArgs.length > 0)
		{
			// Récupération du nom d'utilisateur reçu par le client
			String utilsRecu = commandeArgs[0].toLowerCase();
			
			// J'utilise la fonction File pour créer la liste d'utilisateurs
			File listUtils = new File("Users");
			
			String[] Utils = listUtils.list();
			
			for(int i = 0; i < Utils.length ; i++)
			{
				
				// Si on a un utilisateur alors
				if(Utils[i].equals(utilsRecu))
				{
					System.out.println(utilsRecu);
					Main.utils = utilsRecu;
					CommandExecutor.userGood = true;
					ps.println("0 : La commande 'user' est bonne ");
					return;
				}
			}
			ps.println("2 : L'utilisateur n'existe pas");
		}
		else
		{
			ps.println("2 : Il manque un argument pour la commande 'user' ! ");
		}
	}

}
