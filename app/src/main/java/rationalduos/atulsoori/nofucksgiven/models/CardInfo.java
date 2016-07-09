package rationalduos.atulsoori.nofucksgiven.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by atulr on 03/07/16.
 */
public class CardInfo implements Parcelable {
    private String id;
    private String name;
    private String contributor;
    private int type;
    private String data;
    private int favourites;

    public CardInfo(String _id, String _name, String _contributor, int _type, String _data,int _favourites) {
        id = _id;
        name = _name;
        contributor = _contributor;
        type = _type;
        data = _data;
        favourites = _favourites;
    }

    @Override
    public String toString() {
        return this.id + " " + this.name + " " + this.contributor + " " + this.type + " " + this.data;
    }

    public String getId() {
        return id;
    }

    public String getContributor() {
        return contributor;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getFavourites() {
        return favourites;
    }

    public CardInfo(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<CardInfo> CREATOR = new Parcelable.Creator<CardInfo>() {
        public CardInfo createFromParcel(Parcel in) {
            return new CardInfo(in);
        }

        public CardInfo[] newArray(int size) {

            return new CardInfo[size];
        }

    };

    public void readFromParcel(Parcel in) {
        id = in.readString();
        name = in.readString();
        contributor = in.readString();
        type = in.readInt();
        data = in.readString();
        favourites = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(contributor);
        dest.writeInt(type);
        dest.writeString(data);
        dest.writeInt(favourites);
    }
}
