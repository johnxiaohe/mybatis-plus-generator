package com.reuben.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.reuben.generator.utils.TempPropsUtil;

import java.util.HashMap;
import java.util.Map;

public class CustomInjectionConfig extends InjectionConfig {
    @Override
    public void initMap() {
        Map<String, Object> propMap = new HashMap<>();
        propMap.put("log4jOpen", TempPropsUtil.getProp("log4j.open"));
        if(StringUtils.isBlank(TempPropsUtil.getProp("unified.results"))){
            propMap.put("unifiedResults", false);
        }else{
            propMap.put("unifiedResults", TempPropsUtil.getProp("unified.results"));
            propMap.put("unifiedResultsFullpath", TempPropsUtil.getProp("unified.results.fullpath"));
            propMap.put("unifiedResultsClass", TempPropsUtil.getProp("unified.results.class"));
            propMap.put("unifiedResultsStaticSuccessSet", TempPropsUtil.getProp("unified.results.static.success.set"));
            propMap.put("unifiedResultsStaticFailureSet", TempPropsUtil.getProp("unified.results.static.failure.set"));
        }

        this.setMap(propMap);
    }
}
