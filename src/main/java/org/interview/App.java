package org.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.interview.objects.TweetObj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

/**
 * Main
 *
 * @author DJ
 */
public class App {

	private static final String ENDPOINT_URL = "https://stream.twitter.com/1.1/statuses/filter.json?track=";
	private static final int MAX_TWEETS = 100;
	private static final long MAX_TIMELIMIT = 30000;
	private static final String CONSUMER_KEY = "vp8qXAMoZzy6jowJdtouPLUUb";
	private static final String CONSUMER_SECRET = "IMx3eIRfXXbRimoIz7cNpZCl0dr9dYEdRuDVTr2C4LdResXjN7";

	public static void main(String[] args) {
		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String keyWord;  
			System.out.println("Please enter the String keyword to Search: ");
			keyWord = br.readLine();
			TwitterAuthenticator auth = new TwitterAuthenticator(System.out, CONSUMER_KEY,CONSUMER_SECRET);
			HttpRequestFactory httpRequestFactory = auth.getAuthorizedHttpRequestFactory();
			App app = new App();
			app.getTweets(httpRequestFactory,keyWord);
		} catch (TwitterAuthenticationException | IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void getTweets(HttpRequestFactory httpRequestFactory,String keyWord) {
		try {

			List<TweetObj> tweetStream = new ArrayList<>();
			
			HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(ENDPOINT_URL.concat(keyWord)));

			HttpResponse response = request.execute();

			InputStream input = response.getContent();

			ObjectMapper objMap = new ObjectMapper();
			
			DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			objMap.setDateFormat(dateFormat);

			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String ln = reader.readLine();

			int numberOfTweets = 0;
			long totalTime = System.currentTimeMillis();

			while (ln != null && numberOfTweets < MAX_TWEETS
					&& (System.currentTimeMillis() - totalTime < MAX_TIMELIMIT)) {
				tweetStream.add(objMap.readValue(ln, TweetObj.class));
				ln = reader.readLine();
				numberOfTweets++;
				System.out.print(".");
			}
			
			Map<String, List<TweetObj>> tweetByUser;

			tweetByUser = tweetStream.stream()
					.sorted((p1, p2) -> p1.getUser().getCreationDate().compareTo(p2.getUser().getCreationDate()))
					.collect(Collectors.groupingBy(p -> p.getUser().getUserId()));

			for (List<TweetObj> userTweetList : tweetByUser.values()) {
				userTweetList.sort((p1, p2) -> p1.compareTo(p2));
			}

			for (List<TweetObj> userTweetList : tweetByUser.values()) {
				for (TweetObj tweet : userTweetList) {
					System.out.println(tweet.toString());
				}
			}
		} catch (IOException e) {
			System.err.println("An error occurred during the file access");
		}
	}

}
