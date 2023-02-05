package com.roker.openai;

import java.io.IOException;

/**
 * @description ChatGPT open ai 接口：https://beta.openai.com/account/api-keys
 */
public interface IOpenAI {

    /**
     *
     * @param openAiKey openAiKey
     * @param question  问题
     * @return {@link String}
     * @throws IOException ioexception
     */
    String doChatGPT(String openAiKey, String question) throws IOException;

}