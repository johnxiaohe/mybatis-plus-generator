package com.reuben.generator;

import com.reuben.generator.config.AutoGeneratorConfig;

public class GeneratorTool {

    public static void main(String[] args) {
        AutoGeneratorConfig autoGeneratorConfig = new AutoGeneratorConfig();
        autoGeneratorConfig.getAutoGenerator().execute();
    }
}
