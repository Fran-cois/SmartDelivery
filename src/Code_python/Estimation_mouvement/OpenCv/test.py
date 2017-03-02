import numpy as np
import cv2

cap = cv2.VideoCapture("test.MOV")

while(cap.isOpened()):
    # Capture frame-by-frame
    ret, frame = cap.read()
    small = cv2.resize(frame, (0,0), fx=0.3, fy=0.3) 
    gray = cv2.cvtColor(small, cv2.COLOR_BGR2GRAY)

    # Our operations on the frame come here
    print(1)

    # Display the resulting frame
    if cv2.waitKey(100) & 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()