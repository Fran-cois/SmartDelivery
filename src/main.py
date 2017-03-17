#!/usr/bin/python
import threading
import time
import Queue
# declaration des variables 
q = Queue.Queue()

# fin declaration des variables
def sendOrderToDrone():
    print threading.currentThread().getName(), 'Starting'
    compteur = 0 
    # traitement des variables dans la queue 
    while True:
	    if not q.empty():
	    	print q.get()
	    	compteur +=1 
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

# PERMET d'envoyer le symbole dans arg à l'autre thread 
w1 = threading.Thread(name='SendOrderToThread', target=SendOrderToThread,args="+")
# defini le thread qui lance le drone 
w2 = threading.Thread(name='sendOrderToDrone', target=sendOrderToDrone)



w1.start()
w2.start()
