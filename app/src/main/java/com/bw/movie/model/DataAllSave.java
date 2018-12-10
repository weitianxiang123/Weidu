package com.bw.movie.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DataAllSave {

	@Id
	@Unique
 private	Long id;

 private String data;

	@Generated(hash = 1603046928)
	public DataAllSave(Long id, String data) {
		this.id = id;
		this.data = data;
	}

	@Generated(hash = 863054502)
	public DataAllSave() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
