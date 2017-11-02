package ch.compile.recaptcha;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReCaptchaVerify {

    private final Logger logger = LoggerFactory.getLogger(ReCaptchaVerify.class);


    private static final String SITEVERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String USER_AGENT = "Mozilla/5.0";
    private HttpClient httpClient;
    private Gson gson;


    private String secret;

    private ReCaptchaVerify() {
        this.httpClient = HttpClientBuilder.create().build();
        this.gson = new Gson();
    }

    public ReCaptchaVerify(String secret) {
        this();
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public SiteVerifyResponse verify(String gRecaptchaResponse, String remoteIp) throws IOException {
        HttpPost httpPost = new HttpPost(SITEVERIFY_URL);
        httpPost.addHeader("User-Agent", USER_AGENT);

        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("secret", this.secret));
        parameters.add(new BasicNameValuePair("response", gRecaptchaResponse));
        parameters.add(new BasicNameValuePair("remoteip", remoteIp));
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));

        logger.debug(httpPost.toString());
        logger.debug(parameters.toString());

        HttpResponse httpResponse = httpClient.execute(httpPost);

        logger.debug(httpResponse.getStatusLine().toString());

        String responseBody = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines()
                .parallel()
                .collect(Collectors.joining("\n"));

        logger.debug(responseBody);

        SiteVerifyResponse siteVerifyResponse = gson.fromJson(responseBody, SiteVerifyResponse.class);

        logger.debug(siteVerifyResponse.toString());

        return siteVerifyResponse;
    }

}
