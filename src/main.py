#!/usr/bin/python
# -*- coding: utf-8 -*-

##########################imports #########################
#import serial# marche seulement  pour la raspberry pi 
import struct
from math import ceil
import threading
import time
import Queue
import os, sys 
import random
########################## Fin imports #########################

########################## declaration des variables #########################
q = Queue.Queue()
SPort = '/dev/ttyUSB0'
BRate = 115200
"""
ser = serial.Serial(
    port=SPort,
    baudrate=BRate,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
)
"""

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


TrameInit=[512,600,400,800,0,0,0]


########################## fin declaration des variables de test  #########################




########################## fin declaration des variables #########################





########################## definition des fonctions de creation de trame #########################
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

########################## fin definition des fonctions de creation de trame #########################


########################## definition des threads #########################
def sendOrderToDrone():
    print threading.currentThread().getName(), 'Starting'
    #CHANNEL = 0 # variable qui va de 0 a 6 
    COEF = [20,20,20,20,20,20,20]
    # traitement des variables dans la queue 
    while True:
    	
	    if not q.empty(): # si on recoit une information dans la queue
	    	
	    	operation = q.get()
	    	print operation  # on l'affiche 
	    	
	    	############## creation des trames#############
	    	for i in range(len(operation)):
	    		if "+" in operation[i] :
	    			TrameInit[i]+= COEF[i]*len(operation[i])
	    		if "-" in operation[i] :
					TrameInit[i]-= COEF[i]*len(operation[i])	
	    	for i in range(len(TrameInit)):
	    		TrameInit[i] = max(0,TrameInit[i])
	    		TrameInit[i] = min(1024,TrameInit[i])
	    	print TrameInit
	    	############## fin creation des trames #############
	    	
	    	
	    	
	    	###############test du bon format des trames ############### 
	    	assert len(TrameInit) == NumPacket-1, "Wrong Packet Number"
	    	
			###############  fin test du bon format des trames ############## 
			
			############### On convertit la trame en bytearray ############## 
	    	H = Header + Trame(TrameInit)
	    	
	    	x = bytearray(H)
	    	
	    	############## envoi des trames #############
	    	#ser.write(x)
	    	##############fin envoi #############
	    	
	    	
	    	time.sleep(WaitDelay)
	    	print Delay, TransDelay, WaitDelay
	    	
	    #time.sleep(2)
	    #print threading.currentThread().getName(), 'Exiting'
	    
	     # permet de quiter le programme (pour les tests) 
	    #exit()

def SendOrderToThread():
	#args=("++","+","-","++","++","--","++")
	#rool,pitch,yaw,throttle,an1,an2,an3
	print threading.currentThread().getName(), 'Starting'
	rool = "++"
	yaw = "+"
	throttle = "-"
	pitch="+"
	an1="+"
	an2="++"
	an3="--"
	while True:
		print "i am there"
		rool = raw_input("entrer rool")
		pitch = raw_input("entrer pitch")
		throttle = raw_input("entrer throttle")
		yaw = raw_input("entrer yaw")
		an1 = raw_input("entrer an1")
		an2 = raw_input("entrer an2")
		an3 = raw_input("entrer an3")
		
		q.put([rool,pitch,yaw,throttle,an1,an2,an3])
		time.sleep(WaitDelay)
	########################## fin definition des threads #########################

# PERMET d'envoyer le symbole dans arg à l'autre thread 

# defini le thread qui lance le drone 

w2 = threading.Thread(name='sendOrderToDrone', target=sendOrderToDrone)
w1 = threading.Thread(name='SendOrderToThread', target=SendOrderToThread)


w1.start()
w2.start()
