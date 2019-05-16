package basic.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 ** 通过反射获取ArrayList的capacityֵ
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
		Class<? extends ArrayList> arrayListClass = arrayList.getClass();
        try {
            Field field = arrayListClass.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] objects = (Object[])field.get(arrayList);
            return objects.length;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

