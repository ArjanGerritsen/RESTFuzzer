package nl.ou.se.rest.fuzzer.executor;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;

@Service
public class Executor {

	// variables
	private static int TIMEOUT_MS = 5 * 1000;

	private CloseableHttpClient httpClient;

	// constructors
	public Executor() {
		this.init();
	}

	// methods
	public void init() {
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

	public void start(FuzProject project) {
		project.getRequests().forEach(r -> processRequest(r));
		this.destroy();
	}

	private void processRequest(FuzRequest request) {
		try {
			HttpUriRequest httpUriRequest = getRequest(request);

			System.out.println(httpUriRequest.getRequestLine());

			if (httpUriRequest != null) {
				CloseableHttpResponse response = httpClient.execute(httpUriRequest);

				System.out.println(response.getStatusLine());

				response.close();
			}

			Thread.sleep(1000);

		} catch (SocketTimeoutException e) {
			// TODO
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HttpUriRequest getRequest(FuzRequest request) {
		HttpUriRequest httpUriRequest = null;

		switch (request.getHttpMethod()) {
		case GET:
			httpUriRequest = getGetRequest(request);
			break;
//			POST: 
//				httpUriRequest = getPostRequest(request);
//			break;
//			PATCH: 
//				httpUriRequest = getPatchRequest(request);
//			break;
//			DELETE: 
//				httpUriRequest = getDeleteRequest(request);
//			break;
		default:
			break;
		}

		return httpUriRequest;
	}

	private HttpUriRequest getGetRequest(FuzRequest request) {
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost(request.getProject().getSut().getHost())
					.setPath(request.getProject().getSut().getBasePath() + request.getPath())
//		            .setParameter("q", "httpclient")
//		            .setParameter("btnG", "Google Search")
//		            .setParameter("aq", "f")
//		            .setParameter("oq", "")
					.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpGet get = new HttpGet(uri);
		// TODO Auto-generated method stub
		return get;
	}

	private HttpUriRequest getDeleteRequest(FuzRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private HttpUriRequest getPatchRequest(FuzRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private HttpUriRequest getPostRequest(FuzRequest request) {
		HttpPost post = new HttpPost("http://localhost/rest/persons");

		String json = "{\"id\":1,\"name\":\"John\"}";
//		StringEntity entity = new StringEntity(json);
//		post.setEntity(entity);
//		post.setHeader("Accept", "application/json");
//		post.setHeader("Content-type", "application/json");

		return post;
	}
}
