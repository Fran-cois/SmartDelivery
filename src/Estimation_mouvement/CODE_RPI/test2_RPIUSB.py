import numpy as np
import cv2

def convert(rho1,rho2,taille,decalage):
	diff = abs(rho1-rho2)
	if (diff!=0):
		decalage = decalage*taille/diff
		return (decalage)


cap = cv2.VideoCapture(1)

while(True):
	ret, frame = cap.read()
	small = cv2.resize(frame, (0,0), fx=0.5, fy=0.5)
	(Ny,Nx,a) = np.shape(small)
	gray = cv2.cvtColor(small, cv2.COLOR_BGR2GRAY)
	edges = cv2.Canny(gray,0,150,apertureSize = 3)
	#cv2.imshow('frame',edges)
	lines = cv2.HoughLines(edges,1,np.pi/180,200)
	print(lines)
	if (lines is None):
#		print(1)
		continue
	else : 
		a=np.shape(lines)[0]
#		print(2)
		"""cv2.imshow('frame',small)
		if cv2.waitKey(50) & 0xFF == ord('q'):
			break"""
		tab=[]
		for i in range(a):
			tab.append(lines[i][0])
		print(tab)
		b=np.shape(tab)[0]
		print(b)
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
#		print(tab1)
#		print(tab2)
#		print(np.size(tab1))
#		print(np.size(tab2))
		if (np.size(tab1)==0 or np.size(tab2)==0):
#			print(3)
			continue
		else :
			print(4)
			rho1=(tab1[0][0])
			theta1=tab1[0][1]
			rho2=(tab2[0][0])
			theta2=tab2[0][1]
			rhom= ((rho1)+(rho2))/2
			thetam=((theta1)+(theta2))/2
			thetamdeg = thetam*180/np.pi

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
			printLine(theta2,rhom,1,164,250)

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
				cv2.circle(small,(Nx/2,int((A*Nx+B+B)/2)),1,(0, 0, 255),2)
				return (decalage)

			def printImageMiddle(Nx,Ny):
				cv2.circle(small,(Nx/2,Ny/2),1,(0,255,0),2)
			print(Nx,Ny)
#			printImageMiddle(int(Nx),int(Ny))
#			decalage = printMiddlePoint2(thetam,rhom)
#			decalagecm = convert(rho1,rho2,2,decalage)
#			print(thetamdeg-90, decalagecm)

			cv2.putText(small, 'decalage = ', (10, 30), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1)


			print(5)
	cv2.imshow('frame',small)
	if cv2.waitKey(250) & 0xFF == ord('q'):
		break
				
cap.release()
cv2.destroyAllWindows()
