/*==========================================================================
 File name = DataFile.java
 Version: 0.1
 Time of release: 2015-12-15
All rights reserved by Inspur Group forever, any person or organization must get written permission from Inspur Group to copy a part or whole of this code. Otherwise, Inspur Group will pursue any loss from the copy, including any financial and patent loss, etc.
Copyright is protected from 2015-
----------------------------------------------------------------------------
 Modified records:
 Date            reviser 
 YYYY/MM/DD      XXXX          
 ----------------------------------------------------------------------------
 Indentation:
Tab = 4
==========================================================================*/

/**
 *  @file DataFile.java
 *  @author mathyrs
 *  @version 0.1
 *  @date 2015/12/15
 *  @reviser 
 */
package com.inspur.domain;

/**
 * @brief DataFile类，记录数据文件属性信息，对应TFile数据表。record attributes of data files
 * @author mathyrs
 * @date 2015/12/15
 */

public class DataFile {
	
	String file_id;
	
	String file_name;
	
	String owner_id;
	
	String owner_org_id;
	
	String flight_id;
	
	String gen_time;
	
	String open_class_id;

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner_org_id() {
		return owner_org_id;
	}

	public void setOwner_org_id(String owner_org_id) {
		this.owner_org_id = owner_org_id;
	}

	public String getFlight_id() {
		return flight_id;
	}

	public void setFlight_id(String flight_id) {
		this.flight_id = flight_id;
	}

	public String getGen_time() {
		return gen_time;
	}

	public void setGen_time(String gen_time) {
		this.gen_time = gen_time;
	}

	public String getOpen_class_id() {
		return open_class_id;
	}

	public void setOpen_class_id(String open_class_id) {
		this.open_class_id = open_class_id;
	}
	

}
