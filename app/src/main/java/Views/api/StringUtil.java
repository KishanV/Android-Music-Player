package Views.api;

/**
 * Created by linedeer on 11/19/2016.
 */

public class StringUtil {
    public static  String getFirstChar(String str){
        if(str == null){
            str = "unknown";
        }else if(str.length() == 0){
            str = "unknown";
        }
        return str.substring(0,1);
    }
}
