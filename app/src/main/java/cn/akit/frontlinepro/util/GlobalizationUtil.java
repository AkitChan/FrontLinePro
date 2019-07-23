package cn.akit.frontlinepro.util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created:     by leon
 * Date:        2018/12/1
 * Description: 根据xls文件自动化生成多语言strings内容
 * 需要参考给定的xls文件格式
 */
public class GlobalizationUtil {

    //xls文件所在目录，也是生成的values文件夹的所在目录
    private final static String DIR = Environment.getExternalStorageDirectory().getPath();
    //xls文件名
    //key为国家代码， value 为多语言内容
    private static Map<String, List<ValueModel>> sValueMap = new HashMap<>();


    public void put(String language, List<ValueModel> list) {
        sValueMap.put(language, list);
    }

    /**
     * 创建values文件夹和strings文件，并写入内容
     */
    public static void createValuesStringsXml() {
        Set<String> keySet = sValueMap.keySet();
        for (String code : keySet) {
            File cacheDir = new File(DIR);
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
            File file = new File(cacheDir, "strings.xml");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("<resources>");
                bw.newLine();
                List<ValueModel> list = sValueMap.get(code);
                for (ValueModel model : list) {
                    bw.write("<string name=\"" + model.getName() + "\">" + model.getValue() + "</string>");
                    bw.newLine();
                }
                bw.write("</resources>");
                bw.close();
                System.out.println("写入完成" + cacheDir.getName() + " " + file.getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
