import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute()
	{
		// Implémentation de la commande 'ls'
		File f = Main.currentDirectory;
		String fichiers[] = f.list();
		for(int i = 0;i<fichiers.length;i++)
		{
			if(i != (fichiers.length -1))
			{
				ps.println("1 : " + fichiers[i]);
			}
			else
			{
				ps.println("0 : " + fichiers[i]);
			}
		}
	}
}
