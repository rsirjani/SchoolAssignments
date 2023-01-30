/*
*Title: World Cup Team Database
*Author: Ramtin Sirjani, 250680632
*Date: Nov 27th 2022
*Purpose: To organize teams for the world cup into a database
*ver 2.0
*/

#include <stdio.h> //standard package C
#include <stdlib.h>
#include "worldcup_team.h"

//Variable Declarations
struct Team_node *First_team = NULL; 
struct Team_node *Current_team = NULL; 
int Team_code; 
char Team_name[Max_Team_Name]; 
char Team_seed; 
char Team_seed1; 
char Uniform; 

//Function Definitions for linked list structure
struct Team_node* get_Team(struct Team_node* current, int Team_code){ //Gets a node containing key attribute (Team code) from linked list
    while(current != NULL){
        if(current->team.Team_code == Team_code){
            return current;
        }
        current = current->next;
    }
    return NULL;
}

struct Team_node* get_Team_seed(struct Team_node* current, char Team_seed, char Team_seed1){ //Gets a node containing key attribute (Team Seed) from linked list
    while(current != NULL){
        if(current->team.Group_seeding[0] == Team_seed && current->team.Group_seeding[1] == Team_seed1){
            return current;
        }
        current = current->next;
    }
    return NULL;
}

struct Team_node* put_Team(struct Team_node* first, Team team){ //Inserts a Node into the linked list
    struct Team_node *new_team = malloc(sizeof(struct Team_node));
    new_team->team = team;
    new_team->next = first;
    first = new_team;
    return first;
}