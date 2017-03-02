#ifndef KKSERVER_H_INCLUDED
#define KKSERVER_H_INCLUDED

#pragma once

#include <string>
#include <vector>

class KnockKnockServer
{
public:
	static Logger *logger;
	static Handler *fh;

	static void main(std::vector<std::wstring> &args) throw(IOException);
};


#endif // KKSERVER_H_INCLUDED
