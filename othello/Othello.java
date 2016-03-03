package com.droidislands.othello;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class Othello extends Activity {


    /*
    public static int pxToDp(int px)
{
    return (int) (px / Resources.getSystem().getDisplayMetrics().density);
}
     */

    private float convertPixelsToDp(float px){
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
         float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public  enum enumColors
    {
        green,
        black,
        white
    }
    public static boolean isPlayerWhite = true;

    //Definitions
   private TableLayout othelloBoard = null;
   public static othello_buttons [][] ob = null;
   private  int prevBackgroundColor=0;
    private TableRow gameInfoBlack = null;
    private TableRow gameRow = null;
    private TextView instructor = null;
    private  TextView blackPoints = null;
    private  TextView whitePoints= null;
    private  TableRow gameInfoWhite=null;
    private TableLayout gameScreen = null;
    private TableRow trow = null;

    private Display display =  null;
    private Point size =  null;
    private int width = 0;
    private int height = 0;
    int soundIDvalid = 0;
    int getSoundIDinvalid = 0;
    private SoundPool sp = null;
    private Vibrator v = null;
    ImageView imageBlack = null;
    ImageView imageWhite = null;
    @Override




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // iB = new InitialiseBoard();
        setContentView(R.layout.activity_othello);
        othelloBoard = (TableLayout)findViewById(R.id.gameTable);
        gameInfoBlack = (TableRow)findViewById(R.id.gameInfoBlack);
        gameInfoWhite=(TableRow)findViewById(R.id.gameInfoWhite);
        gameRow = (TableRow)findViewById(R.id.gameRow);
        gameScreen = (TableLayout)findViewById(R.id.gameScreen);
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        getSoundIDinvalid = sp.load(getApplicationContext(), R.raw.invalid, 1);
        soundIDvalid = sp.load(getApplicationContext(), R.raw.valid1, 1);
        imageBlack =  new ImageView(getApplicationContext());
        imageWhite =  new ImageView(getApplicationContext());

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        imageBlack.setImageResource(R.drawable.black128);
        gameInfoBlack.addView(imageBlack);

        imageWhite.setImageResource(R.drawable.white128);
        gameInfoWhite.addView(imageWhite);



        display =  getWindowManager().getDefaultDisplay();
        size =   new Point();
        display.getSize(size);
         width = size.x;
         height = size.y;

        instructor = new TextView(this);
        blackPoints=new TextView(this);
        whitePoints=new TextView(this);
        whitePoints.setText("White Score:2 ");
        blackPoints.setText("Black Score:2 ");
        instructor.setTextColor(Color.BLACK);
        blackPoints.setTextColor(Color.BLACK);
        whitePoints.setTextColor(Color.BLACK);
       // gameInfoWhite.addView(blackPoints);
       // gameInfoWhite.addView(whitePoints);


        //gameInfoBlack.addView(instructor);

        generateBoard();

    }

    private void generateBoard()
    {
        ob = new othello_buttons[8][8];
        for(int i=0; i<8 ;i++) {
            TableRow trow = new TableRow(this);

            for (int j = 0; j < 8; j++) {
                RelativeLayout rl = new RelativeLayout(this);
                ob[i][j] = new othello_buttons(this);
                if((i==3&&j==3) || (i==4&&j==4))
                {
                    Game_logic.paint(true,i,j);


                }
                else if((i==3&&j==4)||(i==4&&j==3) )
                {
                    Game_logic.paint(false,i,j);

                }
                else {
                    ob[i][j].setBackgroundColor(Color.GREEN);
                 //   ob[i][j].setBackgroundResource(R.drawable.green);

                    ob[i][j].isWhite=false;
                    ob[i][j].isBlack=false;
                    ob[i][j].isGreen=true;
                }
                ob[i][j].row = i;
                ob[i][j].column = j ;
                ob[i][j].setOnTouchListener(
                        new View.OnTouchListener()
                        {

                            @Override
                            public boolean onTouch(View v, MotionEvent event)
                            {
                                if (event.getAction() == MotionEvent.ACTION_DOWN)
                                {

                                    prevBackgroundColor= ((ColorDrawable)v.getBackground()).getColor();
                                    v.setBackgroundColor(Color.YELLOW);
                                }
                                else if (event.getAction() == MotionEvent.ACTION_UP)
                                {
                                    if(isPlayerWhite)
                                    {
                                        if(Game_logic.isValidButton(((othello_buttons) v).row,((othello_buttons)v).column,isPlayerWhite))
                                        {
                                            Game_logic.paint(isPlayerWhite,((othello_buttons) v).row,((othello_buttons)v).column);
                                            Game_logic.play(((othello_buttons) v).row,((othello_buttons)v).column,((othello_buttons)v).isWhite);
                                            Game_logic.CalculateScore();
                                            whitePoints.setText("White Score:" + Game_logic.whiteScore + " ");
                                            blackPoints.setText("Black Score:" + Game_logic.blackScore + " ");
                                            sp.play(soundIDvalid, 1, 1, 0, 0, 1);

                                          if(Game_logic.checkValidPlace(!isPlayerWhite))
                                          {
                                              isPlayerWhite = false;
                                            //  v.vibrate(500);


                                              instructor.setText("Black's turn");
                                          }


                                          else if(Game_logic.checkValidPlace(isPlayerWhite))
                                            {
                                                isPlayerWhite =true;
                                                Toast.makeText(getApplicationContext(),"no Places for Black so White's turn again",Toast.LENGTH_SHORT).show();



                                            }
                                            else
                                            {
                                                if(Game_logic.whiteScore>Game_logic.blackScore) {
                                                    instructor.setText("White wins");
                                                }
                                                else if (Game_logic.whiteScore<Game_logic.blackScore)
                                                {
                                                    instructor.setText("Black wins");
                                                }
                                                else
                                                {
                                                    instructor.setText("Tie");
                                                }

                                                //gameTurn.setBackgroundColor(Color.WHITE);
                                            }
                                        }
                                        else
                                        {   v.setBackgroundColor(prevBackgroundColor);
                                            isPlayerWhite = true;
                                            sp.play(getSoundIDinvalid, 1, 1, 0, 0, 1);
                                            Toast.makeText(getApplicationContext(),"Invalid Positon",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                    else
                                    {
                                        if(Game_logic.isValidButton(((othello_buttons) v).row,((othello_buttons)v).column,isPlayerWhite))
                                        {
                                            Game_logic.paint(isPlayerWhite,((othello_buttons) v).row,((othello_buttons)v).column);
                                            Game_logic.play(((othello_buttons) v).row,((othello_buttons)v).column,((othello_buttons)v).isWhite);
                                            Game_logic.CalculateScore();
                                            blackPoints.setText("Black Score:" + Game_logic.blackScore + " ");
                                            whitePoints.setText("White Score:" + Game_logic.whiteScore + " ");
                                            sp.play(soundIDvalid, 1, 1, 0, 0, 1);

                                            if(Game_logic.checkValidPlace(!isPlayerWhite))
                                            {


                                                isPlayerWhite = true;

                                               instructor.setText("White's Turn");




                                            }

                                            else if(Game_logic.checkValidPlace(isPlayerWhite))
                                            {
                                                //gameTurn.setBackgroundColor(Color.WHITE);
                                                isPlayerWhite =false;
                                                Toast.makeText(getApplicationContext(),"no Places for White so Black's turn again",Toast.LENGTH_SHORT).show();


                                            }
                                            else
                                            {
                                                // isPlayerWhite = false;
                                                // Toast.makeText(getApplicationContext(),"Black's Turn",Toast.LENGTH_SHORT).show();
                                                if(Game_logic.whiteScore>Game_logic.blackScore) {
                                                    instructor.setText("White wins");
                                                }
                                                else if (Game_logic.whiteScore<Game_logic.blackScore)
                                                {
                                                    instructor.setText("Black wins");
                                                }
                                                else
                                                {
                                                    instructor.setText("Tie");
                                                }


                                            }
                                        }
                                        else
                                        {
                                            v.setBackgroundColor(prevBackgroundColor);
                                            isPlayerWhite = false;
                                            sp.play(getSoundIDinvalid, 1, 1, 0, 0, 1);

                                            Toast.makeText(getApplicationContext(),"Invalid Position",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            return true ;
                            }
                        });


               rl.setPadding((int)convertPixelsToDp((float)2), (int)convertPixelsToDp((float)2),(int)convertPixelsToDp((float)2), (int)convertPixelsToDp((float)2));  //to differentiate between the consecutive buttons
              //  rl.setPadding(2,2,2,2);


                rl.addView(ob[i][j]);
             //   rl.setLayoutParams(lp);
              //  trow.addView(rl, new TableRow.LayoutParams((int)convertPixelsToDp((float)195),(int)convertPixelsToDp((float)195)));
                trow.addView(rl, new TableRow.LayoutParams(width/8,width/8));



                // trow.addView(rl);


            }


            //othelloBoard.addView(trow, new TableRow.LayoutParams((int) convertPixelsToDp((float) 195), (int) convertPixelsToDp((float) 195)));
            othelloBoard.addView(trow);


            othelloBoard.setBackgroundColor(Color.DKGRAY);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.othello, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
