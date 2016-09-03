package com.kit.utils.communication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.kit.utils.ListUtils;
import com.kit.utils.ResWrapper;

import java.util.ArrayList;

/**
 * Created by Zhao on 16/8/15.
 */

public class SMSUtils {


    public static ArrayList<SMSInfo> getSmsByKeyWord(String keyword) {
        Uri SMS_INBOX = Uri.parse("content://sms/");
        ContentResolver cr = ResWrapper.getInstance().getContext().getContentResolver();
        String[] projection = new String[]{"_id", "person", "address", "date", "body"};
        String[] selectionArgs = new String[]{"%" + keyword + "%"};


//        String[] projection = new String[]{"body"};//"_id", "address", "person",, "date", "type
        String where = " body like ?";
        Cursor cur = cr.query(SMS_INBOX, projection, where, selectionArgs, "date desc");
        if (null == cur)
            return null;

        ArrayList<SMSInfo> smsInfos = new ArrayList<SMSInfo>();
        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToPosition(i);
            String address = cur.getString(cur.getColumnIndex("address"));//手机号
            String person = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));
            long date = cur.getLong(cur.getColumnIndex("date"));


            SMSInfo info = new SMSInfo();
            info.setAddress(address);
            info.setPerson(person);
            info.setBody(body);
            info.setDate(date);

            smsInfos.add(info);
        }

        cur.close();

        if (ListUtils.isNullOrContainEmpty(smsInfos)) {
            return null;
        } else
            return smsInfos;
    }

    public static ArrayList<SMSInfo> getSmsByPhone() {
        Uri SMS_INBOX = Uri.parse("content://sms/");

        ContentResolver cr = ResWrapper.getInstance().getContext().getContentResolver();

        String[] projection = new String[]{"_id", "person", "address", "date", "body"};
        String where = " address = '1066321332' AND date >  "
                + (System.currentTimeMillis() - 10 * 60 * 1000);
        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
        if (null == cur)
            return null;
        ArrayList<SMSInfo> smsInfos = new ArrayList<SMSInfo>();
        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToPosition(i);
            String address = cur.getString(cur.getColumnIndex("address"));//手机号
            String person = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));
            long date = cur.getLong(cur.getColumnIndex("date"));


            SMSInfo info = new SMSInfo();
            info.setAddress(address);
            info.setPerson(person);
            info.setBody(body);
            info.setDate(date);

            smsInfos.add(info);

        }
        cur.close();

        if (ListUtils.isNullOrContainEmpty(smsInfos)) {
            return null;
        } else
            return smsInfos;
    }

}
