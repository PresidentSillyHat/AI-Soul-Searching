package aisearch;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;


public class GBFS {


    public static void search(Node[] maze, Node start, Node goal, int[][] map, int width) throws Exception {
        boolean finish = false;
        int cost = 0;
        int nodesexpanded = 0;
        double estimate = getEstimate(start, goal, width);
        Node current = start;
        start.setVisit();
        Node next;
        mazeDrawer.getInstance();
        mazeDrawer.setBoard(map);


        while (!finish) {

            Node left, right, up, down;
            Queue<Node> frontier = new LinkedList<Node>();
            int neighbors = 0;
            double smallestDistance=1000;
            for (int x = 0; x < 4; x++) {
                switch (x) {
                    case 0:
                            if(current.up != null){
                                up = current.up;
                                frontier.add(up);
                            neighbors++;}
                        break;
                    case 1:
                        if(current.right != null){
                            right = current.right;
                            frontier.add(right);
                            neighbors++;}
                        break;
                    case 2:
                        if(current.down != null){
                            down = current.down;
                            frontier.add(down);
                        neighbors++;}

                        break;
                    case 3:
                        if(current.left != null){
                            left = current.left;
                            frontier.add(left);
                        neighbors++;}
                        break;


                }





            }
            Node A,B,C,D;
            double distA,distB,distC,distD;
            int xn;
            int yn;
            switch (neighbors){
                case 1:

                    next = frontier.poll();
                    next.prev= current;
                    cost++;
                    nodesexpanded++;
                    current = next;
                    current.setVisit();
                    xn = getX(width, current);
                    yn = getY(width, current);
                    mazeDrawer.updateBoard(xn, yn, 0);


                    break;
                case 2:

                        A = frontier.poll();
                        B = frontier.poll();
                        distA= getEstimate(A, goal,width);
                        distB = getEstimate(B,goal,width);
                        if(distA<=distB){
                            next = A;
                            next.prev= current;
                            cost++;
;
                        }
                        else{
                            next = B;

                        }
                    nodesexpanded++;
                    current = next;
                    current.setVisit();
                     xn = getX(width, current);
                     yn = getY(width, current);
                    mazeDrawer.updateBoard(xn, yn, 0);

                    break;
                case 3:

                    A = frontier.poll();
                    B = frontier.poll();
                    C = frontier.poll();
                    next = A;
                    distA= getEstimate(A, goal,width);
                    distB = getEstimate(B,goal,width);
                    distC = getEstimate(C,goal,width);
                    if(distA >= distB && distC >=distB)
                        next= B;
                    if(distB >= distA && distC >=distA)
                        next = A;
                    if(distA>=distC &&distB>=distC)
                        next = C;
                    next.prev= current;
                    cost++;
                    nodesexpanded++;
                    current = next;
                    current.setVisit();
                     xn = getX(width, current);
                     yn = getY(width, current);
                    mazeDrawer.updateBoard(xn, yn, 0);



                    break;
                case 4 :
                    A = frontier.poll();
                    B = frontier.poll();
                    C = frontier.poll();
                    D = frontier.poll();
                    next = A;
                    distA= getEstimate(A, goal,width);
                    distB = getEstimate(B,goal,width);
                    distC = getEstimate(C,goal,width);
                    distD = getEstimate(D,goal,width);
                    if(distA >= distB && distC >=distB&& distD >= distB)
                        next= B;
                    if(distB >= distA && distC >=distA && distD>=distA)
                        next = A;
                    if(distA>=distC &&distB>=distC && distD >=distC)
                        next = C;
                    if(distA>=distD && distB >= distD && distC >= distD)
                        next = D;
                    next.prev= current;
                    cost++;
                    nodesexpanded++;
                    current = next;
                    current.setVisit();
                    xn = getX(width, current);
                    yn = getY(width, current);
                    mazeDrawer.updateBoard(xn, yn, 0);
                    break;

            }

            if(current.isGoal){
                finish = true;
            }
            System.out.println(current.toString());
            System.out.println(getX(width, current)+"  "+ getY(width,current));
            sleep(500);
        }

        System.out.println("the cost is "+ cost);
        System.out.println("the nodes expanded "+ nodesexpanded);


    }

    public static double getEstimate (Node start, Node goal,int width){

        double distance = 0;
        int ys = start.getNumber() / width;
        int xs = start.getNumber() % width;
        int yg = goal.getNumber() / width;
        int xg = goal.getNumber() % width;
        int lenA =abs( ys - yg);
        int lenB = abs(xg - xs);
        distance = sqrt((lenA * lenA) + (lenB * lenB));


        return distance;
    }


    public static int getX ( int width, Node val)
    {
        return val.getNumber() % width;
    }
    public static int getY ( int width, Node val)
    {
        return val.getNumber() / width;
    }
}
