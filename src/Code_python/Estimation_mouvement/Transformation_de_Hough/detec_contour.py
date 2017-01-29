import numpy
import math
import cmath
import imageio
from scipy.ndimage.filters import convolve,gaussian_filter
from  matplotlib.pyplot import *



#On recupere l'image sous forme de matrice de pixel

img = imageio.imread("/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/Transformation de Hough/Image_test/bande.png")
red = img[:,:,0]
green = img[:,:,1]
blue = img[:,:,2]

#print(numpy.shape(img))
#print(img)
#print(red)

#convertion du tableau d'entiers en flottants
array=red*1.0

#print(array)

#taille de la fenetre a l'ecran
figure(figsize=(4,4))
#imshow(array,cmap=cm.gray)


#application d'un filtre gaussien
array = gaussian_filter(array,1)

#Operateur de differentiation de Sobel pour le calcul du gradient pour chaque point de l'image 
sobelX = numpy.array([[-1,0,1],[-2,0,2],[-1,0,1]])
sobelY = numpy.array([[-1,-2,-1],[0,0,0],[1,2,1]])
derivX = convolve(array,sobelX)
derivY = convolve(array,sobelY)

# on multiplie derivY par le complexe j parce que le gradient doit etre un complexe : pour representer chaque point dans le plan complexe
gradient = derivX+derivY*1j

#G correspond a la norme du gradient pour chaque point de l'image
G = numpy.absolute(gradient)

#On determine l'angle par rapport a l'axe des x pour chaque point de l'image.
theta = numpy.angle(gradient)

#representation des composantes du gradient pour la differentiation selon l'axe x puis l'axe y
figure(figsize=(8,4))
f,(p1,p2)=subplots(ncols=2)
p1.imshow(derivX,cmap=cm.gray)
p2.imshow(derivY,cmap=cm.gray)

#representation de la norme du gradient
figure(figsize=(4,4))
#imshow(G,cmap=cm.gray)

#on utilise un seuil pour eliminer le bruit
G1= G.copy()
seuil = 100.0
s = G1.shape
print(s)
for i in range(s[0]):
    for j in range(s[1]):
        if G1[i][j]<seuil:
            G1[i][j] = 0.0
figure(figsize=(4,4))
#f,(p1,p2)=subplots(ncols=2)
#p1.imshow(G,cmap=cm.gray)
#p2.imshow(G1,cmap=cm.gray)
#imshow(G,cmap=cm.gray)


Gfinal = G.copy()
seuil = 100.0
for j in range(s[0]):
    for i in range(s[1]):
        if Gfinal[j][i]<seuil:
            Gfinal[j][i] = 0.0
        else:
            Gfinal[j][i] = 255.0
figure(figsize=(10,6))
f,(p1,p2,p3)=subplots(ncols=3)
p1.imshow(G,cmap=cm.gray)
p2.imshow(G1,cmap=cm.gray)
p3.imshow(Gfinal,cmap=cm.gray)

show()
