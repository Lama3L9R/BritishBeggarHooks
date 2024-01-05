package icu.lama.ukbeggar.hooks;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookUtils {
    private static ClassLoader loader;

    public interface CallbackOnEnter {
        void onEnter(XC_MethodHook.MethodHookParam param, Object... args) throws Throwable;
    }

    public interface CallbackOnLeave {
        void onLeave(XC_MethodHook.MethodHookParam param, Object... args) throws Throwable;
    }

    public static void setup(ClassLoader cl) {
        loader = cl;
    }

    public static void onEnter(String target, CallbackOnEnter callback, Object... argsType) {
        String[] split = target.split("#");
        String pkg = split[0];
        String member = split[1];

        Object[] args = new Object[argsType.length + 1];
        Object hook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                System.arraycopy(param.args, 0, args, 0, argsType.length);
                args[argsType.length] = param;
                callback.onEnter(param, args);
            }
        };

        XposedHelpers.findAndHookMethod(pkg, loader, member, vargs(argsType, hook));
    }

    public static void onLeave(String target, CallbackOnLeave callback, Object... argsType) {
        String[] split = target.split("#");
        String pkg = split[0];
        String member = split[1];

        Object[] args = new Object[argsType.length + 1];
        Object hook = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                System.arraycopy(param.args, 0, args, 0, argsType.length);
                args[argsType.length] = param;
                callback.onLeave(param, args);
            }
        };

        XposedHelpers.findAndHookMethod(pkg, loader, member, vargs(argsType, hook));
    }

    private static <T> T[] vargs(T[] args, T... appending) {
        Object[] result = new Object[args.length + appending.length];

        System.arraycopy(args, 0, result, 0, args.length);
        System.arraycopy(appending, 0, result, args.length, appending.length);

        return (T[]) result;
    }
}
