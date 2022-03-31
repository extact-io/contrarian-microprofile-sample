package io.extact.mp.sample.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ConfigMergeMain {

    public static void main(String[] args) {
        // Configインスタンスの取得
        Config config = ConfigProvider.getConfig();

        // propertiesファイルの設定値の取得
        String name = config.getValue("company.name", String.class);
        int est = config.getValue("company.est", int.class);
        String address = config.getValue("company.address", String.class);
        System.out.printf(  "name:%s, est:%s, address:%s\n", name, est, address);

        // YAMLの設定値の取得
        String empName = config.getValue("employee.name", String.class);
        String dept = config.getValue("employee.dept", String.class);
        String rank = config.getValue("employee.rank", String.class);
        System.out.printf("name:%s, dept:%s, rank:%s\n", empName, dept, rank);
    }
}
