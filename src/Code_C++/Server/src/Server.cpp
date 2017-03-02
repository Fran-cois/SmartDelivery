#include "../include/Server.h"
#include "../include/Drone.h"
using namespace std;

std::wstring ProtocolServer::finalRoom;

std::wstring ProtocolServer::processInput(const std::wstring &theInput)
{
	std::wstring theOutput = L"";
	if (state == WAITING)
	{
		theOutput = L"I'm listening.";
		state = START;
	}
	else if (state == START)
	{
		if (theInput == L"I would like to make a delivery. Is the drone available?")
		{
			if (Drone::available())
			{
				theOutput = L"yes";
				state = DEPARTURE;
			}
			else
			{
				theOutput = L"no";
			state = WAITING;
			}
		}
		else
		{
			theOutput = L"try again";
			state = WAITING;
		}
	}
	else if (state == DEPARTURE)
	{
		if (theInput.length() == 3)
		{
			theOutput = L"I'm going.";
			state = INFLIGHT;
		}
		else
		{
			theOutput = L"I didn't get the room. Where do you want me to go?";
			state = DEPARTURE;
		}
	}
	else if (state == INFLIGHT)
	{
		if (theInput == L"where are you?")
		{
			localisation = Drone::getRoom();
			if (finalRoom == localisation)
			{
				theOutput = L"Bye. I arrived.";
				state = START;
			}
			else
			{
			theOutput = std::wstring(L"je suis � la salle ") + localisation;
			}
		}
		else
		{
			theOutput = L"I didn't get your question. Can you repeat?";
			state = INFLIGHT;
		}
	}
	return theOutput;
}
