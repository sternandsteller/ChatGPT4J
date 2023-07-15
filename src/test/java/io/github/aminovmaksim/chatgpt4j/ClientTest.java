package io.github.aminovmaksim.chatgpt4j;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.aminovmaksim.chatgpt4j.model.ChatRequest;
import io.github.aminovmaksim.chatgpt4j.model.ChatResponse;
import io.github.aminovmaksim.chatgpt4j.model.enums.ModelType;

public class ClientTest {

	private final static Logger logger = LoggerFactory.getLogger(ChatGPTClient.class);

	@Test
	@Ignore
	public static void main(String args[]) {

		logger.info("ClientTest.main() in");

		String API_KEY = "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";

		ChatGPTClient client = ChatGPTClient.builder().apiKey(API_KEY).requestTimeout(30000L)
				.baseUrl("https://api.openai.com/v1/completions").build();

		ChatRequest request = new ChatRequest();

		request.setModel(ModelType.TEXT_DAVINCI_003.getName());
		request.setPrompt("2 in words");
		request.setMax_tokens(16);

		try {

			ChatResponse chatResponse = client.sendChat(request);

			Gson gson = new GsonBuilder().setPrettyPrinting().create(); // pretty print

			logger.info("\nSuccessful! chatResponse:\n" + gson.toJson(chatResponse));

		} catch (Exception e) {
			Gson gson = new Gson();
			logger.info("\nFailed:\n" + gson.toJson(request));
			logger.error(e.toString());
		}
		logger.info("ClientTest.main() out");
	}
}
