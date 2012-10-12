package nl.tudelft.ewi.se.in4355.server;

import static junit.framework.Assert.fail;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridServerTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(GridServerTest.class);

	private static final String HOST = "localhost";
	private static final int PORT = 10_001;

	private Server server;

	@Before
	public void setUp() throws Exception {

		server = new Server(PORT);
		server.setHandler(buildWebAppContext());
		server.start();

	}

	@After
	public void tearDown() throws Exception {

		server.stop();

	}

	private static WebAppContext buildWebAppContext() {
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setWar(new File("src/main/webapp/").getAbsolutePath());
		return webAppContext;
	}

	@Test
	public void run() throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://" + HOST + ":" + PORT + "/");

		HttpResponse response = client.execute(get);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			LOG.info("Server returns 200 OK.");
		} else {
			fail("Server returned: " + response.getStatusLine());
		}

	}

}
