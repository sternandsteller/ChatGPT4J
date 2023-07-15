package io.github.aminovmaksim.chatgpt4j.model.enums;

public enum ModelType {
	TEXT_DAVINCI_003("text-davinci-003"), GPT_3_5_TURBO("gpt-3.5-turbo"), GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
	GPT_4("gpt-4")

	;

	private final String name;

	ModelType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
