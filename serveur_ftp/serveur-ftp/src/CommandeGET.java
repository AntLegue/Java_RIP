import java.io.PrintStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {
	
	public CommandeGET(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() throws IOException
	{
		if(commandeArgs.length > 0)
		{
			// On récupère le nom du fichier que nous allons envoyer
			File cibleFichierRecup;

			
			// Si le fichier existe et on vérifie si l'élément existe
			for(String element : Main.currentDirectory.list())
			{
		
				if(element.equals(commandeArgs[0]))
				{
					
					// Création du fichier cible
					cibleFichierRecup = new File(Main.currentDirectory.getAbsolutePath() + "\\" + element);
					
					// Vérifie si c'est bon un fichier avec la fonction 'isFile'
					if(cibleFichierRecup.isFile())
					{
						ServerSocket sServeurSendFile;
						Socket sSendFile;
						InputStream in;
						OutputStream out;
						try
						{
							// Création du socket pour l'envoi de fichiers
							sServeurSendFile = new ServerSocket(4000);
							
							// Attente de la connexion du client
							sSendFile = sServeurSendFile.accept();
						}
						// Gestion du cas d'échec
						catch (IOException e)
						{
							ps.println("2 : Erreur à la création du socket");
							return;
						}

						// Les flux permettent d'écrire et lire dans le socket (in : lire | out : écrire)
						try
						{
							in = new FileInputStream(cibleFichierRecup);
						}
						catch (IOException ex)
						{
							System.out.println("2 : Erreur, impossible de créer l'imput stream en direction du socket");
							return;
						}
				
						try
						{
							out = sSendFile.getOutputStream();
						}
						catch (FileNotFoundException ex)
						{
							System.out.println("2 : Erreur, le fichier est introuvable");
							return;
						}

						// Création d'un buffer
						byte[] bytes = new byte[16*1024];

						int i;
						
						// On lit dans la socket puis on procède à l'envoi
						while ((i = in.read(bytes)) > 0)
						{
							out.write(bytes, 0, i);
						}
						System.out.println("Le transfert est terminé");

						try
						{
							out.close();
							in.close();
							sSendFile.close();
							sServeurSendFile.close();
						}
						
						// Gestion d'erreur pour les sockets (lors de la fermeture)
						catch (IOException e)
						{
							System.out.println("2  : Erreur, à la fermeture des différents sockets");
							return;
						}
						return;
					}
					else
					{
						ps.println("2 : Erreur, l'élément n'est pas un fichier");
						return;
					}
				}
			}
			ps.println("0 : Le fichier : " + commandeArgs[0] + " n'est pas présent sur le système.");
		}
		else
		{
			ps.println("2 : Il manque un argument pour la commande 'get' ");
		}
	}
}
