package com.zqk.stats.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.zqk.stats.pojo.ItemPojo;

/**
 * @author zqk
 *
 */
public class ListUtil {
	
	
	private  static List<ItemPojo> lista = new ArrayList<ItemPojo>();
	private  static List<ItemPojo> listb = new ArrayList<ItemPojo>();
	

	public static List<ItemPojo> AsubB(List<ItemPojo> lista,List<ItemPojo> listb){		
		List retlist = (List)CollectionUtils.subtract(lista, listb);
		return retlist;
	}
	
	/***
	 * 找出List中相同的
	 * @param lista
	 * @param listb
	 * @return
	 */
	public static List<ItemPojo> AsameB(List<ItemPojo> lista,List<ItemPojo> listb){		
		List retlist = new ArrayList<ItemPojo>();
		Iterator itb= listb.iterator();
		while(itb.hasNext()){
			ItemPojo item = (ItemPojo)itb.next();
			if(lista.contains(item)){
				retlist.add(item);
			}
		}
		return retlist;
	}
		

	
}
