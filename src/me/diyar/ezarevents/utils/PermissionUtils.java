package me.diyar.ezarevents.utils;

import me.diyar.ezarevents.Main;

public class PermissionUtils {
    public static String adminpermission = Main.getInstance().getConfig().getString("ADMIN-PERMISSION");
    public static String joinpermission = Main.getInstance().getConfig().getString("JOIN-PERMISSION");
    public static String specpermission = Main.getInstance().getConfig().getString("SPEC-PERMISSION");
    public static String hostpermission = Main.getInstance().getConfig().getString("HOST-PERMISSION");
}
