
python test_serial.py &
PID=$!
sleep 12
kill $PID
sleep 1
python my-serial.py &
PID=$!
echo $PID
