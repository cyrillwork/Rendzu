/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rendzu;

import java.util.Vector;

/**
 *
 * @author Kiselev_KV
 */
public class BoardModel extends java.util.Observable implements java.io.Serializable
{

    private int M;
    private int N;
    private Cell board[][];
    //private Cell turn;
    private Point last_p;

    public enum Cell
    {

        Empty,
        White,
        Black
    }

    public static class Point
    {

        private int i;
        private int j;

        public Point(int i, int j)
        {
            this.i = i;
            this.j = j;
        }

        public Point(Point p)
        {
            this.i = p.i;
            this.j = p.j;
        }

        public int getI()
        {
            return i;
        }

        public int getJ()
        {
            return j;
        }

        public void setIJ(int i, int j)
        {
            this.i = i;
            this.j = j;
        }

        public void setIJ(Point p)
        {
            this.i = p.getI();
            this.j = p.getJ();
        }

        public void setIJplus(int i, int j)
        {
            this.i += i;
            this.j += j;
        }
    }

    public static class Check
    {

        private Point p;
        private int line;

        public Check(Point p, int line)
        {
            this.p = new Point(p.getI(), p.getJ());
            this.line = line;
        }

        public Point getPoint()
        {
            return p;
        }
        
        public int getLine()
        {
            return line;
        }
    }    

    public BoardModel()
    {
        M = 15;
        N = 15;
        board = new Cell[N][M];
        last_p = new Point(-1, -1);
        //turn = Cell.White;
        setClear();
    }

    public final void setClear()
    {
        last_p.setIJ(-1, -1);
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
            {
                board[i][j] = Cell.Empty;
            }
        }
    }

    public int getM()
    {
        return M;
    }

    public int getN()
    {
        return N;
    }

    public Cell[][] getBoard()
    {
        return board;
    }
    
    public Point getLatsPoint()
    {
        return last_p;
    }

    
    public Cell getCell(Point p)
    {
        return board[p.getI()][p.getJ()];
    }

    public void changeCell(Point p, Cell cell)
    {
        if (board[p.i][p.j] != Cell.Empty)
        {
            return;
        }

        board[p.i][p.j] = cell;

        last_p.setIJ(p);
    }

    public int checkEmptyCell(Point point, Cell[][] board1, Cell cell)
    {
        boolean arr_direct[] = new boolean[8];

        board1[point.i][point.j] = cell;

        if (checkCell(point, 5, board1, arr_direct))
        {
            return 5;
        }
        if (checkCell(point, 4, board1, arr_direct))
        {
            return 4;
        }
        if (checkCell(point, 3, board1, arr_direct))
        {
            return 3;
        }
        if (checkCell(point, 2, board1, arr_direct))
        {
            return 2;
        }
        return 0;
    }

    public Cell invCell(Cell cell)
    {
        Cell cell1 = Cell.Empty;
        if (cell == Cell.Black)
        {
            cell1 = Cell.White;
        } else if (cell == Cell.White)
        {
            cell1 = Cell.Black;
        }
        return cell1;
    }

    public Cell[][] myClone(Cell[][] array1)
    {
        Cell[][] array2 = new Cell[N][M];

        //System.arraycopy(array1, 0, array2, 0, array1.length);


        for (int i = 0; i < N; i++)
        {
            System.arraycopy(array1[i], 0, array2[i], 0, M);
        }

        return array2;
    }

    public Point getDirection(int step, int direct, Point point)
    {
        Point p1 = new Point(point);
        switch (direct)
        {
            case 0:
            {
                p1.setIJplus(step, 0);
            }
            break;
            case 1:
            {
                p1.setIJplus(0, step);
            }
            break;
            case 2:
            {
                p1.setIJplus(step, step);
            }
            break;
            case 3:
            {
                p1.setIJplus(-step, 0);
            }
            break;
            case 4:
            {
                p1.setIJplus(0, -step);
            }
            break;
            case 5:
            {
                p1.setIJplus(-step, -step);
            }
            break;
            case 6:
            {
                p1.setIJplus(-step, step);
            }
            break;
            case 7:
            {
                p1.setIJplus(step, -step);
            }
            break;
        }
        return p1;
    }
    
    public boolean check4Cell(Cell[][] _board, Cell cell)
    {
        boolean arr_direct[] = new boolean[8];
        for (int iii = 0; iii < N; iii++)
        {
            for (int jjj = 0; jjj < M; jjj++)
            {
                if (_board[iii][jjj] == cell)
                {
                    Point p1 = new Point(iii, jjj);
                    Cell[][] board4 = myClone(_board);
                    if (checkCell(p1, 4, board4, arr_direct))
                    {
                        for (int kkk = 0; kkk < arr_direct.length; kkk++)
                        {
                            if (arr_direct[kkk])
                            {
                                Point p2 = getDirection(-1, kkk, p1);
                                Point p3 = getDirection(4, kkk, p1);
                                if ((board4[p2.i][p2.j] == Cell.Empty)
                                    && (board4[p3.i][p3.j] == Cell.Empty))
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public Point getComputerPoint(Cell cell)
    {
        boolean first = true;
        Cell board2[][] = board.clone();
        boolean arr_direct[] = new boolean[8];

        //System.out.println("getComputerPoint" + cell);

        //Is it first time?
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
            {
                if (board[i][j] == cell)
                {
                    first = false;
                    break;
                }
            }
        }
        if (first)
        {
            if (board2[N / 2][M / 2] == Cell.Empty)
            {
                return new Point(N / 2, M / 2);
            } else
            {
                return new Point((N / 2) + 1, M / 2);
            }
        }
        //System.out.println("search one ball");
        //if only one ball to win
        for (int iii = 0; iii < N; iii++)
        {
            for (int jjj = 0; jjj < M; jjj++)
            {
                if (board2[iii][jjj] == Cell.Empty)
                {
                    //Set a ball in empty cell and check if win
                    Point p1 = new Point(iii, jjj);
                    Cell[][] board3 = myClone(board2);
                    board3[iii][jjj] = cell;
                    if (checkCell(p1, 5, board3, arr_direct))
                    {
                        return p1;
                    }
                }
            }
        }

        //if only one ball to win for another player
        Cell cell1 = invCell(cell);
        for (int iii = 0; iii < N; iii++)
        {
            for (int jjj = 0; jjj < M; jjj++)
            {
                if (board2[iii][jjj] == Cell.Empty)
                {
                    //Set a ball in empty cell and check if win
                    Point p1 = new Point(iii, jjj);
                    Cell[][] board3 = myClone(board2);
                    board3[iii][jjj] = cell1;
                    if (checkCell(p1, 5, board3, arr_direct))
                    {
                        return p1;
                    }
                }
            }
        }
        
        //search one ball for 4-lines
        for (int iii = 0; iii < N; iii++)
        {
            for (int jjj = 0; jjj < M; jjj++)
            {
                if (board2[iii][jjj] == Cell.Empty)
                {
                    Cell[][] board3 = myClone(board2);
                    board3[iii][jjj] = cell1;
                    if( check4Cell(board3, cell1) )
                    {
                        return new Point(iii, jjj);
                    }
                }
            }
        }
        
        //search 3-lines another player
        for (int iii = 0; iii < N; iii++)
        {
            for (int jjj = 0; jjj < M; jjj++)
            {
                if (board2[iii][jjj] == cell1)
                {
                    Point p1 = new Point(iii, jjj);
                    Cell[][] board3 = myClone(board2);
                    if (checkCell(p1, 3, board3, arr_direct))
                    {
                        for (int kkk = 0; kkk < arr_direct.length; kkk++)
                        {
                            if (arr_direct[kkk])
                            {
                                Point p2 = getDirection(-1, kkk, p1);
                                Point p3 = getDirection(3, kkk, p1);
                                if ((board2[p2.i][p2.j] == Cell.Empty)
                                    && (board2[p3.i][p3.j] == Cell.Empty))
                                {
                                    return p2;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        
        
        
        //System.out.println("search good decition");
        //search good decition
        java.util.ArrayList<Check> decitions = new java.util.ArrayList<Check>(10);

        for (int iii = 0; iii < N; iii++)
        {
            for (int jjj = 0; jjj < M; jjj++)
            {//Find cell with compuer ball and try to set ball near
                if (board2[iii][jjj] == cell)
                {
                    int I = iii;
                    int J = jjj;

                    int res = 0;
                    Point res_point = null;

                    if (!((I + 1 >= N) || (Cell.Empty != board2[I + 1][J])))
                    {
                        Point p1 = new Point(I + 1, J);
                        int res1 = checkEmptyCell(p1, myClone(board2), cell);
                        if (res < res1)
                        {
                            res = res1;
                            res_point = p1;
                        }
                    }

                    if (!((J + 1 >= M) || (Cell.Empty != board2[I][J + 1])))
                    {
                        Point p2 = new Point(I, J + 1);
                        int res2 = checkEmptyCell(p2, myClone(board2), cell);
                        if (res < res2)
                        {
                            res = res2;
                            res_point = p2;
                        }
                    }

                    if (!((J + 1 >= M) || ((I + 1 >= N)) || (Cell.Empty != board2[I + 1][J + 1])))
                    {
                        Point p3 = new Point(I + 1, J + 1);
                        int res3 = checkEmptyCell(p3, myClone(board2), cell);
                        if (res < res3)
                        {
                            res = res3;
                            res_point = p3;
                        }
                    }

                    if (!((I - 1 < 0) || (Cell.Empty != board2[I - 1][J])))
                    {
                        Point p4 = new Point(I - 1, J);
                        int res4 = checkEmptyCell(p4, myClone(board2), cell);
                        if (res < res4)
                        {
                            res = res4;
                            res_point = p4;
                        }
                    }

                    if (!((J - 1 < 0) || (Cell.Empty != board2[I][J - 1])))
                    {
                        Point p5 = new Point(I, J - 1);
                        int res5 = checkEmptyCell(p5, myClone(board2), cell);
                        if (res < res5)
                        {
                            res = res5;
                            res_point = p5;
                        }
                    }

                    if (!((I - 1 < 0) || (J - 1 < 0) || (Cell.Empty != board2[I - 1][J - 1])))
                    {
                        Point p6 = new Point(I - 1, J - 1);
                        int res6 = checkEmptyCell(p6, myClone(board2), cell);
                        if (res < res6)
                        {
                            res = res6;
                            res_point = p6;
                        }
                    }

                    if (!((I - 1 < 0) || (J + 1 >= M) || (Cell.Empty != board2[I - 1][J + 1])))
                    {
                        Point p7 = new Point(I - 1, J + 1);
                        int res7 = checkEmptyCell(p7, myClone(board2), cell);
                        if (res < res7)
                        {
                            res = res7;
                            res_point = p7;
                        }
                    }

                    if (!((J - 1 < 0) || (I + 1 >= M) || (Cell.Empty != board2[I + 1][J - 1])))
                    {
                        Point p8 = new Point(I + 1, J - 1);
                        int res8 = checkEmptyCell(p8, myClone(board2), cell);
                        if (res < res8)
                        {
                            res = res8;
                            res_point = p8;
                        }
                    }
                    if (res > 0)
                    {
                        decitions.add(new Check(res_point, res));

                    }

                }
            }
        }

        int res = 0;
        Point point = null;

        for (int iii = 0; iii < decitions.size(); iii++)
        {
            Check ccc = decitions.get(iii);
            if (res < ccc.getLine())
            {
                res = ccc.getLine();
                point = ccc.getPoint();
            }
        }

        return point;
    }

    /* Count cells in line*/
    public boolean checkCell(Point point, int Count, Cell board1[][], boolean direct[])
    {
        int I = point.i;
        int J = point.j;
        Cell w = board1[I][J];

        boolean bool1 = true;
        boolean bool2 = true;
        boolean bool3 = true;
        boolean bool4 = true;
        boolean bool5 = true;
        boolean bool6 = true;
        boolean bool7 = true;
        boolean bool8 = true;

        int _bool1 = 0;
        int _bool2 = 0;
        int _bool3 = 0;
        int _bool4 = 0;
        int _bool5 = 0;
        int _bool6 = 0;
        int _bool7 = 0;
        int _bool8 = 0;

        for (int i = 0; i < direct.length; i++)
        {
            direct[i] = false;
        }

        //System.out.println("I="+I+" J="+J+" w="+w);

        for (int i = 1; i < Count; i++)
        {
            if ((I + i >= N) || (w != board1[I + i][J]))
            {
                bool1 = false;
            } else
            {
                _bool1++;
            }

            if ((J + i >= M) || (w != board1[I][J + i]))
            {
                bool2 = false;
            } else
            {
                _bool2++;
            }

            if ((J + i >= M) || ((I + i >= N)) || (w != board1[I + i][J + i]))
            {
                bool3 = false;
            } else
            {
                _bool3++;
            }

            if ((I - i < 0) || (w != board1[I - i][J]))
            {
                bool4 = false;
            } else
            {
                _bool4++;
            }

            if ((J - i < 0) || (w != board1[I][J - i]))
            {
                bool5 = false;
            } else
            {
                _bool5++;
            }

            if ((I - i < 0) || (J - i < 0) || (w != board1[I - i][J - i]))
            {
                bool6 = false;
            } else
            {
                _bool6++;
            }

            if ((I - i < 0) || (J + i >= M) || (w != board1[I - i][J + i]))
            {
                bool7 = false;
            } else
            {
                _bool7++;
            }

            if ((J - i < 0) || (I + i >= M) || (w != board1[I + i][J - i]))
            {
                bool8 = false;
            } else
            {
                _bool8++;
            }

            //System.out.println(""+bool1+bool2+bool3+bool4+bool5+bool6+bool7+bool8);
        }

        if (bool1)
        {
            direct[0] = true;
        }
        if (bool2)
        {
            direct[1] = true;
        }
        if (bool3)
        {
            direct[2] = true;
        }
        if (bool4)
        {
            direct[3] = true;
        }
        if (bool5)
        {
            direct[4] = true;
        }
        if (bool6)
        {
            direct[5] = true;
        }
        if (bool7)
        {
            direct[6] = true;
        }
        if (bool8)
        {
            direct[7] = true;
        }


        return (bool1 || bool2 || bool3 || bool4 || bool5 || bool6 || bool7 || bool8);
    }

    public Cell isEnd(Point p1, Point p2)
    {
        Cell res = Cell.Empty;
        boolean arr_direct[] = new boolean[8];

        if (last_p.getI() != -1)
        {
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < M; j++)
                {
                    if (board[i][j] != Cell.Empty)
                    {
                        if (checkCell(new Point(i, j), 5, board, arr_direct))
                        {
                            //System.out.println("i="+" j="+j);
                            //System.out.println("" + board[i][j]);
                            p1.setIJ(i, j);
                            for(int k=0; k<arr_direct.length; k++)
                            {
                                if(arr_direct[k])
                                {
                                    int step = 4;
                                    switch(k)
                                    {
                                        case 0:
                                            p2.setIJ(i + step, j);
                                        break;
                                        case 1:
                                            p2.setIJ(i, j + step);
                                        break;
                                        case 2:
                                            p2.setIJ(i + step, j + step);
                                        break;
                                        case 3:
                                            p2.setIJ(i - step, j);
                                        break;
                                        case 4:
                                            p2.setIJ(i, j - step);
                                        break;
                                        case 5:
                                            p2.setIJ(i - step, j - step);
                                        break;
                                        case 6:
                                            p2.setIJ(i - step, j + step);
                                        break;
                                        case 7:
                                            p2.setIJ(i + step, j - step);
                                        break;
                                    }
                                    break;
                                }
                            }
                            return board[i][j];
                        }
                    }
                }
            }
        }
        return res;
    }
}
