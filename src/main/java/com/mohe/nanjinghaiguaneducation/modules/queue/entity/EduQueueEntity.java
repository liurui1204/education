package com.mohe.nanjinghaiguaneducation.modules.queue.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("edu_queue")
@Data
public class EduQueueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    // 0-同步人员 1-同步角色
    private Integer object;

    //0-all 1-single
    private Integer type;

    //: 0-未开始执行 1-正在执行 2-执行结束
    private Integer status;

    //（All可为空，single-id）
    private String data;

    //创建时间
    private Date createTime;
}
