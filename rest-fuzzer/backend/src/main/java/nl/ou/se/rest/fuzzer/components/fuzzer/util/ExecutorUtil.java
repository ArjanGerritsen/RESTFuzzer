package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.components.data.fuz.factory.FuzResponseFactory;

@Service
public class ExecutorUtil {

    // variables
    private Logger logger = LoggerFactory.getLogger(ExecutorUtil.class);

    private static final int TIMEOUT_MS = 5 * 1000;

    private static CloseableHttpClient httpClient;

    private FuzResponseFactory responseFactory = new FuzResponseFactory();

    // constructors
    private ExecutorUtil() {
        this.init();
    }

    private void init() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("wordpress", "wordpress");
        provider.setCredentials(AuthScope.ANY, credentials);

        RequestConfig config = RequestConfig.custom().setConnectTimeout(TIMEOUT_MS)
                .setConnectionRequestTimeout(TIMEOUT_MS).setSocketTimeout(TIMEOUT_MS).build();

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setDefaultCredentialsProvider(provider)
                .build();
    }

    public void destroy() {
        try {
            httpClient.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FuzResponse processRequest(FuzRequest request) {
        HttpResponse response = null;
        String failureReason = null;

        LocalDateTime ldt = LocalDateTime.now();
        try {
            HttpUriRequest httpUriRequest = ExecutorUtilHelper.getRequest(request);

            logger.debug(httpUriRequest.getRequestLine().toString());

            if (httpUriRequest != null) {
                response = httpClient.execute(httpUriRequest);

                logger.debug(response.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO
            failureReason = e.getMessage();
        }
        Long ms = ldt.until(LocalDateTime.now(), ChronoUnit.MILLIS);

        logger.info(String.format("it took %s ms to execute request and capture response", ms));

        return createFuzResponse(request, response, failureReason);
    }

    private FuzResponse createFuzResponse(FuzRequest request, HttpResponse response, String failureReason) {
        responseFactory.create(request.getProject(), request);

        if (response != null) {
            responseFactory.setCode(response.getStatusLine().getStatusCode());
            responseFactory.setDescription(response.getStatusLine().getReasonPhrase());

            String body = null;
            try {
                body = EntityUtils.toString(response.getEntity());

            } catch (ParseException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            responseFactory.setBody(body);
        }

        if (failureReason != null) {
            responseFactory.setFailureReason(failureReason);
        }

        return responseFactory.build();
    }
}