
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.*;

public class KnockKnockServer {
	public static Logger logger = Logger.getLogger(KnockKnockServer.class.getName());
	static Handler fh;
	
    public static void main(String[] args) throws IOException {
    	fh= new FileHandler("logfileServer_%u.log");
    	logger.addHandler(fh);
    	SimpleFormatter formatter=new SimpleFormatter();
    	fh.setFormatter(formatter);
    	
        if (args.length != 1) {
            // num port (80 ex )
            logger.severe("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
             //SOCKET tcp/ip  + creation fichiers (lire + ecrire)
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
        	PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())); 
            ){
        	String inputLine, outputLine;
            
            // Initiate conversation with client
            // PROTOCOLE : PROTOCOLE SERVER (PROCESS INPUT)
            ProtocolServer kkp = new ProtocolServer();
            outputLine = kkp.processInput(null);
            out.println(outputLine);
            // traitement des reaction
            while ((inputLine = in.readLine()) != null) {
            	
            	if (inputLine.contains("go to the room ")){
            		ProtocolServer.finalRoom=inputLine.substring(15);
            		logger.info("Serveur: the final room is "+ProtocolServer.finalRoom);
            	}
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                logger.info("Serveur: " + outputLine);
                // fin de la communication -> a faire : couper la communication avec un seul client.
                if (outputLine.equals("Bye. I arrived."))
                    break;
            }
        } catch (IOException e) {
            logger.severe("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            logger.severe(e.getMessage());
        }
    }
}
