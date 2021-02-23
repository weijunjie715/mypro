package com.wei.enums;

import java.util.ArrayList;
import java.util.List;

public enum SampleSliceTypeEnum {
    NATURAL_YEAR("1", "自然年"),
    NATURAL_HALF_YEAR("2", "自然半年"),
    NATURAL_QUARTER("3", "自然季度"),
    MANUAL_MONTH("4", "指定月"),
    MANUAL_NUMBER("5", "指定条数");

    private String type;
    private String name;

    SampleSliceTypeEnum(){
    }

    SampleSliceTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static List<String> getTypes() {
        List<String> types = new ArrayList<>();
        for(SampleSliceTypeEnum sampleSliceTypeEnum:values()){
            types.add(sampleSliceTypeEnum.getType());
        }
        return types;
    }

    public static SampleSliceTypeEnum getSliceTypeEnum(String type) {
        SampleSliceTypeEnum slice = null;
        for(SampleSliceTypeEnum sampleSliceTypeEnum:values()){
            if(sampleSliceTypeEnum.getType().equals(type)){
                slice = sampleSliceTypeEnum;
                break;
            }
        }
        return slice;
    }
}
