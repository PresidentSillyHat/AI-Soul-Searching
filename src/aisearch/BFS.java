package aisearch;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import java.util.Stack;

public class BFS {


    public static void search(Node [] maze, Node start, int[][] map, int width ) throws Exception {
        int cost = 0;
        Node current = start;
        Node next = start;
        int nodesExpanded = 0;
        int Yn = current.getNumber() / width;
        int Xn = current.getNumber() % width;
        boolean finish = false;
        Queue<Node> frontier = new LinkedList<Node>();
        frontier.add(current);
        mazeDrawer.getInstance();
        mazeDrawer.setBoard(map);
        mazeDrawer.updateBoard(Xn,Yn,0);

        while (frontier.peek() != null && !finish) {
            int previous;
            current= frontier.poll();
            if(current.isGoal)
            {
                finish = true;
            }
            System.out.println(current.toString());

            if(current.prev == null)
            {
                previous = 0;
            }
            else
            {
                System.out.println("previous node is "+ current.prev.toString());
                previous= current.prev.getNumber();

            }
            if(!finish) {
                for (int x = 0; x < 4; x++) {
                    switch (x) {
                        case 0:
                            if (current.up != null && current.up.getNumber() != previous && !current.up.checkVisit()) {
                                next = current.up;
                                next.prev = current;
                                Yn = next.getNumber() / width;
                                Xn = next.getNumber() % width;
                                // if (map[Xn][Yn] == 0) {
                                next.setVisit();
                                frontier.add(next);
                                cost++;
                                mazeDrawer.updateBoard(Xn, Yn, 0);

                                // }
                            }
                            break;
                        case 1:
                            if (current.right != null && current.right.getNumber() != previous && !current.right.checkVisit()) {
                                next = current.right;
                                next.prev = current;
                                Yn = next.getNumber() / width;
                                Xn = next.getNumber() % width;
                                // System.out.println(map[Xn][Yn]);
                                // if (map[Xn][Yn] == 0) {
                                next.setVisit();
                                frontier.add(next);
                                cost++;
                                mazeDrawer.updateBoard(Xn, Yn, 0);

                                // }
                            }
                            break;
                        case 2:
                            if (current.down != null && current.down.getNumber() != previous && !current.down.checkVisit()) {
                                next = current.down;
                                next.prev = current;
                                Yn = next.getNumber() / width;
                                Xn = next.getNumber() % width;
                                //  if (map[Xn][Yn] == 0) {
                                next.setVisit();
                                frontier.add(next);
                                cost++;
                                mazeDrawer.updateBoard(Xn, Yn, 0);
//
                                // }
                            }
                            break;
                        case 3:
                            if (current.left != null && current.left.getNumber() != previous && !current.left.checkVisit()) {
                                next = current.left;
                                next.prev = current;
                                Yn = next.getNumber() / width;
                                Xn = next.getNumber() % width;
                                //  if (map[Xn][Yn] == 0) {
                                next.setVisit();
                                frontier.add(next);
                                cost++;
                                mazeDrawer.updateBoard(Xn, Yn, 0);

                                // }
                            }
                            break;


                    }


                }
            }

            sleep(100);
        }
        int counter = 0;
        int Yc = current.getNumber() / width;
        int Xc = current.getNumber() % width;
        mazeDrawer.updateBoard(Xc,Yc, 2);
        while(current.prev != null)
        {
            counter ++;

            Node previous = current.prev;
           int Yp = previous.getNumber() / width;
           int Xp = previous.getNumber() % width;
           mazeDrawer.updateBoard(Xp,Yp, 2);
           current = previous;

        }

        System.out.println("found the goal after expanding "+ cost);
        System.out.println("the optimal path is "+ counter);

    }
}
