package file;

import springBoot.util.PrintUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileUtil {
    public static void writeFileContent(String path, String content) throws Exception {
        File file = new File(path);
        if (!(file.exists())) {
            if (file.createNewFile())
                PrintUtil.print("创建文件成功");
            else {
                throw new Exception(new StringBuilder().append("创建文件").append(path).append("失败!").toString());
            }
        }

        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(content);
        output.close();
    }
    public static void main(String[] args) throws Exception {
        String test = "F:/zip/download/archived";
        File testDir = new File(test);
        if (!testDir.exists()){
            testDir.mkdirs();
        }
    }
}
