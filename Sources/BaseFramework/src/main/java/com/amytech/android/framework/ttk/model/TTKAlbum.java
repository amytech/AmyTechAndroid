package com.amytech.android.framework.ttk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年8月3日 上午11:27:19 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年8月3日 <br>
 *
 * @author marktlzhai
 */
public class TTKAlbum {
	public int aid = 0;
	public List<TTKPicture> pics;
	public String albumname = "";

	public TTKAlbum() {
		super();
	}

	public TTKAlbum(int aid, String albumname) {
		super();
		this.aid = aid;
		this.pics = new ArrayList<TTKPicture>();
		this.albumname = albumname;
	}

	public void addPic(TTKPicture pic) {
		this.pics.add(pic);
	}

	@Override
	public String toString() {
		return aid + "[" + albumname + "]";
	}
}
