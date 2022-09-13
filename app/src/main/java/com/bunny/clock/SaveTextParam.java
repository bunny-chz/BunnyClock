/**                     
    * Project:  BunnyClock
    * Comments: SharedPreferences保存数据类
    * JDK version used: <JDK1.8>
    * Author： Bunny     Github: https://github.com/bunny-chz/
    * Create Date：2022-03-15
    * Version: 1.0
    */

package com.bunny.clock;
import android.content.Context;
import android.content.SharedPreferences;
public class SaveTextParam {
    private final Context context;
    public SaveTextParam(Context context){
        this.context = context;
    }
    public void saveString(String value,String key){
        String name = context.getResources().getString(R.string.SaveTextParam);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String loadString(String key){
        String name = context.getResources().getString(R.string.SaveTextParam);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return shp.getString(key,null);
    }
    public void saveSW(Boolean value,String key){
        String name = context.getResources().getString(R.string.SaveTextParam);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public Boolean loadSW(String key){
        String name = context.getResources().getString(R.string.SaveTextParam);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return shp.getBoolean(key,false);
    }
    public void saveTrueSW(Boolean value,String key){
        String name = context.getResources().getString(R.string.SaveTextParam);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public Boolean loadTrueSW(String key){
        String name = context.getResources().getString(R.string.SaveTextParam);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return shp.getBoolean(key,true);
    }
}