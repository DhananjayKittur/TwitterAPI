package org.interview.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetObj implements Comparable {

	public static final String CREATED_AT = "created_at";

	public static final String MESSAGE_ID = "id";

	public static final String TEXT = "text";

	public static final String USER = "user";

	@JsonProperty(MESSAGE_ID)
	private String messageId;

	@JsonProperty(CREATED_AT)
	private Date creationDate;

	@JsonProperty(TEXT)
	private String messageText;

	@JsonProperty(USER)
	private UserObj user;

	public String getMessageId() {
		return messageId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getMessageText() {
		return messageText;
	}

	public UserObj getUser() {
		return user;
	}

	public int compareTo(Object o) {
		final int EQUAL = 0;
		int result;

		TweetObj oTweet = (TweetObj) o;

		if (this == oTweet)
			result = EQUAL;
		else
			result = this.getCreationDate().compareTo(oTweet.getCreationDate());

		return result;
	}

	@Override
	public String toString() {
		return "\nTweet Fetched: [" + MESSAGE_ID + ": " + messageId + ",\n" + CREATED_AT + ": " + creationDate + ",\n" + TEXT + ": "
				+ messageText + ",\n" + USER + ": " + user.toString() + "]\n";
	}
}
