/**                     
    * Project:  BunnyClock
    * Comments: 开源许可License声明类
    * JDK version used: <JDK1.8>
    * Author： Bunny     Github: https://github.com/bunny-chz/
    * Create Date：2022-03-15
    * Version: 1.0
    */

package com.bunny.clock;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class LicensePage extends AppCompatActivity {
    final String[] about_license_ColorPicker = new String[]{"ColorPicker","Apache v2.0"};
    final String[] about_license_AndroidAboutPage = new String[]{"Android About Page","MIT License"};
    private final String[] license_name = new String[]{"Android About Page","ColorPicker"};
    private final String[] license = new String[]{"MIT License","Apache v2.0"};
    private final String[] license_modify = new String[]{"否","否"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_page);
        initView();
    }
    private void initView() {
        ActionBar actionBar1 = getSupportActionBar();
        if(actionBar1 != null){
            actionBar1.setHomeButtonEnabled(true);
            actionBar1.setDisplayHomeAsUpEnabled(true);
        }
        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setTitle("版权信息");
        List<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0;i< license_name.length;i++) {
            HashMap<String,Object> item= new HashMap<>();
            item.put("license_name",license_name[i]);
            item.put("license",license[i]);
            item.put("license_modify",license_modify[i]);
            list.add(item);
        }
        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.license_listview,new String[]{"license_name","license","license_modify"},new int[]{R.id.license_name,R.id.license,R.id.license_modify});
        ListView license_list = findViewById(R.id.license_list);
        license_list.setAdapter(adapter);
        license_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        LicenseAndroidAboutPage();
                        break;
                    case 1:
                        LicenseColorPicker();
                        break;
                }
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
    public void LicenseAndroidAboutPage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LicensePage.this);
        builder.setTitle("请选择并查看相关开源信息");
        builder.setItems(about_license_AndroidAboutPage,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Uri uri1 = Uri.parse("https://github.com/medyo/android-about-page");
                        Intent intent1 = new Intent();
                        intent1.setAction("android.intent.action.VIEW");
                        intent1.setData(uri1);
                        startActivity(intent1);
                        break;
                    case 1:
                        Uri uri2 = Uri.parse("https://mit-license.org/");
                        Intent intent2 = new Intent();
                        intent2.setAction("android.intent.action.VIEW");
                        intent2.setData(uri2);
                        startActivity(intent2);
                        break;
                }
            }
        });
        builder.create().show();
    }
    public void LicenseColorPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LicensePage.this);
        builder.setTitle("请选择并查看相关开源信息");
        builder.setItems(about_license_ColorPicker,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Uri uri1 = Uri.parse("https://github.com/jaredrummler/ColorPicker");
                        Intent intent1 = new Intent();
                        intent1.setAction("android.intent.action.VIEW");
                        intent1.setData(uri1);
                        startActivity(intent1);
                        break;
                    case 1:
                        Uri uri2 = Uri.parse("https://www.apache.org/licenses/LICENSE-2.0");
                        Intent intent2 = new Intent();
                        intent2.setAction("android.intent.action.VIEW");
                        intent2.setData(uri2);
                        startActivity(intent2);
                        break;
                }
            }
        });
        builder.create().show();
    }
}