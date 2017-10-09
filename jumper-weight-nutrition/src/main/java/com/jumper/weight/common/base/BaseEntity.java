package com.jumper.weight.common.base;

import java.io.Serializable;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -7631366168821273107L;
	protected Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
