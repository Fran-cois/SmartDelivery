import numpy as np
import cv2

if (decalagecm < 0):
    cv2.putText(small, 'DROITE', (10, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
    cv2.putText(small, str(abs(np.around(decalagecm))) + 'cm', (10, 70), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
if (decalagecm > 0):
    cv2.putText(small, 'GAUCHE', (10, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
    cv2.putText(small, str(abs(np.around(decalagecm))) + 'cm' , (10, 70), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
if (thetaf<0):
    cv2.putText(small, 'ROTATION GAUCHE', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
    cv2.putText(small, str(abs(np.around(thetaf))) + 'deg' , (150, 70), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
if (thetaf>0):
    cv2.putText(small, 'ROTATION DROITE', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
    cv2.putText(small, str(abs(np.around(thetaf))) + 'deg' , (150, 70), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)


tampon = 10
rotation = 10
anglecritique = 10
#cas 1
if ( (decalagecm > tampon) and (np.around(abs(thetaf)) < anglecritique):
    cv2.putText(small, 'ROTATION GAUCHE de 10 deg', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
#cas 2
if ( (decalagecm < -tampon) and (np.around(abs(thetaf)) < anglecritique):
    cv2.putText(small, 'ROTATION DROITE de 10 deg', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
#cas 3 et 4
if ( (abs(decalagecm) < tampon) and (np.around(abs(thetaf)) < anglecritique):
    cv2.putText(small, 'OK', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
#cas 5
if ( (abs(decalagecm) < tampon) and (np.around(thetaf) > anglecritique):
    cv2.putText(small, 'ROTATION DROITE de 10 deg', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
#cas 6 
if ( (abs(decalagecm) > tampon) and (np.around(thetaf) < -anglecritique):
    cv2.putText(small, 'ROTATION GAUCHE de 10 deg', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
#cas 7
if ( (decalagecm < -tampon) and (np.around(thetaf) < -anglecritique):
    cv2.putText(small, 'OK', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
#cas 8
if ( (decalagecm < -tampon) and (np.around(thetaf) > anglecritique):
    cv2.putText(small, 'ROTATION DROITE de 10 deg', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
#cas9
if ( (decalagecm > tampon) and (np.around(thetaf) < -anglecritique):
    cv2.putText(small, 'ROTATION GAUCHE de 10 deg', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)
#cas 10
if ( (decalagecm > tampon) and (np.around(thetaf) > anglecritique):
    cv2.putText(small, 'OK', (150, 50), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 0, 255), 1, cv2.CV_AA)







