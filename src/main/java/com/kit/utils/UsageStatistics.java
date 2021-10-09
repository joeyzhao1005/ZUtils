package com.kit.utils;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

public class UsageStatistics {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String[] getForegroundApp(Context context) {
        String packageNameByUsageStats = null;
        String classByUsageStats = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usageStatsManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            }
            if (usageStatsManager == null) {
                return null;
            }
            final long INTERVAL = 10000;
            final long end = System.currentTimeMillis();
            final long begin = end - INTERVAL;
            final UsageEvents usageEvents = usageStatsManager.queryEvents(begin, end);
            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                usageEvents.getNextEvent(event);
                if (event.getPackageName() == null || event.getClassName() == null) {
                    continue;
                }
                packageNameByUsageStats = event.getPackageName();
                classByUsageStats = event.getClassName();
                break;
            }
        }
        return new String[]{packageNameByUsageStats, classByUsageStats};
    }
}
