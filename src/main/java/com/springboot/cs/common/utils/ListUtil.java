package com.springboot.cs.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListUtil {

    /**
     * 判断List不为空,非空返回true,空则返回false
     *
     * @param list
     * @return boolean
     */
    public static boolean isNotNull(List<?> list) {

        if (null != list) {
            if ((list.size() > 0) && !list.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断List是为空,为空返回true,非空则返回false
     *
     * @param list
     * @return boolean
     */
    public static boolean isNull(List<?> list) {

        return null == list || list.size() == 0 || list.isEmpty();
    }

    /**
     *
     * @Title: removeDuplist
     * @Description: 去除集合中重复的内容
     * @param list
     * @return
     * @throws
     */
    public  static  List<String> removeDuplist(List<String> list){
        if(list != null && list.size() > 0){
            HashSet<String> hashSet = new HashSet<String>(list);
            list.clear();
            list.addAll(hashSet);
        }
        return list;
    }

    /**
     *
     * @Title: removeDuplistInt
     * @Description: 去除重复的值
     * @param list
     * @return
     * @throws
     */
    public  static  List<Integer> removeDuplistInt(List<Integer> list){
        if(list != null && list.size() > 0){
            HashSet<Integer> hashSet = new HashSet<Integer>(list);
            list.clear();
            list.addAll(hashSet);
        }
        return list;
    }

    public static int toInt(Object value)
    {
        if(value == null || "".equals(value)){
            return 0;
        }
        try{
            return Integer.valueOf(value.toString());
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * @Description 转换为int数组
     * @param list
     * @param field
     * @return
     * @lastModifier
     */
    public static <T> List<Integer> toIntegerList(List<T> list,String field)
    {
        List<Integer> result = new ArrayList<Integer>();
        if(list == null || list.isEmpty()){
            return result;
        }
        String methodName = "get"+(field.charAt(0)+"").toUpperCase()+field.substring(1);
        for(T o : list){
            Class<?> clazz = o.getClass();
            try{
                Method method = clazz.getMethod(methodName);
                Object value = method.invoke(o);
                result.addAll(toIntegerList(value.toString()));
            }catch(Exception e){

            }
        }
        return result;
    }

    /**
     * @Description 转换为String数组
     * @param list
     * @param field
     * @return
     * @lastModifier
     */
    public static <T> List<String> toStringList(List<T> list,String field)
    {
        List<String> result = new ArrayList<String>();
        if(list == null || list.isEmpty()){
            return result;
        }
        String methodName = "get"+(field.charAt(0)+"").toUpperCase()+field.substring(1);
        for(T o : list){
            Class<?> clazz = o.getClass();
            try{
                Method method = clazz.getMethod(methodName);
                Object value = method.invoke(o);
                result.add(value.toString());
            }catch(Exception e){

            }
        }
        return result;
    }

    /**
     * @Description 转换为int数组
     * @param list
     * @param field
     * @return
     * @lastModifier
     */
    public static <T> List<Long> toLongList(List<T> list,String field)
    {
        List<Long> result = new ArrayList<Long>();
        if(list == null || list.isEmpty()){
            return result;
        }
        String methodName = "get"+(field.charAt(0)+"").toUpperCase()+field.substring(1);
        for(T o : list){
            Class<?> clazz = o.getClass();
            try{
                Method method = clazz.getMethod(methodName);
                Object value = method.invoke(o);
                result.addAll(toLongList(value.toString()));
            }catch(Exception e){

            }
        }
        return result;
    }

    /**
     * @Description 转换为int数组
     * @param ids
     * @return
     * @lastModifier
     */
    public static List<Integer> toIntegerList(String ids)
    {
        return toList(ids,Integer.class);
    }

    /**
     * @Description 转换为long数组
     * @param ids
     * @return
     * @lastModifier
     */
    public static List<Long> toLongList(String ids)
    {
        return toList(ids,Long.class);
    }

    /**
     * @Description 转换为数组
     * @param ids
     * @return
     * @lastModifier
     */
    public static <T> List<T> toList(String ids,Class<T> clazz)
    {
        if(ids == null){
            return new ArrayList<T>();
        }
        return toList(ids.split(","),clazz);
    }

    /**
     * @Description
     * @param ids
     * @return
     * @lastModifier
     */
    public static List<Integer> toIntegerList(String[] ids)
    {
        return ListUtil.toList(ids, Integer.class);
    }

    /**
     * @Description
     * @param ids
     * @return
     * @lastModifier
     */
    public static <T> List<T> toList(String[] ids,Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        Method method;
        try {
            method = clazz.getMethod("valueOf", String.class);
        } catch (NoSuchMethodException e) {
            return result;
        }
        try {
            for(String id : ids){
                if(id == null){
                    result.add((T)method.invoke(null, "0"));
                }
                result.add((T)method.invoke(null, id));
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * @Description 转换为int数组
     * @author zhangxh
     * @date 2016-8-12 上午10:31:04
     * @param ids
     * @return
     * @lastModifier
     */
    public static List<Integer> toIntegerList(List<String> ids)
    {
        List<Integer> result = new ArrayList<Integer>();
        for(String id : ids){
            result.add(toInt(id));
        }
        return result;
    }

    /**
     * 把数组拼接成字符串
     *
     * @param strs
     * @param split
     *            - 分隔符
     * @return
     */
    public static String join(String[] strs, String split) {
        if (strs == null || strs.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            sb.append(split + strs[i]);
        }
        return sb.toString();
    }

    /**
     * 把数组拼接成字符串
     *
     * @param split
     *            - 分隔符
     * @return
     */
    public static String join(List<String> list,String split)
    {
        return join(list.toArray(),split);
    }

    /**
     * 把数组拼接成字符串
     *
     * @param
     * @param split
     *            - 分隔符
     * @return
     */
    public static String join(Object[] arr,String split)
    {
        String result = "";
        for(int i=0;i<arr.length;i++){
            if(i!=arr.length-1){
                result+=arr[i].toString()+split;
            }else{
                result+=arr[i].toString();
            }
        }
        return result;
    }

    /**
     * 把数组拼接成字符串
     *
     *            - 分隔符
     * @return
     */
    public static String join(Object[] arr)
    {
        return join(arr,",");
    }

    /**
     * 把数组拼接成字符串
     *
     *            - 分隔符
     * @return
     */
    public static String join(List<String> list)
    {
        return join(list.toArray());
    }


    public static int size(List list)
    {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public static <T> List<T> limit(List<T> list,int start,int size)
    {
        List<T> result = new ArrayList<T>(0);

        if(list == null){
            return result;
        }
        int last = start+size;
        if(list.size() < last){
            last = list.size();
        }
        for(int i=start;i<last;i++){
            result.add(list.get(i));
        }
        return result;

    }

    public static <T> List<T> filter(List<T> list,T f)
    {
        List<T> result = new ArrayList<T>();
        if(list == null){
            return result;
        }
        for(T t : list){
            if(t.equals(f)){
                continue;
            }
            result.add(t);
        }
        return result;
    }

    /**
     * 取差集
     * @param source
     * @param list
     * @return
     */
    public static <T> List<T> differenceSet(List<T> source,List<T> list){
        List<T> result = new ArrayList<T>(0);
        for(T t : source){
            if(list.indexOf(t) >= 0){
                continue;
            }
            result.add(t);
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<String> objects = new ArrayList<>();
        System.out.println(ListUtil.isNull(objects));
    }
}
