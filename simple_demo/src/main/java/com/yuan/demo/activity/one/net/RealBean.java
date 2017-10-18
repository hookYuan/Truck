package com.yuan.demo.activity.one.net;

/**
 * Created by YuanYe on 2017/10/18.
 */

public class RealBean {

    /**
     * starLevel : 4
     * remarkCotnent : 评价方未及时做出评价，系统默认满意！
     * remarkTime : 2013-02-27 07:21:48
     * explainContent :
     * postMemberId : y**f
     * tpLogoURL : http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png
     */

    private int starLevel;
    private String remarkCotnent;
    private String remarkTime;
    private String explainContent;
    private String postMemberId;
    private String tpLogoURL;

    public int getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(int starLevel) {
        this.starLevel = starLevel;
    }

    public String getRemarkCotnent() {
        return remarkCotnent;
    }

    public void setRemarkCotnent(String remarkCotnent) {
        this.remarkCotnent = remarkCotnent;
    }

    public String getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(String remarkTime) {
        this.remarkTime = remarkTime;
    }

    public String getExplainContent() {
        return explainContent;
    }

    public void setExplainContent(String explainContent) {
        this.explainContent = explainContent;
    }

    public String getPostMemberId() {
        return postMemberId;
    }

    public void setPostMemberId(String postMemberId) {
        this.postMemberId = postMemberId;
    }

    public String getTpLogoURL() {
        return tpLogoURL;
    }

    public void setTpLogoURL(String tpLogoURL) {
        this.tpLogoURL = tpLogoURL;
    }

    @Override
    public String toString() {
        return "RealBean{" +
                "starLevel=" + starLevel +
                ", remarkCotnent='" + remarkCotnent + '\'' +
                ", remarkTime='" + remarkTime + '\'' +
                ", explainContent='" + explainContent + '\'' +
                ", postMemberId='" + postMemberId + '\'' +
                ", tpLogoURL='" + tpLogoURL + '\'' +
                '}';
    }
}
