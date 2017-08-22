package nischayvaish.com.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartPage extends Activity implements CompoundButton.OnCheckedChangeListener, Animation.AnimationListener {
    TextView difficulty, playername, computername, checkboxtext1, checkboxtext2, checkboxtext3, statisticstext, settingstext, exittext, yes_text, no_text, slash;
    Typeface tf1, tf2, tf3;
    ImageView playbutton;
    Dialog quit_dialog;
    CheckBox checkbox1, checkbox2, checkbox3;
    LinearLayout setting, statistics, exit, main_layout;
    EditText nameedit, compedit;
    String state = "Easy";
    Animation myAnimation1, myAnimation2, myAnimation3;
    Intent playbuttonintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        FullScreen fullScreen = new FullScreen();                //for making full screen
        fullScreen.makefullscreen(StartPage.this);
        Init();
        SetFont();


       /* Checkbox();*/
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playbutton.startAnimation(myAnimation1);
                SelectGameMode();
                SendPlayerNameToGamepage();
                startActivity(playbuttonintent);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statisticsintent = new Intent(StartPage.this, Statistics.class);
                startActivity(statisticsintent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingintent = new Intent(StartPage.this, Settings.class);
                startActivity(settingintent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialog();


            }
        });
    }

    public void SelectGameMode() {
        if (state.equals("Easy")) {
            playbuttonintent = new Intent(StartPage.this, GamePage_Easy.class);
        } else if (state.equals("Medium")) {
            playbuttonintent = new Intent(StartPage.this, GamePage_Medium.class);
        } else {
            playbuttonintent = new Intent(StartPage.this, GamePage_Hard.class);
        }

    }


    public void setAlertDialog() {
        quit_dialog = new Dialog(this);
        quit_dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        /*quit_dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);*/
        quit_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        quit_dialog.setContentView(R.layout.quit_dialog);
        quit_dialog.setCancelable(false);
        yes_text = (TextView) quit_dialog.findViewById(R.id.yes_text);
        no_text = (TextView) quit_dialog.findViewById(R.id.no_text);
        slash = (TextView) quit_dialog.findViewById(R.id.slash);
        yes_text.setText("Yes");
        no_text.setText("No");
        slash.setText("/");
        tf3 = Typeface.createFromAsset(getAssets(), "courbd.ttf");
        yes_text.setTypeface(tf3);
        no_text.setTypeface(tf3);
        slash.setTypeface(tf3);
        quit_dialog.show();
        quit_dialog.getWindow().getDecorView().setSystemUiVisibility(this.getWindow().getDecorView().getSystemUiVisibility());
        quit_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        yes_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes_text.startAnimation(myAnimation2);
                onAnimationEnd(myAnimation2);
            }
        });
        no_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_text.startAnimation(myAnimation3);
                onAnimationEnd(myAnimation3);
            }
        });


    }

    public void SendPlayerNameToGamepage() {
        String playerName, computerName;
      /*  playbuttonintent.putExtra("GameMode",state);*/
        playerName = nameedit.getText().toString();
        computerName = compedit.getText().toString();
        Log.e("edittext1", playerName);
        Log.e("edittext2", computerName);
        if (playerName.equals("")) {

            playbuttonintent.putExtra("PlayerName", "X PLAYER1");

        } else {
            playbuttonintent.putExtra("PlayerName", "X" + " " + playerName);
        }
        if (computerName.equals("")) {
            playbuttonintent.putExtra("ComputerName", "0 COMPUTER");
        } else {
            playbuttonintent.putExtra("ComputerName", "0" + " " + computerName);
        }

    }

    public void AppExit() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StartPage.this.finishAndRemoveTask();

        }
        else {
            super.finish();
        }
     /*   this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

    }

    public void Init() {

        difficulty = (TextView) findViewById(R.id.difficulty);
        playername = (TextView) findViewById(R.id.nametext);
        computername = (TextView) findViewById(R.id.comptext);
        statisticstext = (TextView) findViewById(R.id.statisticstext);
        settingstext = (TextView) findViewById(R.id.settingstext);
        exittext = (TextView) findViewById(R.id.exittext);
        playbutton = (ImageView) findViewById(R.id.playbutton);
        nameedit = (EditText) findViewById(R.id.nameedit);
        compedit = (EditText) findViewById(R.id.compedit);
        setting = (LinearLayout) findViewById(R.id.setting);
        statistics = (LinearLayout) findViewById(R.id.statistics);
        exit = (LinearLayout) findViewById(R.id.exit);
        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        myAnimation1 = AnimationUtils.loadAnimation(this, R.anim.playbuttonblink);
        myAnimation2 = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out_with_blink);
        myAnimation3 = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out_with_blink);
        checkboxinit();

    }

    public void checkboxinit() {
        checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkboxtext1 = (TextView) findViewById(R.id.checkboxtext1);
        checkboxtext2 = (TextView) findViewById(R.id.checkboxtext2);
        checkboxtext3 = (TextView) findViewById(R.id.checkboxtext3);
        checkbox1.setOnCheckedChangeListener(this);
        checkbox2.setOnCheckedChangeListener(this);
        checkbox3.setOnCheckedChangeListener(this);
    }


    public void SetFont() {

        nameedit.setTextColor(Color.parseColor("#4a5f88"));
        compedit.setTextColor(Color.parseColor("#4a5f88"));
        tf1 = Typeface.createFromAsset(getAssets(), "courbd.ttf");
        tf2 = Typeface.createFromAsset(getAssets(), "cour.ttf");
        difficulty.setTypeface(tf1);
        playername.setTypeface(tf1);
        computername.setTypeface(tf1);
        statisticstext.setTypeface(tf1);
        settingstext.setTypeface(tf1);
        exittext.setTypeface(tf1);
        checkboxtext1.setTypeface(tf2);
        checkboxtext2.setTypeface(tf2);
        checkboxtext3.setTypeface(tf2);
        nameedit.setTypeface(tf2);
        compedit.setTypeface(tf2);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox1:
            case R.id.checkbox2:
            case R.id.checkbox3:

                int[] ids = new int[]{R.id.checkbox1, R.id.checkbox2, R.id.checkbox3,};
                String[] strStateArr = new String[]{"Easy", "Medium", "Hard"};
                for (int i = 0; i < ids.length; i++) {
                    Log.e("id", isChecked + " " + ids[i] + " " + buttonView.getId());
                    CheckBox cb = (CheckBox) findViewById(ids[i]);
                    if (isChecked) {
                        if (buttonView.getId() == ids[i]) {
                            cb.setChecked(true);
                            state = strStateArr[i];

                        } else {
                            cb.setChecked(false);
                        }                    }
                    }

                if(!isChecked)
                {
                    boolean ischk = false;
                    for (int i = 0; i < ids.length; i++)
                    {
                        Log.e("id", isChecked + " " + ids[i] + " " + buttonView.getId());
                        CheckBox cb = (CheckBox) findViewById(ids[i]);
                        if (cb.isChecked()) {
                            ischk = true;
                            break;
                        }
                    }

                    if(!ischk)
                    {
                        buttonView.setChecked(true);
                    }
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == myAnimation2) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    quit_dialog.dismiss();
                    AppExit();
                }
            }, 500);

        }
        if (animation == myAnimation3) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    quit_dialog.dismiss();
                }
            }, 300);

        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
