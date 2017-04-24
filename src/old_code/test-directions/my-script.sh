
python test_serial.py &
PID=$!
sleep 12
kill $PID
sleep 1
python test-droite.py &
PID=$!
echo $PID
sleep 10
kill $PID
