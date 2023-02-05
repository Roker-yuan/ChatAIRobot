package com.roker.zsxq.model.aggregates;

import com.roker.zsxq.model.res.RespData;
import lombok.Data;

@Data
public class UnAnsweredQuestionsAggregates {

    private boolean succeeded;
    private RespData resp_data;



}