/*
*Title: World Cup Team Database
*Author: Ramtin Sirjani, 250680632
*Date: Nov 27th 2022
*Purpose: To organize teams for the world cup into a database
*ver 2.0
*/

#define Max_Player_Name 25 //Maximum number of teams participating in the world cup
#define Max_Club_Name 25 //Maximum number of teams participating in the world cup

typedef struct //Structure containing information relevant to each team participating in the world cup
{
    int Player_code; //Each player has a unique code greater than or equal to 0
    char Player_name[50]; //Each team has a name that is capped at 24 characters + 1 null character
    int Player_age; //Age from 17-99
    char Club[50]; //Club with which the player is affiliated.
    int Team_code; //BONUS stores team code for player
}Player; //The team type has all the above specifications

struct Player_node{
    Player player; //Player stored in node
    struct node *next; //next node in list
};

extern struct Player_node *First_player; //Empty player list
extern struct Player_node *Current_player; //allows easy iteration through list
extern int Player_code; //Player code variable
extern char Player_name[Max_Player_Name]; //Player name variable
extern char Player_club[Max_Club_Name]; //Player name variable
extern int Player_age; //Player Age variable

//Function Prototypes
struct Player_node* get_Player(struct Player_node* current, int Player_code);
struct Player_node* put_Player(struct Player_node* first, Player player);
struct Player_node* get_Player_TC(struct Player_node* current, int Team_code);