

export WORKON_HOME=$HOME/.virtualenvs 
source /usr/local/bin/virtualenvwrapper.sh\ 
echo -e "\n"
sleep 3
source ~/.bashrc 
workon cv
sleep 3 
python test2_PICAM.py &
PID=$!
sleep 12
kill $PID
