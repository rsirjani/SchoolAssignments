/*
*Title: World Cup Team Database
*Author: Ramtin Sirjani, 250680632
*Date: Nov 27th 2022
*Purpose: To organize teams for the world cup into a database
*ver 2.0
*/

#include <stdio.h> //standard package C
#include <stdlib.h> //Memory allocation functions
#include <string.h> //String functions package
#include "worldCupDB.h" 
#include "worldcup_team.h"
#include "worldcup_player.h"

//Variable Declarations
char Select_command;
char Select_operation;
char ch;
Team new_team;
Player new_player;
                            

void main(){
    printf("******************\n* 2211 World Cup *\n******************\n\n"); //Banner!
    while (Select_command != 'q'){ //when the command input is q, quit the program
        printf("h = help, q = quit, t = control teams, p = control players\n");
        printf("Enter command: ");
        scanf("%c",&Select_command); //prompts the user to input a command
        do {
            ch = getchar();
        } while (ch != '\n');
        while(Select_command != 'h' && Select_command != 'q' && Select_command != 't' && Select_command != 'p'){
            printf("    invalid input!\n\n");
            printf("Enter command: ");
            scanf("%c",&Select_command);
            do {
                ch = getchar();
            } while (ch != '\n');
        }
        switch (Select_command) //Based on the command input do one of several things
            {
            case 'h': ; //input a new team
                printf("\nThis is a program for storing teams and players participating in the 2022 world cup in a linked list.\nTo use this program first choose to edit teams or players through the command prompt.\nFrom there you are able to search for, insert, update, and print players or teams in the database.\n\n");
                break;
            
            case 't':
                printf("i = insert, s = search, u = update, p = print, d = delete, r = registered\n");
                printf("Enter operation code: ");
                scanf("%c",&Select_operation); //prompts the user to input a command
                do {
                    ch = getchar();
                } while (ch != '\n');
                while(Select_operation != 'i' && Select_operation != 's' && Select_operation != 'u' && Select_operation != 'p' && Select_operation != 'd' && Select_operation != 'r'){ //Error checking if the input is not a valid command. Prompts for a valid command
                    printf("    invalid input!\n\n");
                    printf("Enter operation code: ");
                    scanf("%c",&Select_operation);
                    do {
                        ch = getchar();
                    } while (ch != '\n');
                }
                    switch (Select_operation) //Based on the command input do one of several things
                        {
                        case 'i': ; //input a new team

                            //SET TEAM CODE
                            Current_team = First_team;
                            printf("    Enter team code: "); //Prompt the user to enter a team_code
                            scanf("%d",&Team_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while(Team_code < 0){
                                printf("    Only team codes greater than or equal to zero are valid\n    Try again: "); //Error checking if the input team code is invalid prompt for another team code
                                scanf("%d",&Team_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                                while(get_Team(Current_team, Team_code) != NULL){ //Error checking if the input team code is already in the list of teams exit the command and return to the start
                                    Current_team = First_team;
                                    printf("    That team code is taken.\n    Enter team code: ");
                                    scanf("%d",&Team_code);
                                    do {
                                        ch = getchar();
                                    } while (ch != '\n');
                            }
                            }
                            while(get_Team(Current_team, Team_code) != NULL){ //Error checking if the input team code is already in the list of teams exit the command and return to the start
                                Current_team = First_team;
                                printf("    That team code is taken.\n    Enter team code: ");
                                scanf("%d",&Team_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                                while(Team_code < 0){
                                    printf("    Only team codes greater than or equal to zero are valid\n    Try again: "); //Error checking if the input team code is invalid prompt for another team code
                                    scanf("%d",&Team_code);
                                    do {
                                        ch = getchar();
                                    } while (ch != '\n');
                                }
                            }
                            new_team.Team_code = Team_code; //If valid input, sets the team code of the team structure.
                               
                            //SET TEAM NAME   
                            Current_team = First_team;
                            printf("    Enter team name: "); //Prompts for the user to enter a name for the team
                            scanf("%24[^\n]s", &Team_name);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            for(int i = 0; i < Max_Team_Name ; i++){ //removes newline character from input for formatting purposes
                                if(Team_name[i] == '\n'){
                                    Team_name[i] = '\0';
                                }
                            }
                            while(Current_team != NULL){ //Confirms that the team name entered is not already in the array of teams, as duplicate team names would not make sense. If it is, prompts user for a different team name.
                                while(strcmp(Team_name, Current_team->team.Team_name) == 0){
                                    printf("    That team is already entered.\n");
                                    printf("    Enter a new team name: ");
                                    scanf("%24[^\n]s", &Team_name);
                                    do {
                                        ch = getchar();
                                    } while (ch != '\n');
                                    for(int i = 0; i < Max_Team_Name ; i++){
                                        if(Team_name[i] == '\n'){
                                            Team_name[i] = '\0';
                                        }
                                    }
                                    Current_team = First_team;
                                }
                                Current_team = Current_team->next;
                            }
                            Current_team = First_team;
                            for(int i = 0; i < Max_Team_Name ; i++){
                                new_team.Team_name[i] = Team_name[i]; //Sets the team structure's name to the input
                            }

                            //SET GROUP SEEDING
                            Current_team = First_team;
                            printf("    Enter group seeding: "); //Prompts user for a group seeding 
                            scanf(" %c %c",&Team_seed, &Team_seed1);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while ((Team_seed!='A' && Team_seed!='B' && Team_seed!='C' && Team_seed!='D' && Team_seed!='E' && Team_seed!='F' && Team_seed!='G' && Team_seed!='H') || (Team_seed1 != '1' && Team_seed1 != '2' && Team_seed1 != '3' && Team_seed1 != '4')){ //error checking if group seeding is valid. If invalid prompts for another group seeding.
                                printf("    That is not a valid seed\n");
                                printf("    Enter group seeding: ");
                                scanf(" %c %c",&Team_seed, &Team_seed1);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                                while(get_Team_seed(Current_team, Team_seed, Team_seed1) != NULL){
                                    printf("    That seed is unavailable\n");
                                    printf("    Enter group seeding: ");
                                    scanf(" %c %c",&Team_seed, &Team_seed1);
                                    do {
                                        ch = getchar();
                                    } while (ch != '\n');
                                }
                                Current_team = First_team;
                            }
                            while(get_Team_seed(Current_team, Team_seed, Team_seed1) != NULL){
                                printf("    That seed is unavailable\n");
                                printf("    Enter group seeding: ");
                                scanf(" %c %c",&Team_seed, &Team_seed1);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                                while ((Team_seed!='A' && Team_seed!='B' && Team_seed!='C' && Team_seed!='D' && Team_seed!='E' && Team_seed!='F' && Team_seed!='G' && Team_seed!='H') || (Team_seed1 != '1' && Team_seed1 != '2' && Team_seed1 != '3' && Team_seed1 != '4')){ //error checking if group seeding is valid. If invalid prompts for another group seeding
                                    printf("    That is not a valid seed\n");
                                    printf("    Enter group seeding: ");
                                    scanf(" %c %c",&Team_seed, &Team_seed1);
                                    do {
                                        ch = getchar();
                                    } while (ch != '\n');
                                }
                                Current_team = First_team;
                                }
                            Current_team = First_team;
                            new_team.Group_seeding[0] = Team_seed; //Sets the team structure's seed to the input
                            new_team.Group_seeding[1] = Team_seed1; //Sets the team structure's seed to the input

                            //SET UNIFORM
                            Current_team = First_team;
                            printf("    Enter uniform colour: "); //Prompts user for the jersey colour of the team
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
                            new_team.Uniform = Uniform; //Sets the team structure's uniform to the input
                            printf("\n");
                            First_team = put_Team(First_team, new_team);
                            break;

                        case 's': //if command input is s, search for a team in the team array 
                            Current_team = First_team;
                            printf("    Enter team code: "); //input the team code (this is the identifier used to find the team)
                            scanf("%d",&Team_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while(Team_code < 0 ){ //Error checking that a valid team code was input, otherwise prompts for another.
                                printf("    Only team codes greater than or equal to zero are valid\nTry again: ");
                                scanf("%d",&Team_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                            }
                            struct Team_node* search_result = get_Team(Current_team, Team_code);
                            if(search_result != NULL){
                                printf("Team code  Team name                 Group Seeding  Uniform\n");
                                if(search_result->team.Team_code != 0){ //If team with input team code is in list, prints its details.
                                    printf("%2.d         %-24s  %c%c             %c\n\n", search_result->team.Team_code, search_result->team.Team_name, search_result->team.Group_seeding[0], search_result->team.Group_seeding[1], search_result->team.Uniform);
                                }
                                else{ //If team with input team code is in list, prints its details.
                                    printf(" 0         %-24s  %c%c             %c\n", search_result->team.Team_name, search_result->team.Group_seeding[0], search_result->team.Group_seeding[1], search_result->team.Uniform);
                                }
                            }
                            else{ //If no team with input team code is in the team array, lets user know, return to start.
                                printf("    That team code is unused\n\n");
                            }
                            break;

                        case 'u': //update values of a team in the list
                            Current_team = First_team;
                            printf("    Enter team code: "); //input the team code (this is the identifier used to find the team)
                            scanf("%d",&Team_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while(Team_code < 0){ //Error checking that a valid team code was input, otherwise prompts for another.
                                printf("    Only team codes greater than or equal to zero are valid\nTry again: ");
                                scanf("%d",&Team_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                            }
                            if(get_Team(Current_team, Team_code) != NULL){
                            
                                //SET TEAM NAME   
                                Current_team = First_team;
                                printf("    Enter team name: "); //Prompts for the user to enter a name for the team
                                scanf("%24[^\n]s", &Team_name);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                                for(int i = 0; i < Max_Team_Name ; i++){ //removes newline character from input for formatting purposes
                                    if(Team_name[i] == '\n'){
                                        Team_name[i] = '\0';
                                    }
                                }
                                while(Current_team != NULL){ //Confirms that the team name entered is not already in the array of teams, as duplicate team names would not make sense. If it is, prompts user for a different team name.
                                    while(strcmp(Team_name, Current_team->team.Team_name) == 0){
                                        printf("    That team is already entered.\n");
                                        printf("    Enter a new team name: ");
                                        scanf("%24[^\n]s", &Team_name);
                                        do {
                                            ch = getchar();
                                        } while (ch != '\n');
                                        for(int i = 0; i < Max_Team_Name ; i++){
                                            if(Team_name[i] == '\n'){
                                                Team_name[i] = '\0';
                                            }
                                        }
                                        Current_team = First_team;
                                    }
                                    Current_team = Current_team->next;
                                }
                                Current_team = First_team;
                                for(int i = 0; i < Max_Team_Name ; i++){
                                    new_team.Team_name[i] = Team_name[i]; //Sets the team structure's name to the input
                                }

                                //SET GROUP SEEDING
                                Current_team = First_team;
                                printf("    Enter group seeding: "); //Prompts user for a group seeding 
                                scanf(" %c %c",&Team_seed, &Team_seed1);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                                while ((Team_seed!='A' && Team_seed!='B' && Team_seed!='C' && Team_seed!='D' && Team_seed!='E' && Team_seed!='F' && Team_seed!='G' && Team_seed!='H') || (Team_seed1 != '1' && Team_seed1 != '2' && Team_seed1 != '3' && Team_seed1 != '4')){ //error checking if group seeding is valid. If invalid prompts for another group seeding.
                                    printf("    That is not a valid seed\n");
                                    printf("    Enter group seeding: ");
                                    scanf(" %c %c",&Team_seed, &Team_seed1);
                                    do {
                                        ch = getchar();
                                    } while (ch != '\n');
                                    while(get_Team_seed(Current_team, Team_seed, Team_seed1) != NULL){
                                        printf("    That seed is unavailable\n");
                                        printf("    Enter group seeding: ");
                                        scanf(" %c %c",&Team_seed, &Team_seed1);
                                        do {
                                            ch = getchar();
                                        } while (ch != '\n');
                                    }
                                    Current_team = First_team;
                                }
                                while(get_Team_seed(Current_team, Team_seed, Team_seed1) != NULL){
                                    printf("    That seed is unavailable\n");
                                    printf("    Enter group seeding: ");
                                    scanf(" %c %c",&Team_seed, &Team_seed1);
                                    do {
                                        ch = getchar();
                                    } while (ch != '\n');
                                    while ((Team_seed!='A' && Team_seed!='B' && Team_seed!='C' && Team_seed!='D' && Team_seed!='E' && Team_seed!='F' && Team_seed!='G' && Team_seed!='H') || (Team_seed1 != '1' && Team_seed1 != '2' && Team_seed1 != '3' && Team_seed1 != '4')){ //error checking if group seeding is valid. If invalid prompts for another group seeding
                                        printf("    That is not a valid seed\n");
                                        printf("    Enter group seeding: ");
                                        scanf(" %c %c",&Team_seed, &Team_seed1);
                                        do {
                                            ch = getchar();
                                        } while (ch != '\n');
                                    }
                                    Current_team = First_team;
                                    }
                                Current_team = First_team;
                                new_team.Group_seeding[0] = Team_seed; //Sets the team structure's seed to the input
                                new_team.Group_seeding[1] = Team_seed1; //Sets the team structure's seed to the input

                                //SET UNIFORM
                                printf("    Enter uniform colour: "); //Prompts user for the jersey colour of the team
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
                                new_team.Uniform = Uniform; //Sets the team structure's uniform to the input
                                printf("\n");
                                Current_team = First_team;
                                get_Team(Current_team, Team_code)->team = new_team;
                                break;
                            }
                            else{
                                printf("That team code is currently unused\n\n");
                            }
                            break;

                        case 'p': //Prints all teams in team array (that have valid information). Displays in table format.
                            Current_team = First_team;
                            printf("Team code  Team name                 Group Seeding  Uniform\n");
                            while(Current_team != NULL){
                                if(Current_team->team.Team_code == 0){
                                    printf(" 0         %-24s  %c%c             %c\n", Current_team->team.Team_name, Current_team->team.Group_seeding[0], Current_team->team.Group_seeding[1], Current_team->team.Uniform);
                                }
                                else{
                                    printf("%2.d         %-24s  %c%c             %c\n", Current_team->team.Team_code, Current_team->team.Team_name, Current_team->team.Group_seeding[0], Current_team->team.Group_seeding[1], Current_team->team.Uniform);
                                }
                                Current_team = Current_team->next;
                                }
                            Current_team = First_team;
                            printf("\n");
                            break;
                        
                        case 'd':
                            Current_team = First_team;
                            Current_player = First_player;
                            printf("    Enter team code: "); //input the team code (this is the identifier used to find the team)
                            scanf("%d",&Team_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while(Team_code < 0){ //Error checking that a valid team code was input, otherwise prompts for another.
                                printf("    Only team codes greater than or equal to zero are valid\nTry again: ");
                                scanf("%d",&Team_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                            }
                            if(get_Player_TC(Current_player, Team_code) == NULL){   
                                if(get_Team(Current_team, Team_code) != NULL){
                                    struct Team_node* team_to_delete = get_Team(Current_team, Team_code);
                                    Current_team = First_team;
                                    if(First_team == team_to_delete){
                                        First_team = First_team->next;
                                        free(team_to_delete);
                                    }
                                    else{
                                        while(Current_team->next != team_to_delete){
                                            Current_team = Current_team->next;
                                        }
                                        struct Team_node* previous_team = Current_team;
                                        Current_team = First_team;
                                        previous_team->next = team_to_delete->next;
                                        free(team_to_delete);
                                    }
                                }
                                else{
                                    printf("That team code is currently unused\n\n");
                                }
                                break;
                            }
                            else{
                                printf("That team has players on it, and as such it cannot be deleted.\n\n");
                            }
                        
                        case 'r':
                            Current_team = First_team;
                            Current_player = First_player;
                            printf("    Enter team code: "); //input the team code (this is the identifier used to find the team)
                            scanf("%d",&Team_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while(Team_code < 0){ //Error checking that a valid team code was input, otherwise prompts for another.
                                printf("    Only team codes greater than or equal to zero are valid\nTry again: ");
                                scanf("%d",&Team_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                            }
                            if(get_Team(Current_team, Team_code) != NULL){
                                printf("Player code  Player name                 Age  Team Code Club\n");
                                while(Current_player != NULL){
                                    if(Current_player->player.Team_code == Team_code){
                                        printf("%d            %-24s    %2.d   %d         %s\n\n", Current_player->player.Player_code, Current_player->player.Player_name, Current_player->player.Player_age, Current_player->player.Team_code, Current_player->player.Club);
                                    }
                                    Current_player = Current_player->next;
                                }
                                Current_player = First_player;
                                printf("\n");
                                break;
                                }
                            else{ //If no team with input team code is in the team array, lets user know, return to start.
                                printf("    That team code is unused\n\n");
                            }
                            break;
                        
                        default:
                            break;
                    }
                break;
                            
            case 'p':
            {printf("i = insert, s = search, u = update, p = print, d = delete\n");
                printf("Enter operation code: ");
                scanf("%c",&Select_operation); //prompts the user to input a command
                do {
                    ch = getchar();
                } while (ch != '\n');
                while(Select_operation != 'i' && Select_operation != 's' && Select_operation != 'u' && Select_operation != 'p' && Select_operation != 'd'){ //Error checking if the input is not a valid command. Prompts for a valid command
                    printf("    invalid input!\n\n");
                    printf("Enter operation code: ");
                    scanf("%c",&Select_operation);
                    do {
                        ch = getchar();
                    } while (ch != '\n');
                }
                    switch (Select_operation) //Based on the command input do one of several things
                        {
                        case 'i': ; //input a new player

                            //SET PLAYER CODE
                            Current_player = First_player;
                            printf("    Enter player code: "); //Prompt the user to enter a player code
                            scanf("%d",&Player_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while(get_Player(Current_player, Player_code) != NULL){ //Error checking if the input player code is already in the list of teams exit the command and return to the start
                                Current_player = First_player;
                                printf("    That player code is taken.\n    Enter player code: ");
                                scanf("%d",&Player_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                            }
                            new_player.Player_code = Player_code; //If valid input, sets the player code of the player structure.
                               
                            //SET PLAYER NAME   
                            Current_player = First_player;
                            printf("    Enter player name: "); //Prompts for the user to enter a name for the player
                            scanf("%24[^\n]s", &Player_name);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            for(int i = 0; i < Max_Player_Name ; i++){ //removes newline character from input for formatting purposes
                                if(Player_name[i] == '\n'){
                                    Player_name[i] = '\0';
                                }
                            }
                            for(int i = 0; i < Max_Player_Name ; i++){
                                new_player.Player_name[i] = Player_name[i]; //Sets the player structure's name to the input
                            }

                            //SET PLAYER AGE
                            printf("    Enter player age (17-99): "); //Prompt the user to enter a player age
                            scanf("%d",&Player_age);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            if(Player_age < 17 || Player_age > 99){ //Error checking if the input player age is valid input
                                printf("    That player age is invalid.\n    Enter player age (17-99): ");
                                scanf("%d",&Player_age);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                            }
                            new_player.Player_age = Player_age; //If valid input, sets the player age of the player structure.

                            //SET CLUB
                            printf("    Enter club affiliation: "); //Prompts for the user to enter a club for the player
                            scanf("%24[^\n]s", &Player_club);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            for(int i = 0; i < Max_Club_Name ; i++){ //removes newline character from input for formatting purposes
                                if(Player_club[i] == '\n'){
                                    Player_club[i] = '\0';
                                }
                            }
                            for(int i = 0; i < Max_Club_Name ; i++){
                                new_player.Club[i] = Player_club[i]; //Sets the player structure's name to the input
                            }
                            
                            
                            //SET TEAM CODE
                            Current_player = First_player;
                            Current_team = First_team;
                            printf("    Enter team code: "); //Prompt the user to enter a team_code
                            scanf("%d",&Team_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while(Team_code < 0){
                                printf("    Only team codes greater than or equal to zero are valid\n    Try again: "); //Error checking if the input team code is invalid prompt for another team code
                                scanf("%d",&Team_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                                if(get_Team(Current_team, Team_code) == NULL){ //Error checking if the input team code doesn't exist
                                    printf("    That team code is doesn't exist.\n");
                                    break;
                                }
                            }
                            if(get_Team(Current_team, Team_code) == NULL){ //Error checking if the input team code doesn't exist
                                printf("    That team code is doesn't exist.\n");
                                break;
                            }
                            new_player.Team_code = Team_code; //If valid input, sets the team code of the player structure.
                            
                            //Insert player into linked list
                            printf("\n");
                            First_player = put_Player(First_player, new_player); 
                            break;

                        case 's': //if command input is s, search for a team in the team array 
                            Current_player = First_player;
                            printf("    Enter player code: "); //input the player code (this is the identifier used to find the player)
                            scanf("%d",&Player_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            struct Player_node* search_result = get_Player(Current_player, Player_code);
                            if(search_result != NULL){
                               printf("Player code  Player name                 Age  Team Code  Club\n");
                               if(search_result->player.Player_code != 0){ //If team with input team code is in list, prints its details.
                                    printf("%d            %-24s    %2.d   %d          %s\n\n", search_result->player.Player_code, search_result->player.Player_name, search_result->player.Player_age, search_result->player.Team_code, search_result->player.Club);
                                }
                                else{ //If team with input team code is in list, prints its details.
                                    printf("0             %-24s    %2.d   %d         %s\n\n", search_result->player.Player_name, search_result->player.Player_age, search_result->player.Team_code, search_result->player.Club);
                                }
                            }
                            else{ //If no team with input team code is in the team array, lets user know, return to start.
                                printf("    That player code is unused\n\n");
                            }
                            break;

                        case 'u': //update values of a team in the list
                            Current_player = First_player;
                            printf("    Enter player code: "); //input the team code (this is the identifier used to find the team)
                            scanf("%d",&Player_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            if(get_Player(Current_player, Player_code) != NULL){
                            
                            //SET PLAYER NAME   
                            Current_player = First_player;
                            printf("    Enter player name: "); //Prompts for the user to enter a name for the player
                            scanf("%24[^\n]s", &Player_name);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            for(int i = 0; i < Max_Player_Name ; i++){ //removes newline character from input for formatting purposes
                                if(Player_name[i] == '\n'){
                                    Player_name[i] = '\0';
                                }
                            }
                            for(int i = 0; i < Max_Player_Name ; i++){
                                new_player.Player_name[i] = Player_name[i]; //Sets the player structure's name to the input
                            }

                            //SET PLAYER AGE
                            printf("    Enter player age (17-99): "); //Prompt the user to enter a player age
                            scanf("%d",&Player_age);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            if(Player_age < 0 || Player_age > 99){ //Error checking if the input player age is valid input
                                printf("    That player age is invalid.\n    Enter player age (17-99): ");
                                scanf("%d",&Player_age);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                            }
                            new_player.Player_age = Player_age; //If valid input, sets the player age of the player structure.

                            //SET CLUB
                            printf("    Enter club affiliation: "); //Prompts for the user to enter a club for the player
                            scanf("%24[^\n]s", &Player_club);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            for(int i = 0; i < Max_Club_Name ; i++){ //removes newline character from input for formatting purposes
                                if(Player_club[i] == '\n'){
                                    Player_club[i] = '\0';
                                }
                            }
                            for(int i = 0; i < Max_Club_Name ; i++){
                                new_player.Club[i] = Player_club[i]; //Sets the player structure's name to the input
                            }

                            //SET TEAM CODE
                            Current_player = First_player;
                            Current_team = First_team;
                            printf("    Enter team code: "); //Prompt the user to enter a team_code
                            scanf("%d",&Team_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            while(Team_code < 0){
                                printf("    Only team codes greater than or equal to zero are valid\n    Try again: "); //Error checking if the input team code is invalid prompt for another team code
                                scanf("%d",&Team_code);
                                do {
                                    ch = getchar();
                                } while (ch != '\n');
                                if(get_Team(Current_team, Team_code) == NULL){ //Error checking if the input team code doesn't exist
                                    printf("    That team code is doesn't exist.\n");
                                    break;
                                }
                            }
                            if(get_Team(Current_team, Team_code) == NULL){ //Error checking if the input team code doesn't exist
                                printf("    That team code is doesn't exist.\n");
                                break;
                            }
                            new_player.Team_code = Team_code; //If valid input, sets the team code of the player structure.
                               
                            printf("\n");
                            Current_player = First_player;
                            get_Player(Current_player, Player_code)->player = new_player;
                            break;
                            }
                            else{
                                printf("That team code is currently unused\n\n");
                            }
                            break;

                            

                        case 'p': //Prints all teams in team array (that have valid information). Displays in table format.
                            Current_player = First_player;
                            printf("Player code  Player name                 Age  Team Code Club\n");
                            while(Current_player != NULL){
                                printf("%d            %-24s    %2.d   %d         %s\n\n", Current_player->player.Player_code, Current_player->player.Player_name, Current_player->player.Player_age, Current_player->player.Team_code, Current_player->player.Club);
                                Current_player = Current_player->next;
                                }
                            Current_player = First_player;
                            printf("\n");
                            break;
                        
                        case 'd':
                            Current_player = First_player;
                            printf("    Enter player code: "); //input the player code (this is the identifier used to find the team)
                            scanf("%d",&Player_code);
                            do {
                                ch = getchar();
                            } while (ch != '\n');
                            if(get_Player(Current_player, Player_code) != NULL){
                                struct Player_node* player_to_delete = get_Player(Current_player, Player_code);
                                Current_player = First_player;
                                if(First_player == player_to_delete){
                                    First_player = First_player->next;
                                    free(player_to_delete);
                                }
                                else{
                                    while(Current_player->next != player_to_delete){
                                        Current_player = Current_player->next;
                                    }
                                    struct Player_node* previous_player = Current_player;
                                    Current_player = First_player;
                                    previous_player->next = player_to_delete->next;
                                    free(player_to_delete);
                                }
                            }
                            else{
                                printf("That player code is currently unused\n\n");
                            }
                            break;
                        default:
                                break;
                    }
                break;
            }
        }
    }
}