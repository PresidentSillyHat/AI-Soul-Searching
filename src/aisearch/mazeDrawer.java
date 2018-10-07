package aisearch;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Derek Wallace
 */

//draws the board to make visuals easier
public class mazeDrawer extends JFrame{
    private static BufferedImage drawer;
    private static JPanel boardPanel;
    private static mazeDrawer inst=null;
    public static int height;
    public static int width;

    //using static to ease repainting, personal preference
    public static mazeDrawer getInstance() throws IOException{
        if (inst==null){
            inst = new mazeDrawer();}
        inst.repaint();	//updates board when needed
        return inst;
    }
    
    //window stuff
    public mazeDrawer() throws IOException{	//The GUI Frame
        super("AI SearchTime with Derek and Dillon");
 
        drawer=new BufferedImage(1500,1000,BufferedImage.TYPE_INT_ARGB);

        //this.setLayout(new GridBagLayout());
        boardPanel=new JPanel(){
        
            @Override
            protected void paintComponent(Graphics g) {
                
                super.paintComponent(g);
                g.drawImage(drawer, 0, 0, null);	
                
            }
        };
        boardPanel.setSize(1500, 1000);
        this.add(boardPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500,1000);	//Good size for window bc of board
        this.setVisible(true);
    
    }
    
    //pass the initial numberedMap to GUI so it can draw layout
    public static void setBoard(int [][] newMap) throws IOException{

        int h=newMap.length,w=newMap[0].length;
        int tileHeight=1000/h, tileWidth=1500/w,x=0,y=0;
        height=h;width=w;
        
        Graphics g=drawer.getGraphics();
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
            //logic to auto color tiles
                x=j*tileWidth;
                y=i*tileHeight;
                switch (newMap[i][j]) {
                    case 0:
                        g.setColor(Color.black); //wall
                        break;
                    case 1:
                        g.setColor(Color.white); //open
                        break;
                    case 2:
                        g.setColor(Color.blue); //start
                        break;
                    default:
                        g.setColor(Color.green);   //goal
                        break;
                }
                g.fillRect(x, y, tileWidth, tileHeight);

            }
        }
        mazeDrawer.getInstance();	//Repaint board to show changes
        g.dispose();	//Free up space from Graphics g
        
    }
    
    //update board, format is numberedMap[x0][y0], with type having to do with distance (optional)
    //needs to be changed a little, rn it would just trace every taken path with yellow (if you call update every time)
    public static void updateBoard(int x0, int y0,int type) throws IOException{
        
        int tileHeight=1000/height, tileWidth=1500/width,x,y;

        Graphics g=drawer.getGraphics();
        
        x=x0*tileWidth;
        y=y0*tileHeight;
        //TODO: recolor to have shades of something indicate closeness?
        switch (type) {
            case 0:
                g.setColor(Color.yellow); //explored node
                break;
            case 1:
                g.setColor(Color.red); //current
                break;
            case 2:
                g.setColor(Color.cyan); //path to goal
                break;
            default:
                g.setColor(Color.pink);   //
                break;
            }
        g.fillRect(x, y, tileWidth, tileHeight);

            
        
        mazeDrawer.getInstance();	//Repaint board to show changes
        g.dispose();	//Free up space from Graphics g
        
        
    }
}
