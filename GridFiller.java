import java.awt.*;
import java.util.Random;

public class GridFiller {

    private int numRows;
    private int numColumns;
    private int[][] newGen;

    public GridFiller(int rows, int cols) {
        this.numRows = rows;
        this.numColumns = cols;
        this.newGen = new int[rows][cols];

    }

    public void setCell(int x, int y) { //sets cells in the newgen
        if(newGen[x][y] == 1) {
            newGen[x][y] = 0;
        } else {
            newGen[x][y] = 1;
        }
    }

    public void contCells(int[][] newGen){ //counts number of cells to update state
        for (int i = 0; i < newGen[0].length; i++){
            for (int j = 0; j < newGen[0].length; j++){
                setCell(i, j);
            }
        }
        UpdateState();
    }


    public void setRows(int rows) {
        numRows = rows;
    }

    public void setCols(int cols) {
        numColumns = cols;
    }

    protected void BoardStateArray(int rows, int columns){ //returns a 2D array where each slot is either 0 or 1 randomly
        newGen = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newGen[i][j] = (int) Math.round(Math.random()); //0 is dead, alive is 1
            }
        }

    }

    public int[][] getNewGen(){
        return newGen;
    }




    protected void FillCells(Graphics g){

        Random rand = new Random();
        float Alpha = rand.nextFloat();
        float Beta = rand.nextFloat();
        float Gamma = rand.nextFloat();

        int x;
        int y;
        int i;
        int j;

        Color randomColor = new Color(Alpha, Beta, Gamma);


        for(i = 0; i < numRows; i++){
            for(j = 0; j < numColumns; j++){
                x = i * numRows;
                y = j * numColumns;
                if(newGen[i][j] == 1){
                    g.setColor(randomColor);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x,y  ,numRows-1,numColumns-1); //colors the squares if the [i][j] == 1 at x,y
            }
        }



    }

    protected void loadCell(int[][] newGen, Graphics g){
        Random rand = new Random();
        float Alpha = rand.nextFloat();
        float Beta = rand.nextFloat();
        float Gamma = rand.nextFloat();

        int x;
        int y;
        int i;
        int j;

        Color randomColor = new Color(Alpha, Beta, Gamma);

        for(i = 0; i < newGen[0].length; i++){
            for(j = 0; j < newGen[0].length; j++){
                x = i * numRows;
                y = j * numColumns;
                if(newGen[i][j] == 1){
                    g.setColor(randomColor);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x,y ,numRows-1,numColumns-1); //colors the squares if the [i][j] == 1 at x,y
            }
        }
    }

    protected void UpdateState(){

        int NeighborCounting; // number of neighbors
        int state; //alive or dead
        int i; int j;



        int[][] dummy = this.getNeighbors(this.newGen);

        for(i = 0; i < numRows; i++){
            for(j = 0; j < numColumns; j++){



                NeighborCounting = dummy[i][j];
                state = newGen[i][j];


                if(NeighborCounting == 3){
                    newGen[i][j]= 1;

                }else if(NeighborCounting == 2){
                    newGen[i][j] = state;

                }else{
                    newGen [i][j]= 0;

                }



            }

        }




    }






    public int[][] getNeighbors(int[][] board) {
        int[][] neighbors = new int[this.numRows][this.numColumns];
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColumns; j++) {
                neighbors[i][j] = 0;
            }
        }

        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColumns; j++) {
                if (board[i][j] == 1) {
                    if (i - 1 >= 0) {
                        neighbors[i-1][j]++;
                    }
                    if (i + 1 < this.numRows) {
                        neighbors[i+1][j]++;
                    }
                    if (j - 1 >= 0) {
                        neighbors[i][j-1]++;
                    }
                    if (j + 1 < this.numColumns) {
                        neighbors[i][j+1]++;
                    }
                    if (i - 1 >= 0 && j - 1 >= 0) {
                        neighbors[i-1][j-1]++;
                    }
                    if (i - 1 >= 0 && j + 1 < this.numColumns) {
                        neighbors[i-1][j+1]++;
                    }
                    if (i + 1 < this.numRows && j + 1 < this.numColumns) {
                        neighbors[i+1][j+1]++;
                    }
                    if (i + 1 < this.numRows && j - 1 >= 0) {
                        neighbors[i+1][j-1]++;
                    }
                }
            }
        }

        return neighbors;
    }


    public void clearNewGen() {
        for(int i = 0; i < numRows; ++i) {
            for(int j = 0; j < numColumns; ++j) {
                newGen[i][j] = 0;
            }
        }
    }
}
