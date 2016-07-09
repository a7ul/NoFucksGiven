package rationalduos.atulsoori.nofucksgiven.models;

/**
 * Created by atulr on 09/07/16.
 */
public class NavDrawerItem {
    private String title;
    private int icon;
    public NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
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

    public void setTitle(String title) {
        this.title = title;
    }
}
