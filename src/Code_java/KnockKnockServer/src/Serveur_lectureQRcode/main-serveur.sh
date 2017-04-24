echo 'compilation du serveur'
javac Drone.java
javac ProtocolServer.java
javac ServerThread.java
javac KnockKnockServer.java

echo 'autorisation lecture de QRcode'
chmod a+rx script-lancement-qrcode.sh

echo 'lancement du serveur'
java KnockKnockServer 4444 &

echo 'lancement de la lecture des QRcode'
./script-lancement-qrcode.sh
#python test_valeur_QRcode.py
