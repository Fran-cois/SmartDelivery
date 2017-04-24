
python test2_PICAM.py &
PID=$!
zbarcam /dev/video0 &
PID2=$!
sleep 5
kill $PID
kill $PID2
