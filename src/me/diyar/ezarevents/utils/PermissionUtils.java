package me.diyar.ezarevents.utils;

import me.diyar.ezarevents.Main;

public class PermissionUtils {

    public static String adminpermission = Main.getInstance().getConfig().getString("admin-permission");
    public static String hostpermission = Main.getInstance().getConfig().getString("host-permission");
}
