import numpy as np
import cv2

cap = cv2.VideoCapture(1)

while(True):
    # Capture frame-by-frame
	ret, frame = cap.read()
	small = cv2.resize(frame, (0,0), fx=0.5, fy=0.5)
    # Our operations on the frame come here
	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
	edges = cv2.Canny(gray,0,150,apertureSize = 3)
    # Display the resulting frame
	cv2.imshow('frame',small)
	lines = cv2.HoughLines(edges,1,np.pi/180,200)
	print(lines)
	if (lines is None):
		print(1)
		continue
	else: 
		cv2.imshow('frame',small)
		if cv2.waitKey(10) & 0xFF == ord('q'):
			break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()
