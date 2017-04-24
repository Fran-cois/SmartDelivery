from PIL import Image


p1 = Image.open("/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/Transformation de Hough/Image_test/bande.jpeg")
p2 = Image.open("/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/Transformation de Hough/Image_test/sauvegarde.jpeg")
p3 = Image.blend(p2, p1, 0.5)
p3.save("/Users/SimonDahan/Documents/Telecom-ParisTech/PACT/Estimation du mouvement/Transformation de Hough/Image_test/melange.png")
