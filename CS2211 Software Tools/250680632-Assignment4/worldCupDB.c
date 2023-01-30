/*
*Title: World Cup Team Database
*Author: Ramtin Sirjani, 250680632
*Date: Nov 11th 2022
*Purpose: To organize teams for the world cup into a database
*ver 1.1
*/
#include <stdio.h> //standard package C
#include <string.h> //String functions package
#define Max_Teams 32 //Maximum number of teams participating in the world cup

typedef struct //Structure containing information relevant to each team participating in the world cup
{
    int Team_code; //Each team has a unique code from 0-31
    char Team_name[25]; //Each team has a name that is capped at 24 characters + 1 null character
    char Group_seeding[2]; //A group seeding is assigned to each team comprised of 1 character from A-H and a second character from 1-4
    char uniform; //Each team has a uniform colour; red, orange, yellow, green, blue, indigo, or violet.
}Team; //The team type has all the above specifications


void main(){
    printf("******************\n* 2211 World Cup *\n******************\n\n"); //Banner!
    char Select_command; //Declare command variable
    Team All_teams[Max_Teams]; //Declare an array of teams with a length of 32
    int Team_code; //Declare Team code variable
    char Team_name[25]; //Declare Team name variable
    char Team_seed; //Declare a variable for the first character of team seed
    char Team_seed1; //Declare a variable for the second character of team seed
    char Uniform; //Declare a varibale for the uniform colour
    char ch; //Used to flush stdin
    for(int i=0; i<Max_Teams; i++){ //Initialize the array of teams with team structures that have a team code of -1 so they can be identified as 'no team'
        Team initializer;
        initializer.Team_code = -1;
        All_teams[i] = initializer;
    }
    while (Select_command != 'q'){ //when the command input is q, quit the program
    printf("i = input, s = search, u = update, p = print, q = quit\n");
    printf("Enter operation code: ");
    scanf("%c",&Select_command); //prompts the user to input a command
    do {
        ch = getchar();
    } while (ch != '\n');
    while(Select_command != 'i' && Select_command != 's' && Select_command != 'u' && Select_command != 'p' && Select_command != 'q'){ //Error checking if the input is not a valid command. Prompts for a valid command
        printf("    invalid input!\n\n");
        printf("Enter operation code: ");
        scanf("%c",&Select_command);
        do {
            ch = getchar();
        } while (ch != '\n');
    }
        switch (Select_command) //Based on the command input do one of several things
        {
        case 'i': ; //input a new team
            int count = 0;
            for(int i=0 ; i<Max_Teams ; i++){ //if the array of teams is full, let the user know that another team cannot be input.
                if(All_teams[i].Team_code != -1){
                    count++;
                }
            }
            if(count == 32){
                printf("    The list of teams is full!\n\n");
                break;
            }
            printf("    Enter team code: "); //Prompt the user to enter a team_code
            scanf("%d",&Team_code);
            do {
                ch = getchar();
            } while (ch != '\n');
            while(Team_code < 0 || Team_code > 31){
                printf("    Only team codes from 0-31 are valid\nTry again: "); //Error checking if the input team code is invalid prompt for another team code
                scanf("%d",&Team_code);
                do {
                    ch = getchar();
                } while (ch != '\n');
            }
            while(All_teams[Team_code].Team_code == Team_code){ //Error checking if the input team code is already in the array of teams exit the command and return to the start
                printf("    That team code is taken.\n    Enter team code: ");
                scanf("%d",&Team_code);
                do {
                    ch = getchar();
                } while (ch != '\n');
            }
                All_teams[Team_code].Team_code = Team_code; //If valid input, sets the team code of the team structure in the team array at the position team_code to the team_code value. (Time complexity of 1 since each code is unique each team will also have a unique spot in the array)
                printf("    Enter team name: "); //Prompts for the user to enter a name for the team
                scanf("%24[^\n]s", &Team_name);
                do {
                    ch = getchar();
                } while (ch != '\n');
                for(int i = 0; i < 25 ; i++){ //removes newline character from input for formatting purposes
                    if(Team_name[i] == '\n'){
                        Team_name[i] = '\0';
                    }
                }
                for(int i = 0; i < Max_Teams ; i++){ //Confirms that the team name entered is not already in the array of teams, as duplicate team names would not make sense. If it is, prompts user for a different team name.
                    while(strcmp(Team_name, All_teams[i].Team_name) == 0){
                        printf("    That team is already entered.\n");
                        printf("    Enter a new team name: ");
                        scanf("%24[^\n]s", &Team_name);
                        do {
                            ch = getchar();
                        } while (ch != '\n');
                        for(int i = 0; i < 25 ; i++){
                            if(Team_name[i] == '\n'){
                                Team_name[i] = '\0';
                            }
                        }
                    }
                }
                for(int i = 0; i < 25 ; i++){
                    All_teams[Team_code].Team_name[i] = Team_name[i]; //Sets the team structure's name to the input
                }
                printf("    Enter group seeding: "); //Prompts user for a group seeding 
                scanf(" %c %c",&Team_seed, &Team_seed1);
                do {
                    ch = getchar();
                } while (ch != '\n');
                while ((Team_seed!='A' && Team_seed!='B' && Team_seed!='C' && Team_seed!='D' && Team_seed!='E' && Team_seed!='F' && Team_seed!='G' && Team_seed!='H') || (Team_seed1 != '1' && Team_seed1 != '2' && Team_seed1 != '3' && Team_seed1 != '4')) //error checking if group seeding is valid. If invalid prompts for another gorup seeding.
                {
                    printf("    That is not a valid seed\n");
                    printf("    Enter group seeding: ");
                    scanf(" %c %c",&Team_seed, &Team_seed1);
                    do {
                        ch = getchar();
                    } while (ch != '\n');
                }
                All_teams[Team_code].Group_seeding[0] = Team_seed; //Sets the team structure's seed to the input
                All_teams[Team_code].Group_seeding[1] = Team_seed1; //Sets the team structure's seed to the input
                printf("    Enter uniform colour: "); //Prompts used for the jersey colour of the team
                scanf("%c",&Uniform);
                do {
                    ch = getchar();
                } while (ch != '\n');
                while(Uniform!='R' && Uniform!='O' && Uniform!='Y' && Uniform!='G' && Uniform!='B' && Uniform!='I' && Uniform!='V'){ //Error checking if input colour is valid. If invalid prompts user to input another colour.
                    printf("    That is not a valid uniform colour\n");
                    printf("    Enter uniform colour: ");
                    scanf("%c",&Uniform);
                    do {
                        ch = getchar();
                    } while (ch != '\n');
                }
                All_teams[Team_code].uniform = Uniform; //Sets the team structure's uniform to the input
                printf("\n");
            break;
        case 's': //if command input is s, search for a team in the team array 
            printf("    Enter team code: "); //input the team code (this is the identifier used to find the team)
            scanf("%d",&Team_code);
            do {
                ch = getchar();
            } while (ch != '\n');
            while(Team_code < 0 || Team_code > 31){ //Error checking that a valid team code was input, otherwise prompts for another.
                printf("    Only team codes from 0-31 are valid\nTry again: ");
                scanf("%d",&Team_code);
                do {
                    ch = getchar();
                } while (ch != '\n');
            }
            if(All_teams[0].Team_code == Team_code){
                printf(" 0         %-24s  %c%c             %c\n", All_teams[0].Team_name, All_teams[0].Group_seeding[0], All_teams[0].Group_seeding[1], All_teams[0].uniform);
            }
            else if(All_teams[Team_code].Team_code == Team_code){ //If team with input team code is in list, prints its details.
                printf("Team code  Team name                 Group Seeding  Uniform\n");
                printf("%2.d         %-24s  %c%c             %c\n\n", All_teams[Team_code].Team_code, All_teams[Team_code].Team_name, All_teams[Team_code].Group_seeding[0], All_teams[Team_code].Group_seeding[1], All_teams[Team_code].uniform);
            }
            else{ //If no team with input team code is in the team array, lets user know, return to start.
                printf("    That team code is unused\n\n");
            }
            break;
        case 'u': //update values of a team in the list
            printf("    Enter team code: "); //input the team code (this is the identifier used to find the team)
            scanf("%d",&Team_code);
            do {
                ch = getchar();
            } while (ch != '\n');
            while(Team_code < 0 || Team_code > 31){ //Error checking that a valid team code was input, otherwise prompts for another.
                printf("    Only team codes from 0-31 are valid\nTry again: ");
                scanf("%d",&Team_code);
                do {
                    ch = getchar();
                } while (ch != '\n');
            }
            if(All_teams[Team_code].Team_code == Team_code){ //If team with input team code is in list, prompts to update its details.
                printf("    Enter team name: "); //INPUTS FOR UPDATES ARE SAME AS FOR CASE I ABOVE. REFERENCE CASE I FOR COMMENTS EXPLAINING THE FOLLOWING CODE.
                scanf("%24[^\n]s", &Team_name);
                do {
                    ch = getchar();
                } while (ch != '\n');
                for(int i = 0; i < 25 ; i++){
                    if(Team_name[i] == '\n'){
                        Team_name[i] = '\0';
                    }
                }
                for(int i = 0; i < Max_Teams ; i++){
                    while(strcmp(Team_name, All_teams[i].Team_name) == 0){
                        printf("    That team is already entered.\n");
                        printf("    Enter a new team name: ");
                        scanf("%24[^\n]s", &Team_name);
                        do {
                            ch = getchar();
                        } while (ch != '\n');
                        for(int i = 0; i < 25 ; i++){
                            if(Team_name[i] == '\n'){
                                Team_name[i] = '\0';
                            }
                        }
                    }
                }
                for(int i = 0; i < 25 ; i++){
                    All_teams[Team_code].Team_name[i] = Team_name[i];
                }
                printf("    Enter group seeding: ");
                scanf(" %c %c",&Team_seed, &Team_seed1);
                do {
                    ch = getchar();
                } while (ch != '\n');
                while ((Team_seed!='A' && Team_seed!='B' && Team_seed!='C' && Team_seed!='D' && Team_seed!='E' && Team_seed!='F' && Team_seed!='G' && Team_seed!='H') || (Team_seed1 != '1' && Team_seed1 != '2' && Team_seed1 != '3' && Team_seed1 != '4'))
                {
                    printf("    That is not a valid seed\n");
                    printf("    Enter group seeding: ");
                    scanf(" %c %c",&Team_seed, &Team_seed1);
                    do {
                        ch = getchar();
                    } while (ch != '\n');
                }
                All_teams[Team_code].Group_seeding[0] = Team_seed;
                All_teams[Team_code].Group_seeding[1] = Team_seed1;
                printf("    Enter uniform colour: ");
                scanf("%c",&Uniform);
                do {
                    ch = getchar();
                } while (ch != '\n');
                while(Uniform!='R' && Uniform!='O' && Uniform!='Y' && Uniform!='G' && Uniform!='B' && Uniform!='I' && Uniform!='V'){
                    printf("    That is not a valid uniform colour\n");
                    printf("    Enter uniform colour: ");
                    scanf("%c",&Uniform);
                    do {
                        ch = getchar();
                    } while (ch != '\n');
                }
                All_teams[Team_code].uniform = Uniform;
                printf("\n");
            }
            else{
                printf("That team code is currently unused\n\n");
            }
            break;
        case 'p': //Prints all teams in team array (that have valid information). Displays in table format.
            printf("Team code  Team name                 Group Seeding  Uniform\n");
            if(All_teams[0].Team_code != -1){
                printf(" 0         %-24s  %c%c             %c\n", All_teams[0].Team_name, All_teams[0].Group_seeding[0], All_teams[0].Group_seeding[1], All_teams[0].uniform);
            }
            for(int i=1 ; i<32 ; i++){
                if(All_teams[i].Team_code != -1){
                    printf("%2.d         %-24s  %c%c             %c\n", All_teams[i].Team_code, All_teams[i].Team_name, All_teams[i].Group_seeding[0], All_teams[i].Group_seeding[1], All_teams[i].uniform);
                }
            }
            printf("\n");
            break;
        default:
            break;
        }
    }
}