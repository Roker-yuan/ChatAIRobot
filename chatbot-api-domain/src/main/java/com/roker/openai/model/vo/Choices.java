package com.roker.openai.model.vo;

import lombok.Data;

/**
 * @description 选择
 */

@Data
public class Choices {

    private String text;

    private int index;

    private String logprobs;

    private String finish_reason;

}