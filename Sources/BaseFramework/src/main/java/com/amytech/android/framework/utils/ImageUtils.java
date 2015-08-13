package com.amytech.android.framework.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月8日 下午3:25:27 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月8日 <br>
 *
 * @author marktlzhai
 */
public class ImageUtils {

    public static void displayCenterInside(Context context, String url, ImageView view, int widthRes, int heightRes) {
        Picasso.with(context)
                .load(url)
                .placeholder(new ColorDrawable(Color.TRANSPARENT))
                .error(new ColorDrawable(Color.TRANSPARENT))
                .resizeDimen(widthRes, heightRes)
                .centerInside()
                .tag(context)
                .into(view);
    }
}
