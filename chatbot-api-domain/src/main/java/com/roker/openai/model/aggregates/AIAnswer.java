package com.roker.openai.model.aggregates;


import com.roker.openai.model.vo.Choices;
import lombok.Data;

import java.util.List;

/**
 * @description AI Answer
 */
@Data
public class AIAnswer {

    private String id;

    private String object;

    private int created;

    private String model;

    private List<Choices> choices;



}