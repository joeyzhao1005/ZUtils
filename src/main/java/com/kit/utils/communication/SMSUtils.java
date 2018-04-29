package com.kit.utils.communication;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;

import com.kit.utils.ListUtils;
import com.kit.utils.ResWrapper;
import com.kit.utils.StringUtils;
import com.kit.utils.contact.ContactInfo;
import com.kit.utils.log.Zog;

import java.util.ArrayList;

/**
 * Created by Zhao on 16/8/15.
 */

public class SMSUtils {
    public static ArrayList<SMSInfo> getAllSMS(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";

        ArrayList<SMSInfo> smsInfos = new ArrayList<SMSInfo>();

        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, projection, null, null, "date desc");


            if (cur.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                long date;
                String type;

                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");

                do {
                    name = cur.getString(nameColumn);
                    phoneNumber = cur.getString(phoneNumberColumn);
                    smsbody = cur.getString(smsbodyColumn);
                    if (smsbody == null) {
                        smsbody = "";
                    }

                    date = cur.getLong(dateColumn);

                    int typeId = cur.getInt(typeColumn);
                    if (typeId == 1) {
                        type = "接收";
                    } else if (typeId == 2) {
                        type = "发送";
                    } else {
                        type = "";
                    }

                    if (StringUtils.isEmptyOrNullStr(name) && !StringUtils.isEmptyOrNullStr(phoneNumber)) {
                        ContactInfo contactInfo = getContactByAddress(context, phoneNumber);
                        if (contactInfo != null && !StringUtils.isEmptyOrNullStr(contactInfo.getDisplayName())) {
                            name = contactInfo.getDisplayName();
                        }
                    }

                    SMSInfo info = new SMSInfo();
                    info.setAddress(phoneNumber);
                    info.setPerson(name);
                    info.setBody(smsbody);
                    info.setDate(date);

                    smsInfos.add(info);
                } while (cur.moveToNext());
            }

        } catch (SQLiteException ex) {
            Log.d("SQLiteException", ex.getMessage());
        }
        return smsInfos;
    }



    public static ArrayList<SMSInfo> getSmsByKeyWord(String keyword) {
        Uri SMS_INBOX = Uri.parse("content://sms/");
        ContentResolver cr = ResWrapper.getInstance().getApplicationContext().getContentResolver();
        String[] projection = new String[]{"_id", "person", "address", "date", "body"};
        String[] selectionArgs = new String[]{"%" + keyword + "%"};


//        String[] projection = new String[]{"body"};//"_id", "address", "person",, "date", "type
        String where = " body like ?";
        Cursor cur = cr.query(SMS_INBOX, projection, where, selectionArgs, "date desc");
        if (null == cur) {
            return null;
        }

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
        } else {
            return smsInfos;
        }
    }

    public static ArrayList<SMSInfo> getSmsByPhone() {
        Uri SMS_INBOX = Uri.parse("content://sms/");

        ContentResolver cr = ResWrapper.getInstance().getApplicationContext().getContentResolver();

        String[] projection = new String[]{"_id", "person", "address", "date", "body"};
        String where = " address = '1066321332' AND date >  "
                + (System.currentTimeMillis() - 10 * 60 * 1000);
        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
        if (null == cur) {
            return null;
        }
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
        } else {
            return smsInfos;
        }
    }


    public static void mkMsm(Context context, String strPhone) {

        Uri smsToUri = Uri.parse("smsto:" + strPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Zog.showException(e);
        }

    }

    public static void mkMsm(Context context, String strPhone, String strSms) {

        // 第一种方法,有一个界面让你选择是否发送
        Uri uri = Uri.parse("smsto:" + strPhone);
        Intent intent = new Intent();
        intent.putExtra("sms_body", strSms);// 设置短信内容

        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Zog.showException(e);
        }
        // // 第二种方法,直接就发送过去了
        // SmsManager smsManager = SmsManager.getDefault();
        // PendingIntent pendingIntent = PendingIntent.getBroadcast(
        // MainActivity.this, 0, new Intent(), 0);
        // smsManager.sendTextMessage(strPhone, null, strSms,
        // pendingIntent, null);
    }

    public static int findNewSmsCount(Context ctx) {
        Cursor csr = null;
        try {
            csr = ctx
                    .getApplicationContext()
                    .getContentResolver()
                    .query(Uri.parse("content://sms"), null,
                            "type = 1 and read = 0", null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            csr.close();
        }
        return csr.getCount(); // 未读短信数目

    }

    public static int findNewMmsCount(Context ctx) {
        Cursor csr = null;
        try {
            csr = ctx
                    .getApplicationContext()
                    .getContentResolver()
                    .query(Uri.parse("content://mms/inbox"), null, "read = 0",
                            null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            csr.close();
        }
        return csr.getCount();// 未读彩信数目
    }


    private static ContactInfo getContactByAddress(Context context, final String address) {
        Uri personUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
        Cursor cur = context.getContentResolver().query(personUri,
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},
                null, null, null);
        if (cur.moveToFirst()) {
            int nameIdx = cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            ContactInfo item = new ContactInfo();
            item.setDisplayName(cur.getString(nameIdx));
            cur.close();
            return item;
        }
        return null;
    }

    public static void openSMS(Context context, String phoneNum) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("vnd.android-dir/mms-sms");
//        intent.setData(Uri.parse("content://mms-sms/" + phoneNum + "/"));
        intent.putExtra("address", phoneNum);
        context.startActivity(intent);
    }

    public static void sendSMS(Context context, Intent intent,
                               String phonenumber, String msg) {// 发送短信的类
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phonenumber, null, msg, pi, null);// 发送信息到指定号码
    }
}
