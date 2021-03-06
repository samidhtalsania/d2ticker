package greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 



// KEEP INCLUDES - put your custom includes here
import android.os.Parcel;
import android.os.Parcelable;
// KEEP INCLUDES END


/**
 * Entity mapped to table MATCH.
 */
public class Match implements Parcelable{

    private Long id;
    private String t1;
    private String t2;
    private String t1c;
    private String t2c;
    private Long ETA;
    private Boolean alarm_set;

    public Match() {
    }

    public Match(Long id) {
        this.id = id;
    }

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT1c() {
        return t1c;
    }

    public void setT1c(String t1c) {
        this.t1c = t1c;
    }

    public String getT2c() {
        return t2c;
    }

    public void setT2c(String t2c) {
        this.t2c = t2c;
    }

    public Long getETA() {
        return ETA;
    }

    public void setETA(Long ETA) {
        this.ETA = ETA;
    }

    public Boolean getAlarm_set() {
        return alarm_set;
    }

    public void setAlarm_set(Boolean alarm_set) {
        this.alarm_set = alarm_set;
    }

    // KEEP METHODS - put your custom methods here

    @Override
    public int describeContents() {
        return 0;
    }

    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(t1);
        dest.writeString(t2);
        dest.writeLong(ETA);
    }

    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel source) {
            return new Match(source);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };
    private Match(Parcel in){
        id = in.readLong();
        t1 = in.readString();
        t2 = in.readString();
        ETA = in.readLong();
    }


    public Match(Long id, String t1, String t2, String t1c, String t2c, Long ETA, Boolean alarm_set) {
        this.id = id;
        this.t1 = t1;
        this.t2 = t2;
        this.t1c = t1c;
        this.t2c = t2c;
        this.ETA = ETA;
        this.alarm_set = alarm_set;
    }
    // KEEP METHODS END

}
