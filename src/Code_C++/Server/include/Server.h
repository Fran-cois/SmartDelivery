#pragma once

#include <string>

class ProtocolServer
{
private:
	static const int WAITING = 3;
	static const int START = 0;
	static const int DEPARTURE = 1;
	static const int INFLIGHT = 2;
	std::wstring localisation;
public:
	static std::wstring finalRoom;

private:
	int state = WAITING;


public:
	virtual std::wstring processInput(const std::wstring &theInput);
};
