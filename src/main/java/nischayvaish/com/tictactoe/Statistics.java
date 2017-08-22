package nischayvaish.com.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Statistics extends Activity {
    Typeface tf1, tf2;
    TextView tv1, tv2, easy, easy_tv1, easy_tv2, easy_tv3, medium, medium_tv1, medium_tv2, medium_tv3, hard, hard_tv1, hard_tv2, hard_tv3;
    TextView type, won, loss, gameplayed;
    String easy_won, easy_loss, hard_won, hard_loss, medium_won, medium_loss;
    Integer a1, b1, c1, a2, b2, c2, a3, b3, c3;
    SharedPreferences sharedpreferences_easy, sharedpreferences_medium, sharedpreferences_hard;
    LinearLayout setting;
    ImageView gamelogo, backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        FullScreen fullScreen = new FullScreen();                //for making full screen
        fullScreen.makefullscreen(Statistics.this);
        Init();
        SetFont();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingintent = new Intent(Statistics.this, Settings.class);
                settingintent.putExtra("ActivityName", "StatisticsPage");
                finish();
                startActivity(settingintent);
            }
        });
        gamelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Statistics.this.finish();


            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                String activity = intent.getStringExtra("ActivityName");
                if (activity != null) {
                  /*  Intent startpageintent = new Intent(Statistics.this, StartPage.class);*/
                    finish();
                  /*  startActivity(startpageintent);*/
                } else {
                    onBackPressed();
                }
            }

        });
        LoadSharedpreferences();


    }

    public void SetFont() {
        tf1 = Typeface.createFromAsset(getAssets(), "courbd.ttf");
        tf2 = Typeface.createFromAsset(getAssets(), "cour.ttf");
        tv1.setTypeface(tf1);
        tv2.setTypeface(tf1);
        easy.setTypeface(tf1);
        medium.setTypeface(tf1);
        hard.setTypeface(tf1);
        type.setTypeface(tf2);
        gameplayed.setTypeface(tf2);
        won.setTypeface(tf2);
        loss.setTypeface(tf2);
        easy_tv1.setTypeface(tf2);
        easy_tv2.setTypeface(tf2);
        easy_tv3.setTypeface(tf2);
        medium_tv1.setTypeface(tf2);
        medium_tv2.setTypeface(tf2);
        medium_tv3.setTypeface(tf2);
        hard_tv1.setTypeface(tf2);
        hard_tv2.setTypeface(tf2);
        hard_tv3.setTypeface(tf2);

    }

    public void Init() {
        easy = (TextView) findViewById(R.id.easy_text);
        easy_tv1 = (TextView) findViewById(R.id.gameplayed_easy_text);
        easy_tv2 = (TextView) findViewById(R.id.won_easy_text);
        easy_tv3 = (TextView) findViewById(R.id.loss_easy_text);
        medium = (TextView) findViewById(R.id.medium_text);
        medium_tv1 = (TextView) findViewById(R.id.gameplayed_medium_text);
        medium_tv2 = (TextView) findViewById(R.id.won_medium_text);
        medium_tv3 = (TextView) findViewById(R.id.loss_medium_text);
        hard = (TextView) findViewById(R.id.hard_text);
        hard_tv1 = (TextView) findViewById(R.id.gameplayed_hard_text);
        hard_tv2 = (TextView) findViewById(R.id.won_hard_text);
        hard_tv3 = (TextView) findViewById(R.id.loss_hard_text);
        won = (TextView) findViewById(R.id.won_text);
        loss = (TextView) findViewById(R.id.loss_text);
        gameplayed = (TextView) findViewById(R.id.gameplayed_text);
        type = (TextView) findViewById(R.id.type_text);
        tv1 = (TextView) findViewById(R.id.settingtext);
        tv2 = (TextView) findViewById(R.id.statisticstext);
        setting = (LinearLayout) findViewById(R.id.setting);
        gamelogo = (ImageView) findViewById(R.id.gamelogo);
        backbutton = (ImageView) findViewById(R.id.backbutton);

    }

    public void LoadSharedpreferences() {
        //loading score of easy_game
        sharedpreferences_easy = getSharedPreferences("easy", MODE_PRIVATE);
        easy_won = sharedpreferences_easy.getString("winscore_key", "0");
        easy_loss = sharedpreferences_easy.getString("losscore_key", "0");
        a1 = Integer.valueOf(easy_won);
        b1 = Integer.valueOf(easy_loss);
        c1 = a1 + b1;
        easy_tv1.setText(Integer.toString(c1));
        easy_tv2.setText(easy_won);
        easy_tv3.setText(easy_loss);
        Log.d("winning", "value" + a1);
        Log.d("gameplayed_easy", "value" + c1);
        //loading score of medium_game
        sharedpreferences_medium = getSharedPreferences("medium", MODE_PRIVATE);
        medium_won = sharedpreferences_medium.getString("winscore_key", "0");
        medium_loss = sharedpreferences_medium.getString("losscore_key", "0");
        a2 = Integer.valueOf(medium_won);
        b2 = Integer.valueOf(medium_loss);
        c2 = a2 + b2;
        medium_tv1.setText(Integer.toString(c2));
        medium_tv2.setText(medium_won);
        medium_tv3.setText(medium_loss);
        Log.d("winning", "value" + a2);
        Log.d("gameplayed_medium", "value" + c2);
//loading score of hard_game
      /*  sharedpreferences_hard = getSharedPreferences("hard", MODE_PRIVATE);
        hard_won=sharedpreferences_hard.getString("winscore", "00");
        hard_loss=sharedpreferences_hard.getString("losscore", "00");
        a=Integer.valueOf(hard_won);
        b=Integer.valueOf(hard_loss);
        c=a+b;
        *//*easy_tv1.setText(c);*//*
        hard_tv2.setText(hard_won);
        hard_tv3.setText(hard_loss);
        Log.d("winning","value"+a);
        Log.d("gameplayed","value"+c);
*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
