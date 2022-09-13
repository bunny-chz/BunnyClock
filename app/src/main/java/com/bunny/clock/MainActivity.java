/**                     
    * Project:  BunnyClock
    * Comments: 主界面类
    * JDK version used: <JDK1.8>
    * Author： Bunny     Github: https://github.com/bunny-chz/
    * Create Date：2022-03-15
    * Version: 1.0
    */
	
package com.bunny.clock;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    SaveTextParam saveTextParam = new SaveTextParam(this);
    TextClock clock;
    RelativeLayout mainLayout;
    ImageView BG_image;
    TextView quote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDoNotDisturb();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDisParam();
        initView();
        StartThreadDayToNight();
    }
    @SuppressLint("InlinedApi")
    public void initView() {
        mainLayout = findViewById(R.id.mainLayout);
        clock = findViewById(R.id.clock);
        BG_image = findViewById(R.id.BG_image);
        quote = findViewById(R.id.quote);
        initPermission();
        if(saveTextParam.loadString("TextSize") == null){
        matchScreen(getDis());
        }
        if(saveTextParam.loadString("EditQuoteLayoutParam_X") == null){
            RelativeLayout.LayoutParams QuoteLayoutParams = (RelativeLayout.LayoutParams) quote.getLayoutParams();
            QuoteLayoutParams.leftMargin = Integer.parseInt(saveTextParam.loadString("ScreenWeight"))*2/5;
            QuoteLayoutParams.topMargin = Integer.parseInt(saveTextParam.loadString("ScreenHeight"))*7/10;
            saveTextParam.saveString(String.valueOf(QuoteLayoutParams.leftMargin),"EditQuoteLayoutParam_X");
            saveTextParam.saveString(String.valueOf(QuoteLayoutParams.topMargin),"EditQuoteLayoutParam_Y");
            saveTextParam.saveString(String.valueOf(QuoteLayoutParams.leftMargin),"EditQuoteLayoutParam_X_Init");
            saveTextParam.saveString(String.valueOf(QuoteLayoutParams.topMargin),"EditQuoteLayoutParam_Y_Init");
            quote.setLayoutParams(QuoteLayoutParams);
        }
        if(saveTextParam.loadString("EditClockLayoutParam_X") == null){
            RelativeLayout.LayoutParams ClockLayoutParams = (RelativeLayout.LayoutParams) clock.getLayoutParams();
            ClockLayoutParams.leftMargin = Integer.parseInt(saveTextParam.loadString("ScreenWeight"))*2/10;
            ClockLayoutParams.topMargin = Integer.parseInt(saveTextParam.loadString("ScreenHeight"))/4;
            saveTextParam.saveString(String.valueOf(ClockLayoutParams.leftMargin),"EditClockLayoutParam_X");
            saveTextParam.saveString(String.valueOf(ClockLayoutParams.topMargin ),"EditClockLayoutParam_Y");
            saveTextParam.saveString(String.valueOf(ClockLayoutParams.leftMargin),"EditClockLayoutParam_X_Init");
            saveTextParam.saveString(String.valueOf(ClockLayoutParams.topMargin ),"EditClockLayoutParam_Y_Init");
            clock.setLayoutParams(ClockLayoutParams);
        }
        if(saveTextParam.loadString("QuoteTextSize") == null){
            matchQuoteScreen(getDis());}

        if(saveTextParam.loadString("TextColor") == null) {
            saveTextParam.saveString("#ffffffff","TextColor");
        }
        if(saveTextParam.loadString("QuoteTextColor") == null) {
            saveTextParam.saveString("#ffffffff","QuoteTextColor");
        }
        if (saveTextParam.loadString("BGColor") == null) {
            saveTextParam.saveString("#ff000000","BGColor");
        }
        if(saveTextParam.loadString("TextSize") != null) {
            clock.setTextSize(Float.parseFloat(saveTextParam.loadString("TextSize")));
        }
        if(saveTextParam.loadString("QuoteTextSize") != null) {
            quote.setTextSize(Float.parseFloat(saveTextParam.loadString("QuoteTextSize")));
        }
        if(saveTextParam.loadString("QuoteText") == null) {
            saveTextParam.saveString("欢迎使用Bunny Clock","QuoteText");
            saveTextParam.saveSW(true,"AppFirstInit");
        }
        if(!saveTextParam.loadSW("night_SW")) {
            saveTextParam.saveSW(false,"night_SW");
        }
        if(saveTextParam.loadTrueSW("Quote_SW")) {
            saveTextParam.saveTrueSW(true,"Quote_SW");
        }
        if(saveTextParam.loadSW("DoNotDisturb_SW")) {
            changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
        }
        if (!saveTextParam.loadSW("AppFirstInit")){
            if(!saveTextParam.loadSW("DoNotDisturb_SW")) {
                changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_ALL);
            }}
        if (!saveTextParam.loadSW("EnterSettingOption")){
        Toast.makeText(this, "点击数字跳转到”设置“界面", Toast.LENGTH_SHORT).show();}
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingOption.class);
                startActivity(i);
            }
        });
        mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingOption.class);
                startActivity(i);
                return false;
            }
        });
    }
    public void initDoNotDisturb() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ // If api level minimum 23
            int CurrentInterruptionFilter = mNotificationManager.getCurrentInterruptionFilter();
            saveTextParam.saveString(Integer.toString(CurrentInterruptionFilter),"DoNotDisturbInitData");
        }
    }
    //开启一个子线程
    private void StartThreadDayToNight() {
        new Thread(){
            @Override
            public void run() {
                do {
                    try {
                        if(saveTextParam.loadSW("night_SW")){
                        Thread.sleep(500);
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);}
                        if(!saveTextParam.loadSW("night_SW")){
                            Thread.sleep(500);
                            Message message=new Message();
                            message.what=2;
                            handler.sendMessage(message);}
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (true);
            }
        }.start();
    }
    //在主线程中进行数据处理
    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @SuppressLint("NewApi")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {

                RelativeLayout.LayoutParams ClockLayoutParams = (RelativeLayout.LayoutParams) clock.getLayoutParams();
                ClockLayoutParams.leftMargin = Integer.parseInt(saveTextParam.loadString("EditClockLayoutParam_X"));
                ClockLayoutParams.topMargin = Integer.parseInt(saveTextParam.loadString("EditClockLayoutParam_Y"));
                clock.setLayoutParams(ClockLayoutParams);
                RelativeLayout.LayoutParams QuoteLayoutParams = (RelativeLayout.LayoutParams) quote.getLayoutParams();
                QuoteLayoutParams.leftMargin = Integer.parseInt(saveTextParam.loadString("EditQuoteLayoutParam_X"));
                QuoteLayoutParams.topMargin = Integer.parseInt(saveTextParam.loadString("EditQuoteLayoutParam_Y"));
                quote.setLayoutParams(QuoteLayoutParams);
                if((CurrentTime() >= InputStartTimeToMinute() && CurrentTime()<=1440) ||
                        (CurrentTime() < InputEndTimeToMinute() && CurrentTime() >= 0)) {
                    clock.setTextColor(Color.parseColor(saveTextParam.loadString("NightColor")));
                    mainLayout.setBackgroundColor(Color.parseColor(saveTextParam.loadString("NightBGColor")));
                    if(saveTextParam.loadSW("night_BGImage_SW")){
                        BG_image.setImageURI(Uri.parse(saveTextParam.loadString("NightBGImageUri")));
                    }
                    if(!saveTextParam.loadSW("night_BGImage_SW")){
                        BG_image.setImageURI(null);}
                    if(saveTextParam.loadTrueSW("Quote_SW")) {
                        if(saveTextParam.loadSW("night_quote_SW")) {
                            if(quote.getCurrentTextColor() != Color.parseColor(saveTextParam.loadString("NightQuoteTextColor"))) {
                                quote.setTextColor(Color.parseColor(saveTextParam.loadString("NightQuoteTextColor")));
                            }
                        if(!(quote.getText().toString().equals(saveTextParam.loadString("NightQuoteText")))) {
                            quote.setText(saveTextParam.loadString("NightQuoteText"));
                        }}}
                } else if(CurrentTime() >= InputEndTimeToMinute() && CurrentTime() < InputStartTimeToMinute() ) {
                    clock.setTextColor(Color.parseColor(saveTextParam.loadString("DayColor")));
                    mainLayout.setBackgroundColor(Color.parseColor(saveTextParam.loadString("DayBGColor")));
                    if(saveTextParam.loadSW("day_BGImage_SW")){
                        BG_image.setImageURI(Uri.parse(saveTextParam.loadString("DayBGImageUri")));
                    }
                    if(!saveTextParam.loadSW("day_BGImage_SW")){
                        BG_image.setImageURI(null);}
                    if(saveTextParam.loadTrueSW("Quote_SW")) {
                        if(saveTextParam.loadSW("day_quote_SW")) {
                            if(quote.getCurrentTextColor() != Color.parseColor(saveTextParam.loadString("DayQuoteTextColor"))) {
                                quote.setTextColor(Color.parseColor(saveTextParam.loadString("DayQuoteTextColor")));
                            }
                            if(!(quote.getText().toString().equals(saveTextParam.loadString("DayQuoteText")))) {
                                quote.setText(saveTextParam.loadString("DayQuoteText"));
                            }}}
                }
            }
            if (msg.what == 2) {

                RelativeLayout.LayoutParams ClockLayoutParams = (RelativeLayout.LayoutParams) clock.getLayoutParams();
                ClockLayoutParams.leftMargin = Integer.parseInt(saveTextParam.loadString("EditClockLayoutParam_X"));
                ClockLayoutParams.topMargin = Integer.parseInt(saveTextParam.loadString("EditClockLayoutParam_Y"));
                clock.setLayoutParams(ClockLayoutParams);
                RelativeLayout.LayoutParams QuoteLayoutParams = (RelativeLayout.LayoutParams) quote.getLayoutParams();
                QuoteLayoutParams.leftMargin = Integer.parseInt(saveTextParam.loadString("EditQuoteLayoutParam_X"));
                QuoteLayoutParams.topMargin = Integer.parseInt(saveTextParam.loadString("EditQuoteLayoutParam_Y"));
                quote.setLayoutParams(QuoteLayoutParams);
                if(clock.getCurrentTextColor() != Color.parseColor(saveTextParam.loadString("TextColor"))) {
                    clock.setTextColor(Color.parseColor(saveTextParam.loadString("TextColor")));
                }
                if(quote.getCurrentTextColor() != Color.parseColor(saveTextParam.loadString("QuoteTextColor"))) {
                    quote.setTextColor(Color.parseColor(saveTextParam.loadString("QuoteTextColor")));
                }
                if(saveTextParam.loadTrueSW("Quote_SW")) {
                if(!(quote.getText().toString().equals(saveTextParam.loadString("QuoteText")))) {
                    quote.setText(saveTextParam.loadString("QuoteText"));
                }}
                if(!saveTextParam.loadTrueSW("Quote_SW")) {
                    quote.setText("");}
                if(saveTextParam.loadSW("BGColor_SW")){
                    if(mainLayout.getSolidColor() != Color.parseColor(saveTextParam.loadString("BGColor"))) {
                        mainLayout.setBackgroundColor(Color.parseColor(saveTextParam.loadString("BGColor")));
                    }}
                if(!saveTextParam.loadSW("BGColor_SW")){
                    mainLayout.setBackgroundColor(Color.parseColor("#ff000000"));}
                if(saveTextParam.loadSW("BGImage_SW")){
                        BG_image.setImageURI(Uri.parse(saveTextParam.loadString("BGImageUri")));
                    }
                if(!saveTextParam.loadSW("BGImage_SW")){
                    BG_image.setImageURI(null);}
            }
        }
    };
    public long CurrentTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("HH"); //制定输出格式
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("mm");
        Date d = new Date();
        String currentHourString = sdf1.format(d);
        String currentMinuteString = sdf2.format(d);
        long currentMinute = Long.parseLong(currentMinuteString);
        long currentHour = Long.parseLong(currentHourString);
        return currentHour*60+currentMinute;
    }
    public long InputStartTimeToMinute()  {
        long time_start_hour = Long.parseLong(saveTextParam.loadString("time_start_hour"));
        long time_start_minute = Long.parseLong(saveTextParam.loadString("time_start_minute"));
        return time_start_hour*60+time_start_minute;
    }
    public long InputEndTimeToMinute()  {
        long time_end_hour = Long.parseLong(saveTextParam.loadString("time_end_hour"));
        long time_end_minute = Long.parseLong(saveTextParam.loadString("time_end_minute"));
        return time_end_hour*60+time_end_minute;
    }
    public void matchScreen(float height) {
        float matchTextSize = (height*160*7)/(2285*4);
        clock.setTextSize(matchTextSize);
    }
    public void matchQuoteScreen(float height) {
        float matchTextSize = (height*21*7)/(2285*4);
        quote.setTextSize(matchTextSize);
    }
    public void getDisParam() {
        WindowManager wm = (WindowManager) this.getSystemService(this.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        saveTextParam.saveString(String.valueOf(dm.heightPixels),"ScreenHeight");
        saveTextParam.saveString(String.valueOf(dm.widthPixels),"ScreenWeight");
    }
    public float getDis() {
        WindowManager wm = (WindowManager) this.getSystemService(this.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return (dm.heightPixels);
    }
    protected void changeInterruptionFiler(int interruptionFilter){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ // If api level minimum 23
            if(mNotificationManager.isNotificationPolicyAccessGranted()){
                mNotificationManager.setInterruptionFilter(interruptionFilter);
            } else {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
                saveTextParam.saveSW(false,"DoNotDisturb_SW");
            }
        } else {
            Toast.makeText(this, "手机无免打扰功能", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!saveTextParam.loadSW("AppFirstInit")) {
            changeInterruptionFiler(Integer.parseInt(saveTextParam.loadString("DoNotDisturbInitData")));
        }
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        @SuppressLint("InlinedApi")
        String[] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NOTIFICATION_POLICY};
        ArrayList<String> toApplyList = new ArrayList<>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);// 进入到这里代表没有权限.
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 0x01);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}