#!/usr/bin/python
# -*- coding: utf-8 -*-
##########################imports #########################
import serial
import struct
from math import ceil
import threading
import time
import Queue
########################## Fin imports #########################
########################## declaration des variables #########################
q = Queue.Queue()
SPort = '/dev/ttyUSB0'
BRate = 115200

ser = serial.Serial(
    port=SPort,
    baudrate=BRate,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
)


# 22ms 1024 DSM2
Mode  = 0x1
FadeCnt = 0
Header= [Mode, FadeCnt]

NumPacket = 8

Delay = 22e-3
# A pecket is 2 bytes
TransDelay = 2*NumPacket*(10.0/BRate)
# sleep precision is ms
WaitDelay = ceil((Delay - TransDelay)*1000)/1000.0

########################## declaration des variables de test  #########################
TEST=[512,600,400,800,0,0,0]
########################## fin declaration des variables de test  #########################

########################## fin declaration des variables #########################
########################## definition des fonctions #########################
def Trame(L):
    M=[]
    for i,v in enumerate(L):
        sh,sl=CreationBytes(i,v)
        M.append(sh)
        M.append(sl)
    return M

def CreationBytes(id, val):
    s=(id<<10|(val & 0x3FF)) & 0xFFFF
    sl=s & 0xFF
    sh=(s>>8)&0xFF
    return sh,sl

########################## fin definition des fonctions#########################

########################## definition des threads #########################
def sendOrderToDrone():
    print threading.currentThread().getName(), 'Starting'
    compteur = 0 
    # traitement des variables dans la queue 
    while True:
	    if not q.empty():
	    	print q.get()
	    	compteur +=1 
	    	
	    	
	    	#test du bon format des trames 
	    	assert len(TEST) == NumPacket-1, "Wrong Packet Number"
			# fin test du bon format des trames 
			
	    	H = Header + Trame(TEST)
	    	x = bytearray(H)
	    	# envoi des trames 
	    	ser.write(x)
	    	
		    time.sleep(WaitDelay)
		    
			print Delay, TransDelay, WaitDelay
	    time.sleep(2)
	    
	    
	     # permet de quiter le programme (pour les tests) 
	    print threading.currentThread().getName(), 'Exiting')
	    if compteur >0 :
	    	exit()

def SendOrderToThread(symbole):
    print threading.currentThread().getName(), 'Starting'
    q.put(symbole)
    print "sending 2 to the queue "
    time.sleep(3)
    print threading.currentThread().getName(), 'Exiting'
    
########################## fin definition des threads #########################

# PERMET d'envoyer le symbole dans arg à l'autre thread 
w1 = threading.Thread(name='SendOrderToThread', target=SendOrderToThread,args="+")
# defini le thread qui lance le drone 
w2 = threading.Thread(name='sendOrderToDrone', target=sendOrderToDrone)



w1.start()
w2.start()
