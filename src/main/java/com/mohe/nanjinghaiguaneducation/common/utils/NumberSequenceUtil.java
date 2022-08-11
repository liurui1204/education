/**
 * Copyright 2018  http://www.mallplus.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mohe.nanjinghaiguaneducation.common.utils;


import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 系统配置Redis
 *
 * @author jay
 * @email 755822107@qq.com
 * @date 2018/05/18 21:08
 */
@Component
public class NumberSequenceUtil {
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private RedisUtils redisUtils;

    /**
	 * 生成的连续编号
	 * 
	 * @param serviceNo 生成规则前缀
	 * @return 连续的编号
	 */
	public synchronized String getNumberSequenceByService(String serviceNo) {
		System.out.println(redisUtils);
		if (StringUtils.isBlank(serviceNo)) {
			return null;
		}
		String dateStr = DateUtil.format(new Date(), "yyyyMMdd");
//        String dateStr = simpleDateFormat.format(new Date());
		String redisKey = serviceNo + dateStr;
		Object seqValueStr = redisUtils.get(redisKey);
		int seqValue;
		if (null != seqValueStr) {
			seqValue = Integer.parseInt(String.valueOf(seqValueStr));
		} else {
			seqValue = 1;
		}
		//int seqValue = StringUtils.isNotEmpty(String.valueOf(seqValueStr)) ? Integer.parseInt(String.valueOf(seqValueStr)) : 1;
		if (seqValue >= 10000) {
			seqValueStr = seqValue + "";
			redisUtils.set(redisKey, seqValue + 1);
		} else {
			seqValueStr = String.format("%04d", seqValue);
			redisUtils.set(redisKey, seqValue + 1);
		}

		return redisKey + seqValueStr;
	}

}
