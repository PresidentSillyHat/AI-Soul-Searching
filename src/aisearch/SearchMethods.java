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
    
    private List<Node> path=new ArrayList<>();
    private List<Node> frontier=new ArrayList<>();
    
    //Idea: make methods common between all and have switch to determine action for algorithm
    public SearchMethods(Node start) throws Exception{
        frontier.add(start);
        boolean goalReached=false;
        while(goalReached==false){
            goalReached=checkNode();
        }
        //take optimal paths (cut out loops currently in path)
        //not 100% working
        for(int i=path.size()-1;i>=0;i--){
            int k=path.indexOf(path.get(i));
            if(k<0)k=i;
            else{
                for(int j=0;j<i-k;j++){
                    path.remove(k);
                }
            }
            if(i-k!=0)i=path.size();
        }
        System.out.println("Path size after reduction: "+path.size());
        for(int i=0;i<path.size();i++){
            int[] coords=convertNodeNum(path.get(i).number);
            mazeDrawer.updateBoard(coords[0], coords[1], 2); //mark path taken to goal
            sleep(50);
        }
    }
    public boolean checkNode() throws Exception{
        path.add(frontier.get(0));
        int[] coords=convertNodeNum(frontier.get(0).number);
        mazeDrawer.updateBoard(coords[0], coords[1], 1); //explore
        //sleep(20); //makes animation easier to follow
        if (path.get(path.size()-1).isGoal){return true;}//done
        else expandNode();
        return false;
    }
    //expands first node of frontier
    public void expandNode() throws IOException, InterruptedException{
        boolean expanded=false;
        Node A=frontier.get(0);
        colorNext();
        if(path.size()>1)A.prev=path.get(path.size()-2);
        else A.prev=A;
        frontier.remove(0);
        if(A.down!=null && !A.prev.equalNode(A.down)){A.down.prev=A;frontier.add(0,A.down);expanded=true;}
        if(A.right!=null && !A.prev.equalNode(A.right)){A.right.prev=A;frontier.add(0,A.right);expanded=true;}
        if(A.up!=null && !A.prev.equalNode(A.up) && A.up.checked==false){A.up.checked=true;A.up.prev=A;frontier.add(0,A.up);expanded=true;}
        if(A.left!=null && !A.prev.equalNode(A.left) && A.left.checked==false){A.left.checked=true;A.left.prev=A;frontier.add(0,A.left);expanded=true;}
        
        if(expanded==false){backstep();}//dead end, go back
    }
    public void backstep(){
        while(frontier.get(0).checked==true){frontier.remove(0);}
        Node next=frontier.get(0);
        frontier.get(0).checked=true;
        //until at node the next frontier continues from
        while(!next.prev.equalNode(path.get(path.size()-1))){
            path.remove(path.size()-1);
        }
        
    }
    //converts node number to coords for drawing
    public int[] convertNodeNum(int nodeNumber){
        int[] coords=new int[2];
        coords[0]=nodeNumber%mazeDrawer.width;
        coords[1]=nodeNumber/mazeDrawer.width;
        return coords;
    }
    public void colorNext() throws IOException, InterruptedException{
        int[] coords=convertNodeNum(frontier.get(0).number);
        mazeDrawer.updateBoard(coords[0], coords[1], 0); //explore
        //sleep(50);
        if(path.size()>1)coords=convertNodeNum(path.get(path.size()-2).number);
        else coords=convertNodeNum(path.get(path.size()-1).number);
        mazeDrawer.updateBoard(coords[0], coords[1], 0); //explore
    }
}
