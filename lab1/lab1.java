package com.company;
import java.util.*;
public class Lab1 {
    public static void main(String[] args) {
        List<String> llist = new LinkedList<>();
        llist.add("list1");
        llist.add("list2");
        llist.add("list3");
        System.out.println(llist);
        List<String> alist = new ArrayList<>();
        alist.add("list1");
        alist.add("list2");
        alist.add("list3");
        System.out.println(alist);
        Map<String, String> hMap = new HashMap<>();
        hMap.put("int601", "ECP");
        hMap.put("int602", "DATA");
        hMap.put("int603", "DB");
        System.out.println(hMap);
        Map<String, String> tMap = new TreeMap<>();
        tMap.put("int601", "ECP");
        tMap.put("int602", "DATA");
        tMap.put("int603", "DB");
        System.out.println(tMap);
        Set<String> set = new HashSet<>();
        set.add("int601");
        set.add("int602");
        set.add("int602");
        set.add("int603");
        set.add("int603");
        System.out.println(set);
    }
}
