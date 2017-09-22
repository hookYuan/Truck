package com.yuan.map.citylist.module;

import java.util.Comparator;

/**
 * 作者：yuanYe创建于2016/9/26
 * QQ：962851730
 */
public class PinyinComparator implements Comparator<CityBean> {

	public int compare(CityBean o1, CityBean o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
