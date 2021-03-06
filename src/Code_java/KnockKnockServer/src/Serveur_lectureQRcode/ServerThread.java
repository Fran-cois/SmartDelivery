
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
	  private BufferedReader _in; // pour gestion du flux d'entree
	  private KnockKnockServer _KnockKnockServer; // pour utilisation des methodes de la classe principale
	  private int _numClient=0; // contiendra le numero de client gere par ce thread
	  private boolean arrived;

	  //** Constructeur : cree les elements necessaires au dialogue avec le client **
	  ServerThread(Socket s, KnockKnockServer blablaServ) // le param s est donnee dans BlablaServ par ss.accept()
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
	    _KnockKnockServer=blablaServ; // passage de local en global (pour gestion dans les autres methodes)
	    _s=s; // passage de local en global
	    try
	    {
	      // fabrication d'une variable permettant l'utilisation du flux de sortie avec des string
	      _out = new PrintWriter(_s.getOutputStream(),true);
	      // fabrication d'une variable permettant l'utilisation du flux d'entree avec des string
	      _in = new BufferedReader(new InputStreamReader(_s.getInputStream()));
	      // ajoute le flux de sortie dans la liste et recuperation de son numero
	      _numClient = blablaServ.addClient(_out);
	    }
	    catch (IOException e){ }

	    _t = new Thread(this); // instanciation du thread
	    _t.start(); // demarrage du thread, la fonction run() est ici lancee
	  }

	  //** Methode :  executee au lancement du thread par t.start() **
	  //** Elle attend les messages en provenance du serveur et les redirige **
	  // cette methode doit obligatoirement etre implementee a cause de l'interface Runnable
	  public void run() {
		  // on indique dans la console la connection d'un nouveau client
		  System.out.println("Un nouveau client s'est connecte, no " + _numClient);
		  try{
			  PrintWriter writer = new PrintWriter("startDrone.txt", "UTF-8");
			  writer.print(String.valueOf(_numClient + 1));
			  writer.close();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  String inputLine, outputLine;
		  // Initiate conversation with client
		  // PROTOCOLE : PROTOCOLE SERVER (PROCESS INPUT)
		  arrived = false;
		  while (!arrived) {
			  try {
				  System.out.println("debut du try - ligne 64");
				  ProtocolServer kkp = new ProtocolServer();
				  outputLine = kkp.processInput(null);
				  _out.println(outputLine);
				  System.out.println(_out);
				  // traitement des reactions
				  while ((inputLine = _in.readLine()) != null) {
					  System.out.println(inputLine + "ligne 72");
					  if (inputLine.contains("go to the room ")) {
						  ProtocolServer.finalRoom = inputLine.substring(15);
						  logger.info("Serveur: the final room is " + ProtocolServer.finalRoom);
					  }
					  outputLine = kkp.processInput(inputLine);
					  _out.println(outputLine);
					  logger.info("Serveur: " + outputLine);
					  // fin de la communication -> a faire : couper la communication avec un seul client.
					  if (outputLine.equals("Bye. I arrived.")) {
						  arrived = true;

						  break;
					  }
				  }
			  } catch (Exception e) {
				  System.out.println("It did not work.\n");
			  }
		  }

	      try
	      {
	      	// on indique a la console la deconnexion du client
	        System.out.println("Le client no "+_numClient+" s'est deconnecte");
	        _KnockKnockServer.delClient(_numClient); // on supprime le client de la liste
			try{
				PrintWriter writer = new PrintWriter("startDrone.txt", "UTF-8");
				writer.print(String.valueOf(_numClient));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        _s.close(); // fermeture du socket si il ne l'a pas deja ete (a cause de l'exception levee plus haut)
	      }
	      catch (IOException e){ }
	    }
	  }
