
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
    	int period =2*1000;

        if (args.length != 2) {
            logger.severe("Usage: java EchoClient <host name> <port number>");
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
                if (fromServer.equals("I'm going.")||fromServer.contains("je suis � la salle") ||fromServer.contains("I didn't get your question. Can you repeat?")){
                	try {
						Thread.sleep(period);   // Le client patiente avant de demander o� le drone est.
						out.println("where are you?");
						if(fromServer.contains("je suis � la salle ")){
	                		ProtocolClient.localisation=fromServer.substring(19);
	                		logger.info("Server: " + fromServer);
	                		logger.info("localisation "+ProtocolClient.localisation);
	                	}
	                	if (fromServer.contains("I didn't get your question. Can you repeat?")){
	                		logger.warning("Server: " + fromServer);
	                		logger.info("Client: where are you?");
	                	}
	                	else{
	                		logger.info("Server: " + fromServer);
	                		logger.info("Client: where are you?");}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
                if (fromServer.equals("Bye. I arrived.")){
                	logger.info("Server: " + fromServer);
                    break;}
               }
        	kkSocket.close();
        } catch (UnknownHostException e) {
        	logger.severe("Don't know about host "+ hostName);
            System.exit(1);
        } catch (IOException e) {
            logger.severe("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
