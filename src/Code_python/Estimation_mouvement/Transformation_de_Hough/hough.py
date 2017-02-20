import numpy
import math
import cmath
from  matplotlib.pyplot import *
from PIL import Image
from pylab import *


#on ouvre une image dont on a deja delimite les contours.
img = Image.open("/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/Transformation de Hough/Image_test/bandecontour.png")
array=numpy.array(img)*1.0
#print(array)
#figure(figsize=(8,6))
#imshow(img,cmap=cm.gray)

#on recupere le nombres de pixels en abscisse et en ordonne
(Ny,Nx,a) = numpy.shape(array)


rho= 1.0
theta=1.0

#Limite max de theta et rho
Ntheta = int(180.0/theta)
Nrho = int(math.floor(math.sqrt(Nx*Nx+Ny*Ny))/rho)

#discretisation en radian et en pixel (longeur)
dtheta = math.pi/Ntheta
drho = math.floor(math.sqrt(Nx*Nx+Ny*Ny))/Nrho

#on definit l'accumulateur
accum = numpy.zeros((Ntheta,Nrho))

#Pour chaque pixel blanc de l'image, on incremente les points de l'accumulateur 
for j in range(Ny):
    for i in range(Nx):
        if array[j][i][0]!=0:
            for i_theta in range(Ntheta):
                #calcul de theta et rho correspond a la droite courante
                theta = i_theta*dtheta
                rho = i*math.cos(theta)+(Ny-j)*math.sin(theta)
                i_rho = int(rho/drho)
                if (i_rho>0) and (i_rho<Nrho):
                    accum[i_theta][i_rho] += 1

#figure(figsize=(12,6))
#imshow(accum,cmap=cm.gray)



#On utilise un seuil pour garder que les droites les plus presentes
seuil=300
accum_seuil = accum.copy()
for i_theta in range(Ntheta):
    for i_rho in range(Nrho):
        if accum[i_theta][i_rho]<seuil:
            accum_seuil[i_theta][i_rho] = 0

#figure(figsize=(12,6))
#imshow(accum_seuil,cmap=cm.gray)

#On garde dans un tableau, les droites non absurdes
c=0
s=0
lignes = []
for i_theta in range(Ntheta):
    for i_rho in range(Nrho):
        if accum_seuil[i_theta][i_rho]!=0 and (i_rho*drho)>20:
            c=c+1
            lignes.append((i_rho*drho,i_theta*dtheta))
            s=i_rho*drho+s
            #print(i_rho*drho)
            #print(i_theta*dtheta)


# on trie ce tableau en fonction de rho croissant puis on les range dans deux tableaux pour separer les lignes de gauches et droites
tab= sorted(lignes, key=lambda colonnes: colonnes[0])
m=s/c
#print(m)
tab1=[]
tab2=[]
for i in range(c):
    if(tab[i][0]<m):
        tab1.append(tab[i])
    else:
        tab2.append(tab[i])

tab1=sorted(tab1, key=lambda colonnes: colonnes[1])
tab2=sorted(tab2, key=lambda colonnes: colonnes[1])

#print(tab1)

#on recupere les droites dans le tableau de droite et gauche correpondant le mieux a la ligne au sol
rho1=tab1[0][0]
theta1=tab1[0][1]
rho2=tab2[0][0]
theta2=tab2[0][1]


# on calcule les valeurs d'angles et de rho moyens
rhom= (rho1+rho2)/2
thetam=(theta1+theta2)/2

figure(figsize=(8,6))
subplot(axisbg='gray')
axis([0,Nx,0,Ny])


#on affiche toutes les droites de l'accumulateur
"""for rho,theta in lignes:
    a = math.cos(theta)
    b = math.sin(theta)
    x0 = a*rho
    y0 = b*rho
    x1 = int(x0 + 1000*(-b))
    y1 = int(y0 + 1000*(a))
    x2 = int(x0 - 1000*(-b))
    y2 = int(y0 - 1000*(a))
    plot([x1,x2],[y1,y2],color="b")"""

# on affiche ces droites

def printLine(theta,rho,color):
    a=math.cos(theta)
    b=math.sin(theta)
    x0=a*rho
    y0=b*rho
    x1 = int(x0 + 1000*(-b))
    y1 = int(y0 + 1000*(a))
    x2 = int(x0 - 1000*(-b))
    y2 = int(y0 - 1000*(a))
    plot([x1,x2],[y1,y2],color=color)


printLine(theta1,rho1,"b")
printLine(theta2,rho2,"b")
printLine(thetam,rhom,"r")

def printMiddlePoint(thetam,rhom,color):
    a=math.cos(thetam)
    b=math.sin(thetam)
    x0=a*rhom
    y0=b*rhom
    x1 = int(x0 + 1000*(-b))
    y1 = int(y0 + 1000*(a))
    x2 = int(x0 - 1000*(-b))
    y2 = int(y0 - 1000*(a))

    plot((x1+x2)/2,Ny/2,"b:o", color=color)

# (Ny+y0)/2 pour l'image droite



def printImageMiddle(Nx,Ny,color):
    plot(Nx/2,Ny/2,"b:o", color=color)

printImageMiddle(Nx,Ny,"g")

def printMiddlePoint2(thetam,rhom,color):
    a=math.cos(thetam)
    b=math.sin(thetam)
    x0=a*rhom
    y0=b*rhom
    x1 = x0 + 1000*(-b)
    y1 = y0 + 1000*(a)
    x2 = x0 - 1000*(-b)
    y2 = y0 - 1000*(a)
    
    A=(y2-y1)/(x2-x1)
    B=y2-(A*x2)
    X1=-B/A
    X2=(Ny-B)/A
    decalage = (Nx/2)-((X2+X1)/2)
    print(decalage)
    plot((X1+X2)/2,Ny/2,"b:o", color=color)


if (thetam!=0):
    printMiddlePoint2(thetam,rhom,"yellow")
else :
    printMiddlePoint(thetam,rhom,"orange")

print(thetam*180/(numpy.pi))

savefig("/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/Transformation de Hough/Image_test/sauvegarde.jpeg")

show()













