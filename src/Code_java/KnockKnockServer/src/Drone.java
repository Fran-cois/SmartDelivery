import java.io.*;

public class Drone {

	public static String getRoom(){

		String salle;

		/*  Ce qui suit etait le premier test

		double proba=Math.random();
		if (proba<0.75){
			salle="4";
		}
		else{
			salle="3";
		}*/

		//2eme test avec lecture dans un fichier
        String fichier = "valeur_qrcode.txt";

        //lecture fichier
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichier));
            salle = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            salle = "0";
        }

		return salle;
	}

	public static boolean available(){
		return true;
	}

}
