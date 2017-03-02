import numpy as np
import cv2

cap = cv2.VideoCapture("test.MOV")

while(cap.isOpened()):
    # Capture frame-by-frame
    ret, frame = cap.read()

    # Our operations on the frame come here
    print(1)

    # Display the resulting frame
    if cv2.waitKey(100) & 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()