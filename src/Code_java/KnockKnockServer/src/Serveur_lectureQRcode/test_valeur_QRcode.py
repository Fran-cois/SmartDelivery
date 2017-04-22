from random import randint  #pour test sans analyse image
import time

for i in range(20):
    qrcode = str(randint(1,5))   #pour test sans analyse image
    mon_fichier = open('result-QRcode.txt','w')   #on ecrase ou on creer le fichier
    mon_fichier.write(qrcode)  #on ecrit le numero de la salle lu a partir du QRcode
    mon_fichier.close()
    print (qrcode)
    time.sleep(4)
