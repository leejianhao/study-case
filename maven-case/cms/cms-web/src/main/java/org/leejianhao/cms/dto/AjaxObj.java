package org.leejianhao.cms.dto;

/**
 * 专门用来返回Ajax处理之后结果的json数据对象
 * @author ljh
 *
 */
public class AjaxObj {
	/**
	 * 0表示失败
	 * 1表示成功
	 */
	private int result;
	
	/**
	 * 提示消息
	 */
	private String msg;
	
	/**
	 * 附加对象，用来存储一些特定的返回信息
	 */
	private Object obj;

	public AjaxObj(int result, String msg, Object obj) {
		super();
		this.result = result;
		this.msg = msg;
		this.obj = obj;
	}

	public AjaxObj(int result) {
		super();
		this.result = result;
	}
	
	public AjaxObj(int result, String msg) {
		super();
		this.result = result;
		this.msg = msg;
	}

	public AjaxObj(int result, Object obj) {
		super();
		this.result = result;
		this.obj = obj;
	}
	
	public AjaxObj() {
		this.result =1;
	}
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
