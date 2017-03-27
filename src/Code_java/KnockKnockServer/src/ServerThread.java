
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.*;

public class ServerThread implements Runnable
{
	public static Logger logger = Logger.getLogger(KnockKnockServer.class.getName());
	static Handler fh;  
	private Thread _t; // contiendra le thread du client
	  private Socket _s; // recevra le socket liant au client
	  private PrintWriter _out; // pour gestion du flux de sortie
	  private BufferedReader _in; // pour gestion du flux d'entrée
	  private KnockKnockServer _KnockKnockServer; // pour utilisation des méthodes de la classe principale
	  private int _numClient=0; // contiendra le numéro de client géré par ce thread

	  //** Constructeur : crée les éléments nécessaires au dialogue avec le client **
	  ServerThread(Socket s, KnockKnockServer blablaServ) // le param s est donnée dans BlablaServ par ss.accept()
	  {
		  try {
			fh= new FileHandler("logfileServer_%u.log");
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    	logger.addHandler(fh);
	    	SimpleFormatter formatter=new SimpleFormatter();
	    	fh.setFormatter(formatter);
	    _KnockKnockServer=blablaServ; // passage de local en global (pour gestion dans les autres méthodes)
	    _s=s; // passage de local en global
	    try
	    {
	      // fabrication d'une variable permettant l'utilisation du flux de sortie avec des string
	      _out = new PrintWriter(_s.getOutputStream(),true);
	      // fabrication d'une variable permettant l'utilisation du flux d'entrée avec des string
	      _in = new BufferedReader(new InputStreamReader(_s.getInputStream()));
	      // ajoute le flux de sortie dans la liste et récupération de son numero
	      _numClient = blablaServ.addClient(_out);
	    }
	    catch (IOException e){ }

	    _t = new Thread(this); // instanciation du thread
	    _t.start(); // demarrage du thread, la fonction run() est ici lancée
	  }

	  //** Methode :  exécutée au lancement du thread par t.start() **
	  //** Elle attend les messages en provenance du serveur et les redirige **
	  // cette méthode doit obligatoirement être implémentée à cause de l'interface Runnable
	  public void run()
	  {
		// on indique dans la console la connection d'un nouveau client
		System.out.println("Un nouveau client s'est connecte, no "+_numClient);
		String inputLine, outputLine;
	        // Initiate conversation with client
	        // PROTOCOLE : PROTOCOLE SERVER (PROCESS INPUT)
		try
	    {
	        ProtocolServer kkp = new ProtocolServer();
	        outputLine = kkp.processInput(null);
	        _out.println(outputLine);
	        System.out.println(_out);
	        // traitement des reactions
	        while ((inputLine = _in.readLine()) != null) {
	        	
	        	if (inputLine.contains("go to the room ")){
	        		ProtocolServer.finalRoom=inputLine.substring(15);
	        		logger.info("Serveur: the final room is "+ProtocolServer.finalRoom);
	        	}
	            outputLine = kkp.processInput(inputLine);
	            _out.println(outputLine);
	            logger.info("Serveur: " + outputLine);
	            // fin de la communication -> a faire : couper la communication avec un seul client.
	            if (outputLine.equals("Bye. I arrived."))
	                break;
	        }
	    }catch (Exception e){ }
	    finally // finally se produira le plus souvent lors de la deconnexion du client
	    {
	      try
	      {
	      	// on indique à la console la deconnexion du client
	        System.out.println("Le client no "+_numClient+" s'est deconnecte");
	        _KnockKnockServer.delClient(_numClient); // on supprime le client de la liste
	        _s.close(); // fermeture du socket si il ne l'a pas déjà été (à cause de l'exception levée plus haut)
	      }
	      catch (IOException e){ }
	    }
	  }
}