package gna;

import libpract.PriorityFunc;

import java.util.*;

public class Solver  {

    PriorityQueue<Board> boardPQ;
    List<Board> possibleSolutions;
    Board endBoard;

    /**
     * Finds a solution to the initial board.
     *
     * @param priority is either PriorityFunc.HAMMING or PriorityFunc.MANHATTAN
     */
    public Solver(Board initial, PriorityFunc priority) {
        // Use the given priority function (either PriorityFunc.HAMMING
        // or PriorityFunc.MANHATTAN) to solve the puzzle.
        if (priority == PriorityFunc.HAMMING) {
            boardPQ = new PriorityQueue<Board>(10, new Comparator<Board>() {
                @Override
                public int compare(Board b1, Board b2) {
                    return b1.hamming() - b2.hamming();
                }
            });
        } else if (priority == PriorityFunc.MANHATTAN) {
            boardPQ = new PriorityQueue<Board>(10, new Comparator<Board>() {
                @Override
                public int compare(Board b1, Board b2) {
                    return b1.manhattan() - b2.manhattan();
                }
            });
        } else {
            throw new IllegalArgumentException("Priority function not supported");
        }
        boardPQ.add(initial);
        solve();
    }

    private void solve() {
       long begin = System.currentTimeMillis();

        possibleSolutions = new ArrayList<Board>();
        //get minimum with heuristic function
        Board lowHeurBoard = boardPQ.poll();
        possibleSolutions.add(lowHeurBoard);
        while (!lowHeurBoard.isSolved()) {
            for (Board neighbor : lowHeurBoard.neighbors()) {
                    boardPQ.add(neighbor);
            }
            lowHeurBoard = boardPQ.poll();
            possibleSolutions.add(lowHeurBoard);
        }
        endBoard = lowHeurBoard;

        System.out.println("tijd = " + (System.currentTimeMillis() - begin));
    }


    /**
     * Returns a Collection of board positions as the solution. It should contain the initial
     * Board as well as the solution (if these are equal only one Board is returned).
     */
    public Collection<Board> solution() {
        List<Board> solution = new ArrayList<Board>();
        solution.add(endBoard);
        Board tempBoard = endBoard;
        while (tempBoard.getPreviousBoard() != null) {
            solution.add(tempBoard.getPreviousBoard());
            tempBoard = tempBoard.getPreviousBoard();
        }
        Collections.reverse(solution);
        return  solution;
    }
}

