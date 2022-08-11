package com.mohe.nanjinghaiguaneducation.common.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson2.JSON;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceImportService;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Data
public class DynamicHeaderListener extends AnalysisEventListener <Map<Integer, String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicHeaderListener.class);

    private EduStudyPerformanceImportService iStandingBookMangerService;

    private String tabaleName;

    public DynamicHeaderListener() {
    }

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 300;

    List<Map<String, Object>> addList = new ArrayList<>();
    List<Map<String, Object>> updateList = new ArrayList<>();

    /**
     * 存储Key
     */
    Map<Integer, String> key = new HashMap<>();
    /**
     * keuList
     */
    List<String> keyList=new ArrayList<>();

    /**
     * 重写invokeHeadMap方法，获去表头，如果有需要获取第一行表头就重写这个方法，不需要则不需要重写
     *
     * @param headMap Excel每行解析的数据为Map<Integer, String>类型，Integer是Excel的列索引,String为Excel的单元格值
     * @param context context能获取一些东西，比如context.readRowHolder().getRowIndex()为Excel的行索引，表头的行索引为0，0之后的都解析成数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("解析到一条头数据：{}, currentRowHolder: {}", headMap.toString(), context.readRowHolder().getRowIndex());
        Set<Integer> integerSet = headMap.keySet();
        for (Integer integer : integerSet) {
            keyList.add(headMap.get(integer));
        }
        key.putAll(headMap);
    }


    /**
     * 读取数据 筛选过滤数据 赛选出那些数据是添加 那些数据是修改
     * @param data 传入数据
     * @param context 读取数据
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        HashMap<String, Object> objectObjectHashMap = new HashMap<>(16);
        Set<Integer> integerSet = data.keySet();
        String id=null;
        for (Integer integer : integerSet) {
            String s = key.get(integer);
            if("id".equals(s)){
                id = data.get(integer);
                if (StringUtils.isNotBlank(id)) {
                    Double f = Double.valueOf(id);
                    Integer a = (int)Math.ceil(f);
                    objectObjectHashMap.put(s,a);
                }else {
                    objectObjectHashMap.put(s,null);
                }
            }else{
                objectObjectHashMap.put(s,data.get(integer));
            }

        }
        if (StringUtils.isBlank(id)) {
            addList.add(objectObjectHashMap);
        } else {
            updateList.add(objectObjectHashMap);
        }

        // 分批次 插入数据库 可以使用 暂时不适用 使用的 可以开启
        //list.add(data);
//        if (list.size() >= BATCH_COUNT) {
//            saveData();
//            list.clear();
//        }
    }

    /**
     * 读取完成后 保存数据
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("所有数据解析完成！");
    }



    public List<Map<Integer, String>> getDatas() {
        return null;
    }

    public void setDatas(List<Map<Integer, String>> datas) {
        //this.list = datas;
    }

}
