#include "../include/KKServer.h"
#include "../include/Server.h"

using namespace std;


void KnockKnockServer::main(std::vector<std::wstring> &args)
{


	if (args.size() != 1)
	{
		exit(1);
	}

	int portNumber = std::stoi(args[0]);


}
