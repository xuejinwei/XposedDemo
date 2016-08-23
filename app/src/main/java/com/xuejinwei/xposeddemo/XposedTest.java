package com.xuejinwei.xposeddemo;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

/**
 * Created by xuejinwei on 16/8/23.
 * Email:xuejinwei@outlook.com
 */
public class XposedTest implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        String packageName = lpparam.packageName;
        XposedBridge.log("XXX-->" + packageName);
        if (!(packageName.contains("com.tencen") && packageName.contains("mm")))
            return;

        try {
            Object activityThread = callStaticMethod(findClass("android.app.ActivityThread", null), "currentActivityThread");
            Context mContext = (Context) callMethod(activityThread, "getSystemContext");

            try {
                String versionName = mContext.getPackageManager().getPackageInfo(packageName,
                        0).versionName;
                XposedBridge.log("XXX--log" + versionName + "||" + mContext.getPackageManager().getPackageInfo(packageName,
                        0).versionCode + "wechat____log_____xuejinwei");

            } catch (Throwable e) {
                XposedBridge.log(e);
                XposedBridge.log("xuejinweilog");
            }
        } catch (Throwable e) {
            XposedBridge.log(e);
            XposedBridge.log("xuejinweilog");
        }

//        if (!lpparam.packageName.equals("com.android.systemui"))
//            return;
//
//        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                TextView tv = (TextView) param.thisObject;
//                String text = tv.getText().toString();
//                tv.setText(text + " :)");
//                tv.setTextColor(Color.RED);
//            }
//        });
    }
}
