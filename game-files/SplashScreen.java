/*
Programmer Name: Richard Dang
Program Name: Splash Screen
Date and Version: 2013/06/07    Version 1
Teacher: Ms. Dyke
Description: This thread executes a splash screen which is called by Java Boggle class.

Class Variable Dictionary:

	 Name               |             Type                |                       Description/Purpose
____________________________________________________________________________________________________________________________________
	c                           private reference                       gives access to all methods written in Console file

*/

import java.awt.*;
import hsa.Console;
import java.lang.*; //to access Thread class

public class SplashScreen extends Thread
{
    private Console c;


    /*
    Purpose: used to create pauses inbetween executions

	 Name               |             Type                |                    Description/Purpose
    _______________________________________________________________________________________________________________________________________________________
	time                        int                             # of milliseconds that the delay will occur
	e                           Exception                       prevents program from crashing if any operation fails or is interrupted


    [try]: pauses the program for a short period
    [catch]: [catch]: catches exceptions if something goes wrong


    */
    private void delay (int time)
    {
	try
	{
	    Thread.sleep (time);
	}
	catch (Exception e)
	{
	}
    }


    /*
    Purpose: displays a splash screen intro to the player

    Local Variable Dictionary:

	 Name                   |             Type                |                       Description/Purpose
    ____________________________________________________________________________________________________________________________________
	tileBrown                       Color                               color of the game board tiles
	lightDarkGreenGradient          Color                               gradient color of the splashscreen background
	a                               loop                                counts the # of loops from 0 to 300 by 1s
	b                               loop                                counts the # of loops from 0 to 250 by 1s
	d                               loop                                counts the # of loops from 0 to 150 by 1s        
	f                               loop                                counts the # of loops from 0 to 280 by 1s    
	g                               loop                                counts the # of loops from 0 to 240 by 1s
	x                               loop                                counts the # of loops from 0 to 100 by 1s
	y                               loop                                counts the # of loops from 0 to 640 by 1s
	
	

	[for (int a = 0 ; a < 300 ; a++)]: animates 4 tile blocks moving from the top to the middle of the screen
	[for (int b = 0 ; b < 250 ; b++)]: animates 3 tile blocks moving from the left to the middle of the screen
	[for (int d = 0 ; d < 150 ; d++)]: animates 3 tile blocks moving from the top to the middle of the screen
	[for (int f = 0 ; f < 280 ; f++)]: animates 4 tile blocks moving from the right to the middle of the screen
	[for (int g = 0 ; g < 240 ; g++)]: animates 4 tile blocks moving from the bottom to the middle of the screen
	[for (int y = 0 ; y < 100 ; y++)]: animates the game title and creator background moving from the bottom up
	[for (int x = 0 ; x < 640 ; x++)]: creates a gradient color ranging from light to dark green

    */
    public void splashScreen ()
    {
	Color tileBrown = new Color (170, 160, 130);

	c.setTextBackgroundColor (Color.darkGray);
	c.clear ();

	c.setFont (new Font ("Trebuchet", 1, 12));
	c.setColor (Color.white);
	c.drawString ("ver. 12.35.2b", 530, 15);

	for (int a = 0 ; a < 300 ; a++)
	{
	    c.setColor (Color.darkGray);
	    c.fillRoundRect (259, -141 + a, 112, 52, 10, 10);
	    c.setColor (tileBrown);
	    c.fillRoundRect (260, -140 + a, 50, 50, 10, 10);
	    c.fillRoundRect (320, -140 + a, 50, 50, 10, 10);
	    c.setColor (Color.darkGray);
	    c.fillRoundRect (259, -81 + a, 112, 52, 10, 10);
	    c.setColor (tileBrown);
	    c.fillRoundRect (260, -80 + a, 50, 50, 10, 10);
	    c.fillRoundRect (320, -80 + a, 50, 50, 10, 10);
	    c.setFont (new Font ("Trebuchet", 1, 40));
	    c.setColor (Color.darkGray);
	    c.drawString ("G  K", 270, -100 + a);
	    c.drawString ("S   G", 270, -40 + a);
	    delay (3);
	}

	for (int b = 0 ; b < 250 ; b++)
	{
	    c.setColor (Color.darkGray);
	    c.fillRoundRect (-51 + b, 100, 50, 200, 10, 10);
	    c.setColor (tileBrown);
	    c.fillRoundRect (-50 + b, 100, 50, 50, 10, 10);
	    c.fillRoundRect (-50 + b, 160, 50, 50, 10, 10);
	    c.fillRoundRect (-50 + b, 220, 50, 50, 10, 10);
	    c.setColor (Color.darkGray);
	    c.drawString ("B", -40 + b, 140);
	    c.drawString ("J", -40 + b, 200);
	    c.drawString ("P", -40 + b, 260);
	    delay (3);
	}

	for (int d = 0 ; d < 150 ; d++)
	{
	    c.setColor (Color.darkGray);
	    c.fillRoundRect (260, -51 + d, 200, 50, 10, 10);
	    c.setColor (tileBrown);
	    c.fillRoundRect (260, -50 + d, 50, 50, 10, 10);
	    c.fillRoundRect (320, -50 + d, 50, 50, 10, 10);
	    c.fillRoundRect (380, -50 + d, 50, 50, 10, 10);
	    c.setColor (Color.darkGray);
	    c.drawString ("O  D   R", 270, -10 + d);
	    delay (3);
	}

	for (int f = 0 ; f < 280 ; f++)
	{
	    c.setColor (Color.darkGray);
	    c.fillRoundRect (661 - f, 160, 50, 200, 10, 10);
	    c.setColor (tileBrown);
	    c.fillRoundRect (660 - f, 160, 50, 50, 10, 10);
	    c.fillRoundRect (660 - f, 220, 50, 50, 10, 10);
	    c.fillRoundRect (660 - f, 280, 50, 50, 10, 10);
	    c.setColor (Color.darkGray);
	    c.drawString ("H", 670 - f, 200);
	    c.drawString ("I", 680 - f, 260);
	    c.drawString ("E", 670 - f, 320);
	    delay (3);
	}

	for (int g = 0 ; g < 240 ; g++)
	{
	    c.setColor (Color.darkGray);
	    c.fillRoundRect (170, 521 - g, 200, 50, 10, 10);
	    c.setColor (tileBrown);
	    c.fillRoundRect (320, 520 - g, 50, 50, 10, 10);
	    c.fillRoundRect (260, 520 - g, 50, 50, 10, 10);
	    c.fillRoundRect (200, 520 - g, 50, 50, 10, 10);
	    c.setColor (Color.darkGray);
	    c.drawString ("N  W   L", 210, 560 - g);
	    delay (3);
	}

	c.setColor (Color.white);
	c.drawString ("B", 209, 140);
	c.drawString ("O", 270, 139);
	c.drawString ("G", 270, 199);
	c.drawString ("G", 332, 259);
	c.drawString ("L", 338, 321);
	c.drawString ("E", 391, 320);

	for (int y = 0 ; y < 100 ; y++)
	{
	    for (int x = 0 ; x < 640 ; x++)
	    {
		Color lightDarkGreenGradient = new Color (210 - x / 5, 250 - x / 5, 155 - x / 5);
		c.setColor (lightDarkGreenGradient);
		c.drawLine (x, 500 - y, x, 500);
	    }
	    delay (7);
	}

	c.setFont (new Font ("Trebuchet", 1, 55));
	c.setColor (Color.white);
	c.drawString ("J A V A  B O G G L E", 40, 460);
	c.setFont (new Font ("Trebuchet", 1, 15));
	c.drawString ("Programmed in Java by Richard Dang", 190, 485);
	delay (1500);
    }


    public SplashScreen (Console con)
    {
	c = con;
    }


    //runs the splashScreen method
    public void run ()
    {
	splashScreen ();
    }
}
