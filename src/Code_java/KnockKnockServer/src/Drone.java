
public class Drone {
	public static String getRoom(){
		String salle;
		double alea=Math.random();
		if(alea<0.75){ salle="4";}
		else{salle="3";}
		return salle;
	}
	public static boolean available(){
		return true;
	}

}
