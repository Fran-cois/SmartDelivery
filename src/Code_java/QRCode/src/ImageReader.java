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
		//imageRGBToGrey("C:\\Users\\Louise Loesch\\workspace\\pact\\photo-qrcode3.png");
		imageLabeling("C:\\Users\\Louise Loesch\\workspace\\pact\\photo-qrcode2.png");
	}
	
	public static String imageRGBToGrey(String fileName) {
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
			sauverImage(image,"test-photo-qrcode2");
			
		} catch (FileNotFoundException e){
		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "C:\\Users\\Louise Loesch\\workspace\\pact\\test-photo-qrcode2.png";
	}
	
	public static void sauverImage(BufferedImage image,String nomImage) throws IOException 
	{ 
		File nomfichier = new File("C:\\Users\\Louise Loesch\\workspace\\pact\\" + nomImage + ".png"); 
		boolean test = ImageIO.write(image, "png", nomfichier);
		System.out.println(test);
	}
	
	public static void imageLabeling(String filename){
		int[][] matrice_connexe=null;
		try {
			BufferedImage image = ImageIO.read(new File(imageRGBToGrey(filename)));
			int largeurImage = image.getWidth();
			int hauteurImage = image.getHeight();
			Color myWhite = new Color(255, 255, 255); // Couleur blanche
			Color myBlack = new Color(0, 0, 0); // Couleur noire
			matrice_connexe= new int[hauteurImage][largeurImage];
			int[] hash=new int[100000];
			int acc_1=0;//incrémente les composantes connexes
			for(int i = 1; i < hauteurImage; i++){
				for(int j = 1; j < largeurImage; j++){
					if (image.getRGB(j, i)==myWhite.getRGB()){
						matrice_connexe[i][j]=0;
					}
					if (image.getRGB(j, i)==myBlack.getRGB()){
						if (matrice_connexe[i-1][j]!=0 && matrice_connexe[i][j-1]==0){
							matrice_connexe[i][j]=matrice_connexe[i-1][j];
						}
						else if (matrice_connexe[i-1][j]==0 && matrice_connexe[i][j-1]!=0){
							matrice_connexe[i][j]=matrice_connexe[i][j-1];
						}
						else if (matrice_connexe[i-1][j]!=0 && matrice_connexe[i][j-1]!=0){
							matrice_connexe[i][j]=Math.min(matrice_connexe[i-1][j],matrice_connexe[i][j-1]);
							//correspondance des numéros
							hash[Math.max(matrice_connexe[i-1][j],matrice_connexe[i][j-1])]=Math.min(matrice_connexe[i-1][j],matrice_connexe[i][j-1]);
							}
						else {
							matrice_connexe[i][j]=acc_1+1;
							acc_1+=1;
						}
					}
				}
			}
			//traitement de la table de hash: suppresion des cycles
			for (int i=0;i<=acc_1;i++){
				int val=i;
				while(hash[val]!=val){
					val=hash[val];
				}
				hash[i]=val;
			}
			//minimisation des numéros des composantes connexes
			int acc_connexe=0;
			for (int i=1;i<=acc_1;i++){
				int tmp_val=0;
				if (hash[i]>tmp_val){
					acc_connexe+=1;
					tmp_val=hash[i];
				}
				hash[i]=Math.min(hash[i], acc_connexe);
			}
			//Mise à jour des composantes connexes
			for(int i = 0; i < hauteurImage; i++){
				for(int j = 0; j < largeurImage; j++){
					matrice_connexe[i][j]=hash[matrice_connexe[i][j]];
				}
			}
			System.out.println(acc_connexe);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
