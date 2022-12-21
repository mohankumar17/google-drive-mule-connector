package org.mule.extension.GoogleDrive.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.connectivity.oauth.AccessTokenExpiredException;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.mule.runtime.extension.api.annotation.param.Connection;

public class GoogleDriveOperations {

	private final static Logger LOGGER = LoggerFactory.getLogger(GoogleDriveOperations.class);

	private static Client client;
	static {
		client = ClientBuilder.newClient().property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
	}

	@MediaType(value = ANY, strict = false)
	public String listFiles(@Connection GoogleDriveConnection connection) {
		String out = null;
		try {
			LOGGER.info("Operation Started");
			out = getFileList(connection).toString();
		} catch (GDException ex) {
			throw new ModuleException(GoogleDriveError.INVALID_API_RESPONSE, ex);
		}
		LOGGER.info("Operation Finished");
		return out;
	}

	private JSONObject getFileList(GoogleDriveConnection connection) throws GDException {

		WebTarget webTarget = client.target("https://www.googleapis.com/drive/v3/files");
		JSONObject jsonResp = null;

		try {
			Response res = webTarget.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer " + connection.getState().getAccessToken()).get();

			if (res.getStatus() == 200) {
				String stringResp = res.readEntity(String.class);
				jsonResp = new JSONObject(stringResp);
				LOGGER.info("Operation Successfull");
				return jsonResp;

			} else if (res.getStatus() == 401) {
				LOGGER.info("Access Token Expired");
				throw new AccessTokenExpiredException("Access Token Expired!!");
			} else {
				LOGGER.info("Invalid API response status code");
				throw new GDException(new Exception("Invalid API response status code"));
			}
		} catch (Exception ex) {
			LOGGER.info("Invalid API response");
			throw new GDException(ex);
		}
	}
}
