package com.roker.zsxq;

import com.roker.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * 知识星球 api
 *
 * @author liupengyuan
 * @date 2023/02/05
 */
public interface IZsxqApi {
    /**
     * 查询主题的待回答问题
     *
     * @param groupId 组id
     * @param cookie  cookie
     * @return {@link UnAnsweredQuestionsAggregates}
     * @throws IOException ioexception
     */
    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    /**
     * 回答
     *
     * @param groupId  组id
     * @param cookie   cookie
     * @param topicId  主题id
     * @param text     回答内容文本
     * @param silenced 只回复提问人
     * @return boolean
     * @throws IOException ioexception
     */
    boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException;
}
