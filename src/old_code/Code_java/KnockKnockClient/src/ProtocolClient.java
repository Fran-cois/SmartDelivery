

public class ProtocolClient {
	private static final int WAITING = 3;
	private static final int START = 0;
    private static final int DEPARTURE = 1;
    private static final int INFLIGHT = 2;
    //private static final int period=2*1000;
    public static String localisation;
    private String finalRoom;
    public static boolean available=false;

    private int state = WAITING;
    

    public String processInput(String theInput) {
        String theOutput = null;
        /**Timer timer = new Timer();
        TimerTask task=new TimerTask() {
      	  @Override
      	  public void run() {
      		 localisation = Drone.getRoom();
      	  }
        };*/
        if (state == WAITING) {
            theOutput = "I'm listening.";
            state = START;
        } else if (state == START) {
            if(theInput.equals( "I would like to make a delivery. Is the drone available?")){
            	/**if (){
            		theOutput="yes";
            		state = DEPARTURE;
            	}
            	else
            	{ theOutput="no";
            	state=WAITING;
            	}*/
            	
            }
            else{
            	theOutput="try again";
            	state=WAITING;
            }
        } else if (state == DEPARTURE) {
        	finalRoom=""+View.getFinalRoom();
        	if(theInput.length()==3){
        		theOutput="I'm going.";
        		state = INFLIGHT;}
        	/**else{
        		theOutput="I didn't get the room. Where do you want me to go?";
        		state=DEPARTURE;
        	}*/
        } else if (state == INFLIGHT) {
        	if(theInput.equals("where are you?")){
        		if(finalRoom.equals(localisation)) {
        			theOutput = "Bye. I arrived.";
        			state = START;
        		}
        		else{
            	theOutput="je suis à la salle "+localisation;
        		}
        	}
        	else{
        		theOutput= "I didn't get your question. Can you repeat?";
        		state=INFLIGHT;
        	}
        }
        return theOutput;
    }
}
