
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
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
        	PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())); 
            ){
        	String inputLine, outputLine;
            
            // Initiate conversation with client
            ProtocolServer kkp = new ProtocolServer();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
            	
            	if (inputLine.contains("go to the room ")){
            		ProtocolServer.finalRoom=inputLine.substring(15);
            		logger.info("Serveur: the final room is "+ProtocolServer.finalRoom);
            	}
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                logger.info("Serveur: " + outputLine);
                if (outputLine.equals("Bye. I arrived."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}