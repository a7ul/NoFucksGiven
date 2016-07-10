package rationalduos.atulsoori.nofucksgiven.models;

import android.graphics.Color;

/**
 * Created by atulr on 09/07/16.
 */
public class NavDrawerItem {
    private int color;
    private String title;
    private int icon;

    private NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public NavDrawerItem(String title, int icon , int color){
        this(title,icon);
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }
}
