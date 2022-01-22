package com.zqk.stats.pojo;

public class TimePerson {
	
	int timegroup ;
	int personcount;
	int pagecount ; 
	
	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public TimePerson(){}
	
	public TimePerson(int personcount , int timegroup){
		this.personcount = personcount;
		this.timegroup = timegroup ;
	}

	public int getTimegroup() {
		return timegroup;
	}

	public void setTimegroup(int timegroup) {
		this.timegroup = timegroup;
	}

	public int getPersoncount() {
		return personcount;
	}
	public void setPersoncount(int personcount) {
		this.personcount = personcount;
	}
	
}
