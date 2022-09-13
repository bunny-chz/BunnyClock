/**                     
    * Project:  BunnyClock
    * Comments: 关于界面类
    * JDK version used: <JDK1.8>
    * Author： Bunny     Github: https://github.com/bunny-chz/
    * Create Date：2022-03-15
    * Version: 1.0
    */

package com.bunny.clock;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
public class MyAboutPage extends AppCompatActivity {
    private static final String UPDATE_LOG = "V1.0.0基本功能\n" +
            "\n" +"Bunny Clock是一款全屏并持续亮屏显示，秒级别报时的数字时钟软件，具有非常大的自由定制特点。\n" +
            "可以自定义数字颜色，输入颜色的十六进制码即可。\n" +
            "可以自定义字体大小。\n" + "支持自定义时间段夜间模式，改变数字颜色。\n"
            + "支持屏幕自适应改变字体大小。" + "\n" +
            "\n" + "\n" +
            "V1.0.1更新\n" +
            "\n" +
            "新增RGB取色器，更方便快捷地去定义字体颜色。\n" +
            "新增自定义添加背景颜色和背景图片功能" +
            "新增文案功能，支持自定义内容、颜色和字体大小\n" +
            "新增免打扰功能，沉浸式体验，避免消息弹窗影响\n" +
            "新增时钟数字、文案位置设置功能\n";
    private static final String DESCRIPTION = "Bunny Clock是一款全屏并持续亮屏显示，秒级别报时的数字时钟软件，具有非常大的自由定制特点。可以自定义数字颜色、文案字体颜色，通过RGB取色器设置颜色。可以自定义文案内容。可以自定义时钟数字字体大小、文案字体大小。可以自定义时钟数字和文案在屏幕中的位置。支持自定义时间段夜间模式，改变数字颜色、背景颜色、背景图片、文案内容和文案颜色。";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.icon_100)
                .setDescription(DESCRIPTION)
                .addItem(new Element().setTitle("Version 1.0.1"))
                .addItem(getUpdateLog())
                .addItem(getLicenseElement())
                .addGroup("联系开发者(Bunny)")
                .addGitHub("bunnyboy233","GitHub")
                .create();
        setContentView(aboutPage);
    }
    Element getLicenseElement() {
        Element LicenseElement = new Element();
        LicenseElement.setTitle("版权信息");
        LicenseElement.setAutoApplyIconTint(true);
        LicenseElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        LicenseElement.setIconNightTint(android.R.color.white);
        LicenseElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyAboutPage.this, LicensePage.class);
                startActivity(i);
            }
        });
        return LicenseElement;
    }
    Element getUpdateLog() {
        Element LicenseElement = new Element();
        LicenseElement.setTitle("更新日志");
        LicenseElement.setAutoApplyIconTint(true);
        LicenseElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        LicenseElement.setIconNightTint(android.R.color.white);
        LicenseElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyAboutPage.this);
                builder.setCancelable(true);
                builder.setTitle("更新日志");
                builder.setMessage(UPDATE_LOG);
                builder.setPositiveButton("确定", null);
                builder.create().show();
            }
        });
        return LicenseElement;
    }
    private void initView() {
        ActionBar actionBar1 = getSupportActionBar();
        if(actionBar1 != null){
            actionBar1.setHomeButtonEnabled(true);
            actionBar1.setDisplayHomeAsUpEnabled(true);
        }
        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setTitle("关于");
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