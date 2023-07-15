package io.github.aminovmaksim.chatgpt4j;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class GptTest {

	static final String API_KEY = "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";
	static final String API_ORG = "org-LufR8OmCCrRlSIIeyCSTkCne";
	static final String AUDIO_FILE_PATH = "C:\\Users\\salim\\OneDrive\\Documents\\GitHub\\ChatGPT4J\\conference.wav";
	static final String BASE_URL = "https://api.openai.com/v1/audio/transcriptions";

	private final static Logger logger = LoggerFactory.getLogger(ChatGPTClient.class);

	@Test
	@Ignore
	public static void main(String args[]) {

		logger.info("GptTest.main() in");

		ChatGpt client = ChatGpt.builder().apiKey(API_KEY).apiOrg(API_ORG).baseUrl(BASE_URL)
				.audioFilePath(AUDIO_FILE_PATH).requestTimeout(30000L).build();

		try {

			String audioText = client.audioToText();

			logger.info("\nSuccessful! audioText:\n" + audioText);

		} catch (Exception e) {

			Gson gson = new Gson();
			logger.info("\nFailed:\n" + gson.toJson(client));
			logger.error(e.toString());

		}
		logger.info("GptTest.main() out");
	}
}
