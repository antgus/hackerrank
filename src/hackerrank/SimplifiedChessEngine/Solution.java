package hackerrank.SimplifiedChessEngine;


import java.io.*;
import java.util.*;

public class Solution {
    private static final Piece EMPTY = null;
    private static final int BOARD_LENGTH = 4;
    private static final int WHITE = 0;
    private static final int BLACK = 1;
    private static int MAX_MOVES;
    private static final int NO_WINNER = -1;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numGames = in.nextInt();
        for(int i=0; i < numGames; i++) {
            Board b = loadBoard(in);
            solve(b);
        }
    }
    
    public static Board loadBoard(Scanner in) {
        int countWhite = in.nextInt();
        int countBlack = in.nextInt();
        MAX_MOVES = in.nextInt();
        
        Board b = new Board();
        for(int i=0; i < countWhite+countBlack; i++) {
            char pieceType = in.next().trim().charAt(0);
            int x = in.next().trim().charAt(0) - 'A';
            int y = in.nextInt() - 1; // 0-indexed
            Piece p;
            switch(pieceType) {
                case 'N': 
                    p = new Knight();
                    break;
                case 'Q':
                    p = new Queen();
                    break;
                case 'B':
                    p = new Bishop();
                    break;
                case 'R':
                    p = new Rook();
                    break;
                default:
                    throw new RuntimeException("Unsupported piece: " + pieceType);
            }
            p.isAlive = true;
            p.x = x;
            p.y = y;
            p.xStart = x;
            p.yStart = y;
            b.board[x][y] = p;
            if(i >= countWhite) {
                p.player = BLACK;
                b.blacks.add(p);
            } else {
                p.player = WHITE;
                b.whites.add(p);
            }
        }
        return b;
    }
    
    public static class Board {
        List<Piece> whites = new ArrayList<>(10);
        List<Piece> blacks = new ArrayList<>(10);
        Piece[][] board = new Piece[BOARD_LENGTH][BOARD_LENGTH];
        Stack<Move> moves = new Stack<>();
        
        public void applyMove(Move move) {
            moves.push(move);
            if(board[move.xTo][move.yTo] != EMPTY) {
                // there's a piece already there.
                Piece p = board[move.xTo][move.yTo];
                assert(p.player != move.p.player);
                if(p != EMPTY) {
                    p.isAlive = false;
                    move.killedPiece = p;
                }
            }
            move.p.x = move.xTo;
            move.p.y = move.yTo;
            board[move.xTo][move.yTo] = move.p;
            board[move.xFrom][move.yFrom] = EMPTY;
        }
        
        public void undoMove() {
            Move move = moves.pop();
            if(move.killedPiece != null) {
                Piece p = move.killedPiece;
                p.isAlive = true;
                p.x = move.xTo;
                p.y = move.yTo;
                board[move.xTo][move.yTo] = p;
            } else {
                board[move.xTo][move.yTo] = EMPTY;
            }
            move.p.x = move.xFrom;
            move.p.y = move.yFrom;
            assert(board[move.p.x][move.p.y] == EMPTY);
            board[move.xFrom][move.yFrom] = move.p;
        }
        
        public List<Piece> getPieces(int player) {
            if(player == WHITE) {
                return whites;
            } else {
                return blacks;
            }
        }
        
        public boolean isQueenDead(int player) {
            for(Piece p: getPieces(player)) {
                if(p instanceof Queen) {
                    return !p.isAlive;
                }
            }
            return true;
        }
        
        public String toString() {
            String boardString = "";
            for(int i=0; i < board.length; i++) {
                for(int j=0; j < board[0].length; j++) {
                    String s;
                    if(board[i][j] == null) {
                        s = "..";
                    } else {
                        s = board[i][j].toString();
                    }
                    boardString += s;
                }
                boardString += "\n";
            }
            return boardString;
        }
    }
    
    public static void solve(Board b) {
        int currentPlayer = WHITE; 
        int winner = getWinner(b, currentPlayer);
        String whiteWins = winner == WHITE ? "YES" : "NO";
        System.out.println(whiteWins);
    }
    
    public static int getWinner(Board board, int currentPlayer) {
        System.out.println(board);
        if(board.isQueenDead(currentPlayer)) {
            return nextPlayer(currentPlayer);
        } else if(board.moves.size() >= MAX_MOVES) {
            return -1;
        }
        List<Move> possibleMoves = new ArrayList<>(10);
        int result = NO_WINNER;
        for(Piece p: board.getPieces(currentPlayer)) {
            if(p.isAlive) {
                possibleMoves.clear();
                p.populatePossibleMoves(board, possibleMoves);
                for(Move m: possibleMoves) {
                    board.applyMove(m);
                    int winner = getWinner(board, nextPlayer(currentPlayer));
                    board.undoMove();
                    if(winner == currentPlayer) {
                        return currentPlayer;
                    } else if(winner != NO_WINNER) {
                        result = nextPlayer(currentPlayer);
                    }
                }
            }
        }
        return result;
    }
    
    public static int nextPlayer(int currentPlayer) {
        return currentPlayer == 1 ? 0 : 1;
    }
    
    public static class Move {
        Piece p;
        int xFrom;
        int yFrom;
        int xTo;
        int yTo;
        Piece killedPiece = null;
        
        Move(Piece p, int x, int y, int xFrom, int yFrom) {
            this.p = p;
            this.xTo = x;
            this.yTo = y;
            this.xFrom = xFrom;
            this.yFrom = yFrom;
        }
    }
    
    public abstract static class Piece {
        int player;
        int xStart;
        int yStart;
        int x;
        int y;
        boolean isAlive;
        
        public abstract void populatePossibleMoves(Board b, List<Move> moveList);
    }
    
    public static class Queen extends Piece {
        private static int[][] directions = {{1,-1}, {1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}};

        @Override
        public void populatePossibleMoves(Board b, List<Move> moveList) {
            for(int[] direction: directions) {
                for(int i=1; i < BOARD_LENGTH; i++) {
                    int newX = x + direction[0]*i;
                    int newY = y + direction[1]*i;
                    if(isValidMove(newX, newY, b , this)) {
                        moveList.add(new Move(this, newX, newY, x, y));
                    } else {
                        break;
                    }
                }
            }
        }
        
        public String toString() {
            String player = this.player == WHITE ? "+" : "-";
            return player+"Q";
        }
    }
    
    public static boolean isValidMove(int newX, int newY, Board b, Piece p) {
        return isWithinBounds(newX, newY) && hasNoPieceOfSamePlayer(newX, newY, b, p.player);
    }
    
    public static boolean hasNoPieceOfSamePlayer(int newX, int newY, Board b, int player) {
        Piece p = b.board[newX][newY];
        return !(p != EMPTY && p.player==player);
    }
    
    public static class Knight extends Piece {
        private static final int[][] moves = {{1,2}, {2,1}, {-1,2}, {-2,1},{-1,-2},{-2,-1},{1,-2},{2,-1}};
        
        @Override
        public void populatePossibleMoves(Board b, List<Move> moveList) {
            for(int[] move: moves) {
                int newX = x + move[0];
                int newY = y + move[1];
                if(isValidMove(newX, newY, b, this)) {
                    moveList.add(new Move(this, newX, newY, x, y));
                }
            }
        }
        
        public String toString() {
            String player = this.player == WHITE ? "+" : "-";
            return player+"N";
        }
    }
    
    public static class Bishop extends Piece {
        private static final int[][] directions = {{1,1}, {-1,1}, {1,-1}, {1,1}};
        
        @Override
        public void populatePossibleMoves(Board b, List<Move> moveList) {
            for(int[] direction: directions) {
                for(int i=1; i < BOARD_LENGTH; i++) {
                    int newX = x + direction[0]*i;
                    int newY = y + direction[1]*i;
                    if(isValidMove(newX, newY, b, this)) {
                        moveList.add(new Move(this, newX, newY, x ,y));
                    } else {
                        break;
                    }
                }
            }
        }
        
        public String toString() {
            String player = this.player == WHITE ? "+" : "-";
            return player+"B";
        }
    }
    
    public static class Rook extends Piece {
        private static final int[][] directions = {{1,0},{0,1},{-1,0},{0,-1}};
        
        @Override
        public void populatePossibleMoves(Board b, List<Move> moveList) {
            for(int[] direction: directions) {
                for(int i=1; i < BOARD_LENGTH; i++) {
                    int newX = x + direction[0]*i;
                    int newY = y + direction[1]*i;
                    if(isValidMove(newX, newY, b, this)) {
                        moveList.add(new Move(this, newX, newY, x ,y));
                    } else {
                        break;
                    }
                }
            }
        }
        
        public String toString() {
            String player = this.player == WHITE ? "+" : "-";
            return player+"R";
        }
    }
    
    public static boolean isWithinBounds(int x, int y) {
        return x>=0 && x < BOARD_LENGTH && y>=0 && y < BOARD_LENGTH;
    }
}