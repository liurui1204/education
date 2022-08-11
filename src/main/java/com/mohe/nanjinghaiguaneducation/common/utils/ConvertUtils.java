package com.mohe.nanjinghaiguaneducation.common.utils;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;


/**
 * 实体类转换
 */
public class ConvertUtils {


    public static <T, R> R convert(T from, Supplier<R> supplier) {
        return convert(from, supplier, null);
    }

    public static <T, R> R convert(T from, Supplier<R> supplier, BiConsumer<T, R> consumer) {
        if (from == null) {
            return null;
        }

        BiConsumer<T, R> copyConsumer = BeanUtils::copyProperties;

        if (consumer != null) {
            consumer = copyConsumer.andThen(consumer);
        } else {
            consumer = copyConsumer;
        }

        R r = supplier.get();

        consumer.accept(from, r);

            return r;
    }

    public static <K, T, R> Map<K, R> convertMap(Map<K, T> map, Supplier<R> supplier, BiConsumer<T, R> consumer) {
        Map<K, R> returnMap = new HashMap<>();
        map.forEach((k, t) -> {
            R r = supplier.get();
            consumer.accept(t, r);
            returnMap.put(k, r);
        });
        return returnMap;
    }


    public static <K, T, R> Map<K, List<R>> convertMapList(Map<K, List<T>> map, Supplier<R> supplier, BiConsumer<T, R> consumer) {
        Map<K, List<R>> returnMap = new HashMap<>();
        map.forEach((k, v) -> v.forEach(t -> {
            R r = supplier.get();
            consumer.accept(t, r);
            returnMap.computeIfAbsent(k, key -> new ArrayList<>()).add(r);
        }));
        return returnMap;
    }

    public static <T, R> List<R> convertList(List<T> list, Supplier<R> supplier) {
        return Lists.toList(list, t -> convert(t, supplier));
    }

    public static <T, R> List<R> convertList(List<T> list, Supplier<R> supplier, BiConsumer<T, R> consumer) {
        return Lists.toList(list, t -> convert(t, supplier, consumer));
    }


}
