package nischayvaish.com.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class GamePage_Easy extends Activity implements Animation.AnimationListener {
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
    LinearLayout statistics, setting;
    Bundle bundle;
    Dialog lose_dialog;
    RelativeLayout parent;
    SharedPreferences sharedpreferences_easy;
    Typeface tf1, tf2;
    ImageView gamelogo;
    AnimationDrawable frameAnimation;
    Animation myAnimation1, myAnimation2, myAnimation3, myAnimation4, myAnimation5, zoom_in, zoom_out, slideup;
    ImageButton button[][];
    String player1, player2, start_won, start_loss;
    int c[][];
    int winscore = 0, losscore = 0;
    int i, j = 0, a = 0, b = 0, updatedscore1 = 0, updatedscore2 = 0;
    AI ai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page_easy);
        //for making full screen
        FullScreen fullScreen = new FullScreen();
        fullScreen.makefullscreen(GamePage_Easy.this);
        getPreviousValueOfSharedPreference();
        //Button Initialization
        buttonInit();
        // other elements initialization
        init();
        //font set
        setfont();
        //this method is used to set score in the
        // changed activity(acitvity changes when there is situation such as winning,losing,tie)

        //getting Player  Name
        playerNameSet();
        setBoard();
        if (getIntent().getExtras() != null) {

            OnActivityReload();
            //if bundle!=null that means activity shoot by intent and having some data
        }
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statisticsintent = new Intent(GamePage_Easy.this, Statistics.class);
                startActivity(statisticsintent);
            }
        });
        gamelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* Intent homeintent = new Intent(GamePage_Easy.this, StartPage.class);*/
                GamePage_Easy.this.finish();
               /* startActivity(homeintent);*/
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingintent = new Intent(GamePage_Easy.this, Settings.class);
                startActivity(settingintent);
            }
        });

    }

    public void playerNameSet() {
        Intent intent = getIntent();
        player1 = intent.getStringExtra("PlayerName");
        player2 = intent.getStringExtra("ComputerName");
        tv3.setText(player1);
        tv4.setText(player2);

    }

    public void OnActivityReload() {

        Bundle extras = getIntent().getExtras();
        winscore = extras.getInt("score1");
        losscore = extras.getInt("score2");
        tv5.setText("0" + winscore);
        tv6.setText("0" + losscore);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setBoard() {
        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++)
                //mark empty box =2
                c[i][j] = 2;
        }
        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++) {
                //on button click
                button[i][j].setOnClickListener(new MyClickListener(i, j));
                if (!button[i][j].isEnabled()) {
                    button[i][j].setEnabled(false);
                    button[i][j].setImageResource(android.R.color.transparent);
                    button[i][j].setEnabled(true);
                }
            }
        }

    }

    public void setfont() {
        tf1 = Typeface.createFromAsset(getAssets(), "courbd.ttf");
        tv1.setTypeface(tf1);
        tv2.setTypeface(tf1);
        tv3.setTypeface(tf1);
        tv4.setTypeface(tf1);
        tv5.setTypeface(tf1);
        tv6.setTypeface(tf1);
        tv7.setTypeface(tf1);


    }

    public void init() {
        tv1 = (TextView) findViewById(R.id.statisticstext);
        tv2 = (TextView) findViewById(R.id.settingtext);
        tv3 = (TextView) findViewById(R.id.player1);
        tv4 = (TextView) findViewById(R.id.player2);
        tv5 = (TextView) findViewById(R.id.score1);
        tv6 = (TextView) findViewById(R.id.score2);
        tv7 = (TextView) findViewById(R.id.winningtext);
        tv8 = (TextView) findViewById(R.id.retry_text);
        tv9 = (TextView) findViewById(R.id.back_text);
        gamelogo = (ImageView) findViewById(R.id.gamelogo);
        statistics = (LinearLayout) findViewById(R.id.statistics);
        setting = (LinearLayout) findViewById(R.id.setting);
        parent = (RelativeLayout) findViewById(R.id.parent);
        myAnimation1 = AnimationUtils.loadAnimation(this, R.anim.blink);
        myAnimation2 = AnimationUtils.loadAnimation(this, R.anim.blink);
        myAnimation3 = AnimationUtils.loadAnimation(this, R.anim.blink);
        myAnimation4 = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out_without_blink);
        myAnimation5 = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out_without_blink);
        zoom_in = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        slideup = AnimationUtils.loadAnimation(this, R.anim.slide_up);

    }

    public void buttonInit() {
        ai = new AI();
        button = new ImageButton[4][4];
        c = new int[4][4];
        button[1][1] = (ImageButton) findViewById(R.id.button1);
        button[1][2] = (ImageButton) findViewById(R.id.button2);
        button[1][3] = (ImageButton) findViewById(R.id.button3);
        button[2][1] = (ImageButton) findViewById(R.id.button4);
        button[2][2] = (ImageButton) findViewById(R.id.button5);
        button[2][3] = (ImageButton) findViewById(R.id.button6);
        button[3][1] = (ImageButton) findViewById(R.id.button7);
        button[3][2] = (ImageButton) findViewById(R.id.button8);
        button[3][3] = (ImageButton) findViewById(R.id.button9);
    }


    private boolean checkBoard() {
        boolean gameOver = false;
        if ((c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0)
                || (c[1][3] == 0 && c[2][2] == 0 && c[3][1] == 0)
                || (c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0)
                || (c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0)
                || (c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0)
                || (c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0)
                || (c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0)
                || (c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0)) {
            winscore++;
            updatedscore1 = a + 1;
            save_preferences("winscore_key", Integer.toString(updatedscore1));
            tv5.setText("0" + winscore);
           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            },50);*/
            tv7.setText("WIN!!");
            tv7.startAnimation(myAnimation1);
            gameOver = true;
            onAnimationEnd(myAnimation1);

            // textView.setText("Game over. You win!");//TExtview for win

        } else if ((c[1][1] == 1 && c[2][2] == 1 && c[3][3] == 1)
                || (c[1][3] == 1 && c[2][2] == 1 && c[3][1] == 1)
                || (c[1][2] == 1 && c[2][2] == 1 && c[3][2] == 1)
                || (c[1][3] == 1 && c[2][3] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[1][2] == 1 && c[1][3] == 1)
                || (c[2][1] == 1 && c[2][2] == 1 && c[2][3] == 1)
                || (c[3][1] == 1 && c[3][2] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[2][1] == 1 && c[3][1] == 1)) {
            losscore++;
            updatedscore2 = b + 1;
            save_preferences("losscore_key", Integer.toString(updatedscore2));
         /*   new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            },50);*/
            tv7.setText("LOSE!!");
            tv7.startAnimation(myAnimation2);
            tv6.setText("0" + losscore);
            gameOver = true;
            onAnimationEnd(myAnimation2);
            //textView.setText("Game over. You lost!");//Textview for lost
        } else {
            boolean empty = false;
            for (i = 1; i <= 3; i++) {
                for (j = 1; j <= 3; j++) {
                    if (c[i][j] == 2) {
                        empty = true;
                        break;
                    }
                }
            }
            if (!empty) {
               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },50);*/
                tv7.setText("TIE!!");
                tv7.startAnimation(myAnimation3);
                gameOver = true;
                onAnimationEnd(myAnimation3);
                //  textView.setText("Game over. It's a draw!");//Textview for draw

            }
        }
        return gameOver;
    }

    public void SetAlertDialog() {
        lose_dialog = new Dialog(GamePage_Easy.this);
        lose_dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        lose_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lose_dialog.setContentView(R.layout.lose_dialog);
        lose_dialog.setCancelable(false);
        tv8 = (TextView) lose_dialog.findViewById(R.id.retry_text);
        tv9 = (TextView) lose_dialog.findViewById(R.id.back_text);
        tf2 = Typeface.createFromAsset(getAssets(), "courbd.ttf");
        tv8.setTypeface(tf2);
        tv9.setTypeface(tf2);
        lose_dialog.show();
        lose_dialog.getWindow().getDecorView().setSystemUiVisibility(this.getWindow().getDecorView().getSystemUiVisibility());
        lose_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv8.startAnimation(myAnimation4);
                onAnimationEnd(myAnimation4);

            }
        }, 200);


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == myAnimation1) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent tieIntent = new Intent(GamePage_Easy.this, GamePage_Easy.class);
                    bundle = new Bundle();
                    bundle.putInt("score1", winscore);
                    bundle.putInt("score2", losscore);
                    bundle.putString("PlayerName", player1);
                    bundle.putString("ComputerName", player2);
                    tieIntent.putExtras(bundle);
                    GamePage_Easy.this.finish();
                    startActivity(tieIntent);

                }
            }, 500);


        } else if (animation == myAnimation2) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SetAlertDialog();
                }
            }, 500);


        } else if (animation == myAnimation3) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent tieIntent = new Intent(GamePage_Easy.this, GamePage_Easy.class);
                    bundle = new Bundle();
                    bundle.putInt("score1", winscore);
                    bundle.putInt("score2", losscore);
                    bundle.putString("PlayerName", player1);
                    bundle.putString("ComputerName", player2);
                    tieIntent.putExtras(bundle);
                    GamePage_Easy.this.finish();
                    startActivity(tieIntent);
                }
            }, 500);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv9.startAnimation(myAnimation5);
                }
            }, 500);
            //Retry button
            tv8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv8.startAnimation(zoom_in);
                    tv8.setTextColor(Color.parseColor("#FFFFFF"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv8.setTextColor(Color.parseColor("#17a086"));
                            tv8.startAnimation(zoom_out);
                        }
                    }, 200);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent tieIntent = new Intent(GamePage_Easy.this, GamePage_Easy.class);
                            bundle = new Bundle();
                            bundle.putInt("score1", winscore);
                            bundle.putInt("score2", losscore);
                            bundle.putString("PlayerName", player1);
                            bundle.putString("ComputerName", player2);
                            tieIntent.putExtras(bundle);
                            GamePage_Easy.this.finish();
                            startActivity(tieIntent);
                        }
                    }, 500);
                }
            });
            //Back button
            tv9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv9.startAnimation(zoom_in);
                    tv9.setTextColor(Color.parseColor("#FFFFFF"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv9.setTextColor(Color.parseColor("#17a086"));
                            tv9.startAnimation(zoom_out);
                        }
                    }, 200);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            GamePage_Easy.this.finish();

                        }
                    }, 500);


                }
            });

        }


    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void save_preferences(String key, String value) {
        sharedpreferences_easy = getSharedPreferences("easy", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences_easy.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public void getPreviousValueOfSharedPreference() {
        sharedpreferences_easy = getSharedPreferences("easy", MODE_PRIVATE);
        start_won = sharedpreferences_easy.getString("winscore_key", "0");
        start_loss = sharedpreferences_easy.getString("losscore_key", "0");
        a = Integer.valueOf(start_won);
        b = Integer.valueOf(start_loss);
        Log.d("previousvalue a", "" + a);
        Log.d("previousvalue b", "" + b);
    }

    public class MyClickListener implements View.OnClickListener {
        int x;
        int y;

        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }


        @Override
        public void onClick(View view) {
            if (button[x][y].isEnabled()) {
                button[x][y].setEnabled(false);
                button[x][y].setImageResource(R.drawable.cross_animation);
                AnimationDrawable frameAnimation1 = (AnimationDrawable) button[x][y].getDrawable();
                frameAnimation1.start();
                button[x][y].setPadding(5, 5, 5, 5);
                c[x][y] = 0;
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                },300);

                if (!checkBoard()) {
                    ai.takeTurn();
                }
            }
        }

    }

    private class AI {
        public void takeTurn() {
            if (c[1][1] == 2 &&
                    ((c[1][2] == 0 && c[1][3] == 0) ||
                            (c[2][2] == 0 && c[3][3] == 0) ||
                            (c[2][1] == 0 && c[3][1] == 0))) {

                markSquare(1, 1);
            } else if (c[1][2] == 2 &&
                    ((c[2][2] == 0 && c[3][2] == 0) ||
                            (c[1][1] == 0 && c[1][3] == 0))) {
                markSquare(1, 2);
            } else if (c[1][3] == 2 &&
                    ((c[1][1] == 0 && c[1][2] == 0) ||
                            (c[3][1] == 0 && c[2][2] == 0) ||
                            (c[2][3] == 0 && c[3][3] == 0))) {
                markSquare(1, 3);
            } else if (c[2][1] == 2 &&
                    ((c[2][2] == 0 && c[2][3] == 0) ||
                            (c[1][1] == 0 && c[3][1] == 0))) {
                markSquare(2, 1);
            } else if (c[2][2] == 2 &&
                    ((c[1][1] == 0 && c[3][3] == 0) ||
                            (c[1][2] == 0 && c[3][2] == 0) ||
                            (c[3][1] == 0 && c[1][3] == 0) ||
                            (c[2][1] == 0 && c[2][3] == 0) ||
                            (c[1][1] == 0) || (c[1][3] == 0) /*|| (c[3][1] == 0) || (c[3][3] == 0)*/)) {
                markSquare(2, 2);
            } else if (c[2][3] == 2 &&
                    ((c[2][1] == 0 && c[2][2] == 0) ||
                            (c[1][3] == 0 && c[3][3] == 0))) {
                markSquare(2, 3);
            } else if (c[3][1] == 2 &&
                    ((c[1][1] == 0 && c[2][1] == 0) ||
                            (c[3][2] == 0 && c[3][3] == 0) ||
                            (c[2][2] == 0 && c[1][3] == 0))) {
                markSquare(3, 1);
            } else if (c[3][2] == 2 &&
                    ((c[1][2] == 0 && c[2][2] == 0) ||
                            (c[3][1] == 0 && c[3][3] == 0))) {
                markSquare(3, 2);
            } else if (c[3][3] == 2 &&
                    ((c[1][1] == 0 && c[2][2] == 0) ||
                            (c[1][3] == 0 && c[2][3] == 0) ||
                            (c[3][1] == 0 && c[3][2] == 0))) {
                markSquare(3, 3);
            } else {
                Random random = new Random();
                int a = random.nextInt(4);
                int b = random.nextInt(4);
                while (a == 0 || b == 0 || c[a][b] != 2) {
                    a = random.nextInt(4);
                    b = random.nextInt(4);
                }
                markSquare(a, b);
            }
        }

        private void markSquare(final int x, final int y) {
            button[x][y].setEnabled(false);
            //circle_animation
            button[x][y].setImageResource(R.drawable.circle_animation);
            frameAnimation = (AnimationDrawable) button[x][y].getDrawable();
            frameAnimation.start();
            button[x][y].setPadding(5, 5, 5, 5);
            c[x][y] = 1;
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            },300);
            checkBoard();
        }


        }

    }
}