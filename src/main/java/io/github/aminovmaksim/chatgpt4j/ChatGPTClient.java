package io.github.aminovmaksim.chatgpt4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.aminovmaksim.chatgpt4j.model.ChatRequest;
import io.github.aminovmaksim.chatgpt4j.model.ChatResponse;
import lombok.NoArgsConstructor;

public class ChatGPTClient {

	private final String apiKey;
	private final String baseUrl;

	private ChatGPTClient(ChatGPTClientBuilder builder) {
		this.apiKey = builder.apiKey;
		this.baseUrl = builder.baseUrl;
	}

	public static ChatGPTClientBuilder builder() {
		return new ChatGPTClientBuilder();
	}

	private static final String API_ORG = "org-LufR8OmCCrRlSIIeyCSTkCne";

	/**
	 * Send chat completion request
	 * 
	 * @throws ChatGPTClientException if any error occurs
	 */
	public ChatResponse sendChat(ChatRequest chatRequest) throws ChatGPTClientException {
		try {
			Gson gson = new Gson();
			Gson pGson = new GsonBuilder().setPrettyPrinting().create(); // pretty print

			String body = gson.toJson(chatRequest);
			URL url = new URL(baseUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("OpenAI-Organization", API_ORG);
			con.setDoOutput(true);
			con.setRequestProperty("Authorization", "Bearer " + apiKey);
			try (OutputStream os = con.getOutputStream()) {
				byte[] input = body.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
				os.close();
			}
			int responseCode = con.getResponseCode();

			System.out.println("\nresponseCode:" + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			ChatResponse chatResponse = gson.fromJson(response.toString(), ChatResponse.class);
			/*
			 * try {
			 * 
			 * System.out.println("\nchatResponse:\n" + pGson.toJson(response)); } catch
			 * (Exception e) { System.out.println("\n" + e + "\n");
			 * 
			 * }
			 */
			if (chatResponse.getError() != null) {
				throw new RuntimeException(chatResponse.getError().getMessage());
			}

			return chatResponse;
		} catch (Exception e) {
			throw new ChatGPTClientException(e.getMessage());
		}
	}

	@NoArgsConstructor
	public static class ChatGPTClientBuilder {

		private String apiKey;
		private Long webClientTimeout = 60000L;
		private String baseUrl = "https://api.openai.com/v1";

		/**
		 * @param apiKey OpenAI api key
		 */
		public ChatGPTClientBuilder apiKey(String apiKey) {
			this.apiKey = apiKey;
			return this;
		}

		/**
		 * @param timeout connection timeout in milliseconds, default = 60000
		 */
		public ChatGPTClientBuilder requestTimeout(Long timeout) {
			this.webClientTimeout = timeout;
			return this;
		}

		/**
		 * @param baseUrl default = <a href=
		 *                "https://api.openai.com/v1">https://api.openai.com/v1</a>
		 */
		public ChatGPTClientBuilder baseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		public ChatGPTClient build() {
			return new ChatGPTClient(this);
		}
	}
}
