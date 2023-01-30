/* Name:intToEnglish.c
   Purpose: Translates an integer number between 1 and 999 into english
   Author: Ramtin Sirjani */
#include <stdio.h>
void main()
{   /*Variables are declared here*/
    int input_value; //this is the number input by the user to be translated to english.
    char* ones[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}; //the three arrays of strings are used to translate to english depending on the location of the digit and how many digits are in the number
    char* teens[] = {"ten","eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    char* tens[] = {"twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    printf("Please enter an integer value between 1 and 999 (0 to quit):\n"); //prompts the user for correct input.
    scanf(" %d", &input_value); //saves the input value into a variable
    if(input_value != 0) //perform the translation if the input is not zero
    {
    printf("You entered the number "); //formats the output to improve readability
    if (input_value < 10) //translates single digit inputs
    {
        printf(ones[input_value - 1]);
    }
    else if (input_value >= 10 && input_value < 20) //translates inputs with two digits in the 'teens'
    {
        printf(teens[input_value - 10]);
    }
    else if (input_value >= 20 && input_value < 100) //translates all other two digit inputs
    {
        if(input_value%10 == 0)
        {
            printf(tens[(input_value/10)-2]); //translates tens digit if no one digit
        }
        else
        {
            printf(tens[(input_value/10)-2]);  //translates tens digit
            printf("-");
            printf(ones[(input_value%10)-1]); //translates ones digit
        }
    }
    else if (input_value >= 100 && input_value <= 999) //translates three digit inputs 
    {
        printf(ones[(input_value/100)-1]); //translates the hundreds column
        printf(" hundred");
        if (input_value%100 != 0) //checks if there are digits in the ten or ones colums. If there are, allows them to be translated.
        {
            printf(" and ");
        }
        if (input_value%100 < 10 && input_value%100 != 0) //if there is only a ones digit, translates it.
        {
            printf(ones[input_value%100 - 1]);
        }
        if (input_value%100 >= 10 && input_value%100 < 20) //translates 'teens' if present
        {
            printf(teens[input_value%100 - 10]);
        }
        if (input_value%100 >= 20 && (input_value%100) < 100) //translates all other two digit numbers if present. (same as above)
        {
            if((input_value%100)%10 == 0)
            {
                printf(tens[((input_value%100)/10)-2]);
            }
            else
            {
                printf(tens[((input_value%100)/10)-2]);
                printf("-");
                printf(ones[((input_value%100)%10)-1]);
            }
        }
    }
    printf("\n"); //end with newline character
    }
    if (input_value == 0) //quit if 0 is input
    {
        {printf("You quit! Bye bye!\n");}
    }
}
