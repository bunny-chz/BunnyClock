/**                     
    * Project:  BunnyClock
    * Comments: 设置参数类
    * JDK version used: <JDK1.8>
    * Author： Bunny     Github: https://github.com/bunny-chz/
    * Create Date：2022-03-15
    * Version: 1.0
    */

package com.bunny.clock;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class SettingOption extends AppCompatActivity implements ColorPickerDialogListener {
    SaveTextParam saveTextParam = new SaveTextParam(this);
    private static final String SHARE_TEXT = "Bunny Clock是一款全屏并持续亮屏显示，秒级别报时的数字时钟软件，具有非常大的自由定制特点。\n" +
            "可以自定义数字颜色、文案字体颜色，通过RGB取色器设置颜色。\n" +
            "可以自定义文案内容。\n" +
            "可以自定义时钟数字字体大小、文案字体大小。\n" +
            "可以自定义时钟数字和文案在屏幕中的位置。\n" +
            "支持自定义时间段夜间模式，改变数字颜色、背景颜色、背景图片、文案内容和文案颜色。" +
            "\n" +
            "\n" +
            "点击下方链接下载\n" +
            "蓝奏云下载：https://zss233.lanzout.com/b00pjfuyj\n" +
            "密码:2333\n";
    private static final int COMMON_EDIT_COLOR_DIALOG_ID = 1;
    private static final int QUOTE_EDIT_COLOR_DIALOG_ID = 7;
    private static final String TAG = "Setting Color Picker";
    Button common_edit_color_select,quote_edit_color_select;
    TextView common_edit_color_title_text,quote_edit_color_title_text,EditTextSize, EditQuoteText,
            EditQuoteTextSize,AutoNightMode,EditBackground,DoNotDisturb,Share,MyAboutPage,EditQuoteLayoutParam,EditClockLayoutParam;
    View common_edit_color_select_round,quote_edit_color_select_round;
    LinearLayout Quote_edit_color_layout,Common_edit_color_layout,DoNotDisturb_SW_Layout,DoNotDisturbLayout;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch quote_SW;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch DoNotDisturb_SW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);
        initView();
        StartThread();
    }
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void initView() {
        common_edit_color_select = findViewById(R.id.common_edit_color_select);
        common_edit_color_title_text = findViewById(R.id.common_edit_color_title_text);
        common_edit_color_select_round = findViewById(R.id.common_edit_color_select_round);
        quote_edit_color_select = findViewById(R.id.quote_edit_color_select);
        quote_edit_color_title_text = findViewById(R.id.quote_edit_color_title_text);
        quote_edit_color_select_round = findViewById(R.id.quote_edit_color_select_round);
        quote_SW = findViewById(R.id.quote_SW);
        EditTextSize = findViewById(R.id.EditTextSize);
        EditQuoteText = findViewById(R.id.EditQuoteText);
        EditQuoteTextSize = findViewById(R.id.EditQuoteTextSize);
        AutoNightMode = findViewById(R.id.AutoNightMode);
        EditBackground = findViewById(R.id.EditBackground);
        Share = findViewById(R.id.Share);
        MyAboutPage = findViewById(R.id.MyAboutPage);
        Quote_edit_color_layout = findViewById(R.id.Quote_edit_color_layout);
        Common_edit_color_layout = findViewById(R.id.Common_edit_color_layout);
        DoNotDisturb_SW = findViewById(R.id.DoNotDisturb_SW);
        DoNotDisturb = findViewById(R.id.DoNotDisturb);
        DoNotDisturb_SW_Layout = findViewById(R.id.DoNotDisturb_SW_Layout);
        DoNotDisturbLayout = findViewById(R.id.DoNotDisturbLayout);
        EditQuoteLayoutParam = findViewById(R.id.EditQuoteLayoutParam);
        EditClockLayoutParam = findViewById(R.id.EditClockLayoutParam);
        saveTextParam.saveSW(true,"EnterSettingOption");
        if(saveTextParam.loadString("TextColor") != null) {
            if(saveTextParam.loadString("TextColor").equals("#ffffffff")) {
                common_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
                common_edit_color_title_text.setText(saveTextParam.loadString("TextColor"));
            } else {
                common_edit_color_title_text.setText(saveTextParam.loadString("TextColor"));
                common_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("TextColor")));}
        }
        if(saveTextParam.loadTrueSW("Quote_SW")) {
            quote_SW.setChecked(true);
        }
        if(saveTextParam.loadSW("DoNotDisturb_SW")) {
            DoNotDisturb_SW.setChecked(true);
        }
        if(!saveTextParam.loadSW("DoNotDisturb_SW")) {
            DoNotDisturb_SW.setChecked(false);
            saveTextParam.saveSW(false,"DoNotDisturb_SW");
        }
        if(saveTextParam.loadString("QuoteTextColor") != null) {
            if(saveTextParam.loadString("QuoteTextColor").equals("#ffffffff")) {
                quote_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
                quote_edit_color_title_text.setText(saveTextParam.loadString("QuoteTextColor"));
            } else {
                quote_edit_color_title_text.setText(saveTextParam.loadString("QuoteTextColor"));
                quote_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("QuoteTextColor")));}
        }
        common_edit_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!saveTextParam.loadSW("night_SW")) {
                    EditColor();
                }
                if(saveTextParam.loadSW("night_SW")) {
                    Toast.makeText(SettingOption.this, "请关闭“自动夜间模式”开关\n设置→自动夜间模式→自动夜间模式开关", Toast.LENGTH_SHORT).show();
                }
            }
        });
        common_edit_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!saveTextParam.loadSW("night_SW")) {
                    EditColor();
                }
                if(saveTextParam.loadSW("night_SW")) {
                    Toast.makeText(SettingOption.this, "请关闭“自动夜间模式”开关\n设置→自动夜间模式→自动夜间模式开关", Toast.LENGTH_SHORT).show();
                }
            }
        });
        quote_edit_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadSW("Quote_SW")) {
                    EditQuoteColor();
                }
                if(!saveTextParam.loadSW("Quote_SW")) {
                    Toast.makeText(SettingOption.this, "请打开“是否显示文案”开关\n设置→是否显示文案", Toast.LENGTH_SHORT).show();
                }
            }
        });
        quote_edit_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadSW("Quote_SW")) {
                    EditQuoteColor();
                }
                if(!saveTextParam.loadSW("Quote_SW")) {
                    Toast.makeText(SettingOption.this, "请关闭“自动夜间模式”开关\n设置→自动夜间模式→自动夜间模式开关", Toast.LENGTH_SHORT).show();
                }
            }
        });
        EditTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextSize();
            }
        });
        EditClockLayoutParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditClockLayoutParam();
            }
        });
        EditQuoteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditQuoteText();
            }
        });
        EditQuoteTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditQuoteTextSize();
            }
        });
        EditQuoteLayoutParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditQuoteLayoutParam();
            }
        });
        AutoNightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NightModeSetting = new Intent(SettingOption.this, NightModeSetting.class);
                startActivity(NightModeSetting);
            }
        });
        EditBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackgroundSetting = new Intent(SettingOption.this, BackgroundSetting.class);
                startActivity(BackgroundSetting);
            }
        });
        DoNotDisturb_SW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    if(!mNotificationManager.isNotificationPolicyAccessGranted()) {
                        DoNotDisturb_SW.setChecked(false);
                        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                        startActivity(intent);
                    }
                    if(mNotificationManager.isNotificationPolicyAccessGranted()) {
                        if(DoNotDisturb_SW.isChecked()) {
                            changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
                            saveTextParam.saveSW(true,"DoNotDisturb_SW");
                            Toast.makeText(SettingOption.this, "免打扰模式已开启", Toast.LENGTH_SHORT).show();
                        }
                        if(!DoNotDisturb_SW.isChecked()) {
                            changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_ALL);
                            saveTextParam.saveSW(false,"DoNotDisturb_SW");
                            Toast.makeText(SettingOption.this, "免打扰模式已关闭", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    DoNotDisturb_SW.setChecked(false);
                    Toast.makeText(SettingOption.this, "手机无免打扰功能", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SHARE = new Intent();
                        SHARE.setAction(Intent.ACTION_SEND);
                        SHARE.putExtra(Intent.EXTRA_TEXT, SHARE_TEXT);
                        SHARE.setType("text/plain");
                        startActivity(SHARE);
            }
        });
        MyAboutPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MyAboutPage = new Intent(SettingOption.this, MyAboutPage.class);
                startActivity(MyAboutPage);
            }
        });
        ActionBar actionBar1 = getSupportActionBar();
        if(actionBar1 != null){
            actionBar1.setHomeButtonEnabled(true);
            actionBar1.setDisplayHomeAsUpEnabled(true);
        }
        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setTitle("设置");
    }
    public void quote_SW(View view){
        saveTextParam.saveTrueSW(quote_SW.isChecked(),"Quote_SW");
        if(!saveTextParam.loadTrueSW("Quote_SW")) {
            saveTextParam.saveSW(false,"night_quote_SW");
            saveTextParam.saveSW(false,"day_quote_SW");
        }
    }
    public void EditColor() {
        if(saveTextParam.loadString("TextColor") != null) {
            ColorPickerDialog.newBuilder()
                    .setDialogTitle(R.string.common_select_color)
                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                    .setAllowPresets(false)
                    .setDialogId(COMMON_EDIT_COLOR_DIALOG_ID)
                    .setColor(Color.parseColor(saveTextParam.loadString("TextColor")))
                    .setShowAlphaSlider(true)
                    .show(SettingOption.this);
        }else if(saveTextParam.loadString("TextColor") == null) {
            ColorPickerDialog.newBuilder()
                    .setDialogTitle(R.string.common_select_color)
                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                    .setAllowPresets(false)
                    .setDialogId(COMMON_EDIT_COLOR_DIALOG_ID)
                    .setColor(Color.WHITE)
                    .setShowAlphaSlider(true)
                    .show(SettingOption.this);
        }
    }
    public void EditQuoteColor() {
        if(saveTextParam.loadString("QuoteTextColor") != null) {
            ColorPickerDialog.newBuilder()
                    .setDialogTitle(R.string.common_select_color)
                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                    .setAllowPresets(false)
                    .setDialogId(QUOTE_EDIT_COLOR_DIALOG_ID)
                    .setColor(Color.parseColor(saveTextParam.loadString("QuoteTextColor")))
                    .setShowAlphaSlider(true)
                    .show(SettingOption.this);
        }else if(saveTextParam.loadString("EditQuoteColor") == null) {
            ColorPickerDialog.newBuilder()
                    .setDialogTitle(R.string.common_select_color)
                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                    .setAllowPresets(false)
                    .setDialogId(QUOTE_EDIT_COLOR_DIALOG_ID)
                    .setColor(Color.WHITE)
                    .setShowAlphaSlider(true)
                    .show(SettingOption.this);
        }
    }
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    @Override public void onColorSelected(int dialogId, int color) {
        if (dialogId == COMMON_EDIT_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"TextColor");
            if(saveTextParam.loadString("TextColor").equals("#ffffffff")) {
                common_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
                common_edit_color_title_text.setText(saveTextParam.loadString("TextColor"));
            }else {
                common_edit_color_title_text.setText(saveTextParam.loadString("TextColor"));
                common_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("TextColor")));}
        }
        if (dialogId == QUOTE_EDIT_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"QuoteTextColor");
            if(saveTextParam.loadString("QuoteTextColor").equals("#ffffffff")) {
                quote_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
                quote_edit_color_title_text.setText(saveTextParam.loadString("QuoteTextColor"));
            }else {
                quote_edit_color_title_text.setText(saveTextParam.loadString("QuoteTextColor"));
                quote_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("QuoteTextColor")));}
        }
    }

    @Override public void onDialogDismissed(int dialogId) {
        Log.d(TAG, "onDialogDismissed() called with: dialogId = [" + dialogId + "]");
    }
    public void EditTextSize() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingOption.this);
        final AlertDialog dialog = builder.create();
        LayoutInflater factory = LayoutInflater.from(SettingOption.this);
        final View view = factory.inflate(R.layout.dialog_textsize,null);
        dialog.setTitle("编辑字体大小(重启后生效)");
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.show();
        Button confirm = view.findViewById(R.id.confirm);
        Button cancel = view.findViewById(R.id.cancel);
        Button clear = view.findViewById(R.id.clear);
        EditText dialog_edit = view.findViewById(R.id.dialog_edit);
        if(saveTextParam.loadString("TextSize") != null) {
            dialog_edit.setText(saveTextParam.loadString("TextSize"));
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText dialog_edit = view.findViewById(R.id.dialog_edit);
                if(!TextUtils.isEmpty(dialog_edit.getText().toString())){
                    if(Float.parseFloat(dialog_edit.getText().toString()) >11) {
                    saveTextParam.saveString(dialog_edit.getText().toString(),"TextSize");
                    Toast.makeText(SettingOption.this, "编辑成功,重启后生效", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();}
                    else {
                        Toast.makeText(SettingOption.this, "字体过小，请修改。\n" +
                                "字体大小必须>11", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SettingOption.this, "编辑有误！请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_edit.setText("");
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void EditClockLayoutParam() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingOption.this);
        final AlertDialog dialog = builder.create();
        LayoutInflater factory = LayoutInflater.from(SettingOption.this);
        final View view = factory.inflate(R.layout.dialog_clock_layout_param,null);
        dialog.setTitle("设置时钟数字位置");
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.show();
        Button confirm = view.findViewById(R.id.confirm);
        Button cancel = view.findViewById(R.id.cancel);
        Button Set_default = view.findViewById(R.id.Set_default);
        Button clear = view.findViewById(R.id.clear);
        EditText clock_layout_param_X = view.findViewById(R.id.clock_layout_param_X);
        EditText clock_layout_param_Y = view.findViewById(R.id.clock_layout_param_Y);
        TextView screen_param = view.findViewById(R.id.screen_param);
        if(saveTextParam.loadString("EditClockLayoutParam_X") != null) {
            clock_layout_param_X.setText(saveTextParam.loadString("EditClockLayoutParam_X"));
        }
        if(saveTextParam.loadString("EditClockLayoutParam_Y") != null) {
            clock_layout_param_Y.setText(saveTextParam.loadString("EditClockLayoutParam_Y"));
        }
        screen_param.setText("本机(横屏时)可用屏幕高: " + saveTextParam.loadString("ScreenHeight") + "px ,  可用屏幕宽: " + saveTextParam.loadString("ScreenWeight") + "px");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText clock_layout_param_X = view.findViewById(R.id.clock_layout_param_X);
                EditText clock_layout_param_Y = view.findViewById(R.id.clock_layout_param_Y);
                if(!TextUtils.isEmpty(clock_layout_param_X.getText().toString()) &&
                        !TextUtils.isEmpty(clock_layout_param_Y.getText().toString())){
                    saveTextParam.saveString(clock_layout_param_X.getText().toString(),"EditClockLayoutParam_X");
                    saveTextParam.saveString(clock_layout_param_Y.getText().toString(),"EditClockLayoutParam_Y");
                    Toast.makeText(SettingOption.this, "编辑成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(SettingOption.this, "编辑有误！请填完所有输入框", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock_layout_param_X.setText("");
                clock_layout_param_Y.setText("");
            }
        });
        Set_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock_layout_param_X.setText(saveTextParam.loadString("EditClockLayoutParam_X_Init"));
                clock_layout_param_Y.setText(saveTextParam.loadString("EditClockLayoutParam_Y_Init"));
                saveTextParam.saveString(saveTextParam.loadString("EditClockLayoutParam_X_Init"),"EditClockLayoutParam_X");
                saveTextParam.saveString(saveTextParam.loadString("EditClockLayoutParam_Y_Init"),"EditClockLayoutParam_Y");
            }
        });
    }
    public void EditQuoteText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingOption.this);
        final AlertDialog dialog = builder.create();
        LayoutInflater factory = LayoutInflater.from(SettingOption.this);
        final View view = factory.inflate(R.layout.dialog_quote_text,null);
        dialog.setTitle("编辑文案内容(优先显示”自动夜间模式“设置的文案)");
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.show();
        Button confirm = view.findViewById(R.id.confirm);
        Button cancel = view.findViewById(R.id.cancel);
        Button clear = view.findViewById(R.id.clear);
        EditText dialog_edit = view.findViewById(R.id.dialog_edit);
        if(saveTextParam.loadString("QuoteText") != null) {
            dialog_edit.setText(saveTextParam.loadString("QuoteText"));
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText dialog_edit = view.findViewById(R.id.dialog_edit);
                if(!TextUtils.isEmpty(dialog_edit.getText().toString())){
                    saveTextParam.saveString(dialog_edit.getText().toString(),"QuoteText");
                    Toast.makeText(SettingOption.this, "编辑成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(SettingOption.this, "编辑有误！请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_edit.setText("");
            }
        });
    }
    public void EditQuoteTextSize() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingOption.this);
        final AlertDialog dialog = builder.create();
        LayoutInflater factory = LayoutInflater.from(SettingOption.this);
        final View view = factory.inflate(R.layout.dialog_quote_text_size,null);
        dialog.setTitle("编辑文案字体大小(重启后生效)");
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.show();
        Button confirm = view.findViewById(R.id.confirm);
        Button cancel = view.findViewById(R.id.cancel);
        Button clear = view.findViewById(R.id.clear);
        EditText dialog_edit = view.findViewById(R.id.dialog_edit);
        if(saveTextParam.loadString("QuoteTextSize") != null) {
            dialog_edit.setText(saveTextParam.loadString("QuoteTextSize"));
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText dialog_edit = view.findViewById(R.id.dialog_edit);
                if(!TextUtils.isEmpty(dialog_edit.getText().toString())){
                    if(Float.parseFloat(dialog_edit.getText().toString()) >11) {
                    saveTextParam.saveString(dialog_edit.getText().toString(),"QuoteTextSize");
                    Toast.makeText(SettingOption.this, "编辑成功,重启后生效", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();}
                    else {
                        Toast.makeText(SettingOption.this, "字体过小，请修改。\n" +
                                "字体大小>11", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SettingOption.this, "编辑有误！请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_edit.setText("");
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void EditQuoteLayoutParam() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingOption.this);
        final AlertDialog dialog = builder.create();
        LayoutInflater factory = LayoutInflater.from(SettingOption.this);
        final View view = factory.inflate(R.layout.dialog_quote_layout_param,null);
        dialog.setTitle("设置文案位置");
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.show();
        Button confirm = view.findViewById(R.id.confirm);
        Button cancel = view.findViewById(R.id.cancel);
        Button Set_default = view.findViewById(R.id.Set_default);
        Button clear = view.findViewById(R.id.clear);
        EditText layout_param_X = view.findViewById(R.id.layout_param_X);
        EditText layout_param_Y = view.findViewById(R.id.layout_param_Y);
        TextView screen_param = view.findViewById(R.id.screen_param);
        if(saveTextParam.loadString("EditQuoteLayoutParam_X") != null) {
            layout_param_X.setText(saveTextParam.loadString("EditQuoteLayoutParam_X"));
        }
        if(saveTextParam.loadString("EditQuoteLayoutParam_Y") != null) {
            layout_param_Y.setText(saveTextParam.loadString("EditQuoteLayoutParam_Y"));
        }
        screen_param.setText("本机(横屏时)可用屏幕高: " + saveTextParam.loadString("ScreenHeight") + "px ,  可用屏幕宽: " + saveTextParam.loadString("ScreenWeight") + "px");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText layout_param_X = view.findViewById(R.id.layout_param_X);
                EditText layout_param_Y = view.findViewById(R.id.layout_param_Y);
                if(!TextUtils.isEmpty(layout_param_X.getText().toString()) &&
                        !TextUtils.isEmpty(layout_param_Y.getText().toString())){
                    saveTextParam.saveString(layout_param_X.getText().toString(),"EditQuoteLayoutParam_X");
                    saveTextParam.saveString(layout_param_Y.getText().toString(),"EditQuoteLayoutParam_Y");
                    Toast.makeText(SettingOption.this, "编辑成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(SettingOption.this, "编辑有误！请填完所有输入框", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_param_X.setText("");
                layout_param_Y.setText("");
            }
        });
        Set_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_param_X.setText(saveTextParam.loadString("EditQuoteLayoutParam_X_Init"));
                layout_param_Y.setText(saveTextParam.loadString("EditQuoteLayoutParam_Y_Init"));
                saveTextParam.saveString(saveTextParam.loadString("EditQuoteLayoutParam_X_Init"),"EditQuoteLayoutParam_X");
                saveTextParam.saveString(saveTextParam.loadString("EditQuoteLayoutParam_Y_Init"),"EditQuoteLayoutParam_Y");
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //开启一个子线程
    private void StartThread() {
        new Thread(){
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(40);
                        Message message=new Message();
                        message.what=3;
                        handler.sendMessage(message);
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
        @SuppressLint({"NewApi", "ClickableViewAccessibility"})
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 3) {
                if(common_edit_color_select.getCurrentTextColor() == Color.parseColor("#ffffff")) {
                    Quote_edit_color_layout.setBackgroundColor(Color.parseColor("#000000"));
                    Common_edit_color_layout.setBackgroundColor(Color.parseColor("#000000"));
                    EditTextSize.setBackgroundColor(Color.parseColor("#000000"));
                    EditTextSize.setTextColor(Color.parseColor("#ffffff"));
                    EditClockLayoutParam.setBackgroundColor(Color.parseColor("#000000"));
                    EditClockLayoutParam.setTextColor(Color.parseColor("#ffffff"));
                    EditQuoteText.setBackgroundColor(Color.parseColor("#000000"));
                    EditQuoteText.setTextColor(Color.parseColor("#ffffff"));
                    EditQuoteTextSize.setBackgroundColor(Color.parseColor("#000000"));
                    EditQuoteTextSize.setTextColor(Color.parseColor("#ffffff"));
                    EditQuoteLayoutParam.setBackgroundColor(Color.parseColor("#000000"));
                    EditQuoteLayoutParam.setTextColor(Color.parseColor("#ffffff"));
                    AutoNightMode.setBackgroundColor(Color.parseColor("#000000"));
                    AutoNightMode.setTextColor(Color.parseColor("#ffffff"));
                    EditBackground.setBackgroundColor(Color.parseColor("#000000"));
                    EditBackground.setTextColor(Color.parseColor("#ffffff"));
                    DoNotDisturb.setTextColor(Color.parseColor("#ffffff"));
                    DoNotDisturb_SW_Layout.setBackgroundColor(Color.parseColor("#000000"));
                    DoNotDisturbLayout.setBackgroundColor(Color.parseColor("#000000"));
                    Share.setBackgroundColor(Color.parseColor("#000000"));
                    Share.setTextColor(Color.parseColor("#ffffff"));
                    MyAboutPage.setBackgroundColor(Color.parseColor("#000000"));
                    MyAboutPage.setTextColor(Color.parseColor("#ffffff"));
                    EditTextSize.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditTextSize.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditQuoteText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditQuoteText.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditQuoteTextSize.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditQuoteTextSize.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    AutoNightMode.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                AutoNightMode.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditBackground.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditBackground.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    Share.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                Share.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    MyAboutPage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                MyAboutPage.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditClockLayoutParam.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditClockLayoutParam.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditQuoteLayoutParam.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditQuoteLayoutParam.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                } else {
                    Quote_edit_color_layout.setBackgroundColor(getResources().getColor(R.color.white));
                    Common_edit_color_layout.setBackgroundColor(getResources().getColor(R.color.white));
                    EditTextSize.setBackgroundColor(getResources().getColor(R.color.white));
                    EditTextSize.setTextColor(Color.parseColor("#737373"));
                    EditClockLayoutParam.setBackgroundColor(getResources().getColor(R.color.white));
                    EditClockLayoutParam.setTextColor(Color.parseColor("#737373"));
                    EditQuoteText.setBackgroundColor(getResources().getColor(R.color.white));
                    EditQuoteText.setTextColor(Color.parseColor("#737373"));
                    EditQuoteTextSize.setBackgroundColor(getResources().getColor(R.color.white));
                    EditQuoteTextSize.setTextColor(Color.parseColor("#737373"));
                    EditQuoteLayoutParam.setBackgroundColor(getResources().getColor(R.color.white));
                    EditQuoteLayoutParam.setTextColor(Color.parseColor("#737373"));
                    AutoNightMode.setBackgroundColor(getResources().getColor(R.color.white));
                    AutoNightMode.setTextColor(Color.parseColor("#737373"));
                    EditBackground.setBackgroundColor(getResources().getColor(R.color.white));
                    EditBackground.setTextColor(Color.parseColor("#737373"));
                    DoNotDisturb_SW_Layout.setBackgroundColor(getResources().getColor(R.color.white));
                    DoNotDisturbLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    DoNotDisturb.setTextColor(Color.parseColor("#737373"));
                    Share.setBackgroundColor(getResources().getColor(R.color.white));
                    Share.setTextColor(Color.parseColor("#737373"));
                    MyAboutPage.setBackgroundColor(getResources().getColor(R.color.white));
                    MyAboutPage.setTextColor(Color.parseColor("#737373"));
                    EditTextSize.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditTextSize.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditQuoteLayoutParam.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditQuoteLayoutParam.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditQuoteText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditQuoteText.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditQuoteTextSize.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditQuoteTextSize.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    AutoNightMode.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                AutoNightMode.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditBackground.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditBackground.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    EditClockLayoutParam.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                EditClockLayoutParam.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    Share.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                Share.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                    MyAboutPage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                MyAboutPage.setBackgroundColor(getResources().getColor(R.color.select));
                            }
                            return false;
                        }
                    });
                }
            }
        }
    };
    protected void changeInterruptionFiler(int interruptionFilter){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ // If api level minimum 23
            if(mNotificationManager.isNotificationPolicyAccessGranted()){
                mNotificationManager.setInterruptionFilter(interruptionFilter);

                if(saveTextParam.loadSW("AppFirstInit")) {
                    saveTextParam.saveSW(false,"AppFirstInit");
                }
            }
        } else {
            Toast.makeText(this, "手机无免打扰功能", Toast.LENGTH_SHORT).show();
        }
    }
}