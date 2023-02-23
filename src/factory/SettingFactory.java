package factory;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ui.SettingUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import util.Config;

public class SettingFactory implements SearchableConfigurable {

    private SettingUI settingUI = new SettingUI();

    @Override
    public @NotNull
    String getId() {
        return "test.id";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getDisplayName() {
        return "test-config";
    }

    @Override
    public @Nullable
    JComponent createComponent() {
        return settingUI.getComponent();
    }

    @Override
    public boolean isModified() {
        return true;
    }


    /**
     * apply 是一个事件，当我们点击完成配置的 OK、完成，时候就会触发到这个方法。
     * 在这个方法中我们拿到文件的 URL 地址使用 RandomAccessFile 进行读取解析文件，并最终把文件内容展示到阅读窗体中
     *
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        //获取文件路径
        String url = settingUI.getUrlTextField().getText();
        String output = url  + "\\output.txt";

        // TODO: 执行第三方jar包 输出日志到原文件路径下的output.txt
        String cmd = "java -jar .\\lib\\dependencyUpgrade.jar " + url + " > " + output;

        try {
            //执行命令行
            Runtime.getRuntime().exec(new String[]{ "cmd", "/c", cmd});
            //等待十秒，执行完成
            Thread.sleep(10000);
            File file = new File(url + "\\output.txt");
            //输出内容展示在侧边栏
            show(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // TODO: 23/2/2023 获取jar的输出内容放到阅读窗口
    public void show(File file) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(0);

            byte[] bytes = new byte[1024 * 1024];
            int readSize = randomAccessFile.read(bytes);

            byte[] copy = new byte[readSize];
            System.arraycopy(bytes, 0, copy, 0, readSize);

            String str = new String(copy, StandardCharsets.UTF_8);


            // 设置内容
            Config.readUI.getTextContent().setText(str);

        } catch (Exception ignore) {
        }
    }

}
