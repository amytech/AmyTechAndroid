package com.amytech.android.framework.ttk.model;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年8月3日 上午11:43:28 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年8月3日 <br>
 *
 * @author marktlzhai
 */
public class TTKPicture {
	public int id = 0;
	public String name = "";
	public String linkURL = "";
	public String showURL = "";
	public String ext = "";
	public int width = 0;
	public int height = 0;

	public TTKPicture(int id, String linkURL) {
		super();
		this.id = id;
		this.linkURL = linkURL;
	}

	@Override
	public String toString() {
		return id + "[" + linkURL + "]";
	}
}
