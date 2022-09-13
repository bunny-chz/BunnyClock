/**                     
    * Project:  BunnyClock
    * Comments: 夜间模式类
    * JDK version used: <JDK1.8>
    * Author： Bunny     Github: https://github.com/bunny-chz/
    * Create Date：2022-03-15
    * Version: 1.0
    */

package com.bunny.clock;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class NightModeSetting extends AppCompatActivity implements ColorPickerDialogListener {
    SaveTextParam saveTextParam = new SaveTextParam(this);
    private static final int NIGHT_EDIT_COLOR_DIALOG_ID = 2;
    private static final int DAY_EDIT_COLOR_DIALOG_ID = 3;
    private static final int NIGHT_BG_COLOR_DIALOG_ID = 5;
    private static final int DAY_BG_COLOR_DIALOG_ID = 6;
    private static final int DAY_QUOTE_COLOR_DIALOG_ID = 8;
    private static final int NIGHT_QUOTE_COLOR_DIALOG_ID = 9;
    private static final String TAG = "Night Color Picker";
    Button night_color_select,day_color_select,night_background_color_select, day_background_color_select,
            night_background_edit_image_select,day_background_edit_image_select,day_quote_edit_color_select,
            night_quote_edit_color_select;
    TextView night_color_title_text,day_color_title_text, night_background_color_title_text, day_background_color_title_text,
            day_quote_edit_color_title_text,night_quote_edit_color_title_text;
    View night_color_select_round,day_color_select_round,night_background_color_select_round,
            day_background_color_select_round,day_quote_edit_color_select_round,
            night_quote_edit_color_select_round;
    ImageView night_image,day_image;
    EditText day_quote,night_quote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_night);
        initView();
    }
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void initView() {
        night_color_select = findViewById(R.id.night_color_select);
        day_color_select = findViewById(R.id.day_color_select);
        night_background_color_select = findViewById(R.id.night_background_color_select);
        day_background_color_select = findViewById(R.id.day_background_color_select);
        night_color_title_text = findViewById(R.id.night_color_title_text);
        day_color_title_text = findViewById(R.id.day_color_title_text);
        night_background_color_title_text = findViewById(R.id.night_background_color_title_text);
        day_background_color_title_text = findViewById(R.id.day_background_color_title_text);
        night_color_select_round = findViewById(R.id.night_color_select_round);
        day_color_select_round = findViewById(R.id.day_color_select_round);
        night_background_color_select_round = findViewById(R.id.night_background_color_select_round);
        day_background_color_select_round = findViewById(R.id.day_background_color_select_round);
        night_background_edit_image_select = findViewById(R.id.night_background_edit_image_select);
        day_background_edit_image_select = findViewById(R.id.day_background_edit_image_select);
        night_image = findViewById(R.id.night_image);
        day_image = findViewById(R.id.day_image);
        day_quote_edit_color_select_round = findViewById(R.id.day_quote_edit_color_select_round);
        day_quote_edit_color_select = findViewById(R.id.day_quote_edit_color_select);
        night_quote_edit_color_select_round = findViewById(R.id.night_quote_edit_color_select_round);
        night_quote_edit_color_select = findViewById(R.id.night_quote_edit_color_select);
        night_quote_edit_color_title_text = findViewById(R.id.night_quote_edit_color_title_text);
        day_quote_edit_color_title_text = findViewById(R.id.day_quote_edit_color_title_text);
        day_quote = findViewById(R.id.day_quote);
        night_quote = findViewById(R.id.night_quote);
        EditText time_start_hour = findViewById(R.id.time_start_hour);
        EditText time_start_minute = findViewById(R.id.time_start_minute);
        EditText time_end_hour = findViewById(R.id.time_end_hour);
        EditText time_end_minute = findViewById(R.id.time_end_minute);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_SW = findViewById(R.id.night_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_background_edit_image_SW = findViewById(R.id.night_background_edit_image_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch day_background_edit_image_SW = findViewById(R.id.day_background_edit_image_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_quote_SW = findViewById(R.id.night_quote_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch day_quote_SW = findViewById(R.id.day_quote_SW);
        if(saveTextParam.loadString("time_start_hour") != null) {
            time_start_hour.setText(saveTextParam.loadString("time_start_hour"));
            time_start_minute.setText(saveTextParam.loadString("time_start_minute"));
            time_end_hour.setText(saveTextParam.loadString("time_end_hour"));
            time_end_minute.setText(saveTextParam.loadString("time_end_minute"));
            night_SW.setChecked(saveTextParam.loadSW("night_SW"));
        }
        if(saveTextParam.loadString("NightQuoteText") != null) {
            night_quote.setText(saveTextParam.loadString("NightQuoteText"));
        }
        if(saveTextParam.loadString("DayQuoteText") != null) {
            day_quote.setText(saveTextParam.loadString("DayQuoteText"));
        }
        if(!saveTextParam.loadSW("night_BGImage_SW")) {
            saveTextParam.saveSW(false,"night_BGImage_SW");
        }
        if(!saveTextParam.loadSW("day_BGImage_SW")) {
            saveTextParam.saveSW(false,"day_BGImage_SW");
        }
        if(!saveTextParam.loadSW("night_quote_SW")) {
            saveTextParam.saveSW(false,"night_quote_SW");
        }
        if(!saveTextParam.loadSW("day_quote_SW")) {
            saveTextParam.saveSW(false,"day_quote_SW");
        }
        if(saveTextParam.loadSW("night_BGImage_SW")) {
            night_background_edit_image_SW.setChecked(saveTextParam.loadSW("night_BGImage_SW"));
        }
        if(saveTextParam.loadSW("day_BGImage_SW")) {
            day_background_edit_image_SW.setChecked(saveTextParam.loadSW("day_BGImage_SW"));
        }
        if(saveTextParam.loadSW("night_quote_SW")) {
            night_quote_SW.setChecked(saveTextParam.loadSW("night_quote_SW"));
        }
        if(saveTextParam.loadSW("day_quote_SW")) {
            day_quote_SW.setChecked(saveTextParam.loadSW("day_quote_SW"));
        }
        if(saveTextParam.loadString("NightColor") != null) {
            if(saveTextParam.loadString("NightColor").equals("#ffffffff")) {
                night_color_select_round.setBackground(getDrawable(R.drawable.round));
                night_color_title_text.setText(saveTextParam.loadString("NightColor"));
            } else {
            night_color_title_text.setText(saveTextParam.loadString("NightColor"));
            night_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("NightColor")));}
        }
        if(saveTextParam.loadString("NightBGColor") != null) {
            if(saveTextParam.loadString("NightBGColor").equals("#ffffffff")) {
                night_background_color_select_round.setBackground(getDrawable(R.drawable.round));
                night_background_color_title_text.setText(saveTextParam.loadString("NightBGColor"));
            } else {
                night_background_color_title_text.setText(saveTextParam.loadString("NightBGColor"));
                night_background_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("NightBGColor")));}
        }
        if(saveTextParam.loadString("NightQuoteTextColor") != null) {
            if(saveTextParam.loadString("NightQuoteTextColor").equals("#ffffffff")) {
                night_quote_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
                night_quote_edit_color_title_text.setText(saveTextParam.loadString("NightQuoteTextColor"));
            } else {
                night_quote_edit_color_title_text.setText(saveTextParam.loadString("NightQuoteTextColor"));
                night_quote_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("NightQuoteTextColor")));}
        }
        if(saveTextParam.loadString("DayColor") != null) {
            if(saveTextParam.loadString("DayColor").equals("#ffffffff")) {
                day_color_select_round.setBackground(getDrawable(R.drawable.round));
                day_color_title_text.setText(saveTextParam.loadString("DayColor"));
            }else {
            day_color_title_text.setText(saveTextParam.loadString("DayColor"));
            day_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("DayColor")));}
        }
        if(saveTextParam.loadString("DayBGColor") != null) {
            if(saveTextParam.loadString("DayBGColor").equals("#ffffffff")) {
                day_background_color_select_round.setBackground(getDrawable(R.drawable.round));
                day_background_color_title_text.setText(saveTextParam.loadString("DayBGColor"));
            } else {
                day_background_color_title_text.setText(saveTextParam.loadString("DayBGColor"));
                day_background_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("DayBGColor")));}
        }
        if(saveTextParam.loadString("DayQuoteTextColor") != null) {
            if(saveTextParam.loadString("DayQuoteTextColor").equals("#ffffffff")) {
                day_quote_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
                day_quote_edit_color_title_text.setText(saveTextParam.loadString("DayQuoteTextColor"));
            } else {
                day_quote_edit_color_title_text.setText(saveTextParam.loadString("DayQuoteTextColor"));
                day_quote_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("DayQuoteTextColor")));}
        }
        if(saveTextParam.loadString("NightBGImageUri") != null) {
            night_image.setImageURI(Uri.parse(saveTextParam.loadString("NightBGImageUri")));
        }
        if(saveTextParam.loadString("DayBGImageUri") != null) {
            day_image.setImageURI(Uri.parse(saveTextParam.loadString("DayBGImageUri")));
        }
        if(saveTextParam.loadString("NightColor") == null) {
            saveTextParam.saveString("#ffffffff","NightColor");
        }
        if(saveTextParam.loadString("NightColorTEMP") == null) {
            saveTextParam.saveString("#ffffffff","NightColorTEMP");
        }
        if(saveTextParam.loadString("NightBGColor") == null) {
            saveTextParam.saveString("#ff000000","NightBGColor");
        }
        if(saveTextParam.loadString("NightBGColorTEMP") == null) {
            saveTextParam.saveString("#ff000000","NightBGColorTEMP");
        }
        if(saveTextParam.loadString("NightQuoteTextColor") == null) {
            saveTextParam.saveString("#ffffffff","NightQuoteTextColor");
        }
        if(saveTextParam.loadString("NightQuoteTextColorTEMP") == null) {
            saveTextParam.saveString("#ffffffff","NightQuoteTextColorTEMP");
        }
        if(saveTextParam.loadString("DayColor") == null) {
            saveTextParam.saveString("#ffffffff","DayColor");
        }
        if(saveTextParam.loadString("DayColorTEMP") == null) {
            saveTextParam.saveString("#ffffffff","DayColorTEMP");
        }
        if(saveTextParam.loadString("DayBGColor") == null) {
            saveTextParam.saveString("#ff000000","DayBGColor");
        }
        if(saveTextParam.loadString("DayBGColorTEMP") == null) {
            saveTextParam.saveString("#ff000000","DayBGColorTEMP");
        }
        if(saveTextParam.loadString("DayQuoteTextColor") == null) {
            saveTextParam.saveString("#ffffffff","DayQuoteTextColor");
        }
        if(saveTextParam.loadString("DayQuoteTextColorTEMP") == null) {
            saveTextParam.saveString("#ffffffff","DayQuoteTextColorTEMP");
        }
        if(saveTextParam.loadString("NightBGImageUriTEMP") == null) {
            saveTextParam.saveString("null","NightBGImageUriTEMP");
        }
        if(saveTextParam.loadString("DayBGImageUriTEMP") == null) {
            saveTextParam.saveString("null","DayBGImageUriTEMP");
        }
        if(saveTextParam.loadString("NightBGImageUri") == null) {
            saveTextParam.saveString("null","NightBGImageUri");
        }
        if(saveTextParam.loadString("DayBGImageUri") == null) {
            saveTextParam.saveString("null","DayBGImageUri");
        }
        if(saveTextParam.loadString("NightQuoteText") == null) {
            saveTextParam.saveString("我们走进夜海，去打捞遗失的繁星。","NightQuoteText");
            night_quote.setText(saveTextParam.loadString("NightQuoteText"));
        }
        if(saveTextParam.loadString("DayQuoteText") == null) {
            saveTextParam.saveString("现在几点，开心一点。早安!","DayQuoteText");
            day_quote.setText(saveTextParam.loadString("DayQuoteText"));
        }
        night_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("NightColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_EDIT_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("NightColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("NightColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_EDIT_COLOR_DIALOG_ID)
                            .setColor(Color.WHITE)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        night_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("NightColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_EDIT_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("NightColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("NightColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_EDIT_COLOR_DIALOG_ID)
                            .setColor(Color.WHITE)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        night_background_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("NightBGColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_BG_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("NightBGColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("NightBGColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_BG_COLOR_DIALOG_ID)
                            .setColor(Color.BLACK)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        night_background_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("NightBGColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_BG_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("NightBGColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("NightBGColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_BG_COLOR_DIALOG_ID)
                            .setColor(Color.BLACK)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        day_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("DayColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_EDIT_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("DayColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("DayColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_EDIT_COLOR_DIALOG_ID)
                            .setColor(Color.WHITE)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        day_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("DayColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_EDIT_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("DayColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("DayColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_EDIT_COLOR_DIALOG_ID)
                            .setColor(Color.WHITE)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        day_background_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("DayBGColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_BG_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("DayBGColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("DayBGColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_BG_COLOR_DIALOG_ID)
                            .setColor(Color.BLACK)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        day_background_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("DayBGColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_BG_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("DayBGColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("DayBGColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_BG_COLOR_DIALOG_ID)
                            .setColor(Color.BLACK)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        night_background_edit_image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(night_background_edit_image_SW.isChecked()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(intent, 0x03);
                }
                if(!night_background_edit_image_SW.isChecked()) {
                    Toast.makeText(NightModeSetting.this, "请打开“设置夜间背景图片”开关", Toast.LENGTH_SHORT).show();
                }
            }
        });
        day_background_edit_image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(day_background_edit_image_SW.isChecked()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(intent, 0x04);
                }
                if(!day_background_edit_image_SW.isChecked()) {
                    Toast.makeText(NightModeSetting.this, "请打开“设置非夜间背景图片”开关", Toast.LENGTH_SHORT).show();
                }
            }
        });
        night_quote_edit_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("NightQuoteTextColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_QUOTE_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("NightQuoteTextColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("NightQuoteTextColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_QUOTE_COLOR_DIALOG_ID)
                            .setColor(Color.WHITE)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        night_quote_edit_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("NightQuoteTextColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_QUOTE_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("NightQuoteTextColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("NightQuoteTextColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(NIGHT_QUOTE_COLOR_DIALOG_ID)
                            .setColor(Color.WHITE)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        day_quote_edit_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("DayQuoteTextColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_QUOTE_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("DayQuoteTextColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("DayQuoteTextColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_QUOTE_COLOR_DIALOG_ID)
                            .setColor(Color.WHITE)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        day_quote_edit_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTextParam.loadString("DayQuoteTextColorTEMP") != null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_QUOTE_COLOR_DIALOG_ID)
                            .setColor(Color.parseColor(saveTextParam.loadString("DayQuoteTextColorTEMP")))
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }else if(saveTextParam.loadString("DayQuoteTextColorTEMP") == null) {
                    ColorPickerDialog.newBuilder()
                            .setDialogTitle(R.string.background_select_color)
                            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                            .setAllowPresets(false)
                            .setDialogId(DAY_QUOTE_COLOR_DIALOG_ID)
                            .setColor(Color.WHITE)
                            .setShowAlphaSlider(true)
                            .show(NightModeSetting.this);
                }
            }
        });
        ActionBar actionBar1 = getSupportActionBar();
        if(actionBar1 != null){
            actionBar1.setHomeButtonEnabled(true);
            actionBar1.setDisplayHomeAsUpEnabled(true);
        }
        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setTitle("自动夜间模式设置");
    }
    public void night_quote_SW(View view){
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_quote_SW = findViewById(R.id.night_quote_SW);
        if(!saveTextParam.loadSW("Quote_SW")) {
            night_quote_SW.setChecked(false);
            Toast.makeText(NightModeSetting.this, "请打开“是否显示文案”开关\n设置→是否显示文案", Toast.LENGTH_SHORT).show();
        }
    }
    public void day_quote_SW(View view){
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch day_quote_SW = findViewById(R.id.day_quote_SW);
        if(!saveTextParam.loadSW("Quote_SW")) {
            day_quote_SW.setChecked(false);
            Toast.makeText(NightModeSetting.this, "请打开“是否显示文案”开关\n设置→是否显示文案", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x03 && resultCode == RESULT_OK) {
            if (data != null) {
                saveTextParam.saveString(data.getData().toString(),"NightBGImageUriTEMP");
                night_image.setImageURI(Uri.parse(saveTextParam.loadString("NightBGImageUriTEMP")));
            }
        }
        if (requestCode == 0x04 && resultCode == RESULT_OK) {
            if (data != null) {
                saveTextParam.saveString(data.getData().toString(),"DayBGImageUriTEMP");
                day_image.setImageURI(Uri.parse(saveTextParam.loadString("DayBGImageUriTEMP")));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    @Override public void onColorSelected(int dialogId, int color) {
        if (dialogId == NIGHT_EDIT_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"NightColorTEMP");
            if(saveTextParam.loadString("NightColorTEMP").equals("#ffffffff")) {
                night_color_select_round.setBackground(getDrawable(R.drawable.round));
                night_color_title_text.setText(saveTextParam.loadString("NightColorTEMP"));
            }else {
            night_color_title_text.setText(saveTextParam.loadString("NightColorTEMP"));
            night_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("NightColorTEMP")));}
        } else if(dialogId == DAY_EDIT_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"DayColorTEMP");
            if(saveTextParam.loadString("DayColorTEMP").equals("#ffffffff")) {
                day_color_select_round.setBackground(getDrawable(R.drawable.round));
                day_color_title_text.setText(saveTextParam.loadString("DayColorTEMP"));
            }else {
            day_color_title_text.setText(saveTextParam.loadString("DayColorTEMP"));
            day_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("DayColorTEMP")));}
        }
        else if(dialogId == NIGHT_BG_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"NightBGColorTEMP");
            if(saveTextParam.loadString("NightBGColorTEMP").equals("#ffffffff")) {
                night_background_color_select_round.setBackground(getDrawable(R.drawable.round));
                night_background_color_title_text.setText(saveTextParam.loadString("NightBGColorTEMP"));
            }else {
                night_background_color_title_text.setText(saveTextParam.loadString("NightBGColorTEMP"));
                night_background_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("NightBGColorTEMP")));}
        }
        else if(dialogId == DAY_BG_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"DayBGColorTEMP");
            if(saveTextParam.loadString("DayBGColorTEMP").equals("#ffffffff")) {
                day_background_color_select_round.setBackground(getDrawable(R.drawable.round));
                day_background_color_title_text.setText(saveTextParam.loadString("DayBGColorTEMP"));
            }else {
                day_background_color_title_text.setText(saveTextParam.loadString("DayBGColorTEMP"));
                day_background_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("DayBGColorTEMP")));}
        }
        else if(dialogId == NIGHT_QUOTE_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"NightQuoteTextColorTEMP");
            if(saveTextParam.loadString("NightQuoteTextColorTEMP").equals("#ffffffff")) {
                night_quote_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
                night_quote_edit_color_title_text.setText(saveTextParam.loadString("NightQuoteTextColorTEMP"));
            }else {
                night_quote_edit_color_title_text.setText(saveTextParam.loadString("NightQuoteTextColorTEMP"));
                night_quote_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("NightQuoteTextColorTEMP")));}
        }else if(dialogId == DAY_QUOTE_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"DayQuoteTextColorTEMP");
            if(saveTextParam.loadString("DayQuoteTextColorTEMP").equals("#ffffffff")) {
                day_quote_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
                day_quote_edit_color_title_text.setText(saveTextParam.loadString("DayQuoteTextColorTEMP"));
            }else {
                day_quote_edit_color_title_text.setText(saveTextParam.loadString("DayQuoteTextColorTEMP"));
                day_quote_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("DayQuoteTextColorTEMP")));}
        }
    }

    @Override public void onDialogDismissed(int dialogId) {
        Log.d(TAG, "onDialogDismissed() called with: dialogId = [" + dialogId + "]");
    }
    public void SaveSetting() {
        EditText time_start_hour = findViewById(R.id.time_start_hour);
        EditText time_start_minute = findViewById(R.id.time_start_minute);
        EditText time_end_hour = findViewById(R.id.time_end_hour);
        EditText time_end_minute = findViewById(R.id.time_end_minute);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_SW = findViewById(R.id.night_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_background_edit_image_SW = findViewById(R.id.night_background_edit_image_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch day_background_edit_image_SW = findViewById(R.id.day_background_edit_image_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_quote_SW = findViewById(R.id.night_quote_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch day_quote_SW = findViewById(R.id.day_quote_SW);
        if(!TextUtils.isEmpty(time_start_hour.getText().toString()) &&
                !TextUtils.isEmpty(time_start_minute.getText().toString()) &&
                !TextUtils.isEmpty(time_end_hour.getText().toString()) &&
                !TextUtils.isEmpty(time_end_minute.getText().toString())&&
                !TextUtils.isEmpty(day_quote.getText().toString())&&
                !TextUtils.isEmpty(night_quote.getText().toString())
        ){ saveTextParam.saveString(time_start_hour.getText().toString(),"time_start_hour");
            saveTextParam.saveString(time_start_minute.getText().toString(),"time_start_minute");
            saveTextParam.saveString(time_end_hour.getText().toString(),"time_end_hour");
            saveTextParam.saveString(time_end_minute.getText().toString(),"time_end_minute");
            saveTextParam.saveString(day_quote.getText().toString(),"DayQuoteText");
            saveTextParam.saveString(night_quote.getText().toString(),"NightQuoteText");
            if(night_SW.isChecked())
            { saveTextParam.saveSW(true,"night_SW");
                saveTextParam.saveSW(false,"BGColor_SW");
                saveTextParam.saveSW(false,"BGImage_SW");
            } else if(!night_SW.isChecked()){
                saveTextParam.saveSW(false,"night_SW");
            }
            if(night_background_edit_image_SW.isChecked())
            { saveTextParam.saveSW(true,"night_BGImage_SW");
            } else if(!night_background_edit_image_SW.isChecked()){
                saveTextParam.saveSW(false,"night_BGImage_SW");
            }
            if(day_background_edit_image_SW.isChecked())
            { saveTextParam.saveSW(true,"day_BGImage_SW");
            } else if(!day_background_edit_image_SW.isChecked()){
                saveTextParam.saveSW(false,"day_BGImage_SW");
            }
            if(day_quote_SW.isChecked())
            { saveTextParam.saveSW(true,"day_quote_SW");
            } else if(!day_quote_SW.isChecked()){
                saveTextParam.saveSW(false,"day_quote_SW");
            }
            if(night_quote_SW.isChecked())
            { saveTextParam.saveSW(true,"night_quote_SW");
            } else if(!night_quote_SW.isChecked()){
                saveTextParam.saveSW(false,"night_quote_SW");
            }
            if(saveTextParam.loadString("NightColorTEMP") == null) {
                saveTextParam.saveString("#ffffffff","NightColor");
            } else if(saveTextParam.loadString("NightColorTEMP") != null){
                saveTextParam.saveString(saveTextParam.loadString("NightColorTEMP"),"NightColor");
            }
            if(saveTextParam.loadString("NightBGColorTEMP") == null) {
                saveTextParam.saveString("#ff000000","NightBGColor");
            } else if(saveTextParam.loadString("NightBGColorTEMP") != null){
                saveTextParam.saveString(saveTextParam.loadString("NightBGColorTEMP"),"NightBGColor");
            }
            if(saveTextParam.loadString("NightQuoteTextColorTEMP") == null) {
                saveTextParam.saveString("#ffffffff","NightQuoteTextColor");
            } else if(saveTextParam.loadString("NightQuoteTextColorTEMP") != null){
                saveTextParam.saveString(saveTextParam.loadString("NightQuoteTextColorTEMP"),"NightQuoteTextColor");
            }
            if(saveTextParam.loadString("DayColorTEMP") == null) {
                saveTextParam.saveString("#ffffffff","DayColor");
            } else if(saveTextParam.loadString("DayColorTEMP") != null){
                saveTextParam.saveString(saveTextParam.loadString("DayColorTEMP"),"DayColor");
            }
            if(saveTextParam.loadString("DayBGColorTEMP") == null) {
                saveTextParam.saveString("#ff000000","DayBGColor");
            } else if(saveTextParam.loadString("DayBGColorTEMP") != null){
                saveTextParam.saveString(saveTextParam.loadString("DayBGColorTEMP"),"DayBGColor");
            }
            if(saveTextParam.loadString("DayQuoteTextColorTEMP") == null) {
                saveTextParam.saveString("#ffffffff","DayQuoteTextColor");
            } else if(saveTextParam.loadString("DayQuoteTextColorTEMP") != null){
                saveTextParam.saveString(saveTextParam.loadString("DayQuoteTextColorTEMP"),"DayQuoteTextColor");
            }
            if(saveTextParam.loadString("DayBGImageUriTEMP") == null) {
                saveTextParam.saveString("null","DayBGImageUri");
            } else if(saveTextParam.loadString("DayBGImageUriTEMP") != null){
                saveTextParam.saveString(saveTextParam.loadString("DayBGImageUriTEMP"),"DayBGImageUri");
            }
            if(saveTextParam.loadString("NightBGImageUriTEMP") == null) {
                saveTextParam.saveString("null","NightBGImageUri");
            } else if(saveTextParam.loadString("NightBGImageUriTEMP") != null){
                saveTextParam.saveString(saveTextParam.loadString("NightBGImageUriTEMP"),"NightBGImageUri");
            }
            this.finish();
            Toast.makeText(NightModeSetting.this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(NightModeSetting.this, "编辑有误！请填完所有的输入框", Toast.LENGTH_SHORT).show();
        }
    }
    public void SaveJudgment() {
        EditText time_start_hour = findViewById(R.id.time_start_hour);
        EditText time_start_minute = findViewById(R.id.time_start_minute);
        EditText time_end_hour = findViewById(R.id.time_end_hour);
        EditText time_end_minute = findViewById(R.id.time_end_minute);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_SW = findViewById(R.id.night_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_background_edit_image_SW = findViewById(R.id.night_background_edit_image_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch day_background_edit_image_SW = findViewById(R.id.day_background_edit_image_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch night_quote_SW = findViewById(R.id.night_quote_SW);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch day_quote_SW = findViewById(R.id.day_quote_SW);
        if(saveTextParam.loadString("time_start_hour") != null){
            if(((time_start_hour.getText().toString().equals(saveTextParam.loadString("time_start_hour"))) &&
                    (time_start_minute.getText().toString().equals(saveTextParam.loadString("time_start_minute"))) &&
                    (time_end_hour.getText().toString().equals(saveTextParam.loadString("time_end_hour"))) &&
                    (time_end_minute.getText().toString().equals(saveTextParam.loadString("time_end_minute"))) &&
                    (night_quote.getText().toString().equals(saveTextParam.loadString("NightQuoteText"))) &&
                    (day_quote.getText().toString().equals(saveTextParam.loadString("DayQuoteText"))) &&
                    (saveTextParam.loadString("NightColorTEMP").equals(saveTextParam.loadString("NightColor"))) &&
                    (saveTextParam.loadString("NightBGColorTEMP").equals(saveTextParam.loadString("NightBGColor"))) &&
                    (saveTextParam.loadString("NightQuoteTextColorTEMP").equals(saveTextParam.loadString("NightQuoteTextColor"))) &&
                    (saveTextParam.loadString("DayColorTEMP").equals(saveTextParam.loadString("DayColor"))) &&
                    (saveTextParam.loadString("DayBGColorTEMP").equals(saveTextParam.loadString("DayBGColor"))) &&
                    (saveTextParam.loadString("DayQuoteTextColorTEMP").equals(saveTextParam.loadString("DayQuoteTextColor"))) &&
                    (saveTextParam.loadString("NightBGImageUriTEMP").equals(saveTextParam.loadString("NightBGImageUri"))) &&
                    (saveTextParam.loadString("DayBGImageUriTEMP").equals(saveTextParam.loadString("DayBGImageUri"))) &&
                    !(night_SW.isChecked() ^ saveTextParam.loadSW("night_SW")) &&
                    !(night_background_edit_image_SW.isChecked() ^ saveTextParam.loadSW("night_BGImage_SW")) &&
                    !(day_background_edit_image_SW.isChecked() ^ saveTextParam.loadSW("day_BGImage_SW"))  &&
                    !(night_quote_SW.isChecked() ^ saveTextParam.loadSW("night_quote_SW")) &&
                    !(day_quote_SW.isChecked() ^ saveTextParam.loadSW("day_quote_SW"))
            )){ this.finish();
            } else if(!((time_start_hour.getText().toString().equals(saveTextParam.loadString("time_start_hour"))) &&
                (time_start_minute.getText().toString().equals(saveTextParam.loadString("time_start_minute"))) &&
                (time_end_hour.getText().toString().equals(saveTextParam.loadString("time_end_hour"))) &&
                (time_end_minute.getText().toString().equals(saveTextParam.loadString("time_end_minute"))) &&
                    (night_quote.getText().toString().equals(saveTextParam.loadString("NightQuoteText"))) &&
                    (day_quote.getText().toString().equals(saveTextParam.loadString("DayQuoteText"))) &&
                (saveTextParam.loadString("NightColorTEMP").equals(saveTextParam.loadString("NightColor"))) &&
                (saveTextParam.loadString("NightBGColorTEMP").equals(saveTextParam.loadString("NightBGColor"))) &&
                (saveTextParam.loadString("NightQuoteTextColorTEMP").equals(saveTextParam.loadString("NightQuoteTextColor"))) &&
                (saveTextParam.loadString("DayColorTEMP").equals(saveTextParam.loadString("DayColor"))) &&
                (saveTextParam.loadString("DayBGColorTEMP").equals(saveTextParam.loadString("DayBGColor"))) &&
                (saveTextParam.loadString("DayQuoteTextColorTEMP").equals(saveTextParam.loadString("DayQuoteTextColor"))) &&
                (saveTextParam.loadString("NightBGImageUriTEMP").equals(saveTextParam.loadString("NightBGImageUri"))) &&
                (saveTextParam.loadString("DayBGImageUriTEMP").equals(saveTextParam.loadString("DayBGImageUri"))) &&
                !(night_SW.isChecked() ^ saveTextParam.loadSW("night_SW")) &&
                !(night_background_edit_image_SW.isChecked() ^ saveTextParam.loadSW("night_BGImage_SW")) &&
                !(day_background_edit_image_SW.isChecked() ^ saveTextParam.loadSW("day_BGImage_SW")) &&
                    !(night_quote_SW.isChecked() ^ saveTextParam.loadSW("night_quote_SW")) &&
                    !(day_quote_SW.isChecked() ^ saveTextParam.loadSW("day_quote_SW"))
        )) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("提示");
            builder.setMessage("数据有改变，是否保存？");
            builder.setPositiveButton("确定", (dialogInterface, i) -> SaveSetting());
            builder.setNegativeButton("不保存", (dialogInterface, i) -> {
                if(!(saveTextParam.loadString("DayColorTEMP").equals(saveTextParam.loadString("DayColor")))) {
                    saveTextParam.saveString(saveTextParam.loadString("DayColor"),"DayColorTEMP");
                }
                if(!(saveTextParam.loadString("NightColorTEMP").equals(saveTextParam.loadString("NightColor")))) {
                    saveTextParam.saveString(saveTextParam.loadString("NightColor"),"NightColorTEMP");
                }
                if(!(saveTextParam.loadString("NightBGColorTEMP").equals(saveTextParam.loadString("NightBGColor")))) {
                    saveTextParam.saveString(saveTextParam.loadString("NightBGColor"),"NightBGColorTEMP");
                }
                if(!(saveTextParam.loadString("DayBGColorTEMP").equals(saveTextParam.loadString("DayBGColor")))) {
                    saveTextParam.saveString(saveTextParam.loadString("DayBGColor"),"DayBGColorTEMP");
                }
                if(!(saveTextParam.loadString("NightQuoteTextColorTEMP").equals(saveTextParam.loadString("NightQuoteTextColor")))) {
                    saveTextParam.saveString(saveTextParam.loadString("NightQuoteTextColor"),"NightQuoteTextColorTEMP");
                }
                if(!(saveTextParam.loadString("DayQuoteTextColorTEMP").equals(saveTextParam.loadString("DayQuoteTextColor")))) {
                    saveTextParam.saveString(saveTextParam.loadString("DayQuoteTextColor"),"DayQuoteTextColorTEMP");
                }
                if(!(saveTextParam.loadString("NightBGImageUriTEMP").equals(saveTextParam.loadString("NightBGImageUri")))) {
                    saveTextParam.saveString(saveTextParam.loadString("NightBGImageUri"),"NightBGImageUriTEMP");
                }
                if(!(saveTextParam.loadString("DayBGImageUriTEMP").equals(saveTextParam.loadString("DayBGImageUri")))) {
                    saveTextParam.saveString(saveTextParam.loadString("DayBGImageUri"),"DayBGImageUriTEMP");
                }
                this.finish();
            });
            builder.create().show();
        } } else if(saveTextParam.loadString("time_start_hour") == null) {
            this.finish();
        }
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SaveJudgment();
                return true;
            case R.id.save_night_setting:
                SaveSetting();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        SaveJudgment();
    }
}