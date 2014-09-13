package test;

import java.util.ArrayList;
import java.util.List;

public class TestC {
	static List<Integer> roleIds = new ArrayList<Integer>();
	public TestC() {
		roleIds.add(new Integer(1));
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println((Integer[])roleIds.toArray());
	}

}
