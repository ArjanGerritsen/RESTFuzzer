package nl.ou.se.rest.fuzzer.executor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.data.fuz.factory.FuzResponseFactory;
import nl.ou.se.rest.fuzzer.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.service.task.TaskController;

public class ExecutorUtil {

	// variables
	private Logger logger = LoggerFactory.getLogger(TaskController.class);

	private static ExecutorUtil instance = null;

	private static int TIMEOUT_MS = 5 * 1000;
	private static CloseableHttpClient httpClient;

	private FuzResponseFactory responseFactory;

	// constructors
	private ExecutorUtil() {
	}

	// methods
	public static ExecutorUtil getInstance() {
		if (ExecutorUtil.instance == null) {
			ExecutorUtil.instance = new ExecutorUtil();
			ExecutorUtil.init();
		}
		return ExecutorUtil.instance;
	}

	private static void init() {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(TIMEOUT_MS)
				.setConnectionRequestTimeout(TIMEOUT_MS).setSocketTimeout(TIMEOUT_MS).build();

		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
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
		CloseableHttpResponse response = null;
		String failureReason = null;

		try {
			HttpUriRequest httpUriRequest = getRequest(request);

			logger.debug(httpUriRequest.getRequestLine().toString());

			if (httpUriRequest != null) {
				response = httpClient.execute(httpUriRequest);

				logger.debug(response.getStatusLine().toString());

				response.close();
			}

			Thread.sleep(1000);

		} catch (Exception e) {
			failureReason = e.getMessage();
		}

		return createFuzResponse(request, response, failureReason);
	}

	private FuzResponse createFuzResponse(FuzRequest request, CloseableHttpResponse response, String failureReason) {
		responseFactory.create(request);

		if (response != null) {
			responseFactory.setCode(response.getStatusLine().getStatusCode());
			responseFactory.setDescription(response.getStatusLine().getReasonPhrase());

			responseFactory.setBody("TODO save the JSON response");
		}

		if (failureReason != null) {
			responseFactory.setFailureReason(failureReason);
		}

		return responseFactory.build();
	}

	private HttpUriRequest getRequest(FuzRequest request) {
		HttpUriRequest httpUriRequest = null;

		switch (request.getHttpMethod()) {
		case GET:
			httpUriRequest = getGetRequest(request);
			break;
		case POST:
			httpUriRequest = getPostRequest(request);
			break;
		case PATCH:
			httpUriRequest = getPatchRequest(request);
			break;
		case DELETE:
			httpUriRequest = getDeleteRequest(request);
			break;
		default:
			break;
		}

		return httpUriRequest;
	}

	private HttpUriRequest getGetRequest(FuzRequest request) {
		HttpGet get = new HttpGet(getUri(request));

		return get;
	}

	private HttpUriRequest getPostRequest(FuzRequest request) {
		HttpPost post = new HttpPost(getUri(request));

//		post.setHeader("Accept", "application/json");
//		post.setHeader("Content-type", "application/json");

		String json = "{\"id\":1,\"name\":\"John\"}";
//		StringEntity entity = new StringEntity(json);
//		post.setEntity(entity);

		return post;
	}

	private HttpUriRequest getPatchRequest(FuzRequest request) {
		HttpPatch patch = new HttpPatch(getUri(request));

		return patch;
	}

	private HttpUriRequest getDeleteRequest(FuzRequest request) {
		HttpDelete delete = new HttpDelete(getUri(request));

		return delete;
	}

	private URI getUri(FuzRequest request) {
		URIBuilder uriBuilder = new URIBuilder();
		URI uri = null;
		try {
			uriBuilder.setScheme("http");
			uriBuilder.setHost(request.getProject().getSut().getHost());
			uriBuilder.setPath(getPath(request));

			// request.getParameterMap(ParameterContext.QUERY)
			// .setParameter("q", "httpclient")

			uri = uriBuilder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return uri;
	}

	private String getPath(FuzRequest request) {
		StringBuilder path = new StringBuilder(request.getProject().getSut().getBasePath());

		String updatedPath = request.getPath();
		request.getParameterMap(ParameterContext.PATH).forEach(
			(name, value) -> {
				//				TODO
				//				updatedPath = updatedPath.replaceAll(String.format("#{%s}", name), value.toString()); 
			} 
		);
		path.append(updatedPath);

		return path.toString();
	}
}
