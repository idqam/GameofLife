/*
Team Name: Upsilon
Team Roster:
    Owen Ariza Villareal
    Yuen Ng Du
    James Ngatia Muguiyi
Project 3 Game of Life
5/2/2021
CSC 171
University of Rochester
 */

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;


public class GameofLife extends JFrame implements ActionListener, MouseListener {
    protected JPanel buttonControl; //section where buttons go
    protected JPanel Screen; //provides the screen to be drawn on

    protected JButton freezeButton; //freezes the simulation as is
    protected JButton resumeButton; //resumes the simulation as is

    protected JButton startButton; //used to start simulation, everytime it is pressed it resets it
    protected JButton resetButton; //used to end simulation, stops everything and resets to a blank all dead stat
    protected JSlider timerSlider; //used to control speed of simulation


    protected JButton saveButton; //save file
    protected JButton loadButton; //load file


    protected int SliderMin = 1;
    protected int SliderMax = 10;
    protected int SliderDefault = 1;
    protected int mainN = 20;
    protected int genCount = 0; // used to keep track of generations, by default value is 0 as initial gen

    protected String fileName;
    protected Scanner scanner;
    protected Random rand = new Random();



    protected Timer tt;

    protected GridFiller gridFiller;

    protected boolean isRunning = false;
    protected boolean didDoClick = false;

    protected boolean inManualMode = false;

    protected JFileChooser chooseFile;
    protected JButton enterManualMode;
    protected JButton leaveManualMode;





    public GameofLife(){
        super("The Game of Life");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonControl = new JPanel();
        Screen = new JPanel();
        timerSlider = new JSlider(SliderMin, SliderMax, SliderDefault);
        timerSlider.setMajorTickSpacing(1);
        timerSlider.setPaintTicks(true);
        timerSlider.setPaintLabels(true);
        timerSlider.setSnapToTicks(true);

        chooseFile = new JFileChooser();







        buttonControl.setBackground(Color.WHITE);
        Screen.setBackground(Color.WHITE);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        Screen.setPreferredSize(new Dimension(400,400));

        add(Screen);
        add(buttonControl);





        startButton = new JButton("S");//Starts Simulation all random
        resetButton = new JButton("R");//Resets Simulation


        freezeButton = new JButton("F"); //Freezes simulation as is
        resumeButton = new JButton("Re"); //resumes the simulation as is

        saveButton = new JButton("Save"); //saves current board state to a file
        loadButton = new JButton("Load"); //loads a board state from a file
        enterManualMode = new JButton("Y"); //enters manual mode
        leaveManualMode = new JButton("N"); //leaves manual mode



        buttonControl.add(startButton);
        buttonControl.add(resetButton);
        buttonControl.add(freezeButton);
        buttonControl.add(resumeButton);
        buttonControl.add(timerSlider);
        buttonControl.add(saveButton);
        buttonControl.add(loadButton);
        buttonControl.add(enterManualMode);
        buttonControl.add(leaveManualMode);





        startButton.addActionListener(this);

        resetButton.addActionListener(this);
        freezeButton.addActionListener(this);
        resumeButton.addActionListener(this);
        Screen.addMouseListener(this);

        saveButton.addActionListener(this);
        loadButton.addActionListener(this);

        enterManualMode.addActionListener(this);
        leaveManualMode.addActionListener(this);

        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                DrawGrid(mainN , mainN , Screen.getWidth(), Screen.getHeight());
            }
            @Override
            public void windowLostFocus(WindowEvent e) {
            }
        });
        buttonControl.setLayout(new FlowLayout());

        tt = new Timer(1000  , e -> {

            if(isRunning) {
                gridFiller.UpdateState();
                GameofLife.this.DrawGrid(mainN , mainN , GameofLife.this.Screen.getWidth(), GameofLife.this.Screen.getHeight());
                genCount += 1;
                System.out.println("Current generation is: " + genCount);

            }

        });

        System.out.println("Timer value: " + timerSlider.getValue() + " Current delay is: " + tt.getDelay() + "ms"); //Initial printing of default values

        timerSlider.addChangeListener(e -> {
            tt.setDelay(timerSlider.getValue() * 1000);
            System.out.println("Timer value: " + timerSlider.getValue() + " Current delay is: " + tt.getDelay() + "ms");
            //will print current timer value and delay after the timer is interacted with
            //Updates the delay of the simulation updating,
            // the bigger the value the slower it goes
            //By default the delay is 1000ms = 1 second
        });

        pack();
        gridFiller = new GridFiller(Screen.getWidth()/(mainN ), Screen.getHeight()/(mainN ));
    }

    public static void main(String[] args){
        GameofLife gg = new GameofLife();
        gg.setVisible(true);

    }


    protected void DrawGrid(int NRow, int NColumns,  int ScreenWidth, int ScreenHeight){

        //lines is the number of lines to be drawn and XLines is for the horizontal lines Ylines for the vertical
        int i;

        Graphics g = Screen.getGraphics();



        g.setColor(Color.BLACK);


        for(i = 0; i <  NRow; i++){
            g.drawLine(0, i * (ScreenHeight / (mainN )), ScreenWidth, i * (ScreenHeight / (mainN ))); //draws the horizontal lines
        }

        for(i = 0; i < NColumns; i++){
            g.drawLine(i * (ScreenWidth / (mainN )), 0, i*(ScreenWidth/(mainN )), ScreenHeight); //draws vertical
        }

        gridFiller.setRows(ScreenWidth / (mainN ));
        gridFiller.setCols(ScreenHeight / (mainN ));



        gridFiller.FillCells(g);


    }

    protected void ClearGrid(){


        gridFiller.clearNewGen();
        DrawGrid(mainN ,mainN ,Screen.getWidth(),Screen.getHeight());

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int width = Screen.getWidth();
        int height = Screen.getHeight();

        int uniqueNumState = rand.nextInt(100000);

        if(e.getSource() == startButton){
            isRunning = true;

            if(!didDoClick) {
                gridFiller.BoardStateArray(width/(mainN ), height/(mainN ));
            }
            DrawGrid(mainN ,mainN ,width,height);

            tt.setRepeats(true);
            tt.start();
        }else if(e.getSource() == resetButton){
            tt.stop();
            isRunning = false;
            didDoClick = false;
            genCount = 0;
            ClearGrid();
        }else if(e.getSource() == freezeButton){

            tt.stop();
            isRunning = false;
            didDoClick = false;
        }else if(e.getSource() == resumeButton){
            tt.setRepeats(true);
            tt.start();
            isRunning = true;
            didDoClick = true;

        }else if(e.getSource() == saveButton){
            tt.stop();
            isRunning = false;
            didDoClick = false;

            File file = new File("File_" + uniqueNumState );

            int [][] newGen = gridFiller.getNewGen();
            try{
                FileWriter fr = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fr);

                for (int i = 0; i < newGen[0].length; i++){
                    for (int j = 0; j < newGen[0].length ; j++){
                        bw.write(newGen[i][j]);
                    }
                    bw.write("\n");
                }
                bw.close();
                fr.close();
                System.out.println("Your file has been saved as: " + file + " at generation " + genCount);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            tt.start();
            isRunning = true;
            didDoClick = true;
        } else if(e.getSource() == loadButton){
            tt.stop();
            isRunning = false;
            didDoClick = false;
            genCount = 0;


            System.out.println("Enter file name with extension to load it.");
            scanner = new Scanner(System.in);
            fileName = scanner.nextLine();

            File file = new File(fileName);
            Graphics g = Screen.getGraphics();

            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                int[][] newGen = new int[20][20];
                for (int i = 0; i < newGen[0].length; i++){
                    for (int j = 0; j < newGen[0].length; j++){
                        newGen[i][j] = br.read();
                    }
                }
                br.close();
                gridFiller.contCells(newGen);
                gridFiller.loadCell(newGen, g);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            tt.start();
            isRunning = true;
            didDoClick = true;

        }else if(e.getSource() == enterManualMode){
            inManualMode = true;

        }else if(e.getSource() == leaveManualMode){
            inManualMode = false;
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        didDoClick = true;
        if(inManualMode) {
            gridFiller.setCell((int)(mainN *(e.getPoint().x / (double)Screen.getWidth())), (int)(mainN *(e.getPoint().y / (double)Screen.getHeight())));
            DrawGrid(mainN ,mainN ,Screen.getWidth(),Screen.getHeight());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
