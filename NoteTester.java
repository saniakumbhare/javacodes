//Sania Kumbhare
//CSC 236-INC2
//Lab2-B
import java.util.*;
import java.math.*;
public class NoteTester
{
public static void main(String[] args)
{

double noteValue;
String noteLength;
int tempLength;

Scanner userInput = new Scanner(System.in);


System.out.println("Please enter a value for your note\n"+
"between -48 and 39:");
noteValue = userInput.nextDouble();


while(noteValue < -48 || noteValue > 39)
{
System.out.println("Please enter a valid value for your note\n"+
"between -48 and 39:");
noteValue = userInput.nextDouble();
}


System.out.println("Please enter the number between 1-5\n" +
"you would like to represent\n" +
"the length of your note.\n"+
"1. Whole\n" +
"2. Half\n" +
"3. Quarter\n" +
"4. Eighth\n" +
"5. Sixteenth");
tempLength = userInput.nextInt();


while(tempLength > 5 || tempLength < 1)
{
System.out.println("Please enter the number between 1-5\n" +
"you would like to represent\n" +
"the length of your note.\n"+
"1. Whole\n" +
"2. Half\n" +
"3. Quarter\n" +
"4. Eighth\n" +
"5. Sixteenth");
tempLength = userInput.nextInt();
}


if(tempLength == 1)
{
noteLength = "whole";
}
else if(tempLength == 2)
{
noteLength = "half";
}
else if(tempLength == 3)
{
noteLength = "quarter";
}
else if(tempLength == 4)
{
noteLength = "eighth";
}
else if(tempLength == 5)
{
noteLength = "sixteenth";
}
else
{
noteLength = "Invalid";
}


Note demoNote = new Note(noteValue, noteLength);


System.out.print("Your value was " + demoNote.getValue() + ", your length was a(n) " + demoNote.getLength()
+ " note, your note's \nletter value was " + demoNote.getLetter() + " which is a " +
demoNote.getSharp() + " note. \nThe frequency of your" +
" note is " + demoNote.getFreq() + " hz." );

}
}


class Note
{

private double value = 0;
private String length = "Quarter";
private String letter;
private String sharp;
private double freq;

public Note(double value, String length)
{
this.value = value;
this.length = length;
}


void setValue(double value)
{
this.value = value;
}


void setLength(String x)
{
this.length = length;
}




public double getValue()
{
return value;
}

public String getLength()
{
return length;
}


public String getLetter()
{
if(value == 0 || value == 12 || value == 24 || value == 36 ||
value == -12 || value == -24 || value == -36 || value == -48)
{
letter = "A";
}
else if(value == 1 || value == 13 || value == 25 || value == 37 ||
value == -11 || value == -23 || value == -35 || value == -47)
{
letter = "A#";
}
else if(value == 2 || value == 14 || value == 26 || value == 38 ||
value == -10 || value == -22 || value == -34 || value == -46)
{
letter = "B";
}
else if(value == 3 || value == 15 || value == 27 || value == 39 ||
value == -9 || value == -21 || value == -33 || value == -45)
{
letter = "C";
}
else if(value == 4 || value == 16 || value == 28 || value == -8 ||
value == -20 || value == -32 || value == -44)
{
letter = "C#";
}
else if(value == 5 || value == 17 || value == 29 || value == -7 ||
value == -19 || value == -31 || value == -43)
{
letter = "D";
}
else if(value == 6 || value == 18 || value == 30 || value == -6 ||
value == -18 || value == -30 || value == -42)
{
letter = "D#";
}
else if(value == 7 || value == 19 || value == 31 || value == -5 ||
value == -17 || value == -29 || value == -41)
{
letter = "E";
}
else if(value == 8 || value == 20 || value == 32 || value == -4 ||
value == -16 || value == -28 || value == -40)
{
letter = "F";
}
else if(value == 9 || value == 21 || value == 33 || value == -3 ||
value == -15 || value == -27 || value == - 39)
{
letter = "F#";
}
else if(value == 10 || value == 22 || value == 34 || value == -2 ||
value == -14 || value == -26 || value == -38)
{
letter = "G";
}
else if(value == 11 || value == 23 || value == 35 || value == -1 ||
value == -13 || value == -25 || value == -36)
{
letter = "G#";
}

else
{
letter = "invalid";
}
return letter;
}

public String getSharp()
{
if(value == 1 || value == 13 || value == 25 || value == 37 ||
value == -11 || value == -23 || value == -35 || value == -47 ||
value == 4 || value == 16 || value == 28 || value == -8 ||
value == -20 || value == -32 || value == -44 ||
value == 6 || value == 18 || value == 30 || value == -6 ||
value == -18 || value == -30 || value == -42 ||
value == 9 || value == 21 || value == 33 || value == -3 ||
value == -15 || value == -27 || value == - 39 ||
value == 11 || value == 23 || value == 35 || value == -1 ||
value == -13 || value == -25 || value == -36)
{
sharp = "sharp";
}
else
{
sharp = "natural";
}
return sharp;
}


public double getFreq()
{
freq = (440 * Math.pow(2, value / 12));
return freq;
}
}