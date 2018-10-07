package aisearch;

/**
 *
 * @author Derek Wallace
 */

//just the basics rn, update if needed
public class Node {
    
    public Node prev;
    public Node left;
    public Node right;
    public Node down;
    public Node up;
    public int number; //used for debugging
    public boolean isGoal=false;
    public boolean visited = false;
    
    public Node(int num){
        number=num;
    }
    public String toString(){
        return "Node number: "+number;
    }
    public int getNumber(){
        return number;
    }
    public boolean checkVisit(){return  visited;}
    public void setVisit(){visited = true;}
    
}
