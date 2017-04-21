chmod a+rx lecture_qrcode.sh
while [ 1 ]
do
	echo 'lancement de la lecture de qrcode'
	./lecture_qrcode.sh &
	sleep 3
done

