import cv2
import cv
import numpy as np
from matplotlib.pyplot import *

img = cv2.imread('/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/OpenCvtest/test.jpg')
small = cv2.resize(img, (0,0), fx=0.25, fy=0.25) 
gray = cv2.cvtColor(small,cv2.COLOR_BGR2GRAY)
edges = cv2.Canny(small,50,150,apertureSize = 3)


(Ny, Nx,a) = np.shape(small)
lines = cv2.HoughLines(edges,1,np.pi/180,200)

"""for rho,theta in lines[0]:
	a = np.cos(theta)
	b = np.sin(theta)
	x0 = a*rho
	y0 = b*rho
	x1 = int(x0 + 1000*(-b))
	y1 = int(y0 + 1000*(a))
	x2 = int(x0 - 1000*(-b))
	y2 = int(y0 - 1000*(a))
    
	cv2.line(small,(x1,y1),(x2,y2),(0,0,255),2)"""


# extraction de la ligne mediane

tab= lines[0]
print(tab)
b= np.shape(tab)[0]
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

#on recupere les droites dans le tableau de droite et gauche correpondant le mieux a la ligne au sol
rho1=(tab1[0][0])
theta1=tab1[0][1]
rho2=(tab2[0][0])
theta2=tab2[0][1]




# on calcule les valeurs d'angles et de rho moyens
rhom= ((rho1)+(rho2))/2
thetam=((theta1)+(theta2))/2
#on trace la droite centrale

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

#affichage du milieu de la droite

"""def printMiddlePoint(thetam,rhom):
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
	cv2.circle(small,((x1+x2)/2,Ny/2),1,(1, 164, 250),2)"""

def printMiddlePoint2(thetam,rhom):
	a=np.cos(thetam)
	b=np.sin(thetam)
	x0=a*rhom
	y0=b*rhom
	x1 = x0 + 1000*(-b)
	y1 = y0 + 1000*(a)
	x2 = x0 - 1000*(-b)
	y2 = y0 - 1000*(a)
	A=(y2-y1)/(x2-x1)
	B=y2-(A*x2)
	X1=(-B/A)
	X2=(Ny-B)/A
	decalage = Ny/2-int((A*Nx+B+B)/2)
	cv2.circle(small,(Nx/2,int((A*Nx+B+B)/2)),1,(1, 164, 250),2)
	return (decalage)

decalage=printMiddlePoint2(thetam,rhom)

# affichage du point central sur l'image
def printImageMiddle(Nx,Ny):
    cv2.circle(small,(Nx/2,Ny/2),1,(0,255,0),2)

printImageMiddle(Nx,Ny)



cv2.putText(small, 'decalage = ' + str(decalage) + " px" + "," + str(thetam)+ " deg", (10, 30), cv2.FONT_HERSHEY_PLAIN, 1.5, (1, 164, 250), 1, cv2.CV_AA)


cv2.imshow('image',small)
cv2.waitKey(0)

#sauvegarde de l'image

cv2.imwrite('houghlines.jpg',small)


