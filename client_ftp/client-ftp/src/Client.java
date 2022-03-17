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
			// Affichage de la ligne puis si r�ponse �quivalent � 1 alors on lit les donn�es
			line = br.readLine();
			System.out.println(line);
			if(line.split(" ")[0].equals("0") || line.split(" ")[0].equals("2"))
			break;
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("[Client FTP]");
		
		// Cr�ation du socket pour la connexion au serveur
		Socket socket = new Socket("localhost", 2020);
		
		// Utilis� pour r�cup�rer les infos de connexion
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		affServMsg(br);
		
		// G�re des "Stream" en utilisant des String � la place des bytes et "Scanner" r�cup�re le texte saisi par l'utilisateur
		PrintStream ps = new PrintStream(socket.getOutputStream());
		Scanner fScanRead = new Scanner(System.in);

		String commandeSaisie;
		
		// Tant qu'on a une commande de saisie (except� bye)
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

				// Connection au serveur pour l'envoie de donn�e du serveur vers le client
				Socket sRecieveFile = new Socket("localhost",4000);
				File fFile = new File(fName);
				fFile.createNewFile();

				//Buffer
				byte[] bytes = new byte[16 * 1024];


				// Stream et de r�ception et d'�criture du fichier
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
				System.out.println("Succ�s : Le fichier est bien re�u");

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
						// V�rifie si l'�l�ment existe
						if(element.equals(fFile2))
						{
							// On cr�e un nouvel objet on v�rifie si l'�l�ment est bien un fichier
							fCible = new File(repertoireCourant.getAbsolutePath() + "\\" + element);
							
							if(fCible.isFile())
								{
							ServerSocket sServerSendFile;
							Socket sSendFile;
							InputStream in;
							OutputStream out;
							try
							{
								// Cr�ation de la socket d'envoi du fichier et mise en attente de la connection du client
								sServerSendFile = new ServerSocket(6000);
								sSendFile = sServerSendFile.accept();
							}
							catch (IOException e)
							{
								ps.println("2 : Erreur, lors de la cr�ation du socket");
								return;
							}

							// Les flux permettent d'�crire et lire dans le socket (in : lire | out : �crire)
							try
							{
								in = new FileInputStream(fCible);
							}
							catch (IOException ex)
							{
								System.out.println("2 : Erreur, impossible de cr�er l'imput stream en direction du socket");
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

							// Cr�ation d'un buffer
							byte[] bytes = new byte[16*1024];

							int i;
							
							// On lit dans la socket puis on proc�de � l'envoi
							while ((i = in.read(bytes)) > 0)
							{
								out.write(bytes, 0, i);
							}
							System.out.println("Le transfert est termin�");

							try
							{
								out.close();
								in.close();
								sSendFile.close();
								sServerSendFile.close();
							}
							catch (IOException e)
							{
								System.out.println("2  : Erreur, � la fermeture des diff�rents sockets");
								return;
							}
							return;
						}
						else
						{
							ps.println("2 : Erreur, l'�l�ment n'est pas un fichier");
							return;
						}
					}
				}



			}
			else
			{
				// Permet l'envoie de la commande saisie par l'utilisateur et afficher les infos envoy�es venant du serveur
				ps.println(commandeSaisie);
				affServMsg(br);
			}

			
		}
		ps.println("bye");
		System.out.println("Arr�t de la communication avec le serveur FTP.");
		
		fScanRead.close();
		socket.close();
		
	}

	
}