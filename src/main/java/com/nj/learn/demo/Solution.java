package com.nj.learn.demo;

import java.util.Arrays;
import java.util.List;

public class Solution {


    public static void main(String[] args) {
//        ListNode node =new ListNode(0);
        String a = "asdasd111";
        String b = "12316161";
//        System.out.println(a.hashCode() % 3);
//        System.out.println(b.hashCode() % 3);
        List<String> ids = Arrays.asList("q321231","asda3312",a,b,"1111","q321231","asda3312",a,b,"9999999999");
        ids.stream().map(str->str.hashCode() % 3).forEach(System.out::println);
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode hair = new ListNode(0);
        hair.next = head;
        ListNode pre = hair;

        while (head != null) {
            ListNode tail = pre;
            // 查看剩余部分长度是否大于等于 k
            for (int i = 0; i < k; ++i) {
                tail = tail.next;
                if (tail == null) {
                    return hair.next;
                }
            }
            ListNode nex = tail.next;
            ListNode[] reverse = myReverse(head, tail);
            head = reverse[0];
            tail = reverse[1];
            // 把子链表重新接回原链表
            pre.next = head;
            tail.next = nex;
            pre = tail;
            head = tail.next;
        }

        return hair.next;
    }

    public ListNode[] myReverse(ListNode head, ListNode tail) {
        ListNode prev = tail.next;
        ListNode p = head;
        while (prev != tail) {
            ListNode nex = p.next;
            p.next = prev;
            prev = p;
            p = nex;
        }
        return new ListNode[]{tail, head};
    }
}

class ListNode {
    public ListNode next;
    public int sum ;
    public ListNode(int sum){
        this.sum = sum;
    }
}
