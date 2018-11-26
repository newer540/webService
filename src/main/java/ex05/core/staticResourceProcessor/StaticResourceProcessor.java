package ex05.core.staticResourceProcessor;



import ex03.Processor.Constants;
import ex05.connector.Request;
import ex05.connector.Response;

import java.io.*;

public class StaticResourceProcessor {
    private static int bufferSize=2048;

    public void process(Request request, Response response) throws IOException {

        File file = new File(Constants.WEB_ROOT + request.getRequestURI());
        FileInputStream fileInputStream=null;
        PrintWriter writer=null;

        OutputStream output=response.getOutputStream();
        try {
        byte[] buffer;
        int ch;
        if(file.exists()){
            String fileName = file.getName();
            String extension=fileName.substring(fileName.indexOf("."));
            if (".jpg".equals(extension)||".ico".equals(extension)){
                response.setContentType("image/jpg");
                writer=response.getWriter();
                fileInputStream=new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream,2048);
                int bufferSize=2048;
                buffer=new byte[bufferSize];
                while((ch=bufferedInputStream.read(buffer))!=-1) {
                    String hexLen=Integer.toHexString(ch);
                    String lenStr=hexLen+"\r\n";
                    output.write(lenStr.getBytes());
                    output.write(buffer, 0, ch);
                    output.write("\r\n".getBytes());
                }
                String endStr="0\r\n\r\n";
                output.write(endStr.getBytes());
          //      output.flush();
            //    output.close();


            }else if (".mp4".equals(extension)){
                response.setContentType("video/mp4");
            }else{
                writer=response.getWriter();
                fileInputStream=new FileInputStream(file);
                buffer=new byte[bufferSize];
                while((ch=fileInputStream.read(buffer))!=-1) {
                    writer.print(new String(buffer,0,ch));
                }
                writer.flush();
            }

        }else{
            response.setStatus(404);
            response.setStatusDesc("5555 not found");
            writer=response.getWriter();
            writer.flush();
        }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
