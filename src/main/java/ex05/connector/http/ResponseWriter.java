package ex05.connector.http;
import ex05.core.Constants;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * 这个类存在的原因：
 * 当调用println()系列方法时，PrintWriter会在用户输入内容后追加与系统相关 换行符 比如“\r\n”  或 “\n”
 * 而作为  Http 协议，只接受“\r\n”，所以实现这个类，以特定的换行符追加
 *
 *
 *
 * 调用  输出请求的println方法
 *
 *
 * 调用  输出块编码的print方法
 *
 *
 *
 * 对于实现  块编码 功能，其中的数量应是 浏览器将要读取的字节的数量，因此服务端应发送 内容 所占的字节数量
 * 而内容所占的字节数量与服务端的编码有关  ,此外，浏览器应采用与服务器相同的编码
 */

public class ResponseWriter extends PrintWriter {
    public ResponseWriter(Writer out) {
        super(out);
    }


    @Override
    public void print(boolean b) {

    }

    @Override
    public void print(char c) {

    }

    @Override
    public void print(int i) {

    }

    @Override
    public void print(long l) {

    }

    @Override
    public void print(float f) {

    }

    @Override
    public void print(double d) {

    }

    @Override
    public void print(char[] s) {

    }



    @Override
    public void print(Object obj) {

    }

    @Override
    public void println() {

       // super.write("\r\n");
        super.print("\r\n");
        super.flush();
    }

    @Override
    public void println(boolean x) {

    }

    @Override
    public void println(char x) {

    }

    @Override
    public void println(int x) {
;
    }

    @Override
    public void println(long x) {

    }

    @Override
    public void println(float x) {

    }

    @Override
    public void println(double x) {

    }

    @Override
    public void println(char[] x) {

    }

    /**
     * all println(x)  will send success, only when invoke  flush()
     * @param x
     */

    @Override
    public void println(String x) {
        x+="\r\n";
        String hexLen=completeHexLen(x);
       // super.write(hexLen);                           // 长度
        super.print(hexLen);
        println();                                 //块编码“\r\n”
        //super.write(x);                            //内容
        super.print(x);
        println();                                 //块编码 "\r\n"
    }
    @Override
    public void print(String s) {
        String hexLen=completeHexLen(s);
        //  super.write(hexLen);    //  长度
        super.print(hexLen);
        println();              //  块编码的“\r\n”
        //super.write(s);           //内容
        super.print(s);
        println();                //  块编码的“\r\n”
    }
    private String completeHexLen(String x){
        byte[] bytes=new byte[0];
        try {
            bytes = x.getBytes(Constants.Charaset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int length = bytes.length;
        return Integer.toHexString(length);
    }

    @Override
    public void println(Object x) {

    }

    // 这个flush  是 标识 一个事物的完成
    @Override
    public void flush() {
        print("");
        super.flush();
    }


    public void printlnOfResponseStatus(String x){
        if(x.length()!=0)
        //super.write(x);
            super.print(x);
        println();
    }
}
