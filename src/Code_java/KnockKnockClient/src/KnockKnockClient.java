import java.io.*;
import java.net.*;
import java.util.logging.*;

public class KnockKnockClient {
	public static Logger logger = Logger.getLogger(KnockKnockClient.class.getName());
	static Handler fh;
	
	
    public static void main(String[] args) throws IOException {
    	fh= new FileHandler("logfileClientServer_%u.log");
    	logger.addHandler(fh);
    	SimpleFormatter formatter=new SimpleFormatter();
    	fh.setFormatter(formatter);
    	
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket kkSocket = new Socket(hostName, portNumber);
        	PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                 new InputStreamReader(kkSocket.getInputStream()));
        		
            ) {
        	
        	String fromServer;

            while ((fromServer = in.readLine()) != null) {
                if (fromServer.equals("I'm listening.")){
                	out.println("I would like to make a delivery. Is the drone available?"); 
                	logger.info("Server: " + fromServer);
                	logger.info("Client: I would like to make a delivery. Is the drone available?");
                }
                if(fromServer.equals("try again")){
                	out.println("I would like to make a delivery. Is the drone available?"); 
                	logger.warning("Server: " + fromServer);
                	logger.info("Client: I would like to make a delivery. Is the drone available?");
                }
                if (fromServer.equals("yes")){
                	ProtocolClient.available=true;
                	out.println("go to the room "+View.getFinalRoom()); //Application android
                	logger.info("Server: " + fromServer);
                	logger.info("Client: go to the room "+View.getFinalRoom());
                }
                if(fromServer.equals("no")){
                	out.println("I would like to make a delivery. Is the drone available?");
                	logger.info("Server: " + fromServer);
                	logger.info("Client: I would like to make a delivery. Is the drone available?");
                }
                if(fromServer.equals("I didn't get the room. Where do you want me to go?")){
                	out.println(""+View.getFinalRoom()); //Application android
                	logger.warning("Server: " + fromServer);
                	logger.info("Client: go to the room "+View.getFinalRoom());
                }
                if (fromServer.equals("I'm going.")||fromServer.contains("je suis à la salle") ||fromServer.contains("I didn't get your question. Can you repeat?")){
                	if(fromServer.contains("je suis à la salle ")){
                		ProtocolClient.localisation=fromServer.substring(19);
                		logger.info("Server: " + fromServer);
                		logger.info("localisation "+ProtocolClient.localisation);
                	}
                	out.println("where are you?");
                	if (fromServer.contains("I didn't get your question. Can you repeat?")){
                		logger.warning("Server: " + fromServer);
                		logger.info("Client: where are you?");
                	}
                	else{
                		logger.info("Server: " + fromServer);
                		logger.info("Client: where are you?");}
                }
                if (fromServer.equals("Bye. I arrived."))
                	//logger.info("Server: " + fromServer);
                    break;
               }
        	kkSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}