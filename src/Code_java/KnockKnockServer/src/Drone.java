
public class Drone {
	public static String getRoom(){
		String salle;
		double proba=Math.random();
		if (proba<0.75){
			salle="4";
		}
		else{
			salle="3";
		}
		return salle;
	}
	public static boolean available(){
		return true;
	}

}
