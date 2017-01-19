/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.io.*;

public class KnockKnockProtocol {
	private static final int START = 0;
    private static final int DEPARTURE = 1;
    private static final int INFLIGHT = 2;
    private static final int period=2*1000;

    private int state = START;
    

    public String processInput(String theInput) {
        String theOutput = null;
        Timer timer = new Timer();
        TimerTask task=new TimerTask() {
        	  @Override
        	  public void run() {
        		 localisation = drone.getRoom;
        	  }

        if (state == START) {
            theOutput = "I would like to make a delivery. Is the drone available?";
            if (drone.available){
            	state = DEPARTURE;
            }
        } else if (state == DEPARTURE) {
        	int finalRoom=view.getFinalRoom; //Application android
            state = INFLIGHT;
            theOutput="I'm going."
        } else if (state == INFLIGHT) {
            timer.schedule(task,2*1000,period) // demande toutes les 2 secondes à partir de 2s la salle où est le drone 
            if(finalRoom.equals(drone.getRoom) {
                theOutput = "Bye. I've arrived.";
                state = START;
            }
        }
        return theOutput;
    }
}