package com.yuan.basemodule.common.adapter;

/**
 * 作者：yuanYe创建于2016/10/26
 * QQ：962851730
 * ExpandableAdapter 二级展开
 */
public class ExpandableItem {

    private int groupPosition = 0; //一级菜单对应位置

    private int childPosition = 0; //二级菜单对应位置

    public int getChildPosition() {
        return childPosition;
    }

    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }

    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }
}
