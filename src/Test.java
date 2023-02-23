import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) {
        // TODO: 执行第三方jar包 输出日志到原文件路径下的output.txt
        String url = "D:\\1javawork\\multiModelDemo";
        String output = url  + "\\output.txt";
        String cmd = "java -jar .\\lib\\dependencyUpgrade.jar " + url + " > " + output;

        try {
            //执行命令行
            //why 没有输出？ 无效？
            Process process = Runtime.getRuntime().exec(new String[]{ "cmd", "/c", cmd});
            //等待十秒，执行完成
            Thread.sleep(10000);
            //获取输出结果(?)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GB2312"));
            String line;
            StringBuilder b = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                b.append(line).append("\n");
            }
            System.out.println(b);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
