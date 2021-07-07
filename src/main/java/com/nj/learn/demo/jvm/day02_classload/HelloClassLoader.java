package com.nj.learn.demo.jvm.day02_classload;

import java.io.*;
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
        Hello hello=new Hello();

        new HelloClassLoader().findClass("com.nj.jvm.day02.Hello").newInstance();
    }

    @Override
    protected Class<?> findClass(String name) {
        String helloBase64="yv66vgAAADQAHgoABwAPCgAGABAJABEAEggAEwoAFAAVBwAWBwAXAQAGPGluaXQ+AQADKClWAQAE" +
                "Q29kZQEAD0xpbmVOdW1iZXJUYWJsZQEABWhlbGxvAQAKU291cmNlRmlsZQEACkhlbGxvLmphdmEM" +
                "AAgACQwADAAJBwAYDAAZABoBABNIZWxsbywgY2xhc3NMb2FkZXIhBwAbDAAcAB0BABZjb20vbmov" +
                "anZtL2RheTAyL0hlbGxvAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANv" +
                "dXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRs" +
                "bgEAFShMamF2YS9sYW5nL1N0cmluZzspVgAhAAYABwAAAAAAAgABAAgACQABAAoAAAApAAEAAQAA" +
                "AAkqtwABKrYAArEAAAABAAsAAAAOAAMAAAAEAAQABQAIAAYAAQAMAAkAAQAKAAAAJQACAAEAAAAJ" +
                "sgADEgS2AAWxAAAAAQALAAAACgACAAAACQAIAAoAAQANAAAAAgAO" ;
        byte [] bytes= decode(helloBase64);
        try {
            BufferedOutputStream ou = new BufferedOutputStream(new FileOutputStream(new File("resources\\HelloBase.class")));
            ou.write(bytes);
            ou.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
        return defineClass(name,bytes,0,bytes.length);
    }

    private byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
