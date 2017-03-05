import numpy as np
import cv2


#img = cv2.imread('/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/OpenCvtest/bandcontour.png',0)
#imgray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
img = cv2.imread('/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/OpenCvtest/bandecontour.png')
gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
ret,thresh = cv2.threshold(gray,127,255,0)
contours, hierarchy = cv2.findContours(thresh,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
cnt = contours[4]
img2 =cv2.drawContours(gray, [cnt], 0, (0,255,0), 3)
cv2.imshow('test',img)
cv2.waitKey(0)
cv2.destroyAllWindows()

#cv2.imwrite('testcv.jpg',img2)