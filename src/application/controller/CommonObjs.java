package application.controller;

import application.dal.DbConnectionInt;
import application.dal.DbSqlite;

public class CommonObjs {
	private DbConnectionInt dataBaseObj;

	private final static CommonObjs commonObj = new CommonObjs();
	
	private CommonObjs() {
		
	}
	
	public static CommonObjs getInstance() {
		return commonObj;
	}
	
	public DbConnectionInt getDataBaseObj() {
		return dataBaseObj;
	}
	
	public void setDataBaseObj(DbConnectionInt newDbConnectionInt) {
		this.dataBaseObj = newDbConnectionInt;
	}
}
