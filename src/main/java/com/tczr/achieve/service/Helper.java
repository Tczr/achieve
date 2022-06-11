package com.tczr.achieve.service;

import com.tczr.achieve.shared.RegularProcedure;
import com.tczr.achieve.task.Task;
import com.tczr.achieve.user.User;

import java.util.List;

public class Helper {

    public static void quickSort(List list)
    {
        quickSort(list, 0, list.size()-1);
    }
    private static void quickSort(List<RegularProcedure> list, int start, int end)
    {
        if(start<end)
        {
            int p = partition(list, start, end);
            quickSort(list, start, p);
            quickSort(list, p+1, end);
        }
    }

    private static int partition(List<RegularProcedure> list, int  start, int end)
    {
        int lp = start-1, rp=end+1;
        int pivot = start+(end-start)/2;

        while(true)
        {
            do{
                lp++;
            }while (list.get(lp).getId()<list.get(pivot).getId());

            do{
                rp--;
            }while (list.get(rp).getId()>list.get(pivot).getId());
            if(lp>=rp) return rp;

            swap(list, lp, rp);
        }
    }

    private static void swap(List list, int index1, int index2)
    {
        Object temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }
}