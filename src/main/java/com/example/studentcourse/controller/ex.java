//package com.example.studentcourse.controller;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class ex {
//
//    public int searchFor(int i, int j, int c, int[] nums) {
//        for (int k = 0; k < nums.length; k++) {
//            if (c + nums[k] == 0 && k != i && k != j) {
//
//                return nums[k];
//            }
//        }
//        return 0;
//    }
//
//    public List<List<Integer>> threeSum(int[] nums) {
//        List<List<Integer>> listOfList = new ArrayList<>();
//
//        for (int i = 0; i < nums.length; i++) {
//            int a = nums[i];
//
//            int j = 0, c = 0, b = 0;
//            for (; j < nums.length; j++) {
//                if (i == j) continue;
//                b = nums[j];
//                c = a + b;
//
//
//                int ans = searchFor(i, j, c, nums);
//
//                if (ans != 0) {
//
//                    List<Integer> newOne = new ArrayList<>(Arrays.asList(a, b, ans)).stream().sorted().collect(Collectors.toList());
//
//
//                    if (listOfList.stream().noneMatch(x -> {
//                        List<Integer> sortedX = new ArrayList<>(x);
//                        Collections.sort(sortedX);
//                        return sortedX.equals(newOne);
//                    })) {
//                        listOfList.add(newOne);
//                    }
//
//                }
//            }
//
//        }
//        return listOfList;
//    }
//        public static void main (String[]args){
//            ex obj = new ex();
//
//            // ---- Testcase 1 ----
//            int[] nums1 = {-1, 0, 1, 2, -1, -4};
//            System.out.println("Input: " + Arrays.toString(nums1));
//            System.out.println("Output: " + obj.threeSum(nums1));
//
//        /* ---- Testcase 2 ----
//        int[] nums2 = {0, 1, 1};
//        System.out.println("\nInput: " + Arrays.toString(nums2));
//        System.out.println("Output: " + obj.threeSum(nums2));
//
//        // ---- Testcase 3 ----
//        int[] nums3 = {0, 0, 0};
//        System.out.println("\nInput: " + Arrays.toString(nums3));
//        System.out.println("Output: " + obj.threeSum(nums3));
//
//         */
//        }
//    }
//}
