package aisearch;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Derek Wallace
 */

//instructions say unified search with the 4 types being special cases
public class SearchMethods {
    
    private List<Node> path=new ArrayList<>(); //dont track path? expand until goal?
    private List<Node> frontier=new ArrayList<>();
    private Node goal;
    private Node start;
    private int expandedCount=0;
    
    //Idea: make methods common between all and have switch to determine action for algorithm
    public SearchMethods(Node starter, int type, Node goalN) throws Exception{
        frontier.add(starter);
        goal=goalN;
        path.add(starter);
        this.start=starter;
        //run algorithm
        boolean goalReached=false;
        while(goalReached==false){
            goalReached=checkNode(type);
        }
       //trace shortest path found back from goal
        Node counter=goal;
        path.add(goal);
        while(counter.prev!=null && counter.prev!=this.start){
            path.add(1,counter.prev);
            counter=counter.prev;
        }
        //Color shortest path found
        System.out.println("Optimal Found Path size: "+path.size());
        System.out.println("Nodes expanded: "+expandedCount);
        for(int i=0;i<path.size();i++){
            int[] coords=convertNodeNum(path.get(i).number);
            mazeDrawer.updateBoard(coords[0], coords[1], 2); //mark path taken to goal
            sleep(50);
        }
    }
    //universal for all
    public boolean checkNode(int type) throws Exception{
        //sleep(50);
        switch(type){
            case 1:
                if (frontier.size()<1){return true;}//done
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                if (frontier.size()<1){return true;}
                break;
                
        }
        return expandNode(type);
    }
    
    //expands nodes in frontier
    //where heuristic is needed and searches change
    public boolean expandNode(int type) throws IOException, InterruptedException{
        boolean expanded=false;
        Node A=frontier.get(0);//default
        int hi=0,h=heuristicFunction(frontier.get(0),getPath(frontier.get(0)).size());
        switch (type) {
            case 1:
                //Logic for DFS expansion
                A=frontier.get(0);
                break;
            case 2:
                //Logic for Breadth first
                break;
            case 3:
                //logic for greedy
                break;
            case 4:
                //Logic for A* expansion
                for(int i=0;i<frontier.size();i++){
                    List<Node> pathToA=getPath(frontier.get(i));
                    int newH=heuristicFunction(frontier.get(i),pathToA.size());
                    if(newH<h){h=newH;hi=i;}
                    else if(newH==h){
                        double p1=distanceTiebreak(frontier.get(hi));
                        double p2=distanceTiebreak(frontier.get(i));
                        if(p2<=p1)hi=i;
                    }
                }
                
                A=frontier.get(hi);
                break;
            default:
                break;
            }
        if(A.isGoal==true)return true;
        if(A.prev==null)A.prev=A;
        frontier.remove(hi); //0 for dfs, correct indice for A*
        if(type<5 && A.checked==false){
            if(A.up!=null && !A.prev.equalNode(A.up) && A.up.checked==false){addFrontier(A.up,A);expanded=true;}
            if(A.left!=null && !A.prev.equalNode(A.left) && A.left.checked==false){addFrontier(A.left,A);}
            if(A.down!=null && !A.prev.equalNode(A.down) && A.down.checked==false){addFrontier(A.down,A);expanded=true;}
            if(A.right!=null && !A.prev.equalNode(A.right) && A.right.checked==false){addFrontier(A.right,A);expanded=true;}
        }
        if(expanded==false){
            backstep(hi,type);
        }//remove repeated frontier (explored intersections)
        return false;
    }
    //remove repeated frontier (explored intersections)
    public void backstep(int hn, int type){
        while(frontier.size()>1 && frontier.get(0).checked==true){frontier.remove(0);} 
    }
    
    //eval function for informed, prior is used for AStar, leave 0 for greedy
    public int heuristicFunction(Node pos, int priorCost){
        int[]coords=convertNodeNum(pos.number);
        int[]goalCoords=convertNodeNum(goal.number);
        //manhattan distance, absolute value incase goal is above/below or left/right
        int distance=Math.abs((coords[0]-goalCoords[0]))+Math.abs((coords[1]-goalCoords[1]));
        distance=priorCost+distance; 
        return distance;
    }
    //used for tiebreaking when heuristic is same for both, picks the one in shorter straightline
    public double distanceTiebreak(Node pos){
        int[]coords=convertNodeNum(pos.number);
        int[]goalCoords=convertNodeNum(goal.number);
        //manhattan distance, absolute value incase goal is above/below or left/right
        double distance=Math.sqrt(Math.pow(coords[0]-goalCoords[0],2)+Math.pow(coords[1]-goalCoords[1],2));
        return distance;
    }
    //get path backwards from current node to start
    public List getPath(Node A){
        List pathA=new ArrayList<>();
        Node counter=A;
        pathA.add(A);
        while(counter.prev!=null && counter.prev!=counter){
         pathA.add(counter.prev);
         counter=counter.prev;
        }
        return pathA;
    }
    //used for maintaining shortest routes to each node
    public boolean isShorter(Node neo, Node cur){
        return getPath(neo).size()<getPath(cur).size();
    }
    //made a method for repeated actions, updates paths and frontier
    public void addFrontier(Node neo, Node A) throws IOException, InterruptedException{
        if (neo.prev!=null && !isShorter(A,neo.prev)){}
        else neo.prev=A;
        A.checked=true;
        frontier.add(0,neo);
        colorNode(A,1); //color expanded
        colorNode(neo,4); //color frontier
        expandedCount++;
    }
    //converts node number to coords for drawing
    public int[] convertNodeNum(int nodeNumber){
        int[] coords=new int[2];
        coords[0]=nodeNumber%mazeDrawer.width;
        coords[1]=nodeNumber/mazeDrawer.width;
        return coords;
    }
    
    //used to animate path exploration, yellow is current node, red is explored
    public void colorNode(Node n,int type) throws IOException, InterruptedException{
        int[] coords=convertNodeNum(n.number);
        mazeDrawer.updateBoard(coords[0], coords[1], type); //turn color
        
        
        
    }
}
