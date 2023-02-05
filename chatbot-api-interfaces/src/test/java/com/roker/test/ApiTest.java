package com.roker.test;


import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class ApiTest {
    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/48884411112848/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", "zsxq_access_token=FE24595D-E2C0-7C81-CDD9-AE747C7A4B39_F03DA961522CD131; zsxqsessionid=7130193ad4053c6d57e67f3dab1a4d70; abtest_env=product; sensorsdata2015jssdkcross={\"distinct_id\":\"544455158112824\",\"first_id\":\"185f79f0df75e2-0299f37f9d1c95c-16525635-1296000-185f79f0df817c1\",\"props\":{\"$latest_traffic_source_type\":\"直接流量\",\"$latest_search_keyword\":\"未取到值_直接打开\",\"$latest_referrer\":\"\"},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg1Zjc5ZjBkZjc1ZTItMDI5OWYzN2Y5ZDFjOTVjLTE2NTI1NjM1LTEyOTYwMDAtMTg1Zjc5ZjBkZjgxN2MxIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNTQ0NDU1MTU4MTEyODI0In0=\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"544455158112824\"},\"$device_id\":\"185f79f0df75e2-0299f37f9d1c95c-16525635-1296000-185f79f0df817c1\"}");
        get.addHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }

}
