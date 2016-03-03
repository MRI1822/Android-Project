package com.droidislands.othello;

import android.graphics.Color;


/**
 * Created by ID032389 on 8/22/2014.
 */
public  class Game_logic {

    private static int count = 0;
    private static boolean result = false;
    static int whiteScore =0 ;
    static  int blackScore = 0;
    public static void paint( boolean isWhite ,int row,int column)
    {
        if (isWhite) {
            Othello.ob[row][column].setBackgroundColor(Color.WHITE);
         //   Othello.ob[row][column].setBackgroundResource(R.drawable.white);
            Othello.ob[row][column].isWhite = true;
            Othello.ob[row][column].isBlack = false;
            Othello.ob[row][column].isGreen = false;
        }
        else
        {
            Othello.ob[row][column].setBackgroundColor(Color.BLACK);
          //  Othello.ob[row][column].setBackgroundResource(R.drawable.black);
            Othello.ob[row][column].isWhite = false;
            Othello.ob[row][column].isBlack = true;
            Othello.ob[row][column].isGreen = false;


        }
    }

    public static boolean checkValidPlace(boolean isWhite)
    {
        boolean validity =false ;

        for (int row = 0; row <8 ; row++) {
            for (int column = 0; column <8 ; column++) {
                if(Othello.ob[row][column].isGreen)
                {
                   if (leftCheck(row, column - 1, isWhite) || rightCheck(row, column + 1, isWhite) || bottomCheck(row + 1, column, isWhite) || topCheck(row - 1, column, isWhite) || leftTopCheck(row - 1, column - 1, isWhite) || leftBottomCheck(row + 1, column - 1, isWhite) || rightBottomCheck(row + 1, column + 1, isWhite) || rightTopCheck(row - 1, column + 1, isWhite))
                  {
                    count = 0; //refreshes the count which got incremented during the checking in above else if condition
                    validity = true;
                    return validity;

                  }

                }

            }

        }
        return validity;
    }

    public static void CalculateScore ()
    {
         whiteScore =0 ;  blackScore =0 ;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j <8 ; j++) {
                if (Othello.ob[i][j].isWhite)
                {
                    whiteScore ++;
                }
                else if (Othello.ob[i][j].isBlack)
                {
                    blackScore ++;
                }

            }

        }

    }


    public static boolean isValidButton(int row,int column,boolean isWhite) {

        boolean validity ;
        if (!Othello.ob[row][column].isGreen)
        {
            validity = false;
        }

        else if (!leftCheck(row, column - 1, isWhite) && !rightCheck(row, column + 1, isWhite) && !bottomCheck(row + 1, column, isWhite) && !topCheck(row - 1, column, isWhite) && !leftTopCheck(row - 1, column - 1, isWhite) && !leftBottomCheck(row + 1, column - 1, isWhite) && !rightBottomCheck(row + 1, column + 1, isWhite) && !rightTopCheck(row - 1, column + 1, isWhite))
        {
            count = 0; //refreshes the count which got incremented during the checking in above else if condition
            validity = false;
        }
        else
            validity = true;

       return  validity ;
    }

        private static boolean leftCheck(int row, int column,boolean isWhite)
    {
        if (isWhite) {

            if (column < 0) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack) {
                count++;
                leftCheck(row, column - 1, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite && Othello.ob[row][column+1].isBlack)
            {
                result = true;
                return result;
            }
            else
            {
                result=false;
                return  result;
            }


        } else
        {
            if (column < 0) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite) {
                count++;
                leftCheck(row, column - 1,isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack && Othello.ob[row][column+1].isWhite)
            {
                result = true;
                return result;
            }
            else{
                result=false;
                return  result;
            }
        }
        return result;
    }
    private static boolean rightCheck(int row, int column,boolean isWhite)
    {
        if (isWhite) {

            if (column > 7 ) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack) {
                count++;
                rightCheck(row, column + 1, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite && Othello.ob[row][column-1].isBlack)
            {
                result = true;
                return result;
            }
            else
            {
                result=false;
                return  result;
            }


        } else
        {
            if (column > 7) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite) {
                count++;
                rightCheck(row, column + 1,isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack && Othello.ob[row][column-1].isWhite)
            {
                result = true;
                return result;
            }
            else{
                result=false;
                return  result;
            }
        }
        return result;
    }

        private static boolean bottomCheck(int row, int column,boolean isWhite)
    {
        if (isWhite) {

            if (row > 7 ) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack) {
                count++;
                bottomCheck(row + 1, column, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite && Othello.ob[row-1][column].isBlack)
            {
                result = true;
                return result;
            }
            else
            {
                result=false;
                return  result;
            }


        } else
        {
            if (row > 7) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite) {
                count++;
                bottomCheck(row + 1, column, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack && Othello.ob[row-1][column].isWhite)
            {
                result = true;
                return result;
            }
            else{
                result=false;
                return  result;
            }
        }
        return result;
    }

    private static boolean topCheck(int row, int column,boolean isWhite)
    {
        if (isWhite) {

            if (row < 0 ) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack) {
                count++;
                topCheck(row - 1, column, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite && Othello.ob[row+1][column].isBlack)
            {
                result = true;
                return result;
            }
            else
            {
                result=false;
                return  result;
            }


        } else
        {
            if (row < 0) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite) {
                count++;
                topCheck(row - 1, column, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack && Othello.ob[row+1][column].isWhite)
            {
                result = true;
                return result;
            }
            else{
                result=false;
                return  result;
            }
        }
        return result;
    }

    private static boolean leftTopCheck(int row, int column,boolean isWhite)
    {
        if (isWhite) {

            if (row < 0 || column <0) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack) {
                count++;
                leftTopCheck(row - 1, column - 1, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite && Othello.ob[row+1][column+1].isBlack)
            {
                result = true;
                return result;
            }
            else
            {
                result=false;
                return  result;
            }


        } else
        {
            if (row < 0 || column <0) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite) {
                count++;
                leftTopCheck(row - 1, column - 1, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack && Othello.ob[row+1][column +1].isWhite)
            {
                result = true;
                return result;
            }
            else{
                result=false;
                return  result;
            }
        }
        return result;
    }

    private static boolean rightBottomCheck(int row, int column,boolean isWhite)
    {
        if (isWhite) {

            if (row > 7 || column >7) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack) {
                count++;
                rightBottomCheck(row+1, column+1, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite && Othello.ob[row-1][column-1].isBlack)
            {
                result = true;
                return result;
            }
            else
            {
                result=false;
                return  result;
            }


        } else
        {
            if (row > 7 || column > 7) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite) {
                count++;
                rightBottomCheck(row + 1, column + 1,isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack && Othello.ob[row-1][column-1].isWhite)
            {
                result = true;
                return result;
            }
            else{
                result=false;
                return  result;
            }
        }
        return result;
    }

    private static boolean rightTopCheck(int row, int column,boolean isWhite)
    {
        if (isWhite) {

            if (row < 0 || column >7) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack) {
                count++;
                rightTopCheck(row - 1, column + 1, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite && Othello.ob[row+1][column-1].isBlack)
            {
                result = true;
                return result;
            }
            else
            {
                result=false;
                return  result;
            }


        } else
        {
            if (row < 0 || column > 7) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite) {
                count++;
                rightTopCheck(row - 1, column + 1,isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack && Othello.ob[row+1][column-1].isWhite)
            {
                result = true;
                return result;
            }
            else{
                result=false;
                return  result;
            }
        }
        return result;
    }

    private static boolean leftBottomCheck(int row, int column,boolean isWhite)
    {
        if (isWhite) {

            if (row > 7 || column <0) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack) {
                count++;
                leftBottomCheck(row + 1, column - 1, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite && Othello.ob[row-1][column+1].isBlack)
            {
                result = true;
                return result;
            }
            else
            {
                result=false;
                return  result;
            }


        } else
        {
            if (row > 7 || column < 0) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isWhite) {
                count++;
                leftBottomCheck(row + 1, column - 1, isWhite);

            } else if (Othello.ob[row][column].isGreen) {
                result = false;
                return result;
            } else if (Othello.ob[row][column].isBlack && Othello.ob[row-1][column+1].isWhite)
            {
                result = true;
                return result;
            }
            else{
                result=false;
                return  result;
            }
        }
        return result;
    }






    public static void play(int row,int column,boolean isWhite)
    {
         count =0;
        if(leftCheck(row,column-1,isWhite))
        {
            for (int i = 1; i <=count; i++)
            {
                paint(isWhite,row,column-i);
            }

        }

        count = 0;
        if(rightCheck(row, column + 1, isWhite))
        {
            for (int i = 1; i <=  count; i++)
            {
                paint(isWhite,row,column+i);
            }

        }
        count = 0;
        if(bottomCheck(row + 1, column, isWhite))
        {
            for (int i = 1; i <=  count; i++)
            {
                paint(isWhite,row+i,column);
            }

        }
        count =0;
        if(topCheck(row - 1, column, isWhite))
        {
            for (int i = 1; i <=  count; i++)
            {
                paint(isWhite,row-i,column);
            }

        }
        count =0;
        if(leftTopCheck(row - 1, column- 1, isWhite))
        {
            for (int i = 1; i <=  count; i++)
            {
                paint(isWhite,row-i,column-i);
            }

        }
        count = 0;
        if(rightBottomCheck(row + 1, column+ 1, isWhite))
        {
            for (int i = 1; i <=  count; i++)
            {
                paint(isWhite,row+i,column+i);
            }

        }
        count =0 ;
        if(rightTopCheck(row - 1, column + 1, isWhite))
        {
            for (int i = 1; i <=  count; i++)
            {
                paint(isWhite,row -i,column+i);
            }

        }

        count =0;

        if(leftBottomCheck(row+1,column-1,isWhite))
        {
            for (int i = 1; i <=  count; i++)
            {
                paint(isWhite,row +i,column-i);
            }
        }
        count =0;

        //remove isblack wherever necessary


    }

}
