package org.interview.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserObj {

	public static final String CREATED_AT = "created_at";

	public static final String USER_ID = "id";

	public static final String NAME = "name";

	public static final String SCREEN_NAME = "screen_name";

	@JsonProperty(USER_ID)
	private String userId;

	@JsonProperty(CREATED_AT)
	private Date creationDate;

	@JsonProperty(NAME)
	private String name;

	@JsonProperty(SCREEN_NAME)
	private String screenName;

	public String getUserId() {
		return userId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	@Override
	public String toString() {
		return "[" + USER_ID + ": " + userId + "," + CREATED_AT + ": " + creationDate + "," + NAME + ": " + name
				+ "," + SCREEN_NAME + ": " + screenName + "]";
	}
}
