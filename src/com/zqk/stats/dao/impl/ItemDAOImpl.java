package com.zqk.stats.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.zqk.stats.dao.ItemDAO;
import com.zqk.stats.pojo.ItemPojo;




@Repository
public class  ItemDAOImpl  extends BaseDaoImpl implements ItemDAO{

	public ItemPojo getItem(long num_iid) {
		return (ItemPojo)getSqlMapClientTemplate().queryForObject("ItemImpl.getItem", num_iid);
	}

	public int getItemCount(long shopid) {
		return (Integer) getSqlMapClientTemplate().queryForObject("ItemImpl.getItemCount", shopid);
	}
	
	public ItemPojo getItemByShopId(long shopid) {
		return (ItemPojo) getSqlMapClientTemplate().queryForObject("ItemImpl.getItemByShopId", shopid);
	}
	

	public void insertItem(ItemPojo item) {
		getSqlMapClientTemplate().insert("ItemImpl.insert", item);
	}
	
	

	public List<ItemPojo> getNumIidList(long shopid) {
		return (List<ItemPojo>) getSqlMapClientTemplate().queryForList("ItemImpl.getNumIidList", shopid);
	}

	public void deleteItem(long num_iid) {
		getSqlMapClientTemplate().delete("ItemImpl.delete", num_iid);
	}

	public void insertBatchItemData(final List varList) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)	throws SQLException {
				executor.startBatch();
				for (int i = 0; i < varList.size(); i++) {
					ItemPojo evDto = (ItemPojo) varList.get(i);
					executor.insert("ItemImpl.insert", evDto);
//					if ( i%50 == 0 ) {
//						System.out.println("pipiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
//						executor.executeBatch();
//					}
				}
				executor.executeBatch();
				return null;
			}
		});
	}
	
	
	public void updateBatchItemData(final List varList) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)	throws SQLException {
				executor.startBatch();
				for (int i = 0; i < varList.size(); i++) {
					ItemPojo evDto = (ItemPojo) varList.get(i);
					executor.update("ItemImpl.update", evDto);
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	public void deleteBatchItemData(final List varList) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)	throws SQLException {
				executor.startBatch();
				for (int i = 0; i < varList.size(); i++) {
					ItemPojo evDto = (ItemPojo) varList.get(i);
					executor.delete("ItemImpl.delete", evDto.getNum_iid());
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	
	public List<ItemPojo> getItemList(long shopid) {
		return (List<ItemPojo>) getSqlMapClientTemplate().queryForList("ItemImpl.getItemList", shopid);
	}


}
