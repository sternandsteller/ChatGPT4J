package io.github.aminovmaksim.chatgpt4j.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

	private Long created;

	private Usage usage;

	private Error error;

	public Error getError() {
		return error;
	}

	public String response;

	public String getResponse() {
		return choices.get(0).getText();
	}

	private List<Choice> choices;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Choice {
		private String text;

		public String getText() {
			return text;
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Usage {

		private Integer prompt_tokens;
		private Integer completion_tokens;
		private Integer total_tokens;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Error {

		public String message;

		public String getMessage() {
			return message;
		}

		private String type;
		private String code;
	}
}
