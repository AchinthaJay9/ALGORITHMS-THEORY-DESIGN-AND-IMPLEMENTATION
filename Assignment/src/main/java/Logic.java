//2019530 A.E.W Jayatilake

package main.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Logic {

    static int[][] dataArray;
    static int startX;
    static int startY;
    static int endX;
    static int endY;

    public static void main(String[] args) {

        List<String> steps = new ArrayList<>();

        Parser parser = new Parser();

        parser.dataParser();

        Nodes startPoint = new Nodes(startX, startY);

        LinkedList<Nodes> waitingNode = new LinkedList<>();
        Nodes[][] tempPoints = new Nodes[dataArray.length][dataArray[0].length];

        waitingNode.addLast(new Nodes(startX, startY));
        tempPoints[startY][startX] = startPoint;

        generateSteps(steps, startPoint, waitingNode, tempPoints);

        printSteps(steps);


    }

    private static void printSteps(List<String> steps) {

        System.out.println("Steps for Game");
        System.out.println("-------------------------");
        System.out.println("\n");

        for (int x = steps.size() - 1; x >= 0; x--) {

            System.out.println(steps.get(x));
        }

        System.out.println("\n");
        System.out.println("-------------------------");
    }

    private static void generateSteps(List<String> steps, Nodes startPoint, LinkedList<Nodes> holder, Nodes[][] tempPoints) {

        while (holder.size() != 0) {
            Nodes currPos = holder.pollFirst();
            for (int move = 1; move < 5; move++) {

                Nodes nextNode = getNextNode(tempPoints, currPos, move);


                if (nextNode != null) {
                    holder.addLast(nextNode);
                    tempPoints[nextNode.getyCardinality()][nextNode.getxCardinality()] = new Nodes(currPos.getxCardinality(), currPos.getyCardinality());

                    getFinlSteps(steps, startPoint, tempPoints, currPos, nextNode);
                }
            }
        }
    }

    private static void getFinlSteps(List<String> steps, Nodes startPoint, Nodes[][] tempPoints, Nodes currPos, Nodes nextNode) {
        if (nextNode.getyCardinality() == endY && nextNode.getxCardinality() == endX) {
            Nodes tmp = currPos;
            int round = 0;

            while (tmp != startPoint) {

                round = addEndPoints(steps, currPos, round);

                Nodes tempPoint = tempPoints[tmp.getyCardinality()][tmp.getxCardinality()];

                addMiddlePoints(steps, tmp, tempPoint);

                addStartingPoints(steps, startPoint, tempPoint);

                tmp = tempPoints[tmp.getyCardinality()][tmp.getxCardinality()];

            }
        }
    }

    private static void addMiddlePoints(List<String> steps, Nodes tmp, Nodes tempPoint) {
        if (tmp.getyCardinality() > tempPoint.getyCardinality()) {
            steps.add("Move Down to (" + (tmp.getxCardinality()+1) + "," + (tmp.getyCardinality()+1) + ")");
        } else if (tmp.getyCardinality() < tempPoint.getyCardinality()){
            steps.add("Move Up to (" + (tmp.getxCardinality()+1) + "," + (tmp.getyCardinality()+1) + ")");
        }

        if (tmp.getxCardinality() < tempPoint.getxCardinality()) {
            steps.add("Move Left to (" + (tmp.getxCardinality()+1) + "," + (tmp.getyCardinality()+1) + ")");
        } else if (tmp.getxCardinality() > tempPoint.getxCardinality()){
            steps.add("Move Right to (" + (tmp.getxCardinality()+1) + "," + (tmp.getyCardinality()+1) + ")");
        }
    }

    private static void addStartingPoints(List<String> steps, Nodes startPoint, Nodes tempPoint) {
        if (tempPoint == startPoint){
            if (tempPoint.getyCardinality() > startPoint.getyCardinality()) {
                steps.add("Move Down to (" + (tempPoint.getxCardinality()+1) + "," + (tempPoint.getyCardinality()+1) + ")");
            } else if (tempPoint.getyCardinality() < startPoint.getyCardinality()){
                steps.add("Move Up to (" + (tempPoint.getxCardinality()+1) + "," + (tempPoint.getyCardinality()+1) + ")");
            }

            if (tempPoint.getxCardinality() < startPoint.getxCardinality()) {
                steps.add("Move Left to (" + (tempPoint.getxCardinality()+1) + "," + (tempPoint.getyCardinality()+1) + ")");
            } else if (tempPoint.getxCardinality() > startPoint.getxCardinality()){
                steps.add("Move Right to (" + (tempPoint.getxCardinality()+1) + "," + (tempPoint.getyCardinality()+1) + ")");
            }

            steps.add("Start at (" + (startPoint.getxCardinality()+1) + "," + (startPoint.getyCardinality()+1) + ")");
        }
    }

    private static int addEndPoints(List<String> steps, Nodes currPos, int round) {
        if (round == 0) {
            steps.add("Done");
            if (endY > currPos.getyCardinality()) {
                steps.add("Move Down to (" + (currPos.getxCardinality()+1) + "," + (endY+1) + ")");
            } else if (endY < currPos.getyCardinality()){
                steps.add("Move Up to (" + (currPos.getxCardinality()+1) + "," + (endY+1) + ")");
            }

            if (endX < currPos.getxCardinality()) {
                steps.add("Move Left to (" + (endY+1) + "," + (currPos.getyCardinality()+1) + ")");
            } else if (endX > currPos.getxCardinality()){
                steps.add("Move Right to (" + (endY+1) + "," + (currPos.getyCardinality()+1) + ")");
            }

            round++;
        }
        return round;
    }

    private static Nodes getNextNode(Nodes[][] tempPoints, Nodes currPos, int move) {
        Nodes nextPos;

        int x = currPos.getxCardinality();
        int y = currPos.getyCardinality();

        int xDiffValue = (move == 1 ? -1 : (move == 2 ? 1 : 0));
        int yDiffValue = (move == 3 ? -1 : (move == 4 ? 1 : 0));

        int i = 1;
        while (x + i * xDiffValue >= 0
                && x + i * xDiffValue < dataArray[0].length
                && y + i * yDiffValue >= 0
                && y + i * yDiffValue < dataArray.length
                && dataArray[y + i * yDiffValue][x + i * xDiffValue] != 1) {
            i++;
        }

        i--;

        if (tempPoints[y + i * yDiffValue][x + i * xDiffValue] != null) {
            nextPos = null;
        }
        else
            nextPos = new Nodes(x + i * xDiffValue, y + i * yDiffValue);
        return nextPos;
    }


}
