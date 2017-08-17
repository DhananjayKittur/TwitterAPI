package org.interview.oauth.twitter;

import java.io.PrintStream;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterAuthenticatorTest {
	@Mock
	private String consumerKey;

	@Mock
	private String consumerSecret;

	@Mock
	private PrintStream out;
	@InjectMocks
	private TwitterAuthenticator twitterAuthenticator;

}
