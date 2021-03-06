
public class ProtocolServer {
	private static final int WAITING=3;
    private static final int START = 0;
    private static final int DEPARTURE = 1;
    private static final int INFLIGHT = 2;
    private String localisation = "9";
    static String finalRoom;
    private String loc_temp;

    private int state = WAITING;
    

    public String processInput(String theInput) {
        String theOutput = null;
        if (state == WAITING) {
            theOutput = "I'm listening.";
            state = START;
        } else if (state == START) {
            if(theInput.equals( "I would like to make a delivery. Is the drone available?")){
            	if (Drone.available()){
            		theOutput="yes";
            		state = DEPARTURE;
            	}
            	else
            	{ theOutput="no";
            	state=WAITING;
            	}
            }
            else{
            	theOutput="try again";
            	state=WAITING;
            }
        } else if (state == DEPARTURE) {
        	System.out.println(finalRoom);
        	if(theInput.length()<=21){
        		theOutput="I'm going.";
        		state = INFLIGHT;}
        	else{
        		theOutput="I didn't get the room. Where do you want me to go?";
        		state=DEPARTURE;
        	}
        } else if (state == INFLIGHT) {
        	if(theInput.equals("where are you?")){
        		loc_temp = Drone.getRoom();
        		if (!loc_temp.equals("9")) {
        			localisation = loc_temp;
				}
        		if(finalRoom.equals(localisation)) {
        			theOutput = "Bye. I arrived.";
        			state = START;
        		}
        		else{
            	theOutput="I'm in the room "+localisation;
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
