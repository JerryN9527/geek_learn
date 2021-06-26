package com.nj.learn.jvm;

import java.io.*;
import java.lang.reflect.Method;

/**
 * 作业1：
 *  自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class
 *  文件所有字节（x=255-x）处理后的文件
 */

public class XlassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception {
        //获取加密后的对象实例
        Class<?> helloClass = new XlassLoader().findClass("Hello");
        Object obj = helloClass.newInstance();
        //执行对象方法
        Method method = helloClass.getMethod("hello");
        method.invoke(obj);
    }

    /**
     * 重写获取class方法
     * @param name
     * @return
     */
    @Override
    protected Class<?> findClass(String name)  {
        File file = new File("src\\main\\resources\\jvm\\Hello.xlass");
        byte[] bytes = fileToBytes(file);
        return defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * 文件转换成bytes字节组
     * @param file
     * @return
     */
    private byte[] fileToBytes(File file) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream input;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            int read;
            while ( ( read =input.read() ) != -1 ){
                out.write(255 - read);
            }
            out.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
