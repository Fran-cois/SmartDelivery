import numpy as np
import cv2

cap = cv2.VideoCapture("test.MOV")

while(cap.isOpened()):
    # Capture frame-by-frame
    ret, frame = cap.read()
    small = cv2.resize(frame, (0,0), fx=0.3, fy=0.3) 
    gray = cv2.cvtColor(small, cv2.COLOR_BGR2GRAY)
    edges = cv2.Canny(gray,0,150,apertureSize = 3)

    lines = cv2.HoughLines(edges,1,np.pi/180,200)
    print(lines)
    if (lines is None):
            continue
    else:
        tab=lines[0]
        b=np.shape(tab)[0]
        s=0
        for i in range(b):
            s=s+abs(tab[i][0])
        m=s/b

        tab1=[]
        tab2=[]

        for i in range(b):
            if(abs(tab[i][0])<m):
                tab1.append(tab[i])
            else:
                tab2.append(tab[i])

        tab1=sorted(tab1, key=lambda colonnes: colonnes[1])
        tab2=sorted(tab2, key=lambda colonnes: colonnes[1])


      


    # Our operations on the frame come here
    print(1)

    # Display the resulting frame
    if cv2.waitKey(100) & 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()