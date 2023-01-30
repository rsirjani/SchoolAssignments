/*
*Title: World Cup Team Database
*Author: Ramtin Sirjani, 250680632
*Date: Nov 27th 2022
*Purpose: To organize teams for the world cup into a database
*ver 2.0
*/

#include <stdio.h> //standard package C
#include <stdlib.h>
#include "worldcup_player.h"

//Variable Declarations
struct Player_node *Current_player = NULL;
struct Player_node *First_player = NULL;
int Player_code; 
char Player_name[Max_Player_Name]; 
char Player_club[Max_Club_Name]; 
int Player_age; 

struct Player_node* get_Player(struct Player_node* current, int Player_code){
    while(current != NULL){
        if(current->player.Player_code == Player_code){
            return current;
        }
        current = current->next;
    }
    return NULL;
}

struct Player_node* put_Player(struct Player_node* first, Player player){
    struct Player_node *new_player = malloc(sizeof(struct Player_node));
    new_player->player = player;
    new_player->next = first;
    first = new_player;
    return first;
}

struct Player_node* get_Player_TC(struct Player_node* current, int Team_code){
    while(current != NULL){
        if(current->player.Team_code == Team_code){
            return current;
        }
        current = current->next;
    }
    return NULL;
}


