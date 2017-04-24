import time
fichier = open('MonFichier', 'r')
var = fichier.readline()
time.sleep(1)
fichier.close()

while ("1" not in var):
	fichier = open('MonFichier', 'r')
	var = fichier.readline()
	print var
	time.sleep(1)
	fichier.close()
