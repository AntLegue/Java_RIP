import java.io.IOException;
import java.io.PrintStream;

public class CommandExecutor {
	
	public static boolean userGood = false ;
	public static boolean pwGood = false ;
	
	public static void executeCommande(PrintStream ps, String commande) throws IOException {
		if(userGood && pwGood) {
			// Permet de changer de répertoire (cd .. permet de revenir en arrière)
			if(commande.split(" ")[0].equals("cd")) (new CommandeCD(ps, commande)).execute();
	
			// Utilisé pour télécharger un fichier sur le client
			if(commande.split(" ")[0].equals("get")) (new CommandeGET(ps, commande)).execute();
			
			// Liste tous les dossiers, fichiers présents 
			if(commande.split(" ")[0].equals("ls")) (new CommandeLS(ps, commande)).execute();
		
			// Afficher le repertoire courant
			if(commande.split(" ")[0].equals("pwd")) (new CommandePWD(ps, commande)).execute();
			
			// Permet d'envoyer un fichier sur le serveur depuis le client
			if(commande.split(" ")[0].equals("stor")) (new CommandeSTOR(ps, commande)).execute();
							}
		else {
			if(commande.split(" ")[0].equals("pass") || commande.split(" ")[0].equals("user")) {
				// Pour le mot de passe à dès l'authentification
				if(commande.split(" ")[0].equals("pass")) (new CommandePASS(ps, commande)).execute();
	
				// Pour le nom d'utilisateur dès l'authentification
				if(commande.split(" ")[0].equals("user")) (new CommandeUSER(ps, commande)).execute();
			}
			else
				ps.println("2 : Erreur, Vous n'êtes pas connecté !");
		}
	}

}
