package buyer.bizzcity.bizzcityinfobuyer.Utils;

/**
 * Created by Andriod Avnish on 11-Apr-18.
 */

public class Const {

    String id;
    String catid;
    String eventName;
    String photo;
    String date;
    String desc;
    String orgby;
    String todate;
    String orgAddress;
    String orgMobile;


    public Const(String id,String catid, String eventName,String photo,String date, String desc,String orgby,String todate,String orgAddress,String orgMobile){
        this.id=id;
        this.catid=catid;
        this.eventName=eventName;
        this.photo=photo;
        this.date=date;
        this.desc=desc;
        this.orgby=orgby;
        this.todate=todate;
        this.orgAddress=orgAddress;
        this.orgMobile=orgMobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatid() {
        return catid;
    }

    public String getEventName() {
        return eventName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public String getOrgby() {
        return orgby;
    }

    public String getTodate() {
        return todate;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public String getOrgMobile() {
        return orgMobile;
    }
}
