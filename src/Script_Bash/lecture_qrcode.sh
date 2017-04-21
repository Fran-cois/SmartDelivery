trap "echo  " 2
zbarcam | grep -o '[0-9]' > result-QRcode.txt &
sleep 2
ps -ef | grep zbarcam | grep -v grep | awk '{print $2}' | xargs kill
