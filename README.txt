Owen Ariza Villareal 

					How the programme works:

Flares Chosen: (a), (f), (g), (e)

There are two main files in the folder, one is called GridFiller.java which is used for counting neighbors, color the squares, create a random 2D array of 0,1 randomly chosen where 0 represents dead (white color) and 1 is alive (any color chosen at random but never white). The file where things actually run is called GameOfLife.java when ran it will produce a screen of the game. 

The following buttons work as follows:
	S button will start the simulation and load a random array.

	R button resets the simulation to blank and makes all the cells 0.

	F  button will freeze the simulation as is.

	Re button will resume the simulation as is without reset .

	The slider will control how fast the simulation updates, only makes it slower not faster. By default it is set 1, these values are multiples by 1000 as 1000ms = 1s and then it sets the value to the timer delay method. Every time the slider is updated, the console prints the value of the timer in units and the current delay is terms of milliseconds. 
	
	Y button allows the user to enter manual mode which lets you select a cell to make it alive or click it again to make it dead, this can be done while the simulation is running or frozen or initially when it is all blank before start or after reset by just clicking Y once. 

	N button will exit manual mode.

	Save button will create a saved file of the current board state as "File_randomInt" where randomInt is a randomly generated integer.

	
	Load button will pause the simulation freezing the timer and then allow the user to enter the name of a file and then it will load it after clicking enter. This resets the generation count to 0 and treats the loaded board state as the initial board state. The name inputed must be exactly the same as the file wished to be loaded else it will throw an exception for error.

General:
	By default the timer fires every 1000ms which is 1second and the simulation updates. And then the generation is printed in terms of "Current generation is: x" where x is the number of times the timer has fired (I.e. how many times the simulation has fired). 
 

Bugs:
	There are no known bugs except a small resizing bug when trying to resize the screen without first clearing (resetting [not pressing R]). When this bug appears it will still the run code and update but very very wonky and produce an index out of bounds error. But if resized before the simulation starts then no error happens. 
	

