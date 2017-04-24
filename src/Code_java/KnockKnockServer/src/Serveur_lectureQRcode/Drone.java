import java.io.*;

public class Drone {

	public static String getRoom(){

		String salleLue;

		/*  Ce qui suit etait le premier test

		double proba=Math.random();
		if (proba<0.75){
			salle="4";
		}
		else{
			salle="3";
		}*/

		//2eme test avec lecture dans un fichier
        String fichier = "result-QRcode.txt";

        //lecture fichier
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichier));
            salleLue = br.readLine();
            return (valeurSalle(salleLue));
            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return ("9");  //on n'arrive pas a lire le fichier
        } catch (IOException e1) {
            e1.printStackTrace();
            return ("9");
        }
	}

	public static boolean available(){
		return true;
	}

	public static String valeurSalle(String salleLue) {
	    if (salleLue.contains("1")) {
	        return "1";
        }
        else if (salleLue.contains("2")) {
            return "2";
        }
        else if (salleLue.contains("3")) {
            return "3";
        }
        else if (salleLue.contains("4")) {
            return "4";
        }
        else if (salleLue.contains("5")) {
            return "5";
        }
	else{
        return "9";}
    }
}
