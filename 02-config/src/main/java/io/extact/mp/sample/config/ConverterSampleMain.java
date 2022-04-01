package io.extact.mp.sample.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ConverterSampleMain {

    public static void main(String[] args) {
        // Configインスタンスの取得
        Config config = ConfigProvider.getConfig();

        // "true"をbooleanとして取得
        boolean booleanValue = config.getValue("booleanValue", boolean.class);
        // "100"をintとして取得
        int intValue = config.getValue("intValue", int.class);
        // "123.456"をdoubleとして取得
        double doubleValue = config.getValue("doubleValue", double.class);
        // "io.extact.mp.sample.config.SimpleSampleMain"をClassインスタンスとして取得
        Class<?> classValue = config.getValue("classValue", Class.class);
        // "1,2,3,4"を単一文字列として取得
        String stringValue = config.getValue("arrayValue", String.class);
        // "1,2,3,4"をカンマで分割した文字列配列として取得
        String[] stringArrayValue = config.getValue("arrayValue", String[].class);

        // 取得値の出力
        System.out.println("booleanValue:" + booleanValue);
        System.out.println("intValue:" + intValue);
        System.out.println("doubleValue:" + doubleValue);
        System.out.println("classValue:" + classValue.getName());
        System.out.println("arrayValue:" + stringValue);
        System.out.println("arrayValue:" + String.join(":", stringArrayValue));
    }
}

