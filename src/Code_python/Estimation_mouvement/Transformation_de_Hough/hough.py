import numpy
import math
import cmath
from  matplotlib.pyplot import *
from PIL import Image


#on ouvre une image dont on a deja delimita les contours.
img = Image.open("/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/Transformation de Hough/Image_test/contours-imagetest3.png")
array=numpy.array(img)*1.0
#print(array)
figure(figsize=(8,6))
imshow(array,cmap=cm.gray)

#on recupere le nombres de pixels en abscisse et en ordonné
(Ny,Nx,a) = numpy.shape(array)
print(Nx)
print(Ny)
print(a)

#print(array[0][0][0])

# on definit l'accumualteur
rho= 1.0
theta=1.0
Ntheta = int(180.0/theta)
Nrho = int(math.floor(math.sqrt(Nx*Nx+Ny*Ny))/rho)
dtheta = math.pi/Ntheta
drho = math.floor(math.sqrt(Nx*Nx+Ny*Ny))/Nrho
accum = numpy.zeros((Ntheta,Nrho))

#Pour chaque pixel blanc de l'image, on incremente les points de l'accumulateur 
for j in range(Ny):
    for i in range(Nx):
        if array[j][i][0]!=0:
            for i_theta in range(Ntheta):
                theta = i_theta*dtheta
                rho = i*math.cos(theta)+(Ny-j)*math.sin(theta)
                i_rho = int(rho/drho)
                if (i_rho>0) and (i_rho<Nrho):
                    accum[i_theta][i_rho] += 1

figure(figsize=(12,6))
imshow(accum,cmap=cm.gray)

seuil=100
accum_seuil = accum.copy()
for i_theta in range(Ntheta):
    for i_rho in range(Nrho):
        if accum[i_theta][i_rho]<seuil:
            accum_seuil[i_theta][i_rho] = 0

figure(figsize=(12,6))
imshow(accum_seuil,cmap=cm.gray)

lignes = []
for i_theta in range(Ntheta):
    for i_rho in range(Nrho):
        if accum_seuil[i_theta][i_rho]!=0:
            lignes.append((i_rho*drho,i_theta*dtheta))
figure(figsize=(8,6))
axis([0,Nx,0,Ny])
for rho,theta in lignes:
    a = math.cos(theta)
    b = math.sin(theta)
    x0 = a*rho
    y0 = b*rho
    x1 = int(x0 + 1000*(-b))
    y1 = int(y0 + 1000*(a))
    x2 = int(x0 - 1000*(-b))
    y2 = int(y0 - 1000*(a))
    plot([x1,x2],[y1,y2],color="b")
show()
