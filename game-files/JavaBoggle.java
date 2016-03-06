/*
Programmer Name: Richard Dang
Program Name: Java Boggle
Date and Version: 2013/06/07    Version 1
Teacher: Ms. Dyke
Description: This program allows the user to play a game of single-player 4x4 Boggle following the same rules as the original game.

Class Variable Dictionary:

	 Name               |             Type                |                       Description/Purpose
____________________________________________________________________________________________________________________________________________
	c                           reference                       gives access to all methods written in Console file
	choice                      String                          stores a choice entered by the player at the main menu
	currentName                 String                          stores the current player name every time new game is chosen
	words                       String                          stores the words entered by the player during the game
	checkHeader                 String                          used to check if the file header matches correctly
	partialWord                 String                          stores letters of the word entered that have been matched to the game board
	line                        int                             stores the line number when reading or printing to/from files
	wordCheck                   int                             checks to see if the word exists in the dictionary
	spacing                     int                             used to space out the game board tiles and correct words
	wordNum                     int                             keeps track of the number of words the user enteres
	currentScore                int                             stores the current player score every time new game is chosen
	X                           int                             stores the x-coordinate of letter locations on the game board
	Y                           int                             stores the y-coordinate of letter locations on the game board
	letterNum                   int                             used to check each letter in the word entered against the game board
	stopTimer                   static boolean                  static variable that is passed to the StopTimer class
	checkFirstLetter            boolean                         used to check if the game board contains the first letter of a word
	coordCheck                  boolean                         used to check if the coordinates of surrounding letters match the word entered
	HIGHSCORETOTAL              final static int                total number of highscores (does not change)
	names                       String []                       stores the names of those in the highscore file
	scores                      String []                       stores the scores of those in the highscore file
	ROW                         final static int                total number of rows on the game board (does not change)
	COL                         final static int                total number of columns on the game board (does not change)
	letters                     char [] []                      stores all the characters on the game board
	input                       BufferedReader                  used to read from files
	output                      PrintWriter                     used to print to files
	tileBrown                   Color                           color of the game board tiles

*/

import java.awt.*;
import hsa.*;
import java.io.*;
import java.util.Random;


public class JavaBoggle
{
    Console c;

    String choice;
    String checkHeader = "";
    String words;
    String currentName;
    String partialWord;

    int line = 0;
    int currentScore = 0;
    int wordCheck = 0;
    int spacing = 0;
    int wordNum = 0;
    int letterNum = 1;
    int X = 0;
    int Y = 0;

    static boolean stopTimer = false;
    boolean checkFirstLetter = false;
    boolean coordCheck = true;

    final static int HIGHSCORETOTAL = 10;
    String[] names = new String [HIGHSCORETOTAL];
    String[] scores = new String [HIGHSCORETOTAL];

    final static int ROW = 4;
    final static int COL = 4;
    char[] [] letters = new char [ROW] [COL];

    BufferedReader input;
    PrintWriter output;

    Color tileBrown = new Color (170, 160, 130);


    /*
    Purpose: displays the title with a Boggle design, centered to the top of the Console

    LocalVariableDictionary:

	 Name               |             Type                |                    Description/Purpose
    _______________________________________________________________________________________________________________________
    blueGreenGradient                   Color                           gradient color of the title background
    x                                   loop                            counts # of loops from 0 to 640 by 1s
    y                                   loop                            counts # of loops from 0 to 60 by 12s


    [for (int x = 0 ; x < 640 ; x++)]: creates a gradient color ranging from blue to green
    [for (int x = 0 ; x < 60 ; x += 12)]: creates the boggle grid on the title background

    */
    private void drawTitle ()
    {
	c.setTextBackgroundColor (Color.darkGray);
	c.setTextColor (Color.white);
	c.clear ();

	for (int x = 0 ; x < 640 ; x++)
	{
	    Color blueGreenGradient = new Color (20 + x / 5, 100 + x / 5, 85 + x / 5);
	    c.setColor (blueGreenGradient);
	    c.drawLine (x, 0, x, 60);
	}

	c.setColor (tileBrown);
	c.fillRoundRect (30, 10, 40, 40, 10, 10);
	c.fillRoundRect (75, 10, 40, 40, 10, 10);
	c.fillRoundRect (120, 10, 40, 40, 10, 10);
	c.fillRoundRect (165, 10, 40, 40, 10, 10);
	c.fillRoundRect (240, 10, 40, 40, 10, 10);
	c.fillRoundRect (285, 10, 40, 40, 10, 10);
	c.fillRoundRect (330, 10, 40, 40, 10, 10);
	c.fillRoundRect (375, 10, 40, 40, 10, 10);
	c.fillRoundRect (420, 10, 40, 40, 10, 10);
	c.fillRoundRect (465, 10, 40, 40, 10, 10);
	c.fillRect (550, 5, 48, 48);

	c.setColor (Color.black);
	c.drawRoundRect (30, 10, 40, 40, 10, 10);
	c.drawRoundRect (75, 10, 40, 40, 10, 10);
	c.drawRoundRect (120, 10, 40, 40, 10, 10);
	c.drawRoundRect (165, 10, 40, 40, 10, 10);
	c.drawRoundRect (240, 10, 40, 40, 10, 10);
	c.drawRoundRect (285, 10, 40, 40, 10, 10);
	c.drawRoundRect (330, 10, 40, 40, 10, 10);
	c.drawRoundRect (375, 10, 40, 40, 10, 10);
	c.drawRoundRect (420, 10, 40, 40, 10, 10);
	c.drawRoundRect (465, 10, 40, 40, 10, 10);

	c.setFont (new Font ("Trebuchet", 1, 35));
	c.setColor (Color.white);

	c.drawString ("J  A  V  A     B  O G  G  L  E", 42, 42);

	c.setColor (Color.black);
	for (int y = 0 ; y < 60 ; y += 12)
	{
	    c.drawLine (550, 5 + y, 598, 5 + y);
	    c.drawLine (550 + y, 5, 550 + y, 53);
	}

	c.setCursor (5, 1);
    }


    /*
    Purpose: creates a new highscore file if it is not found (file type and header must match)

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    _____________________________________________________________________________________________________________________________________________
       e                            IOException                    prevents program from crashing if IO operation fails or is interrupted


       [try]: creates a highscore file with a specific header and saves it
       [catch (IOException e)]: catches IO exceptions if something goes wrong

    */
    private void createHighscoreFile ()
    {
	try
	{
	    output = new PrintWriter (new FileWriter ("BoggleHighscores.jbg"));
	    output.println ("File Extension '.jbg' is copyrighted by Java Boggle Inc.");
	    output.close ();
	}
	catch (IOException e)
	{
	}
    }


    /*
    Purpose: checks the current highscores (name and score) in the 'BoggleHighscore.jbg' file and saves them into an array

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    _____________________________________________________________________________________________________________________________________________
       e                            IOException                    prevents program from crashing if IO operation fails or is interrupted
       line/HIGHSCORETOTAL          loop                           counts # of loops from 0 to HIGHSCORETOTAL by 1s


       [try]: reads the highscore files and checks to see if the header matches
       [if (checkHeader.equals ("File Extension '.jbg' is copyrighted by Java Boggle Inc."))]: if the header matches read from file
       [for (line = 0 ; line < HIGHSCORETOTAL ; line++)]: stores highscore names and scores into arrays
       [else]: if header does not match create new highscore file
       [catch (IOException e)]: creates a new highscore file if it is not found

    */
    private void checkScores ()
    {
	try
	{
	    input = new BufferedReader (new FileReader ("BoggleHighscores.jbg"));

	    checkHeader = input.readLine ();
	    if (checkHeader.equals ("File Extension '.jbg' is copyrighted by Java Boggle Inc."))
	    {
		for (line = 0 ; line < HIGHSCORETOTAL ; line++)
		{
		    names [line] = input.readLine ();
		    scores [line] = input.readLine ();
		}
	    }
	    else
		createHighscoreFile ();
	}

	catch (IOException e)
	{
	    createHighscoreFile ();
	}
    }


    /*
    Purpose: compares the current score to the scores from the highscore file then proceeds to sort them, replacing new highscores with old lower scores

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    _______________________________________________________________________________________________________________________________________________________
	tempScore                   int                             stores each highscore as a temporary score
	line/HIGHSCORETOTAL         loop                            counts # of loops from 0 to HIGHSCORETOTAL by 1s
	x/line                      loop                            counts # of loops from HIGHSCORETOTAL - 2 to line by 2s
	e                           NumberFormatException           prevents program from crashing if parsing operation fails or is interrupted
	e                           IOException                     prevents program from crashing if IO operation fails or is interrupted


	[for (line = 0 ; line < HIGHSCORETOTAL ; line++)]: reads all the highscores from scores [] array
	[try]: parses the string read from the file into a integer
	[catch (NumberFormatException e)]: catches number format exceptions if something goes wrong
	[if (currentScore > tempScore || scores [line] == null)]: if the current score is more than any other highscore or if not all ten spots are filled
	[if (currentScore > tempScore)]: if the current score is more than any other highscore
	[for (int x = HIGHSCORETOTAL - 2 ; x >= line ; x--)]: replaces and sorts the current name and score into the names [] and scores [] array
	[if (currentScore != 0)]: if the current score does not equal to 0
	[try]: prints the new highscores to the highscore file
	[for (line = 0 ; line < HIGHSCORETOTAL ; line++)]: repeats 10 times
	[if (names [line] == null && scores [line] == null)]: if there are no values in names [] array or scores [] array skip printing to file
	[else]: print to file
	[catch (IOException e)]: catches IO exceptions if something goes wrong

    */
    private void sortScores ()
    {
	int tempScore = 0;

	for (line = 0 ; line < HIGHSCORETOTAL ; line++)
	{
	    try
	    {
		tempScore = Integer.parseInt (scores [line]);
	    }
	    catch (NumberFormatException e)
	    {
	    }

	    if (currentScore > tempScore || scores [line] == null)
	    {
		if (currentScore > tempScore)
		{
		    for (int x = HIGHSCORETOTAL - 2 ; x >= line ; x--)
		    {
			names [x + 1] = names [x];
			scores [x + 1] = scores [x];
		    }
		}
		names [line] = currentName;
		scores [line] = currentScore + "";
		break;
	    }
	}

	if (currentScore != 0)
	{
	    try
	    {
		output = new PrintWriter (new FileWriter ("BoggleHighscores.jbg"));
		output.println ("File Extension '.jbg' is copyrighted by Java Boggle Inc.");

		for (line = 0 ; line < HIGHSCORETOTAL ; line++)
		{
		    if (names [line] == null && scores [line] == null)
			break;
		    else
		    {
			output.println (names [line]);
			output.println (scores [line]);
		    }
		}
		output.close ();
	    }
	    catch (IOException e)
	    {
	    }
	}
    }


    /*
    Purpose: displays the main menu and gives the user four choices: New Game, Instructions, Highscores and Quit


	[while (true]: continues to ask player to enter an option until they enter a correct one
	[if (choice.equalsIgnoreCase ("a") || choice.equalsIgnoreCase ("b") || choice.equalsIgnoreCase ("c") || choice.equalsIgnoreCase ("d"))]: continue if they enter a correct option


    */
    public void mainMenu ()
    {
	drawTitle ();
	checkScores ();

	c.println ("Welcome to Java Boggle! Please Enjoy.");
	c.println ();
	c.println ("You may proceed by selecting one of the options below.");
	c.println ();
	c.println ("A. Start a new game");
	c.println ();
	c.println ("B. How to play");
	c.println ();
	c.println ("C. View highscores");
	c.println ();
	c.println ("D. Quit Game");

	while (true)
	{
	    c.setCursor (17, 1);
	    c.println ();
	    c.setCursor (17, 1);
	    c.print ("You have selected option: ");
	    choice = c.readLine ();
	    if (choice.equalsIgnoreCase ("a") || choice.equalsIgnoreCase ("b") || choice.equalsIgnoreCase ("c") || choice.equalsIgnoreCase ("d"))
		break;
	    new Message ("You can only choose choices A-D.", "Option Error");
	}
    }


    /*
    Purpose: used to create pauses inbetween executions

	 Name               |             Type                |                    Description/Purpose
    _______________________________________________________________________________________________________________________________________________________
	time                        int                             # of milliseconds that the delay will occur
	e                           Exception                       prevents program from crashing if any operation fails or is interrupted


	[try]: pauses the program for a short period
	[catch (Exception e)]: [catch]: catches exceptions if something goes wrong

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
    Purpose: asks the user for their name and displays "Ready, Set, Go" before starting the game

    [while (true)]: continues to ask the player for a name until they enter and name with at least 1 letter and no more than 10
    [if (currentName.length () > 0 && currentName.length () < 11 && !currentName.equals (""))]: continues if the player does not enter a blank name and is less than 10 characters

    */
    public void askName ()
    {
	drawTitle ();

	while (true)
	{
	    c.setCursor (6, 1);
	    c.println ();
	    c.setCursor (6, 1);
	    c.print ("Please enter your name (10 char. max): ");
	    currentName = c.readLine ();
	    if (currentName.length () > 0 && currentName.length () < 11 && !currentName.equals (""))
		break;
	    new Message ("Your name can only be a max of 10 characters long and must contain at least 1 character.", "Name Error");
	}

	c.println ();
	c.println ("The time countdown and game will begin when you enter any key.");
	c.getChar ();

	c.setFont (new Font ("Trebuchet", 1, 35));
	c.setColor (Color.white);
	c.drawString ("READY...", 100, 300);
	delay (800);
	c.drawString ("SET...", 300, 300);
	delay (800);
	c.drawString ("GO!", 450, 300);
	delay (500);
    }


    /*
    Purpose: creates and displays a 4x4 Boggle game board using random letters from the alphabet

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    _______________________________________________________________________________________________________________________________________________________
	ascii                       int                             stores a random ascii value which is equivalent to the upper case alphabet
	rand                        Random                          used to randomize values
	row/ROW                     loop                            counts the # of loops from 0 to ROW by 1s
	column/COL                  loop                            counts the # of loops from 0 to COL by 1s


	[for (int row = 0 ; row < ROW ; row++)]: creates the rows of the game board
	[for (int column = 0 ; column < COL ; column++)]: creates the columns of the game board
	[if (row == 0)]: changes the spacing inbetween columns
	[else if (row == 1)]: changes the spacing inbetween columns
	[else if (row == 2)]: changes the spacing inbetween columns
	[else]: changes the spacing inbetween columns

    */
    private void createBoard ()
    {
	Random rand = new Random ();
	int ascii = 0;

	c.setFont (new Font ("Trebuchet", 1, 35));

	for (int row = 0 ; row < ROW ; row++)
	{
	    for (int column = 0 ; column < COL ; column++)
	    {
		ascii = rand.nextInt (26) + 65;
		letters [row] [column] = (char) ascii;

		if (row == 0)
		    spacing = 140;
		else if (row == 1)
		    spacing = 200;
		else if (row == 2)
		    spacing = 260;
		else
		    spacing = 320;

		c.setColor (tileBrown);
		c.fillRoundRect (41 + column * 60, spacing - 40, 50, 50, 10, 10);
		c.setColor (Color.white);
		c.drawRoundRect (41 + column * 60, spacing - 40, 50, 50, 10, 10);
		c.setColor (Color.white);
		c.drawString (letters [row] [column] + "", 50 + column * 60, spacing);
	    }
	}
    }


    /*
    Purpose: checks the "BoggleDictionary.jbg" file to see if the word the user entered exists

	Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    _______________________________________________________________________________________________________________________________________________________
	readWords                   String                          reads individual words from the dictionary
	e                           IOException                     prevents program from crashing if IO operation fails or is interrupted


	[try]: reads words from the dictionary
	[if (checkHeader.equals ("File Extension '.jbg' is copyrighted by Java Boggle Inc."))]: if the dictionary file has the correct header
	[while (true)]: continues to check every word in the dictionary
	[if (readWords == null)]: stop if it reaches the end of the dictionary
	[if (words.equalsIgnoreCase (readWords))]: stop if the word is found
	[else]: stop if the word is not found
	[else]: if the dictionary if not found
	[catch (IOException e)]: the dictionary is not found

    */
    private void dictionaryCheck ()
    {
	String readWords = "";

	try
	{
	    input = new BufferedReader (new FileReader ("BoggleDictionary.jbg"));
	    checkHeader = input.readLine ();
	    if (checkHeader.equals ("File Extension '.jbg' is copyrighted by Java Boggle Inc."))
	    {
		while (true)
		{
		    readWords = input.readLine ();
		    if (readWords == null)
			break;
		    if (words.equalsIgnoreCase (readWords))
		    {
			wordCheck = 1;
			break;
		    }
		    else
			wordCheck = 2;
		}
	    }
	    else
		wordCheck = 3;
	}
	catch (IOException e)
	{
	    wordCheck = 3;
	}
    }


    /*
    Purpose: return method that adds onto the users score as they enter correct words

    Local Variable Dictionary:

	 Name               |             Type                |                       Description/Purpose
    ____________________________________________________________________________________________________________________________________________
	words                       String                          stores the each current word entered by the player
	currentScore                int                             stores the current player's score as they play

    [if (words.length () >= 8)]: add 11 points if the correct word entered is equal to 8 characters
    [else if (words.length () == 7)]: add 5 points if the correct word entered is equal to 7 characters
    [else if (words.length () == 6)]: add 3 points if the correct word entered is equal to 6 characters
    [else if (words.length () == 5)]: add 2 points if the correct word entered is equal to 5 characters
    [else]: add 1 points if the correct word entered is more than 3 characters and less than 5

    */
    private int scoreCount (String words, int currentScore)
    {
	if (words.length () >= 8)
	    currentScore += 11;
	else if (words.length () == 7)
	    currentScore += 5;
	else if (words.length () == 6)
	    currentScore += 3;
	else if (words.length () == 5)
	    currentScore += 2;
	else
	    currentScore += 1;
	return currentScore;
    }


    /*
    Purpose: displays correct words onto the "WORDS" board after the user enters them

    [if (wordNum < 11)]: print the correct words on the first column
    [else]: print the correct words on the second column
    [if (wordNum == 11)]: reset the spacing to 0

    */
    private void displayWords ()
    {
	c.setFont (new Font ("Trebuchet", 1, 15));

	if (wordNum < 11)
	    c.drawString (words, 350, 170 + spacing);
	else
	{
	    if (wordNum == 11)
		spacing = 0;
	    c.drawString (words, 425, 170 + spacing);
	}
	spacing += 20;

    }



    //displays a dialog with the user's score when their time is over
    private void displayTimesUpDialog ()
    {
	c.setColor (Color.white);
	c.fillRect (130, 130, 380, 290);
	c.setColor (Color.black);
	c.fillRect (140, 140, 360, 270);
	c.setFont (new Font ("Trebuchet", 1, 50));
	c.setColor (Color.white);
	c.drawString ("TIMES UP!", 180, 220);
	c.setFont (new Font ("Trebuchet", 1, 40));
	c.drawString ("Score: " + currentScore, 230, 300);
	c.setFont (new Font ("Trebuchet", 1, 15));
	c.drawString ("Press any key to  continue...", 220, 360);
	c.getChar ();
    }


    /*
    Purpose: runs through the entire board starting from the first letter of the word entered and checks if adjoining letters form the word

    Local Variable Dictionary:

	 Name               |             Type                   |                    Description/Purpose
    _________________________________________________________________________________________________________________________________________
	e                       ArrayIndexOutOfBoundsException      prevents program from crashing if the array index goes out of bounds


	[if (letterNum < words.length ())]: only runs if the current letter number is less than the maximum number of letters in the word
	[try]: checks surrounding letters to see if the word exists on the board
	[if (X < 1 || Y < 1 || X > 2 || Y > 2)]: limits the coordinate movements from exceeding the array index
	[if (X < 1 || Y < 1) / if (X > 2 || Y > 2)]: limits the coordinate movements from exceeding the array index
	[if (Character.toUpperCase (words.charAt (letterNum)) == letters [-] [-])]: checks to see if the current letter in the word matches any surrounding letters on the board
	[else]: none of the surronding letters matches so the word does not exist on the board
	[else]: if the x and y coordinates are not 0 or 4 then all surrounding letters can be checked
	[if (Character.toUpperCase (words.charAt (letterNum)) == letters [-] [-])]: checks to see if the current letter in the word matches any surrounding letters on the board
	[else]:none of the surronding letters matches so the word does not exist on the board
	[catch (ArrayIndexOutOfBoundsException e)]: catches the array exception if the coordinates go out of bounds
	[if (coordCheck == true && !partialWord.equalsIgnoreCase (words))]: runs the method again with the next letter in word entered
	[if (partialWord.equalsIgnoreCase (words))]: the word exists on the board because adjoining letters form it

    */
    private void letterSequence ()
    {
	if (letterNum < words.length ())
	{
	    try
	    {
		if (X < 1 || Y < 1 || X > 2 || Y > 2)
		{
		    if (X < 1 || Y < 1)
		    {
			if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y] [X + 1]
				|| Character.toUpperCase (words.charAt (letterNum)) == letters [Y + 1] [X]
				|| Character.toUpperCase (words.charAt (letterNum)) == letters [Y + 1] [X + 1])
			{
			    if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y] [X + 1])
				X++;
			    else if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y + 1] [X])
				Y++;
			    else
			    {
				X++;
				Y++;
			    }
			    partialWord = partialWord + words.charAt (letterNum);
			}
			else
			    coordCheck = false;
		    }

		    if (X > 2 || Y > 2)
		    {
			if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X - 1]
				|| Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X]
				|| Character.toUpperCase (words.charAt (letterNum)) == letters [Y] [X - 1])
			{
			    if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X])
				Y--;
			    else if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y] [X - 1])
				X--;
			    else
			    {
				X--;
				Y--;
			    }
			    partialWord = partialWord + words.charAt (letterNum);
			}
			else
			    coordCheck = false;
		    }

		}
		else
		{
		    if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y] [X + 1]
			    || Character.toUpperCase (words.charAt (letterNum)) == letters [Y + 1] [X]
			    || Character.toUpperCase (words.charAt (letterNum)) == letters [Y + 1] [X + 1]
			    || Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X - 1]
			    || Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X]
			    || Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X + 1]
			    || Character.toUpperCase (words.charAt (letterNum)) == letters [Y] [X - 1]
			    || Character.toUpperCase (words.charAt (letterNum)) == letters [Y + 1] [X - 1])
		    {
			if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y] [X + 1])
			    X++;
			else if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y + 1] [X])
			    Y++;
			else if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X])
			    Y--;
			else if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y] [X - 1])
			    X--;
			else if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y + 1] [X + 1])
			{
			    X++;
			    Y++;
			}
			else if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X - 1])
			{
			    X--;
			    Y--;
			}

			else if (Character.toUpperCase (words.charAt (letterNum)) == letters [Y - 1] [X + 1])
			{
			    X++;
			    Y--;
			}
			else
			{
			    X--;
			    Y++;
			}
			partialWord = partialWord + words.charAt (letterNum);
		    }
		    else
			coordCheck = false;
		}
	    }

	    catch (ArrayIndexOutOfBoundsException e)
	    {
		coordCheck = false;
	    }
	}

	if (coordCheck == true && !partialWord.equalsIgnoreCase (words))
	{
	    letterNum++;
	    letterSequence ();
	}

	if (partialWord.equalsIgnoreCase (words))
	    coordCheck = true;

    }



    /*
    Purpose: checks to see if the first letter of the word entered exists on the board then calls letterSequence method

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    __________________________________________________________________________________________________________________
	row/ROW                     loop                            counts the # of loops from 0 to ROW by 1s
	column/COL                  loop                            counts the # of loops from 0 to COL by 1s


    [for (int row = 0 ; row < ROW ; row++)]: checks every row for the first letter of the word entered
    [for (int column = 0 ; column < COL ; column++)]: checks every column for the first letter of the word entered
    [if (Character.toUpperCase (words.charAt (0)) == letters [row] [column])]: checks adjoining letters from the first letter to see if the word exists on the board
    [if (checkFirstLetter == true)]: leave the for loop if the first letter of the word entered is found on the board

    */
    private void checkWords ()
    {
	checkFirstLetter = false;

	for (int row = 0 ; row < ROW ; row++)
	{
	    for (int column = 0 ; column < COL ; column++)
	    {

		if (Character.toUpperCase (words.charAt (0)) == letters [row] [column])
		{
		    X = column;
		    Y = row;
		    partialWord = "" + words.charAt (0);
		    letterNum = 1;
		    coordCheck = true;
		    letterSequence ();
		    checkFirstLetter = true;
		    break;
		}
	    }
	    if (checkFirstLetter == true)
		break;
	}
    }



    /*
    Purpose: starts the game and continues to ask the user to enter word corresponding to the game board until the time is over or they choose to quit

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    _______________________________________________________________________________________________________________________________________________________
	usedWords                   String []                       stores correct words entered by the player as already used
	doubleWord                  boolean                         checks to see if the word entered by the player has already been used
	x/wordNum                   loop                            counts the # of loops from 0 to wordNum by 1s


	[while (true)]: continues to ask the player to enter a word
	[if (words.equals ("@quit"))]: stops the timer and returns to main menu
	[if (BoggleTimer.minutes == 0 && BoggleTimer.seconds == 0)]: displays a times up dialog and returns to main menu
	[else]: checks the word entered by the player
	[if (wordCheck == 1)]: checks to see if the word was found in the dictionary
	[if (!words.equals (""))]: prevents scrolling if the user continues to enter the 'enter' button
	[if (words.length () < 3)]: warns the user that the word entered must be more than 3 letters in order to count
	[else]: checks if the word exists on the game board
	[if (checkFirstLetter == true && coordCheck == true)]: continues if both first letter and surrounding letters match the word
	[if (wordNum > 0)]: if there is more than 1 word store
	[for (int x = 0 ; x < wordNum ; x++)]: checks if the player enters the same word twice
	[if (usedWords [x].equalsIgnoreCase (words))]: changes a variable to true and leaves the for loop
	[if (doubleWord == false)]: if the word they entered has not already been used add to score and display the word
	[else]: warns the player that the word they entered has already been used
	[else]: warns the player that the word is not found on the board
	[else if (wordCheck == 2)]: warns the player that the word us not found on the dictionary
	[else]: stop the timer and warns the user that the dictionary cannot be found returning them to the main menu
	[if (!words.equals ("@quit"))]: sorts the scores if the player did not choose to quit

    */
    public void playGame ()
    {
	String[] usedWords = new String [999];
	boolean doubleWord = false;
	stopTimer = false;
	wordNum = 0;
	currentScore = 0;

	drawTitle ();
	createBoard ();
	spacing = 0;

	c.setColor (tileBrown);
	c.fillRect (330, 100, 170, 300);
	c.setColor (Color.white);
	c.fillRect (330, 100, 170, 3);
	c.fillRect (330, 397, 170, 3);
	c.fillRect (330, 100, 3, 300);
	c.fillRect (500, 100, 3, 300);
	c.fillRect (330, 140, 170, 3);
	c.drawString ("WORDS", 345, 135);

	c.setCursor (8, 67);
	c.print ("Score: " + currentScore);

	c.setCursor (12, 65);
	c.println ("Time Remaining: ");

	c.setCursor (24, 20);
	c.println ("Note: You may enter '@quit' to resign from the current game");

	while (true)
	{
	    c.setCursor (22, 1);
	    c.println ();
	    c.println ();
	    c.setCursor (22, 1);
	    c.print ("Enter word: ");
	    words = c.readLine ();

	    dictionaryCheck ();

	    if (words.equalsIgnoreCase ("@quit"))
	    {
		stopTimer = true;
		break;
	    }

	    if (BoggleTimer.minutes == 0 && BoggleTimer.seconds == 0)
	    {
		displayTimesUpDialog ();
		break;
	    }
	    else
	    {
		if (wordCheck == 1)
		{
		    if (!words.equals (""))
		    {
			if (words.length () < 3)
			{
			    c.setCursor (21, 1);
			    c.println ("The word must be more than 3 characters...");
			}
			else
			{
			    checkWords ();

			    if (checkFirstLetter == true && coordCheck == true)
			    {
				doubleWord = false;

				if (wordNum > 0)
				{
				    for (int x = 0 ; x < wordNum ; x++)
				    {
					if (usedWords [x].equalsIgnoreCase (words))
					{
					    doubleWord = true;
					    break;
					}
				    }
				}

				if (doubleWord == false)
				{
				    usedWords [wordNum] = words;
				    c.setCursor (8, 74);
				    currentScore = scoreCount (words, currentScore);
				    c.println (currentScore);
				    displayWords ();
				    c.setCursor (21, 1);
				    c.println ("Good job!");
				    wordNum++;
				}

				else
				{
				    c.setCursor (21, 1);
				    c.println ("The word has been already been used...");
				}
			    }

			    else
			    {
				c.setCursor (21, 1);
				c.println ("The word was not found in board...");
			    }
			}
		    }
		}

		else if (wordCheck == 2)
		{
		    c.setCursor (21, 1);
		    c.println ("The word does not exist in dictionary...");
		}
		else
		{
		    stopTimer = true;
		    c.setCursor (21, 1);
		    c.println ("The dictionary cannot be found...");
		    pauseProgram ("return to main menu");
		    break;
		}
	    }
	}

	if (!words.equals ("@quit"))
	    sortScores ();
    }



    /*
    Purpose: reads the instructions from "BoggleInstructions.jbg" file and displays them to the user

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    _______________________________________________________________________________________________________________________________________________________
	INSTRUCTIONTOTAL            final int                       total number of instruction lines (does not change)
	instructions                array                           stores the instructions from the instructions file
	line                        loop                            counts the # of loops from 0 to 8 by 1s
	line/INSTRUCTIONTOTAL       loop                            counts the # of loops from 0 to INSTRUCTIONTOTAL by 1s
	e                           IOException                     prevents program from crashing if IO operation fails or is interrupted


	[try]: reads the instructions from the file
	[if (checkHeader.equals ("File Extension '.jbg' is copyrighted by Java Boggle Inc."))]: if the file header matches read the instructions
	[else]: if the file header does not match display instructions cannot be found
	[catch (IOException e)]: if the file cannot be found display instructions cannot be found
	[if (instructions [line] != null)]: prevents the program from display <null> to the player
	[for (int line = 0 ; line < INSTRUCTIONTOTAL ; line++)]: draws the instructions
	[if (line < 6)]: changes the spacing between objective and scoring
	[else]: changes the spacing between objective and scoring

    */
    public void instructions ()
    {
	final int INSTRUCTIONTOTAL = 8;
	String[] instructions = new String [INSTRUCTIONTOTAL];

	line = 0;
	checkHeader = "";

	drawTitle ();

	c.setFont (new Font ("Trebuchet", 1, 25));
	c.setColor (Color.white);
	c.drawString ("Instructions", 260, 95);
	c.fillRect (250, 97, 160, 3);
	c.setFont (new Font ("Trebuchet", 1, 15));
	c.setCursor (7, 13);

	try
	{
	    input = new BufferedReader (new FileReader ("BoggleInstructions.jbg"));

	    checkHeader = input.readLine ();

	    if (checkHeader.equals ("File Extension '.jbg' is copyrighted by Java Boggle Inc."))
	    {
		for (int line = 0 ; line < 8 ; line++)
		    instructions [line] = input.readLine ();
	    }

	    else
	    {
		c.setCursor (22, 1);
		c.println ("Instructions cannot be found...");
	    }
	}

	catch (IOException e)
	{
	    c.setCursor (22, 1);
	    c.println ("Instructions cannot be found...");
	}

	if (instructions [line] != null)
	{
	    for (int line = 0 ; line < INSTRUCTIONTOTAL ; line++)
	    {
		if (line < 6)
		{
		    c.setCursor (7 + line, 13);
		    c.println (instructions [line]);
		}
		else
		{
		    c.setCursor (9 + line, 13);
		    c.println (instructions [line]);
		}
	    }

	    c.drawString ("Objective: ", 10, 135);
	    c.drawLine (7, 136, 87, 136);
	    c.drawString ("Scoring:", 10, 294);
	    c.drawLine (7, 295, 73, 295);

	    c.drawString ("# OF LETTERS          3          4          5          6          7          8+", 100, 370);
	    c.drawString ("POINTS                        1          1          2          3          5          11", 100, 400);
	    c.fillRect (80, 378, 470, 3);
	}

	c.setCursor (23, 1);
	pauseProgram ("return to the main menu");
    }


    /*
    Purpose: creates a chart and displays the highscores them to the user in order of ranking

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    ______________________________________________________________________________________________________________________________
	x                           loop                            counts the # of loops from 0 to 10 by 1s
	line/HIGHSCORETOTAL         loop                            counts the # of loops from 0 to HIGHSCORETOTAL by 1s


	[for (int x = 0 ; x < 10 ; x++)]: displays the rank #
	[for (line = 0 ; line < HIGHSCORETOTAL ; line++)]: displays the top 10 player names
	[if (names [line] == null)]: displays "-------" if there is not yet top 10 player names
	[else]: display player name
	[for (line = 0 ; line < HIGHSCORETOTAL ; line++)]: diplays the top 10 player scores
	[if (scores [line] == null)]: displays "---" if there is not yet top 10 player scores
	[else]: display player score

    */
    public void highScores ()
    {
	line = 0;

	drawTitle ();

	c.setFont (new Font ("Trebuchet", 1, 25));
	c.setColor (Color.white);
	c.drawString ("High Scores", 255, 95);
	c.fillRect (250, 97, 160, 3);

	c.setFont (new Font ("Trebuchet", 1, 15));
	c.drawString ("Rank", 160, 150);
	c.drawString ("Name", 305, 150);
	c.drawString ("Score", 455, 150);

	checkScores ();

	for (int x = 0 ; x < 10 ; x++)
	{
	    c.setCursor (10 + x, 23);
	    c.println (x + 1);
	}

	for (line = 0 ; line < HIGHSCORETOTAL ; line++)
	{
	    c.setCursor (10 + line, 39);
	    if (names [line] == null)
		c.println ("-------");
	    else
		c.println (names [line]);
	}

	for (line = 0 ; line < HIGHSCORETOTAL ; line++)
	{
	    c.setCursor (10 + line, 59);
	    if (scores [line] == null)
		c.println ("---");
	    else
		c.println (scores [line]);
	}

	c.fillRect (120, 160, 425, 3);
	c.fillRect (255, 140, 3, 270);
	c.fillRect (400, 140, 3, 270);

	c.setCursor (23, 1);
	pauseProgram ("return to the main menu");
    }



    /*
    Purpose: allows the program to pause until the user enters a key to continue

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    ________________________________________________________________________________________________________________________
	 message                          String                        stores a string depending on where it is used

    */
    private void pauseProgram (String message)
    {
	c.println ();
	c.println ("Press any key to " + message + "...");
	c.getChar ();
    }


    /*
    Purpose: displays a goodbye message to the user along with the credits and closes Console after the user enters any key

    Local Variable Dictionary:

	 Name               |             Type                |                    Description/Purpose
    __________________________________________________________________________________________________________________________
	     x                           loop                            counts the # of loops from 0 to 325 by 65s


	[for (int x = 0 ; x < 325 ; x += 65)]: draws the credits on a boggle board

    */
    public void goodbye ()
    {
	drawTitle ();

	c.setColor (tileBrown);
	c.fillRect (190, 180, 260, 260);

	c.setColor (Color.black);
	for (int x = 0 ; x < 325 ; x += 65)
	{
	    c.drawLine (190, 180 + x, 450, 180 + x);
	    c.drawLine (190 + x, 180, 190 + x, 440);
	}

	c.setFont (new Font ("Trebuchet", 1, 50));
	c.setColor (Color.black);
	c.drawString ("M  A  D  E", 200, 230);
	c.drawString ("B  Y   :   R", 200, 295);
	c.drawString (" I   C H  A", 200, 360);
	c.drawString ("R  D   .  D", 200, 425);
	c.setColor (Color.white);
	c.drawString ("M  A  D  E", 205, 230);
	c.drawString ("B  Y   :   R", 205, 295);
	c.drawString (" I   C H  A", 205, 360);
	c.drawString ("R  D   .  D", 205, 425);

	c.println ("Thank you for playing Java Boggle!");
	c.println ();
	c.println ("I hope you had fun playing, have a nice day.");
	c.setCursor (23, 1);
	pauseProgram ("exit");
	c.close ();
    }


    /*
    Purpose: calls the "SplashScreen" class file and displays it to the user as an intro

    Local Variable Dictionary:

	 Name               |             Type                |                       Description/Purpose
    ______________________________________________________________________________________________________________________________________
	    s                          reference                       gives access to all methods written in Splash Screen class

    */
    public void splashScreen ()
    {
	SplashScreen s = new SplashScreen (c);
	s.start ();

	try
	{
	    s.join ();
	}
	catch (Exception e)
	{
	}
    }


    /*
    Purpose: calls the "BoggleTimer" class file and displays it to the user while they are playing

    Local Variable Dictionary:

	 Name               |             Type                |                       Description/Purpose
    ______________________________________________________________________________________________________________________________________
	    b                          reference                       gives access to all methods written in Boggle Timer class

    */
    public void timer ()
    {
	drawTitle ();

	BoggleTimer b = new BoggleTimer (c);
	b.start ();
    }


    // instantiates Console class
    public JavaBoggle ()
    {
	c = new Console ("Java Boggle");
    }


    /*
    Purpose: controls the flow of the program, instantiates the class and executes the methods

	Local Variable Dictionary:

	 Name               |             Type                |                       Description/Purpose
    ___________________________________________________________________________________________________________________________
	     j                        instance                            uses new command to create object in RAMs

    */
    public static void main (String[] args)
    {
	JavaBoggle j = new JavaBoggle ();
	j.splashScreen ();
	while (true)
	{
	    j.mainMenu ();
	    if (j.choice.equalsIgnoreCase ("a"))
	    {
		j.askName ();
		j.timer ();
		j.playGame ();
	    }
	    else if (j.choice.equalsIgnoreCase ("b"))
		j.instructions ();
	    else if (j.choice.equalsIgnoreCase ("c"))
		j.highScores ();
	    else
		break;
	}
	j.goodbye ();
    }
}


