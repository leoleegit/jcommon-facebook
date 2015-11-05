package org.jcommon.com.facebook.test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.utils.ReflectUtils;

public class ReflectUtilsTest {
	private  List<Comment> data;
	
	private static String toString(ParameterizedType parameterizedType) {
		Type[] types = parameterizedType.getActualTypeArguments();
		String ret = "\n\t" + parameterizedType + "\n\t\t泛型个数：" + types.length + "==>";
		for (Type type : types) {
			ret += type + ", ";
		}
		return ret;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReflectUtilsTest test = new ReflectUtilsTest();
		for (Class<?> clazz = test.getClass(); 
			      clazz != Object.class; clazz = clazz.getSuperclass()) {
	        Field[] fs = clazz.getDeclaredFields();
	        Type type = null;
	        for (Field f : fs) {
	        	type = clazz.getGenericSuperclass();
	        	System.out.println(f.getName() + ": "+ toString((ParameterizedType)f.getGenericType()));
	        }     
		}
	}

}
