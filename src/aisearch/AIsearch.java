package aisearch;

import java.io.FileReader;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Derek Wallace
 */
public class AIsearch {
    private static Node[] tiles;  //nodes are placed in specific order, node N=numberedMap[N/width][N%width]
    private static Node start;  //starting node to be put into searches
    private static int width;
    private static int height;
    public static void main(String[] args) throws Exception {
        
        int[][] numberedMap=loadMap("medium maze.txt"); //numberedMap is used initially for setting up nodes and drawing
        height=numberedMap.length;
        width=numberedMap[0].length;
        tiles=linkNodes(numberedMap); //tiles is the finished, linked graph of nodes
        
        mazeDrawer.getInstance();
        mazeDrawer.setBoard(numberedMap);
        //loadMap("large maze.txt");
        
        //example use of update board to draw a path
        //  its easier just to call on expansion
        int y1=start.getNumber()/width;
        int x1=start.getNumber()%width;
        int delay=500;
        mazeDrawer.updateBoard(x1+1,y1,0);
        sleep(delay); //slows down to show path slowly expand
        mazeDrawer.updateBoard(x1+2,y1,0);
        sleep(delay);
        mazeDrawer.updateBoard(x1+2,y1-1,0);
        sleep(delay);
        mazeDrawer.updateBoard(x1+2,y1-2,0);
        sleep(delay);
        mazeDrawer.updateBoard(x1+1,y1-2,0);
        sleep(delay);
        mazeDrawer.updateBoard(x1,y1-2,0);


    }
    
    //read the textfile maze into strings without messy chars
    //could be probably be optimized for different fileRead type now bc of changes
    public static int[][] loadMap(String fileName) throws Exception{
        FileReader fr=new FileReader(fileName);
        int c;
        String str="";
        List<String> strA=new ArrayList<>(); //list of lines
        //process out newlines from midline
        while((c=fr.read())!=-1){
            System.out.print((char)c); //show map
            if(!(((char)c=='\n') || ((char)c=='\r'))){str=str+(char)c;}
            else if(((char)c=='\n' || (char)c=='\r')){
                if(strA.size()<1) {strA.add(str);str="";}
                else if(strA.size()>0 && strA.get(0).length()>str.length()){} //newline char found midline
                else {strA.add(str);str="";}
            }
            else System.out.println("Err: "+(char)c);
        }
        strA.add(str);
        System.out.println("\nWidth: "+str.length()+"\nHeight: "+strA.size());
        //now that size is known, fill info array
        int[][] map=fillMap(strA);
        
        return map;
    }
    
    //fill the array representing map with numbers
    public static int[][] fillMap(List<String> lines){
        int[][] map=new int[lines.size()][lines.get(0).length()];
        //find values of each spot
        for(int i=0;i<lines.size();i++){
            for(int j=0;j<lines.get(i).length();j++){
                char c=lines.get(i).charAt(j);
                switch (c) {
                    case '%': //wall
                        map[i][j]=0;
                        break;
                    case ' ': //empty
                        map[i][j]=1;
                        break;
                    case 'P': //start
                        map[i][j]=2;
                        break;
                    case '*': //goal
                        map[i][j]=3;
                        break;
                    default:
                        map[i][j]=5; //shouldn't happen
                        break;
                }
            }
        }
        return map;
    }
    //link nodes together properly so navigation is possible
    public static Node[] linkNodes(int[][] map){
        Node[] Maze=new Node[map.length*map[0].length];
        int nodeNum=0;
        //may i have some loops brother
        for(int i=0;i<Maze.length;i++){Maze[i]=new Node(i);}
        //connect left/right
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                //both spots aren't walls
                if(map[i][j]>0 && map[i][j+1]>0){ //allowed bc of short circuit, length-1 is always wall
                    Maze[nodeNum].right=Maze[nodeNum+1];
                    Maze[nodeNum+1].left=Maze[nodeNum];
                }
                //only have to check on L/R link pass, 0 and length-1 are always walls
                if(map[i][j]==2){start=Maze[nodeNum];System.out.println("Starting at "+start);}
                if(map[i][j]==3){Maze[nodeNum].isGoal=true;}
                
                nodeNum++;
            }
        }
        nodeNum=0;
        //connect up/down
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                //both spots aren't walls
                if(map[i][j]>0 && map[i+1][j]>0){
                    Maze[nodeNum].down=Maze[nodeNum+map[0].length];
                    Maze[nodeNum+map[0].length].up=Maze[nodeNum];
                }

                nodeNum++;
            }
        }
        
        return Maze;
    }
    
}
