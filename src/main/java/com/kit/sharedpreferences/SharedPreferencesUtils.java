package com.kit.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.kit.utils.GsonUtils;
import com.kit.utils.log.Zog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 存储配置信息的工具类 <br>
 * 注：可读取的数据类型有-<code>boolean、int、float、long、String.</code>
 */
public class SharedPreferencesUtils {
    // private final String MAK = "innoview";
//    public int MODE = Context.MODE_WORLD_READABLE
//            + Context.MODE_WORLD_WRITEABLE;
    public SharedPreferences sharedpreferences;


    public SharedPreferencesUtils(Context context, String fileName, int mode) {
        sharedpreferences = context.getSharedPreferences(fileName, mode);
    }


    public SharedPreferencesUtils(Context context, String fileName) {
        sharedpreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public boolean saveSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            editor.putString(key, value);
        } catch (Exception e) {
            editor.putString(key, value);
            e.printStackTrace();
        }
        return editor.commit();
    }

    public String loadStringSharedPreference(String key, String stringValue) {
        String str = null;
        try {
            str = sharedpreferences.getString(key, stringValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public String loadStringSharedPreference(String key) {
        String str = null;
        try {
            str = sharedpreferences.getString(key, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public boolean saveSharedPreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int loadIntSharedPreference(String key, int intValue) {
        return sharedpreferences.getInt(key, intValue);
    }

    public int loadIntSharedPreference(String key) {
        return sharedpreferences.getInt(key, 0);
    }


    public boolean saveSharedPreferences(String key, long value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public boolean saveSharedPreferences(String key, float value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }


    public float loadFloatSharedPreference(String key, Float floatValue) {
        return sharedpreferences.getFloat(key, floatValue);
    }

    public float loadFloatSharedPreference(String key) {
        return sharedpreferences.getFloat(key, 0f);
    }

    public boolean saveSharedPreferences(String key, Long value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public long loadLongSharedPreference(String key, long longValue) {
        return sharedpreferences.getLong(key, longValue);
    }


    public float loadLongSharedPreference(String key, float longValue) {
        return sharedpreferences.getFloat(key, longValue);
    }

    public long loadLongSharedPreference(String key) {
        return sharedpreferences.getLong(key, 0l);
    }

    public boolean saveSharedPreferences(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean loadBooleanSharedPreference(String key, boolean booleanValue) {
        return sharedpreferences.getBoolean(key, booleanValue);
    }

    public boolean loadBooleanSharedPreference(String key) {
        return sharedpreferences.getBoolean(key, false);
    }

    public boolean saveAllSharePreference(String keyName, List<?> list) {
        int size = list.size();
        if (size < 1) {
            return false;
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (list.get(0) instanceof String) {
            for (int i = 0; i < size; i++) {
                editor.putString(keyName + i, (String) list.get(i));
            }
        } else if (list.get(0) instanceof Long) {
            for (int i = 0; i < size; i++) {
                editor.putLong(keyName + i, (Long) list.get(i));
            }
        } else if (list.get(0) instanceof Float) {
            for (int i = 0; i < size; i++) {
                editor.putFloat(keyName + i, (Float) list.get(i));
            }
        } else if (list.get(0) instanceof Integer) {
            for (int i = 0; i < size; i++) {
                editor.putLong(keyName + i, (Integer) list.get(i));
            }
        } else if (list.get(0) instanceof Boolean) {
            for (int i = 0; i < size; i++) {
                editor.putBoolean(keyName + i, (Boolean) list.get(i));
            }
        }
        return editor.commit();
    }

    public Map<String, ?> loadAllSharePreference(String key) {
        return sharedpreferences.getAll();
    }

    public boolean removeKey(String key) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public boolean removeAllKey() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        return editor.commit();
    }

    /**
     * 判断是否存在key
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return sharedpreferences.contains(key);
    }


    /**
     * 清空
     *
     * @return
     */
    public void clear() {
        sharedpreferences.edit().clear().commit();
    }

    /**
     * 使用SharedPreferences保存对象类型的数据 先将对象类型转化为二进制数据，然后用特定的字符集编码成字符串进行保存
     *
     * @param object     要保存的对象
     * @param context
     * @param shaPreName 保存的文件名
     * @param saveLength 保持有多少个object
     */
    public static void saveObject(Context context, String shaPreName, String saveTag, Object object,
                                  int saveLength) {


        Zog.i( "saveObject!!!!!!!");

        if (saveLength <= 0)
            saveLength = Integer.MAX_VALUE;

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                shaPreName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<Object> list = getObject(context, shaPreName, saveTag);

        if (null == list) {
            list = new ArrayList<Object>();
        }
        list.add(object);

        Collections.reverse(list);

        List<Object> listLimited = new ArrayList<Object>();

//        for (int i = 0; i < list.size() && i < saveLength; i++) {
//            Object o = list.get(i);
//
//            String js = GsonUtils.toJson(o);
//
//            listLimited.add(js);
//        }


        Iterator<Object> iter = list.iterator();
        for (int i = 0; i < list.size() && i < saveLength; i++) {
            Object obj = iter.next();
            String js = GsonUtils.toJson(obj);
            listLimited.add(js);
        }

        Collections.reverse(listLimited);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(listLimited);
            String strList = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            editor.putString(saveTag, strList);
            editor.commit();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 根据文件名取得存储的数据对象 先将取得的数据转化成二进制数组，然后转化成对象
     *
     * @param context
     * @param shaPreName 读取数据的文件名
     * @return
     */
    public static List<Object> getObject(Context context,
                                         String shaPreName, String saveTag) {

        List<Object> list;

        SharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = context.getSharedPreferences(
                    shaPreName, Context.MODE_PRIVATE);
        } catch (Exception e) {
            Zog.showException(e);
            return null;
        }

        String message = sharedPreferences.getString(saveTag, "");
        byte[] buffer = Base64.decode(message.getBytes(), Base64.DEFAULT);

        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);


        Zog.i( isExist(context, shaPreName) + "");
        if (!isExist(context, shaPreName)) {
            newSP(context, shaPreName);
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            list = (List<Object>) ois.readObject();
            ois.close();
            return list;
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {

            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    /**
     * 使用SharedPreferences保存对象类型的数据 先将对象类型转化为二进制数据，然后用特定的字符集编码成字符串进行保存
     *
     * @param context
     * @param shaPreName 保存的文件名
     */
    public static void newSP(Context context, String shaPreName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                shaPreName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();
    }


    /**
     * 根据文件名取得存储的数据对象 先将取得的数据转化成二进制数组，然后转化成对象
     *
     * @param context
     * @param shaPreName 读取数据的文件名
     * @return
     */
    public static boolean isExist(Context context,
                                  String shaPreName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                shaPreName, Context.MODE_PRIVATE);
        if (sharedPreferences.getAll().isEmpty()) {
            return false;
        }

        return true;
    }
}