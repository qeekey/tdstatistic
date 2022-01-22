package com.zqk.stats.pojo;

import java.util.Comparator;

public class ItemComparator implements Comparator { 

	public int compare(Object o1, Object o2) {
		ItemPojo c1 = (ItemPojo) o1;
		ItemPojo c2 = (ItemPojo) o2;
		if (c1.getNum_iid() > c2.getNum_iid()) {
			return 1;
		} else {
			if (c1.getNum_iid() == c2.getNum_iid()) {
				return 0;
			} else {
				return -1;
			}
		}
	} 

}
