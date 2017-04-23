import java.io.*;

public class Drone {

	public static String getRoom(){

		String salle;
		String salleLue;
		System.out out;

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
            out.println(salleLue);
            return (valeurSalle(salleLue));
            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return ("0");  //on n'arrive pas a lire le fichier
        } catch (IOException e1) {
            e1.printStackTrace();
            return ("0");
        }
	}

	public static boolean available(){
		return true;
	}

	public static String valeurSalle(String salleLue) {
	    if (salleLue.contains("1")) {
	        return "1";
        }
        if (salleLue.contains("2")) {
            return "2";
        }
        if (salleLue.contains("3")) {
            return "3";
        }
        if (salleLue.contains("4")) {
            return "4";
        }
        if (salleLue.contains("5")) {
            return "5";
        }

        return "0";
    }
}
