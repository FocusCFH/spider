package org.example.spider.utils.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName SheetRowModel
 * @Description
 * @Author chenfuhao
 * @Date 2022/5/17 12:00
 * @Version 1.0
 **/
@Data
public class SheetRowModel {

    /**
     * 能源种类
     */
    private String energyType;

    /**
     * 排放因子值
     */
    private String emissionFactorValue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 材料使用量（kg）
     */
    private String materialUsage;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序号
     */
    private Integer sort;
}
