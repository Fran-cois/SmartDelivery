
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.*;
import java.io.*;

public class KnockKnockServer {
	public static Logger logger = Logger.getLogger(KnockKnockServer.class.getName());
	static Handler fh;
	private Vector _tabClients = new Vector(); // contiendra tous les flux de sortie vers les clients
	  private int _nbClients=0; // nombre total de clients connectes

	  //** Methode : la premiere methode executee, elle attend les connections **
    public static void main(String[] args) throws IOException {
    	fh= new FileHandler("logfileServer_%u.log");
    	logger.addHandler(fh);
    	SimpleFormatter formatter=new SimpleFormatter();
    	fh.setFormatter(formatter);
    	KnockKnockServer blablaServ = new KnockKnockServer(); // instance de la classe principale
        if (args.length != 1) {
            // num port (80 ex )
            logger.severe("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try {
             //SOCKET tcp/ip  + creation fichiers (lire + ecrire)
            ServerSocket serverSocket = new ServerSocket(portNumber);
            printWelcome(portNumber);
        	while (true) // attente en boucle de connexion (bloquant sur ss.accept)
        	{
        		new ServerThread(serverSocket.accept(),blablaServ); // un client se connecte, un nouveau thread client est lance
        	}
        	 
        } catch (IOException e) {
            logger.severe("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            logger.severe(e.getMessage());}
        }
        
      //** Methode : affiche le message d'accueil **
        static private void printWelcome(Integer port)
        {
          System.out.println("Lancement du serveur.\n");
        }
        
      //** Methode : envoie le message a tous les clients **
        synchronized public void sendAll(String message,String sLast)
        { 
        	PrintWriter out; // declaration d'une variable permettant l'envoi de texte vers le client
            for (int i = 0; i < _tabClients.size(); i++) // parcours de la table des connectes
            {
              out = (PrintWriter) _tabClients.elementAt(i); // extraction de l'element courant (type PrintWriter)
              if (out != null) // securite, l'element ne doit pas etre vide
              {
              	// ecriture du texte passe en parametre (et concatenation d'une string de fin de chaine si besoin)
                out.print(message+sLast);
                out.flush(); // envoi dans le flux de sortie
              }
            }
        }
      //** Methode : detruit le client no i **
        synchronized public void delClient(int i)
        {
          _nbClients--; // un client en moins ! snif
          if (_tabClients.elementAt(i) != null) // l'element existe ...
          {
            _tabClients.removeElementAt(i); // ... on le supprime
          }
        }

        //** Methode : ajoute un nouveau client dans la liste **
        synchronized public int addClient(PrintWriter out)
        {
          _nbClients++; // un client en plus ! ouaaaih
          _tabClients.addElement(out); // on ajoute le nouveau flux de sortie au tableau
          return _tabClients.size()-1; // on retourne le numero du client ajoute (size-1)
        }

        //** Methode : retourne le nombre de clients connectes **
        synchronized public int getNbClients()
        {
          return _nbClients; // retourne le nombre de clients connectes
        }
}
