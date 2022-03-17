import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client
{
	public static File repertoireCourant = new File(".");
	
	
	// Permettant l'affichage des messages du serveur
	private static void affServMsg(BufferedReader br) throws IOException
	{
		String line;
		
		while(true)
		{
			// Affichage de la ligne puis si réponse équivalent à 1 alors on lit les données
			line = br.readLine();
			System.out.println(line);
			if(line.split(" ")[0].equals("0") || line.split(" ")[0].equals("2"))
			break;
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("[Client FTP]");
		
		// Création du socket pour la connexion au serveur
		Socket socket = new Socket("localhost", 2020);
		
		// Utilisé pour récupérer les infos de connexion
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		affServMsg(br);
		
		// Gère des "Stream" en utilisant des String à la place des bytes et "Scanner" récupère le texte saisi par l'utilisateur
		PrintStream ps = new PrintStream(socket.getOutputStream());
		Scanner fScanRead = new Scanner(System.in);

		String commandeSaisie;
		
		// Tant qu'on a une commande de saisie (excepté bye)
		while(true)
		{
			// Si dans le chat, on saisi "bye" alors fin de communication avec le serveur
			if((commandeSaisie = fScanRead.nextLine()).equals("bye"))
			{
				break;
			}
			// Si commande "get"
			else if(commandeSaisie.split(" ")[0].equals("get"))
			{
				String fName = commandeSaisie.split(" ")[1];
				ps.println(commandeSaisie);

				Thread.currentThread();
				Thread.sleep(1000);

				// Connection au serveur pour l'envoie de donnée du serveur vers le client
				Socket sRecieveFile = new Socket("localhost",4000);
				File fFile = new File(fName);
				fFile.createNewFile();

				//Buffer
				byte[] bytes = new byte[16 * 1024];


				// Stream et de réception et d'écriture du fichier
				InputStream in = sRecieveFile.getInputStream();
				OutputStream out = new FileOutputStream(fFile);
				
				int i;
				
				//Lecuture de la socket et envoi dans le fichier
				while ((i = in.read(bytes)) > 0)
				{
					out.write(bytes, 0, i);
				}

				out.close();
				in.close();
				sRecieveFile.close();
				System.out.println("Succès : Le fichier est bien reçu");

			}
			// Si la commande "Stor" 
			else if(commandeSaisie.split(" ")[0].equals("stor"))
			{
				String fFile2 = commandeSaisie.split(" ")[1];
				ps.println(commandeSaisie);

				File fCible;

				fCible = new File(fFile2);

				
				
				for(String element : repertoireCourant.list())
					{
						// Vérifie si l'élément existe
						if(element.equals(fFile2))
						{
							// On crée un nouvel objet on vérifie si l'élément est bien un fichier
							fCible = new File(repertoireCourant.getAbsolutePath() + "\\" + element);
							
							if(fCible.isFile())
								{
							ServerSocket sServerSendFile;
							Socket sSendFile;
							InputStream in;
							OutputStream out;
							try
							{
								// Création de la socket d'envoi du fichier et mise en attente de la connection du client
								sServerSendFile = new ServerSocket(6000);
								sSendFile = sServerSendFile.accept();
							}
							catch (IOException e)
							{
								ps.println("2 : Erreur, lors de la création du socket");
								return;
							}

							// Les flux permettent d'écrire et lire dans le socket (in : lire | out : écrire)
							try
							{
								in = new FileInputStream(fCible);
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
								sServerSendFile.close();
							}
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



			}
			else
			{
				// Permet l'envoie de la commande saisie par l'utilisateur et afficher les infos envoyées venant du serveur
				ps.println(commandeSaisie);
				affServMsg(br);
			}

			
		}
		ps.println("bye");
		System.out.println("Arrêt de la communication avec le serveur FTP.");
		
		fScanRead.close();
		socket.close();
		
	}

	
}