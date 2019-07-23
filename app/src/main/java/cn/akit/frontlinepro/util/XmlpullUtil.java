package cn.akit.frontlinepro.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenYuXuan on 2019/6/3.
 * email : southxvii@163.com
 */
public class XmlpullUtil {

    public static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }


    public static XmlPullParser getXmlPullParser(String xml) throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser pullParser = factory.newPullParser();
        Reader reader = new StringReader(xml);
        pullParser.setInput(reader);
        return pullParser;
    }

    public static Map<String, String> parse(String xml) throws XmlPullParserException, IOException {
        XmlPullParser pullParser = getXmlPullParser(xml);
        Map<String, String> map = new HashMap<>();
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if ("string".equals(pullParser.getName())) {
                        map.put(pullParser.getAttributeValue("", "name"), pullParser.nextText());
                    }
                case XmlPullParser.END_TAG:
                    break;
            }
            event = pullParser.next();
        }
        return map;
    }


}
