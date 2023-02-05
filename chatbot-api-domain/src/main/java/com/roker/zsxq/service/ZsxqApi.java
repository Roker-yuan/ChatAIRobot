package com.roker.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roker.zsxq.IZsxqApi;
import com.roker.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.roker.zsxq.model.req.AnswerReq;
import com.roker.zsxq.model.req.ReqData;
import com.roker.zsxq.model.res.AnswerRes;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ZsxqApi implements IZsxqApi {

    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);
    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        // 创建 CloseableHttpClient 对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建 HttpGet 对象，请求 URL 包含 groupId 参数
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");

        // 添加请求头 cookie
        get.addHeader("cookie", cookie);
        // 添加请求头 "Content-Type"
        get.addHeader("Content-Type", "application/json;charset=utf8");

        // 执行请求
        CloseableHttpResponse response = httpClient.execute(get);

        // 判断请求状态是否为 OK
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            // 将响应内容转换为字符串
            String jsonStr = EntityUtils.toString(response.getEntity());
            // 记录信息，包括 groupId 和 json 字符串
            logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, jsonStr);
            // 将 json 字符串转换为 UnAnsweredQuestionsAggregates 对象
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        }else {
            // 抛出运行时异常
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Error Code is " + response.getStatusLine().getStatusCode());
        }

    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        // 创建 CloseableHttpClient 对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建 HttpPost 对象，请求 URL 包含 topicId 参数
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/answer");
        // 添加请求头 cookie
        post.addHeader("cookie", cookie);
        // 添加请求头 "Content-Type"
        post.addHeader("Content-Type", "application/json;charset=utf8");
        // 添加请求头 "user-agent"
        post.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");

        // 创建 AnswerReq 对象，包含回答的文本和是否需要隐藏
        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        // 将 AnswerReq 对象转换为 JSON 字符串
        String paramJson = JSONObject.toJSONString(answerReq);
        // 创建 StringEntity 对象，设置请求内容
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        // 执行请求
        CloseableHttpResponse response = httpClient.execute(post);
        // 判断请求状态是否为 OK
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 将响应内容转换为字符串
            String jsonStr = EntityUtils.toString(response.getEntity());
            // 记录信息，包括 groupId，topicId 和 json 字符串
            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            // 将 json 字符串转换为 AnswerRes 对象
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            // 返回是否成功
            return answerRes.isSucceeded();
        } else {
            // 抛出运行时异
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
