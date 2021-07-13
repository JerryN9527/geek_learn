package com.nj.learn.demo.jvm.day02_classload;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 * 三类加载器：
 * 1. 启动类加载器（BootstrapClassLoader）
 * 2. 扩展类加载器（ExtClassLoader）
 * 3. 应用类加载器（AppClassLoader）
 *
 * 加载器特点：
 * 1. 双亲委托
 * 2. 负责依赖
 * 3. 缓存加载
 */
public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) throws Exception {
//        Hello hello=new Hello();
        Class<?> hello = new HelloClassLoader().findClass("Hello");
        Object obj = hello.newInstance();
        Method method = hello.getDeclaredMethod("hello");
        Object invoke = method.invoke(obj);
        System.out.println(invoke);
    }

    @Override
    protected Class<?> findClass(String name) {

        String a = "new Date()";
        String helloBase64="yv66vgAAADQAFAoABgAPCgAFABAHABEKAAMADwcAEgcAEwEABjxpbml0PgEAAygpVgEABENvZGUB" +
                "AA9MaW5lTnVtYmVyVGFibGUBAAVoZWxsbwEAEigpTGphdmEvdXRpbC9EYXRlOwEAClNvdXJjZUZp" +
                "bGUBAApIZWxsby5qYXZhDAAHAAgMAAsADAEADmphdmEvdXRpbC9EYXRlAQAFSGVsbG8BABBqYXZh" +
                "L2xhbmcvT2JqZWN0ACEABQAGAAAAAAACAAEABwAIAAEACQAAACoAAQABAAAACiq3AAEqtgACV7EA" +
                "AAABAAoAAAAOAAMAAAAGAAQABwAJAAgAAQALAAwAAQAJAAAAIAACAAEAAAAIuwADWbcABLAAAAAB" +
                "AAoAAAAGAAEAAAALAAEADQAAAAIADg==" ;
        byte [] bytes= decode(helloBase64);
//        try {
////            BufferedOutputStream ou = new BufferedOutputStream(new FileOutputStream(new File("E:\\nj\\geek_learn\\src\\main\\java\\com\\nj\\learn\\demo\\jvm\\day02_classload\\Hello.class")));
////            ou.write(bytes);
////            ou.flush();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        return defineClass(name,bytes,0,bytes.length);
    }

    private byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
