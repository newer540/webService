package ex05.connector.http.assist;

public class HttpHeader {
    public byte[] name;
    public int nameEnd;
    public byte[]  value;
    public int valueEnd;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof byte[]){
            byte[] that= (byte[]) obj;
            if(that.length==name.length){
                for(int i=0;i<that.length;i++){
                    if (that[i]!=name[i])
                        return false;
                }
                return true;
            }
        }
        return false;

    }
}
