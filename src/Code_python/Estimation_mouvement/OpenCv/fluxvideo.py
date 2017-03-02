import numpy as np
import cv2


cap = cv2.VideoCapture('/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/OpenCvtest/test.MOV')

while(cap.isOpened()):
    ret, frame = cap.read()
    small = cv2.resize(frame, (0,0), fx=0.3, fy=0.3) 
    gray = cv2.cvtColor(small, cv2.COLOR_BGR2GRAY)
    edges = cv2.Canny(gray,0,150,apertureSize = 3)

    lines = cv2.HoughLines(edges,1,np.pi/180,200)
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

        if (np.size(tab1)==0 or np.size(tab2)==0):
            continue
        else:
            rho1=(tab1[0][0])
            theta1=tab1[0][1]
            rho2=(tab2[0][0])
            theta2=tab2[0][1]
            rhom= ((rho1)+(rho2))/2
            thetam=((theta1)+(theta2))/2

            def printLine(theta,rho,B,G,R):
                a = np.cos(theta)
                b = np.sin(theta)
                x0 = a*rho
                y0 = b*rho
                x1 = int(x0 + 1000*(-b))
                y1 = int(y0 + 1000*(a))
                x2 = int(x0 - 1000*(-b))
                y2 = int(y0 - 1000*(a))
                cv2.line(small,(x1,y1),(x2,y2),(B,G,R),2)

            printLine(theta1,rho1,255,0,0)
            printLine(theta2,rho2,255,0,0)
            printLine(theta2,rhom,0,0,255)

            def printMiddlePoint(thetam,rhom):
                a = np.cos(thetam)
                b = np.sin(thetam)
                x0 = a*rhom
                y0 = b*rhom
                x1 = int(x0 + 1000*(-b))
                y1 = int(y0 + 1000*(a))
                x2 = int(x0 - 1000*(-b))
                y2 = int(y0 - 1000*(a))
                decalage = (Nx/2)-(x1+x2)/2
                print(decalage)
                cv2.circle(small,((x1+x2)/2,Ny/2),1,(1, 164, 250),2)

            cv2.imshow('frame',small)
            if cv2.waitKey(20) & 0xFF == ord('q'):
                break



cap.release()
cv2.destroyAllWindows()
cv2.destroyAllWindows()