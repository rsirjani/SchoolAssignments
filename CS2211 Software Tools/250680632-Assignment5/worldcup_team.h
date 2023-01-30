/*
*Title: World Cup Team Database
*Author: Ramtin Sirjani, 250680632
*Date: Nov 27th 2022
*Purpose: To organize teams for the world cup into a database
*ver 2.0
*/

#define Max_Team_Name 25 //Maximum number of teams participating in the world cup

typedef struct //Structure containing information relevant to each team participating in the world cup
{
    int Team_code; //Each team has a unique code greater than or equal to 0
    char Team_name[25]; //Each team has a name that is capped at 24 characters + 1 null character
    char Group_seeding[2]; //A group seeding is assigned to each team comprised of 1 character from A-H and a second character from 1-4. Each team must have a unique seed.
    char Uniform; //Each team has a uniform colour; red, orange, yellow, green, blue, indigo, or violet.
}Team; //The team type has all the above specifications

struct Team_node{
    Team team; //Team stored in node
    struct node *next; //next node in list
};


extern struct Team_node *First_team; //Empty team list
extern struct Team_node *Current_team; //allows easy iteration through list
extern int Team_code; //Team code variable
extern char Team_name[]; //Team name variable
extern char Team_seed; //variable for the first character of team seed
extern char Team_seed1; //variable for the second character of team seed
extern char Uniform; //Declare a varibale for the uniform colour

//Function Prototypes
struct Team_node* get_Team(struct Team_node* current, int Team_code);
struct Team_node* get_Team_seed(struct Team_node* current, char Team_seed, char Team_seed1);
struct Team_node* put_Team(struct Team_node* first, Team team);

