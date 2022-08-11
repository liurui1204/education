package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;


public class EduTrainingTreeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */

	private String id;

	/**
	 * 实训点名称
	 */
	private String trainingBaseName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrainingBaseName() {
		return trainingBaseName;
	}

	public void setTrainingBaseName(String trainingBaseName) {
		this.trainingBaseName = trainingBaseName;
	}
}
