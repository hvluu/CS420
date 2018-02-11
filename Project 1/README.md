==========================
Project Description      |	
==========================

The A* search can be used to solve the 8-puzzle problem. As described in the book, there are two candidate heuristic functions:
h1 = the number of misplaced tiles
h2 = the sum of the distances of the tiles from their goal positions

You are to implement the A* using both heuristics and compare their efficiency in terms of depth of the solution and search costs. The following figure (Figure 3.29 in the book) provides some data points that you can refer to. To test your program and analyze the efficiency, you should generate random problems (>100 cases) with different solution lengths. Please collect data on the different solution lengths that you have tested, with their corresponding search cost (# of nodes generate). A good testing program should test a range of possible cases (2 <= d <= 20). Note that the average solution cost for a randomly generated 8-puzzle instance is about 22 steps.

The input of your program should be either (1) a randomly generated 8-puzzle problem by your program; or (2) a specific 8-puzzle configuration entered through the standard input, which contains the configuration for only one puzzle, in the following format:

1 2 4
0 5 6
8 3 7

The goal state is:

0 1 2
3 4 5
6 7 8

Where 0 represents the empty tile.

Please handle the input/output gracefully.

Note that the 8-puzzle states are divided into two disjoint sets, such that any state is reachable from any other state in the same set, which no state is reachable from any state in the other set. Before you solve a puzzle, you need to make sure that it's solvable. 

====================================================
Compile the Java source code 			   |			   
        `javac MainDriver.java`			   |
Run the Program 				   |
        `java MainDriver`		           |
====================================================           

===========================
How to Use Program        |
===========================

Select the option you wish to select 	
(1) Solve a randomly genereated Puzzle
(2) Solve a user-input Puzzle
(3) Large Random Testing
(4) Test sample cases
(5) Terminate Program		   

Option 1: 
	The program will randomly generate a puzzle and then will ask you to select which 	function you want the program to use to solve the puzzle

Option 2:
	The program will ask you to enter a puzzle. Once you finish, the program will ask 	you to select which heuristic function you want to use to solve the puzzle

Option 3:
	The program will ask you how many tests you want the program to run. After enter 	the number of tests, the program will randomly generate the number of puzzle that 	you entered and use both heuristic functions to solve. Also, after solving each 	puzzle, it will output the stats of each puzzle right below the puzzle.

Option 4:
	If selected, the program will read in the text file that is already created and 	placed in the same directory as the program. The output file will be generated in 	the same directory after it finishes solving the puzzles in the file.

Option 5:
	Quit the program

Note: After each selection, the program will run in a loop and will only be terminated if user selects option 5.
