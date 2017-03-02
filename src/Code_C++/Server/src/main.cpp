#include <iostream>
#include <string>
#include <cmath>
#include "Server.h"
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
//#include "ot/net/ServerSocket.h"

using namespace std;


int main(int argc, char* argv[])
{
    int server;

    bool isExit = false;
    int bufsize = 1024;
    char buffer[bufsize];

    if (sizeof(argv) != 1) {
        cout << "Usage: java KnockKnockServer <port number>" <<endl;
        isExit = true;
    }

    //int portNumber = argv[0];

    try {
        server = ServerSocket(int portNumber);
        ServerSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    {
        string inputLine, outputLine;

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
        logger.severe("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
        logger.severe(e.getMessage());
    }
}
return 0;
}
