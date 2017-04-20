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
from picamera.array import PiRGBArray
from picamera import PiCamera
import numpy as np
import cv2
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

########################## declaration des variables de traitement d'image #########################

# initialize the camera and grab a reference to the raw camera capture
camera = PiCamera()
camera.resolution = (640, 480)
camera.framerate = 32
rawCapture = PiRGBArray(camera, size=(640, 480))

Nombre_de_frame_Seconde = 30 
Angle_Seuil = 10
Decalage_Seuil = 10
# allow the camera to warmup
time.sleep(0.1)

########################## fin declaration des variables de traitement d'image	 #########################

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

########################## definition des fonctions du traitement d'image #########################
def convert(rho1,rho2,taille,decalage):
	diff = abs(rho1-rho2)
	if (diff!=0):
		decalage = decalage*taille/diff
		return (decalage)
def printLine(theta,rho,B,G,R):
	a = np.cos(theta)
	b = np.sin(theta)
	x0 = a*rho
	y0 = b*rho
	x1 = int(x0 + 1000*(-b))
	y1 = int(y0 + 1000*(a))
	x2 = int(x0 - 1000*(-b))
	y2 = int(y0 - 1000*(a))
	cv2.line(small,(x1,y1),(x2,y2),(B,G,R),2)
def printMiddlePoint2(thetam,rhom):
	a=np.cos(thetam)
	b=np.sin(thetam)
	x0=a*rhom
	y0=b*rhom
	x1 = x0 + 1000*(-b)
	y1 = y0 + 1000*(a)
	x2 = x0 - 1000*(-b)
	y2 = y0 - 1000*(a)
	A=(y2-y1)/(x2-x1)
	B=y2-(A*x2)
	X1=(-B/A)
	X2=(Ny-B)/A
	decalage = Ny/2-int((A*Nx+B+B)/2)
	cv2.circle(small,(Nx/2,int((A*Nx+B+B)/2)),1,(0, 0, 255),2)
	return (decalage)

def printImageMiddle(Nx,Ny):
	cv2.circle(small,(Nx/2,Ny/2),1,(0,255,0),2)
def fonction_changement_parametre(angle,cm):
	if angle > Angle_Seuil or abs(cm) > Decalage_Seuil :
		if cm >0 :
			# decalage a droite -> aller a gauche 
		else : 
			#decalage a gauche -> aller a droite 		
			

########################## fin definition des fonctions  du traitement d'image #########################


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
	compteur_de_frame = 0 
	for frame in camera.capture_continuous(rawCapture, format="bgr", use_video_port=True):
		rool = "="
		yaw = "="
		throttle = "="
		pitch="="
		an1="="
		an2="="
		an3="="
		frame = frame.array
		small = cv2.resize(frame, (0,0), fx=0.5, fy=0.5)
		(Ny,Nx,a) = np.shape(small)
		gray = cv2.cvtColor(small, cv2.COLOR_BGR2GRAY)
		edges = cv2.Canny(gray,0,150,apertureSize = 3)
		#cv2.imshow('frame',edges)
		lines = cv2.HoughLines(edges,1,np.pi/180,200)
		print(lines)
		if (lines is None):
			print(1)
	#		continue
			rawCapture.truncate(0)
		else : 
			a=np.shape(lines)[0]
			print(2)
			tab=[]
			for i in range(a):
				tab.append(lines[i][0])
			print(tab)
			b=np.shape(tab)[0]
			print(b)
			s=0
			for i in range(b):
				s=s+abs(tab[i][0])
			m=s/b
	
			tab1=[]
			tab2=[]
	
			for i in range(b):
				if(abs(tab[i][0])<m):
					tab1.append(tab[i])
				else:
					tab2.append(tab[i])
	
			tab1=sorted(tab1, key=lambda colonnes: colonnes[1])
			tab2=sorted(tab2, key=lambda colonnes: colonnes[1])
			if (np.size(tab1)==0 or np.size(tab2)==0):
				print(3)
	#			continue
				rawCapture.truncate(0)
				
			else :
				print(4)
				rho1=(tab1[0][0])
				theta1=tab1[0][1]
				rho2=(tab2[0][0])
				theta2=tab2[0][1]
				rhom= ((rho1)+(rho2))/2
				thetam=((theta1)+(theta2))/2
				thetamdeg = thetam*180/np.pi
				
				printLine(theta1,rho1,255,0,0)
				printLine(theta2,rho2,255,0,0)
				printLine(theta2,rhom,1,164,250)
				decalagecm = convert(rho1,rho2,2,decalage)
				if (compteur_de_frame% Nombre_de_frame_Seconde == 0 ):
					#on change les parametres 
					angle = thetamdeg
					cm = decalagecm
					fonction_changement_parametre(angle,cm)
				rawCapture.truncate(0)
			compteur_de_frame += 1
			q.put([rool,pitch,yaw,throttle,an1,an2,an3])
	
	########################## fin definition des threads #########################

# PERMET d'envoyer le symbole dans arg à l'autre thread 

# defini le thread qui lance le drone 

w2 = threading.Thread(name='sendOrderToDrone', target=sendOrderToDrone)
w1 = threading.Thread(name='SendOrderToThread', target=SendOrderToThread)


w1.start()
w2.start()
