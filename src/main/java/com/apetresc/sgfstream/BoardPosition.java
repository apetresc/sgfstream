package com.apetresc.sgfstream;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class BoardPosition {
    public static final int OFF_BOARD = -1;
    public static final int BLANK = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    private int boardSize;
    private int[][] board;
    private int[] lastMove = new int[2];
    private int moveNumber = 0;

    public BoardPosition(int boardSize) {
        this.boardSize = boardSize;
        this.board = new int[boardSize][boardSize];
    }

    public void applyNode(SGFNode node) {
        if (node.getProperties().containsKey("W")) {
            for (String move : node.getProperties().get("W").getValues()) {
                board[move.charAt(0) - 'a'][move.charAt(1) - 'a'] = 2;
                lastMove[0] = move.charAt(0) - 'a';
                lastMove[1] = move.charAt(1) - 'a';
                moveNumber++;
            }
        }
        if (node.getProperties().containsKey("B")) {
            for (String move : node.getProperties().get("B").getValues()) {
                board[move.charAt(0) - 'a'][move.charAt(1) - 'a'] = 1;
                lastMove[0] = move.charAt(0) - 'a';
                lastMove[1] = move.charAt(1) - 'a';
                moveNumber++;
            }
        }

        // Check for captures
        Set capturedStones = new HashSet();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if ((dx + dy != 1) && (dx + dy != -1)) continue;
                if (getPoint(lastMove[0] + dx, lastMove[1] + dy) == (board[lastMove[0]][lastMove[1]] == BLACK ? WHITE : BLACK)) {
                    Set group = floodFill(lastMove[0] + dx, lastMove[1] + dy);
                    if (!hasLiberties(group)) {
                        Iterator groupIterator = group.iterator();
                        while (groupIterator.hasNext()) {
                            int[] groupPoint = (int[]) groupIterator.next();
                            board[groupPoint[0]][groupPoint[1]] = BLANK;
                            capturedStones.add(groupPoint);
                        }
                    }
                }
            }
        }
    }

    public int getPoint(int x, int y) {
        if (x >= boardSize || x < 0 || y >= boardSize || y < 0)
            return OFF_BOARD;
        return board[x][y];
    }

    private Set floodFill(int x, int y) {
        Set group = new HashSet();
        LinkedList points = new LinkedList();
        points.add(new int[] {x, y});
        while (!points.isEmpty()) {
            int[] point = (int[]) points.removeFirst();
            group.add(point);
            for (int dx = -1; dx <= 1; dx++) {
                inner:
                for (int dy = -1; dy <= 1; dy++) {
                    if ((dx+dy != 1) && (dx+dy != -1)) continue;
                    if (getPoint(point[0]+dx, point[1]+dy) != getPoint(point[0], point[1])) continue;

                    Iterator it = group.iterator();
                    while (it.hasNext()) {
                        int[] p = (int[]) it.next();
                        if ((p[0] == point[0] + dx) && (p[1] == point[1] + dy)) {
                            continue inner;
                        }
                    }

                    points.addLast(new int[] {point[0]+dx, point[1]+dy});
                }
            }
        }
        return group;
    }

    private boolean hasLiberties(Set points) {
        Iterator it = points.iterator();
        while (it.hasNext()) {
            int[] point = (int[]) it.next();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if ((dx+dy != 1) && (dx+dy != -1)) continue;
                    if (getPoint(point[0]+dx, point[1]+dy) != BLANK) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int[] getLastMove() {
        return lastMove;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Move " + moveNumber + "\n");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                sb.append(board[j][i] == 0 ? '.' : board[j][i] == 1 ? '#' : 'O');
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
