package com.amytech.android.framework.ttk;

/**
 * Title: AmyImageLibrary <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月31日 下午3:24:00 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月31日 <br>
 *
 * @author marktlzhai
 */
public class UploadResponse {
	public int width = 0;
	public int height = 0;
	public String type = "jpg";
	public long size = 0;
	// 原图地址
	public String imageURL = "";
	// 缩略图地址(小)
	public String s_imageURL = "";
	// 缩略图地址(大)
	public String t_imageURL = "";

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("width = " + width + "\n");
		sb.append("height = " + height + "\n");
		sb.append("type = " + type + "\n");
		sb.append("size = " + size + "\n");
		sb.append("imageURL = " + imageURL + "\n");
		sb.append("s_imageURL = " + s_imageURL + "\n");
		sb.append("t_imageURL = " + t_imageURL + "\n");

		return sb.toString();
	}
}
