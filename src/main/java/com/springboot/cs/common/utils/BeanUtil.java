package com.springboot.cs.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanUtil {

    public static <T> T copy(Object source,Class<T> clazz)
    {
        if(source == null){
            return null;
        }
        try {
            T target = clazz.newInstance();
            copy(source,target);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static List<Field> getAllField(Class<?> clazz){

        List<Field> liField = new ArrayList<Field>();
        while(true){
            Field[] fields = clazz.getDeclaredFields();
            for(Field field :fields){
                liField.add(field);
            }
            clazz = clazz.getSuperclass();
            if(clazz == null){
                break;
            }
        }
        return liField;
    }


    public static Field getField(Class<?> clazz,String name){
        Field field = null;
        while(true){
            try {
                field = clazz.getDeclaredField(name);
                break;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
            if(clazz == null){
                break;
            }
        }

        return field;
    }


    public static void copy(Object source,Object target){
        if(source == null || target == null) return;

        Class<?> clazz = target.getClass();
        Class<?> sourceClazz = source.getClass();

        List<Field> liFiled = getAllField(clazz);
        for(Field field : liFiled){
            Field srouceField = getField(sourceClazz,field.getName());
            if(srouceField == null){
                continue;
            }
            field.setAccessible(true);
            srouceField.setAccessible(true);
            try {
                field.set(target, srouceField.get(source));
            } catch (Exception e) {
            }
        }
    }

    public static <T> List<T> copy(List source,Class<T> clazz){
        List<T> result = new ArrayList<T>();
        if(source == null || source.isEmpty()){
            return result;
        }
        for(Object o : source){
            T e = copy(o,clazz);
            result.add(e);
        }

        return result;
    }
}
