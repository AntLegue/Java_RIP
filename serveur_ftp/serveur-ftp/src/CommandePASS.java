import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class CommandePASS extends Commande
{
	public CommandePASS(PrintStream ps, String commandeStr)
	{
		super(ps, commandeStr);
	}

	public void execute()
	{
		if(CommandExecutor.userGood == true)
		{
			if(commandeArgs.length > 0)
			{
				// R�cup�ration du mot de passe re�u par le client
				
				// Cr�ation du fichier de l'utilisateur
				File fUtils = new File("Users\\"+ Main.utils + "\\pw.txt");
				
				// Cr�ation de l'objet scan pour lire les diff�rents fichiers
				Scanner fScanRead;
				
				// Si le fichier de l'utilisateur existe et le mot de passe est bon 
				if(fUtils.exists())
				{
					try
					{
						fScanRead = new Scanner(fUtils);
					}
					catch (FileNotFoundException e)
					{
						e.printStackTrace();
						return;
					}
				}
				else
				{
					ps.println("2 : Le fichier de mot de passe n'est pas trouvé");
					return;
				}
				
				// R�cup�ration du mot de passe 
				String fPass = fScanRead.nextLine();
				
				//Fermeture du scanner
				fScanRead.close();

				// Si mot de passe OK 
				if(fPass.equals(commandeArgs[0]))
				{
					CommandExecutor.pwGood = true;
					ps.println("1 : La commande 'pass' est correct");
					ps.println("0 : Bienvenue sur le serveur, vous �tes bien connect�");
				}
				
				// Sinon si mot de passe NOK
				else
				{
					ps.println("2 : Le mot de passe n'est pas correct");
				}
			}
			else
			{
				ps.println("2 : Il manque un argument pour la commande 'pass' ");
			}
		}
		else
		{
			ps.println("2 : Erreur, il n'y a aucun utilisateur de renseign�");
		}
	}

}
