package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.components.data.fuz.factory.FuzResponseFactory;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.service.task.TaskController;
import nl.ou.se.rest.fuzzer.components.shared.Constants;
import nl.ou.se.rest.fuzzer.components.shared.JsonUtil;

public class ExecutorUtil {

	// variables
	private Logger logger = LoggerFactory.getLogger(TaskController.class);

	private static final int TIMEOUT_MS = 5 * 1000;
	private static final String PLACEHOLDER_PATH_VARIABLE = "\\{%s\\}";

	private static final String HEADER_ACCEPT = "Accept";
	private static final String HEADER_CONTENT_TYPE = "Content-type";

	private static CloseableHttpClient httpClient;
	private static ExecutorUtil instance = null;

	private FuzResponseFactory responseFactory = new FuzResponseFactory();

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
		HttpResponse response = null;
		String failureReason = null;

		try {
			HttpUriRequest httpUriRequest = getRequest(request);

			logger.debug(httpUriRequest.getRequestLine().toString());

			if (httpUriRequest != null) {
				response = httpClient.execute(httpUriRequest);

				logger.debug(response.getStatusLine().toString());
			}

		} catch (Exception e) {
			e.printStackTrace(); // TODO
			failureReason = e.getMessage();
		}

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
		case PUT:
			httpUriRequest = getPutRequest(request);
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
		setHeaders(get);
		return get;
	}

	private HttpUriRequest getPostRequest(FuzRequest request) {
		HttpPost post = new HttpPost(getUri(request));
		setHeaders(post);
		setFormDataParameters(post, request);
		return post;
	}

	private HttpUriRequest getPatchRequest(FuzRequest request) {
		HttpPatch patch = new HttpPatch(getUri(request));

		return patch;
	}

	private HttpUriRequest getPutRequest(FuzRequest request) {
		HttpPut put = new HttpPut(getUri(request));

		return put;
	}

	private HttpUriRequest getDeleteRequest(FuzRequest request) {
		HttpDelete delete = new HttpDelete(getUri(request));

		return delete;
	}

	private void setHeaders(HttpUriRequest httpUriRequest) {
		httpUriRequest.setHeader(HEADER_ACCEPT, "application/json");
		httpUriRequest.setHeader(HEADER_CONTENT_TYPE, "application/json");
	}

	private void setFormDataParameters(HttpPost post, FuzRequest request) {
		Map<String, Object> parameters = request.getParameterMap(ParameterContext.FORMDATA);
		String json = JsonUtil.mapToString(parameters);
		post.setEntity(jsonToEntity(json));
	}
	
	private HttpEntity jsonToEntity(String json) {
		StringEntity entity = null;
		try {
			entity = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			logger.warn(String.format(Constants.Fuzzer.ENCODING_UNSUPPORTED, e.getMessage()));
		}
		return entity;
	}

	private URI getUri(FuzRequest request) {
		URIBuilder uriBuilder = new URIBuilder();
		URI uri = null;
		try {
			uriBuilder.setScheme("http");
			uriBuilder.setHost(request.getProject().getSut().getHost());
			uriBuilder.setPath(getPath(request));

			// add query parameters
			for (Map.Entry<String, Object> entry : request.getParameterMap(ParameterContext.QUERY).entrySet()) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}

			uri = uriBuilder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return uri;
	}

	private String getPath(FuzRequest request) {
		StringBuilder path = new StringBuilder(request.getProject().getSut().getBasePath());

		// replace path parameters
		String updatedPath = request.getPath();
		for (Map.Entry<String, Object> entry : request.getParameterMap(ParameterContext.PATH).entrySet()) {
			updatedPath = updatedPath.replaceAll(String.format(PLACEHOLDER_PATH_VARIABLE, entry.getKey()), entry.getValue().toString());
		}
		path.append(updatedPath);

		return path.toString();
	}
}
