import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class ImageReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		imageRGBToGrey("C:\\Users\\Louise Loesch\\workspace\\photo-qrcode3.png");
	}
	
	public static void imageRGBToGrey(String fileName) {
		try {
			BufferedImage image = ImageIO.read(new File(fileName));
			//sauverImage(image,"tmp");
			
			int largeurImage = image.getWidth();
			int hauteurImage = image.getHeight();
			
			Color couleur;
			Color myWhite = new Color(255, 255, 255); // Couleur blanche
			int rgb_white = myWhite.getRGB();
			
			Color myBlack = new Color(0, 0, 0); // Couleur noire
			int rgb_black = myBlack.getRGB();
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			// creation du tableau du nombre de pixel à la meme itensite pour l'histogramme
			int[] tab_intensity = new int[256]; 
			for(int colonne = 0; colonne < largeurImage; colonne++){
				for(int ligne = 0; ligne < hauteurImage; ligne++){
					couleur = new Color(image.getRGB(colonne, ligne), false);
					int intensity = (int) (0.2989*couleur.getRed()+0.5870*couleur.getGreen()+0.1140*couleur.getBlue());
					tab_intensity[intensity]+=1;
				}
			}
			
			//création de l'histogramme pour avoir le seuil
			int nb_non_null=0;
			//int moy=0;
			for (int cpt=0; cpt<256;cpt++){
				 
				if (tab_intensity[cpt]!=0){
					String abscisse=String.format("%d", cpt);
					String ordonnee=String.format("%d", tab_intensity[cpt]);
					dataset.addValue(tab_intensity[cpt], abscisse, ordonnee);
					nb_non_null+=1;
					//moy+=cpt;
					}
			 }
			 
			//affichage de l'histogramme
			BarChart chart= new BarChart("histogramme",dataset);
			chart.pack();
	        RefineryUtilities.centerFrameOnScreen(chart);
	        chart.setVisible(true);
			
			//determination du seuil
	        int mediane =nb_non_null/2;
	        System.out.println("mediane = "+mediane);
	        /**moyenne donne un moins bon résultat que mediane
	        moy=moy/nb_non_null;
	        System.out.println("moyenne = "+ moy);*/
			
			//mise à jour de l'image en noir et blanc avec le seuil.
			for(int colonne = 0; colonne < largeurImage; colonne++){
				for(int ligne = 0; ligne < hauteurImage; ligne++){
					couleur = new Color(image.getRGB(colonne, ligne), false);
					int intensity = (int) (0.2989*couleur.getRed()+0.5870*couleur.getGreen()+0.1140*couleur.getBlue());
					if(intensity<mediane)
						image.setRGB(colonne, ligne, rgb_black);
					else 
						image.setRGB(colonne, ligne, rgb_white);
				}
			}
			sauverImage(image,"test-photo-qrcode3");
		} catch (FileNotFoundException e){
		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sauverImage(BufferedImage image,String nomImage) throws IOException 
	{ 
		File nomfichier = new File("C:\\Users\\Louise Loesch\\workspace\\" + nomImage + ".png"); 
		boolean test = ImageIO.write(image, "png", nomfichier);
		System.out.println(test);
	}
	

}
