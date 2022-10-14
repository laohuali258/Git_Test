package com.hqf.compus.common;

public class BaseContext {

    private static ThreadLocal<Long> thread = new ThreadLocal<>();


    /**
     * 设置当前id值
     * @param id
     */
    public static void setCurrentId(Long id){


        
        thread.set(id);

    }

    /**
     * 获取当前id值
     * @return
     */
    public static Long getCurrentId(){

       return thread.get();
    }
}
