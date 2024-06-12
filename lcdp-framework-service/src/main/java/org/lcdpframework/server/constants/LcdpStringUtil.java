package org.lcdpframework.server.constants;

public class LcdpStringUtil {

    public static String toCamel(String str){

        if(str == null || str.trim().equals("")){
            return "";
        }

        final char underLine = '_';
        int length = str.length();

        StringBuilder newStr = new StringBuilder();
        for(int i=0;i<length;i++){
            char c = str.charAt(i);
            if(c==underLine){
                if(++i<length){
                    newStr.append(Character.toUpperCase(str.charAt(i)));
                }
            }else{
                newStr.append(str.charAt(i));
            }
        }
        return newStr.toString();
    }

    public static String formatMethodUrl(String methodUrl){
        if(methodUrl.startsWith("/")){
            return methodUrl;
        }else {
            return "/"+methodUrl;
        }
    }
}
