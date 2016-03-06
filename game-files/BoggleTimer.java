/*
Programmer Name: Richard Dang
Program Name: Boggle Timer
Date and Version: 2013/06/07    Version 1
Teacher: Ms. Dyke
Description: This class executes a countdown timer while the game is running which is called by Java Boggle class.

Class Variable Dictionary:

	 Name               |             Type                |                       Description/Purpose
________________________________________________________________________________________________________________________________________________
	c                           private reference                       gives access to all methods written in Console file
	minutes                     static int                      static variable scores minutes remaining and is passed to JavaBoggle class
	seconds                     static int                      static variable scores seconds remaining and is passed to JavaBoggle class
*/

import java.awt.*;
import hsa.Console;
import java.lang.*; //to access Thread class

public class BoggleTimer extends Thread
{
    private Console c;
    static int minutes;
    static int seconds;


    /*
    Purpose: displays a timer to the player while the game is running

    Local Variable Dictionary:

	 Name               |             Type                |                       Description/Purpose
    ____________________________________________________________________________________________________________________________________
	e                           Exception                       prevents program from crashing if any operation fails or is interrupted

	
	[while (true)]: continues to run the countdown timer
	[if (minutes == 0 && seconds == 0 || JavaBoggle.stopTimer == true)]: stops the timer when both minutes and seconds is equal to 0 or the user chooses to quit
	[if (JavaBoggle.stopTimer == false)]: displays the timer at 0:00 if the user does not choose to quit
	[if (seconds < 10)]: displays a 0 in front of the seconds if it is less than 10
	[else]: displays the # of seconds remaining
	[if (seconds == 0)]: subracts 1 from minutes if seconds is equals to 0 
	[if (minutes == 3)]: if seconds is 0 and minutes is 3 set minutes to 2
	[else if (minutes == 2)]: if seconds is 0 and minutes is 2 set minutes to 1
	[else]: if seconds is 0 and minutes is 1 set minutes to 0
	[try]: delays the countdown to 1 second everytime the loop runs
	[catch]: catches exceptions if something goes wrong
	
    */
    public void boggleTimer ()
    {
	minutes = 3;
	seconds = 1;

	while (true)
	{
	    c.setFont (new Font ("DialogInput", 0, 13));
	    seconds -= 1;

	    c.setColor (Color.darkGray);
	    c.fillRect (549, 235, 40, 25);
	    c.setColor (Color.white);

	    if (minutes == 0 && seconds == 0 || JavaBoggle.stopTimer == true)
	    {
		if (JavaBoggle.stopTimer == false)
		    c.drawString ("0:00", 550, 250);
		break;
	    }


	    c.drawString (minutes + ":", 550, 250);

	    if (seconds < 10)
		c.drawString ("0" + seconds, 565, 250);
	    else
		c.drawString (seconds + "", 565, 250);

	    if (seconds == 0)
	    {
		if (minutes == 3)
		    minutes = 2;
		else if (minutes == 2)
		    minutes = 1;
		else
		    minutes = 0;
		seconds = 60;
	    }

	    try
	    {
		Thread.sleep (1000);
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    public BoggleTimer (Console con)
    {
	c = con;
    }


    //runs the boggleTimer method
    public void run ()
    {
	boggleTimer ();

    }
}
