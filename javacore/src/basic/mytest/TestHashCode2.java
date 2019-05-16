package basic.mytest;
import java.util.ArrayList;

public class TestHashCode2
{
	public static void main(String[] args)
	{
		ArrayList<Object> list = new ArrayList<Object>();
		int numberExist = 0;

		//֤��hashcode��ֵ�����ڴ��ַ
		for (int i = 0; i < 10000; i++)
		{
			Object obj = new Object();
			if (list.contains(obj.toString()))
			{
				System.out.println(obj.toString() + "  exists in the list. " + i);
				numberExist++;
			} else
			{
				list.add(obj.toString());
			}
		}

		System.out.println("repetition number:" + numberExist);
		System.out.println("list size:" + list.size());

		//֤���ڴ��ַ�ǲ�ͬ�ġ�
		numberExist = 0;
		list.clear();
		for (int i = 0; i < 10000; i++)
		{
			Object obj = new Object();
			if (list.contains(obj))
			{
				System.out.println(obj + "  exists in the list. " + i);
				numberExist++;
			} else
			{
				list.add(obj);
			}
		}

		System.out.println("repetition number:" + numberExist);
		System.out.println("list size:" + list.size());
	}
}