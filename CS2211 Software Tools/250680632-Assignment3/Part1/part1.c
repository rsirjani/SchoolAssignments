/* Title: Assignment 3 Part 1
   Author: Ramtin Sirjani
   Date: October 22nd 2022
   Purpose: Manipulate numbers in an array*/

#include <stdio.h>

void Print_array(int array[], int num_ints){ //This method formats and prints the contents and locations of values in an array of integers.
    printf("Part 1:\n   Your array is: ");
    for(int i = 0; i<num_ints; i++){
        if(i == num_ints-1){
            printf("[%d] = %d\n\n", i, array[i]);
        }
        else{
            printf("[%d] = %d, ", i, array[i]);
        }
    }
}

int Largest_val(int array[], int num_ints){ //This is a helper method to return the largest value in an array of integers.
    int largest_int = 0;
    for(int i = 0; i<num_ints; i++){
        if(array[i] > largest_int)
            largest_int = array[i];
    }
    return largest_int;
}

void Print_largest(int array[], int num_ints){ //This method formats and prints the largest value in an array of integers.
    int largest = Largest_val(array, num_ints);
    printf("Part 2:\n   The largest value in your array is: %d\n\n", largest);
}

void Reverse_array(int array[], int num_ints){ //This method formants and prints an array of integers in reverse location order.
    printf("Part 3:\n   Your array in reverse is: ");
    for(int i = 0; i<num_ints; i++){
        printf("%d ", array[num_ints - (i+1)]);
    }
    printf("\n\n");
}

void Sum_val(int array[], int num_ints){ //This method prints the sum of all values in an array of integers.
    int sum = 0;
    for(int i = 0; i<num_ints; i++){
        sum += array[i];
    }
    printf("Part 4:\n   The sum of all values in your array is: %d\n\n", sum);
}

void Decreasing_order(int* array, int num_ints){ //This method prints the values in an array of integers in decreasing order.
    int dec_order[num_ints];
    int array1[num_ints];
    for(int i = 0; i<num_ints; i++){
        array1[i] = array[i];
    }
    for(int i = 0; i<num_ints; i++){
        int largest = Largest_val(array1, num_ints);
        int copies = 0;
        dec_order[i] = largest;
        for(int j = 0; j<num_ints; j++){
            if(array1[j] == largest){
                array1[j] = 0;
                copies += 1;
            }   
        }
        for(int j = 1; j<copies;j++){
            dec_order[i+1] = largest;
            i++;
        }
    }
    printf("Part 5:\n   Your array in sorted order is: ");
    for(int i = 0; i<num_ints; i++){
        printf("%d ", dec_order[i]);
    }
    printf("\n\n");
}

void Swap_first_last(int array[], int num_ints){ //This method prints an array of integers after swapping the locations of the first and last values.
    int first = array[0];
    int last = array[num_ints-1];
    printf("Part 6:\n   Your array with first and last elements switched is: ");
    printf("%d ", last);
    for(int i=1; i<num_ints-1; i++){
        printf("%d ", array[i]);
    }
    printf("%d \n", first);
}

void main(){ //This is the main method and prompts the user for the size of an array of integers and values to fill that array. 
    int num_ints;
    printf("Please enter the number of integers to process: ");
    scanf("%d", &num_ints);
    int int_array[num_ints];
    int* p;
    p = int_array;
    printf("  There is enough room in your array for %d integers (%d bytes)\n\n", num_ints, sizeof(int_array)); 
    printf("Please enter your integers separated by spaces: ");
    for(int i = 0; i<num_ints; i++){
        scanf("%d", &int_array[i]);
    }
    printf("\n\n");
    Print_array(int_array, num_ints);
    Print_largest(int_array, num_ints);
    Reverse_array(int_array, num_ints);
    Sum_val(int_array, num_ints);
    Decreasing_order(p, num_ints);
    Swap_first_last(int_array, num_ints);
}