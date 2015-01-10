package greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table MATCH.
 */
public class Match {

    private Long id;
    private String t1;
    private String t2;
    private String t1c;
    private String t2c;
    private Long ETA;

    public Match() {
    }

    public Match(Long id) {
        this.id = id;
    }

    public Match(Long id, String t1, String t2, String t1c, String t2c, Long ETA) {
        this.id = id;
        this.t1 = t1;
        this.t2 = t2;
        this.t1c = t1c;
        this.t2c = t2c;
        this.ETA = ETA;
    }

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

}
