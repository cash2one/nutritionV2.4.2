package com.jumper.weight.common.util;

import java.io.Serializable;

public class ReturnMsg implements Serializable {
	
	private static final long serialVersionUID = 6589008794478961923L;
	public static final int SUCCESS = 1;
	public static final int DATA_NULL = 10;
	public static final int FAIL = 0;
	private int msg;
	private String msgbox;
	private Object data;

	public ReturnMsg() {
		
	}

	public ReturnMsg(int msg, String msgbox, Object data) {
		this.msg = msg;
		this.msgbox = msgbox;
		this.data = data;
	}

	public ReturnMsg(int msg, String msgbox) {
		this.msg = msg;
		this.msgbox = msgbox;
	}

	public ReturnMsg(int msg, Object data) {
		this.msg = msg;
		this.data = data;
	}

	public int getMsg() {
		return msg;
	}

	public void setMsg(int msg) {
		this.msg = msg;
	}

	public String getMsgbox() {
		return msgbox;
	}

	public void setMsgbox(String msgbox) {
		this.msgbox = msgbox;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}