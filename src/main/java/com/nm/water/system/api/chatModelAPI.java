package com.nm.water.system.api;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 请确保您已将 API Key 存储在环境变量 ARK_API_KEY 中
// 初始化Ark客户端，从环境变量中读取您的API Key
public class chatModelAPI {
    // 从环境变量中获取您的 API Key。此为默认方式，您可根据需要进行修改
    static String apiKey = "10fa633f-ece4-470f-946a-52938516247e";
    // 此为默认路径，您可根据业务所在地域进行配置
    static String baseUrl = "https://ark.cn-beijing.volces.com/api/v3";
    static ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
    static Dispatcher dispatcher = new Dispatcher();
    static ArkService service = ArkService.builder().dispatcher(dispatcher).connectionPool(connectionPool).baseUrl(baseUrl).apiKey(apiKey).build();

    public static String getVoluntaryTestReview(String info){
        String res = "";
        String sysInfo = "你是资深的高考志愿分析师，特别擅长分析志愿并给出意见。你的意见非常精准，只包含考生的地区、高考成绩和想报考的院校专业是否合理，报考成功的可能，就业前景，mbti性格和想报考专业是否匹配。如不匹配，会根据 mbti性格和就业前景推荐合适的专业。";
        res = getAIReviewData(sysInfo,info);
        if(StringUtils.isBlank(res)){
            res = "志愿分析异常";
        }
        return res;
    }

    public static String getMajorTestReview(String info){
        String res = "";
        String sysInfo = "你是资深的高考志愿填报辅导老师，擅长根据成绩分析考生适应专业。你只需要知道考生的各科成绩，和性格特点，就能根据考生情况给出推荐专业。你的答案非常精准，只包含5个推荐专业和推荐的简短理由。";
        res = getAIReviewData(sysInfo,info);
        if(StringUtils.isBlank(res)){
            res = "志愿分析异常";
        }
        return res;
    }

    public static String characterJobReview(String info){
        String res = "";
        String sysInfo = "你十分熟悉MBTI各性格特点同时是资深大学生职业规划师，咨询者会告诉你性别，MBTI性格，你会给出简短但精准的意见，意见只包含咨询者性格类型适合的职业，以及该职业从业者通常就读的大学专业。";
        res = getAIReviewData(sysInfo,info);
        if(StringUtils.isBlank(res)){
            res = "职业分析异常";
        }
        return res;
    }

    public static String getAIReviewData(String systemInfo, String info){
        String res = "";
        try {
            final List<ChatMessage> messages = new ArrayList<>();
            final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemInfo).build();
            final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(info).build();
            messages.add(systemMessage);
            messages.add(userMessage);

            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    // 指定您创建的方舟推理接入点 ID，此处已帮您修改为您的推理接入点 ID
                    .model("ep-20250314095545-fpf76")
                    .messages(messages)
                    .build();

            List<ChatCompletionChoice> allChoice = service.createChatCompletion(chatCompletionRequest).getChoices();

            for(ChatCompletionChoice choice:allChoice){
                res = choice.getMessage().getContent().toString();
            }

            service.shutdownExecutor();
        }catch (Exception e){
            res = "分析异常";
        }

        return res;
    }

}
