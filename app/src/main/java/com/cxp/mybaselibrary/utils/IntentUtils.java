package com.cxp.mybaselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class IntentUtils {
    public static void startActivity(Context ctx, Class cls) {
        ctx.startActivity(new Intent(ctx, cls));
    }

    public static void startActivityAndFinish(Activity ctx, Class cls) {
        ctx.startActivity(new Intent(ctx, cls));
        ctx.finish();
    }
}
