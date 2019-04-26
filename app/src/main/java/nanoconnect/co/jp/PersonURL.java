package nanoconnect.co.jp;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonURL implements Parcelable {
    private String name;
    private String mail;
    private String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.mail);
        dest.writeString(this.url);
    }

    public static final Creator<PersonURL> CREATOR = new Creator<PersonURL>() {
        @Override
        public PersonURL createFromParcel(Parcel in) {
            return new PersonURL(in);
        }

        @Override
        public PersonURL[] newArray(int size) {
            return new PersonURL[size];
        }
    };

    private PersonURL(Parcel in) {
        this.name = in.readString();
        this.mail = in.readString();
        this.url = in.readString();
    }

    public PersonURL(String name, String mail, String url) {
        this.name = name;
        this.mail  = mail;
        this.url = url;
    }
}
