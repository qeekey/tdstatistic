package com.zqk.stats.dao.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class BaseDaoImpl {
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Autowired
	public void setSqlMapClientTemplate(SqlMapClientTemplate sc){
		sqlMapClientTemplate=sc;
	}
	public SqlMapClientTemplate getSqlMapClientTemplate(){
		return sqlMapClientTemplate;
	}
	

}
