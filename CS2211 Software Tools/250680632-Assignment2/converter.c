/* Name:converter.c
   Purpose: Converts input value from one unit of measurement to a different, but comparable, unit of measurement
   Author: Ramtin Sirjani */
#include <stdio.h>
void main()
{   //Initializing variables for conversions.
    int action; 
    char direction;
    float value;
    int test;
    //asks the user to select which conversion should be performed.
    printf("What do you want to do? Enter an integer between 1 and 5:\n1 for conversion between Kilograms and Pounds\n2 for conversion between Hectares and Acres\n3 for conversion between Litres and Gallons\n4 for conversion between Kilometre and Mile\n5 for quit\n");
    scanf(" %d", &action);
    fflush(stdin);
    //Checks if the input is valid.
    if(action == 1 || action == 2 || action == 3 || action == 4 || action == 5) test = 1; else test = 0;
    while(test == 0){printf("Invalid input, Enter an integer between 1 and 5:\n");scanf(" %d", &action);fflush(stdin);if(action == 1 || action == 2 || action == 3 || action == 4 || action == 5) test = 1;}
    //initializes the requested conversion based on the above input.
    switch (action)
    {
    //Kilogram/Pounds conversion.
    case 1: 
        //Requests user to input direction of conversion. 
        printf("You have selected conversion between Kilograms and Pounds!\nFor kilograms --> pounds input K\nFor pounds --> kilograms input P\n");
        scanf(" %c", &direction);
        //Checks if input is valid.
        if(direction == 'K' || direction == 'P') test = 1; else test = 0;
        while(test == 0){fflush(stdin);printf("Invalid input\nFor kilograms --> pounds input K\nFor pounds --> kilograms input P\n");scanf(" %c", &direction);if(direction == 'K' || direction == 'P') test = 1;}
        //Performs Conversion
        switch (direction)
            { 
            case 'K':
                printf("Please input a value in kilograms:\n");
                scanf(" %f", &value);
                printf("Your conversion is %.2f pounds\n", value*2.20462);
                break;
            default:
                printf("Please input a value in pounds:\n");
                scanf(" %f", &value);
                printf("Your conversion is %.2f kilograms\n", value/2.20462);
                break;
            }
        break;
    //Hectares/Acres conversion.
    case 2:
        //Requests user to input direction of conversion. 
        printf("You have selected conversion between hectares and acres!\nFor hectares --> acres input H\nFor acres --> hectares input A\n");
        scanf(" %c", &direction);
        //Checks if input is valid.
        if(direction == 'H' || direction == 'A') test = 1; else test = 0;
        while(test == 0){fflush(stdin);printf("Invalid input\nFor hectares --> acres input H\nFor acres --> hectares input A\n"); scanf(" %c", &direction);if(direction == 'H' || direction == 'A') test = 1;}
        //Performs Conversion
        switch (direction)
            { 
            case 'H':
                printf("Please input a value in hectares:\n");
                scanf(" %f", &value);
                printf("Your conversion is %.2f acres\n", value*2.47105);
                break;
            default:
                printf("Please input a value in acres:\n");
                scanf(" %f", &value);
                printf("Your conversion is %.2f hectares\n", value/2.47105);
                break;
            }
        break;
    //Litres/Gallons conversion.
    case 3:
        //Requests user to input direction of conversion. 
        printf("You have selected conversion between litres and gallons!\nFor litres --> gallons input L\nFor gallons --> litres input G\n");
        scanf(" %c", &direction);
        //Checks if input is valid.
        if(direction == 'L' || direction == 'G') test = 1; else test = 0;
        while(test == 0){fflush(stdin);printf("Invalid input\nFor litres --> gallons input L\nFor gallons --> litres input G\n"); scanf(" %c", &direction);if(direction == 'L' || direction == 'G') test = 1;}
        //Performs Conversion
        switch (direction)
        { 
            case 'L':
                printf("Please input a value in litres:\n");
                scanf(" %f", &value);
                printf("Your conversion is %.2f gallons\n", value*0.264172);
                break;
            default:
                printf("Please input a value in gallons:\n");
                scanf(" %f", &value);
                printf("Your conversion is %.2f litres\n", value/0.264172);
                break;
        }
        break;
    //Kilometres/Miles conversion.
    case 4:
        //Requests user to input direction of conversion. 
        printf("You have selected conversion between kilometres and miles!\nFor kilometres --> miles input K\nFor miles --> kilometres input M\n");
        scanf(" %c", &direction);
        //Checks if input is valid.
        if(direction == 'K' || direction == 'M') test = 1; else test = 0;
        while(test == 0){fflush(stdin);printf("Invalid input\nFor kilometres --> miles input K\nFor miles --> kilometres input M\n"); scanf(" %c", &direction);if(direction == 'K' || direction == 'M') test = 1;}
        //Performs Conversion
        switch (direction)
        { 
            case 'K':
                printf("Please input a value in kilometres:\n");
                scanf(" %f", &value);
                printf("Your conversion is %.2f miles\n", value*0.621371);
                break;
            default:
                printf("Please input a value in miles:\n");
                scanf(" %f", &value);
                printf("Your conversion is %.2f kilometres\n", value/0.621371);
                break;
        }
        break;
    //Quit.
    default:
        printf("You have quit the program, bye bye!\n");
        break;
    }
}

