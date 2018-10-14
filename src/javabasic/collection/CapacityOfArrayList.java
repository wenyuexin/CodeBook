package javabasic.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 ** 获取ArrayList的capacityֵ
 */
public class CapacityOfArrayList {
	public static void main(String[] args) {
		ArrayList<Integer> arrayList = new ArrayList<>();
        System.out.println(getArrayListCapacity(arrayList));
 
        arrayList.add(0);
        System.out.println(getArrayListCapacity(arrayList));
 
        for(int i = 0; i < 10; ++i)
            arrayList.add(0);
        System.out.println(getArrayListCapacity(arrayList));
 
        for(int i = 0; i < 5; ++i)
            arrayList.add(0);
        System.out.println(getArrayListCapacity(arrayList));
    }
 
    public static int getArrayListCapacity(ArrayList<?> arrayList) {
        @SuppressWarnings("rawtypes")
		Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            Field field = arrayListClass.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] objects = (Object[])field.get(arrayList);
           return objects.length;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    /*
     * size
     */
    public static int getCapacity(ArrayList<?> arrayList) {
    	Object[] a = arrayList.toArray();
    	return a.length;
    }
}

