package io.github.aminovmaksim.chatgpt4j;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.github.aminovmaksim.chatgpt4j.model.enums.ModelType;
import lombok.NoArgsConstructor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatGpt {

	private final String apiOrg;
	private final String apiKey;
	private final String baseUrl;
	private final String audioFilePath;

	private ChatGpt(ChatGPTClientBuilder builder) {
		this.apiOrg = builder.apiOrg;
		this.apiKey = builder.apiKey;
		this.baseUrl = builder.baseUrl;
		this.audioFilePath = builder.audioFilePath;
	}

	public static ChatGPTClientBuilder builder() {
		return new ChatGPTClientBuilder();
	}

	public String audioToText() throws Exception {

		String language = "en";
		final String model = ModelType.WHISPER_1.getName();
		File audioFile = new File(audioFilePath);

		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("file", audioFile.getName(),
						RequestBody.create(audioFile, MediaType.get("audio/x-wave")))
				.addFormDataPart("model", model).addFormDataPart("language", language).build();

		Request request = new Request.Builder().url(baseUrl).addHeader("Authorization", "Bearer " + apiKey)
				.addHeader("OpenAI-Organization", apiOrg).addHeader("Content-Type", "multipart/form-data")
				.post(requestBody).build();

		OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(0, TimeUnit.SECONDS).writeTimeout(0, TimeUnit.SECONDS).build();

		Response response = httpClient.newCall(request).execute();
		System.out.println("response:\n" + response);

		return response.body().string();

		/*
		 * URL url = new URL(baseUrl); HttpURLConnection con = (HttpURLConnection)
		 * url.openConnection(); con.setRequestMethod("POST"); con.setDoInput(true);
		 * con.setDoOutput(true); con.setRequestProperty("Content-Type",
		 * "multipart/form-data"); con.setRequestProperty("Accept", "application/json");
		 * con.setRequestProperty("OpenAI-Organization", API_ORG);
		 * con.setRequestProperty("Authorization", "Bearer " + apiKey);
		 * con.setRequestProperty("model", "whisper-1" + apiKey); OutputStream
		 * outputStream = con.getOutputStream(); PrintWriter writer = new
		 * PrintWriter(new OutputStreamWriter(outputStream)); String boundary = "===" +
		 * System.currentTimeMillis() + "===";
		 * writer.append("--").append(boundary).append("\r\n"); writer.
		 * append("Content-Disposition: form-data; name=\"file\"; filename=\"file.wav\"\r\n"
		 * ); writer.append("Content-Type: audio/wav\r\n"); writer.append("\r\n");
		 * writer.flush();
		 * 
		 * String filePath =
		 * "C:\\Users\\salim\\OneDrive\\Documents\\GitHub\\ChatGPT4J\\conference.wav";
		 * FileInputStream fileInputStream = new FileInputStream(new File(filePath));
		 * byte[] buffer = new byte[4096]; int bytesRead; while ((bytesRead =
		 * fileInputStream.read(buffer)) != -1) { outputStream.write(buffer, 0,
		 * bytesRead); } outputStream.flush(); fileInputStream.close();
		 * 
		 * writer.append("\r\n"); writer.append("--").append(boundary).append("--\r\n");
		 * writer.flush();
		 * 
		 * BufferedReader reader = new BufferedReader(new
		 * InputStreamReader(con.getInputStream())); String line; StringBuilder response
		 * = new StringBuilder(); while ((line = reader.readLine()) != null) {
		 * response.append(line); } reader.close(); int responseCode =
		 * con.getResponseCode();
		 * 
		 * // Print the response System.out.println("Response Code: " + responseCode);
		 * System.out.println("Response Body: " + response.toString()); // Close the
		 * connection con.disconnect();
		 */
	}

	@NoArgsConstructor
	public static class ChatGPTClientBuilder {

		private String apiKey;
		private String apiOrg;
		private String baseUrl;
		private String audioFilePath;

		public ChatGPTClientBuilder apiKey(String apiKey) {
			this.apiKey = apiKey;
			return this;
		}

		public ChatGPTClientBuilder apiOrg(String apiOrg) {
			this.apiOrg = apiOrg;
			return this;
		}

		public ChatGPTClientBuilder baseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		public ChatGPTClientBuilder audioFilePath(String audioFilePath) {
			this.audioFilePath = audioFilePath;
			return this;
		}

		/**
		 * @param timeout connection timeout in milliseconds, default = 60000
		 */
		public ChatGPTClientBuilder requestTimeout(Long timeout) {
			return this;
		}

		public ChatGpt build() {
			return new ChatGpt(this);
		}
	}
	// private static boolean languageIsNotSupported(String language) {
	// Set<String> supportedLanguages = new HashSet<>(Arrays.asList(
	// "af", "ar", "hy", "az", "be", "bs", "bg", "ca", "zh", "hr", "cs", "da", "nl",
	// "en", "et", "fi", "fr", "gl", "de", "el", "he", "hi", "hu", "is", "id", "it",
	// "ja", "kn", "kk", "ko", "lv", "lt", "mk", "ms", "mr", "mi", "ne", "no", "fa",
	// "pl", "pt", "ro", "ru", "sr", "sk", "sl", "es", "sw", "sv", "tl", "ta", "th",
	// "tr", "uk", "ur", "vi", "cy"
	// ));

	// return !supportedLanguages.contains(language);
	// }

}
