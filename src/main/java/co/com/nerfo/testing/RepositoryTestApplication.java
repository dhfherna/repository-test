package co.com.nerfo.testing;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.get.GetResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class RepositoryTestApplication {

	public static void main(String[] args) {
		try {
			//SpringApplication.run(RepositoryTestApplication.class, args);
			//Point to keystore with appropriate certificates for security.
			System.setProperty("javax.net.ssl.trustStore", "C:/Users/Michael Page/Documents/MisNotas/CertificadosWeb/Bancolombia_TrustStore.jks");
			System.setProperty("javax.net.ssl.trustStorePassword", "Clave123*");

			//Establish credentials to use basic authentication.
			//Only for demo purposes. Don't specify your credentials in code.
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

			credentialsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials("opensource", "User_elastic2021"));

			//Create a client.
			RestClientBuilder builder = RestClient.builder(new HttpHost("emma-vault.apps.bancolombia.com", 443, "https"))
					.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
						@Override
						public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
							return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
						}
					});
			RestHighLevelClient client = new RestHighLevelClient(builder);

			//Getting back the document
			GetRequest getRequest = new GetRequest("open_metrics", "1");
			GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);

			System.out.println(response.getSourceAsString());
			client.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
