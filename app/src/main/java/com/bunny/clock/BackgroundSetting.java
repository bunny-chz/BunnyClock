/**                     
    * Project:  BunnyClock
    * Comments: 设置背景类
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class BackgroundSetting extends AppCompatActivity implements ColorPickerDialogListener {
    SaveTextParam saveTextParam = new SaveTextParam(this);
    private static final String TAG = "BGSetting Color Picker";
    private static final int BACKGROUND_EDIT_COLOR_DIALOG_ID = 4;
    Button background_edit_color_select,background_edit_image_select;
    TextView background_edit_color_title_text;
    View background_edit_color_select_round;
    ImageView image;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch background_edit_color_SW,background_edit_image_SW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background_setting);
        initView();
    }
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void initView() {
        background_edit_color_select = findViewById(R.id.background_edit_color_select);
        background_edit_color_title_text = findViewById(R.id.background_edit_color_title_text);
        background_edit_color_select_round = findViewById(R.id.background_edit_color_select_round);
        background_edit_color_SW = findViewById(R.id.background_edit_color_SW);
        background_edit_image_SW = findViewById(R.id.background_edit_image_SW);
        background_edit_image_select = findViewById(R.id.background_edit_image_select);
        image = findViewById(R.id.image);
        if(saveTextParam.loadString("BGColor") != null) {
            if(saveTextParam.loadString("BGColor").equals("#ffffffff")) {
                background_edit_color_title_text.setText(saveTextParam.loadString("BGColor"));
                background_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
            } else {
                background_edit_color_title_text.setText(saveTextParam.loadString("BGColor"));
                background_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("BGColor")));}
        }
        if(saveTextParam.loadString("BGImageUri") != null) {
            image.setImageURI(Uri.parse(saveTextParam.loadString("BGImageUri")));
        }
        if(saveTextParam.loadString("BGImageUri") == null) {
            saveTextParam.saveString("null","BGImageUri");
        }
        if(saveTextParam.loadSW("BGColor_SW")) {
            background_edit_color_SW.setChecked(true);
        }
        if(saveTextParam.loadSW("BGImage_SW")) {
            background_edit_image_SW.setChecked(true);
        }
        ActionBar actionBar1 = getSupportActionBar();
        if(actionBar1 != null){
            actionBar1.setHomeButtonEnabled(true);
            actionBar1.setDisplayHomeAsUpEnabled(true);
        }
        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setTitle("设置背景");
        background_edit_color_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(background_edit_color_SW.isChecked()) {
                    EditBackgroundColor();
                }
                if(!background_edit_color_SW.isChecked()) {
                    Toast.makeText(BackgroundSetting.this, "请打开“设置背景颜色”开关", Toast.LENGTH_SHORT).show();
                }
            }
        });
        background_edit_color_select_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(background_edit_color_SW.isChecked()) {
                    EditBackgroundColor();
                }
                if(!background_edit_color_SW.isChecked()) {
                    Toast.makeText(BackgroundSetting.this, "请打开“设置背景颜色”开关", Toast.LENGTH_SHORT).show();
                }
            }
        });
        background_edit_image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(background_edit_image_SW.isChecked()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(intent, 0x02);
                }
                if(!background_edit_image_SW.isChecked()) {
                    Toast.makeText(BackgroundSetting.this, "请打开“设置背景图片”开关", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void background_edit_color_SW(View view){
        if(!saveTextParam.loadSW("night_SW")) {
            saveTextParam.saveSW(background_edit_color_SW.isChecked(),"BGColor_SW");
        }
        if(saveTextParam.loadSW("night_SW")) {
            background_edit_color_SW.setChecked(false);
            Toast.makeText(BackgroundSetting.this, "请关闭“自动夜间模式”开关\n设置→自动夜间模式→自动夜间模式开关", Toast.LENGTH_SHORT).show();
        }
    }
    public void background_edit_image_SW(View view){
        if(!saveTextParam.loadSW("night_SW")) {
            saveTextParam.saveSW(background_edit_image_SW.isChecked(),"BGImage_SW");
        }
        if(saveTextParam.loadSW("night_SW")) {
            background_edit_image_SW.setChecked(false);
            Toast.makeText(BackgroundSetting.this, "请关闭“自动夜间模式”开关\n设置→自动夜间模式→自动夜间模式开关", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x02 && resultCode == RESULT_OK) {
            if (data != null) {
                saveTextParam.saveString(data.getData().toString(),"BGImageUri");
                image.setImageURI(data.getData());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void EditBackgroundColor() {
        if(saveTextParam.loadString("BGColor") != null) {
            ColorPickerDialog.newBuilder()
                    .setDialogTitle(R.string.background_select_color)
                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                    .setAllowPresets(false)
                    .setDialogId(BACKGROUND_EDIT_COLOR_DIALOG_ID)
                    .setColor(Color.parseColor(saveTextParam.loadString("BGColor")))
                    .setShowAlphaSlider(true)
                    .show(BackgroundSetting.this);
        }else if(saveTextParam.loadString("BGColor") == null) {
            ColorPickerDialog.newBuilder()
                    .setDialogTitle(R.string.background_select_color)
                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                    .setAllowPresets(false)
                    .setDialogId(BACKGROUND_EDIT_COLOR_DIALOG_ID)
                    .setColor(Color.BLACK)
                    .setShowAlphaSlider(true)
                    .show(BackgroundSetting.this);
        }
    }
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    @Override public void onColorSelected(int dialogId, int color) {
        if (dialogId == BACKGROUND_EDIT_COLOR_DIALOG_ID) {
            saveTextParam.saveString("#" + Integer.toHexString(color),"BGColor");
            if(saveTextParam.loadString("BGColor").equals("#ffffffff")) {
                background_edit_color_title_text.setText(saveTextParam.loadString("BGColor"));
                background_edit_color_select_round.setBackground(getDrawable(R.drawable.round));
            }else {
                background_edit_color_title_text.setText(saveTextParam.loadString("BGColor"));
                background_edit_color_select_round.setBackgroundColor(Color.parseColor(saveTextParam.loadString("BGColor")));}
        }
    }

    @Override public void onDialogDismissed(int dialogId) {
        Log.d(TAG, "onDialogDismissed() called with: dialogId = [" + dialogId + "]");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
