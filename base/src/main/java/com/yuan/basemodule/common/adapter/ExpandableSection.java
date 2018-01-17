package com.yuan.basemodule.common.adapter;

/**
 * 作者：yuanYe创建于2016/10/26
 * QQ：962851730
 * ExpandableAdapter 一级展开
 */

public class ExpandableSection {

    private boolean isExpandable = false; //是否展开
    private int groupPosition = 0; //一级菜单对应位置

    public ExpandableSection() {

    }

    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
