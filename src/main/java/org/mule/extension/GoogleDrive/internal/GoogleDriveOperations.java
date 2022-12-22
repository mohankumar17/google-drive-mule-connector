package org.mule.extension.GoogleDrive.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.connectivity.oauth.AccessTokenExpiredException;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import org.mule.runtime.extension.api.annotation.param.Connection;

public class GoogleDriveOperations {

	private final static Logger LOGGER = LoggerFactory.getLogger(GoogleDriveOperations.class);

	private static Client client;
	static {
		client = ClientBuilder.newClient().property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
	}

	// GET
	@MediaType(value = ANY, strict = false)
	public String listFiles(@Connection GoogleDriveConnection connection) {
		String out = null;
		try {
			LOGGER.info("List Files Operation Started");
			out = getFileList(connection).toString();
		} catch (GDException ex) {
			throw new ModuleException(GoogleDriveError.INVALID_API_RESPONSE, ex);
		}
		LOGGER.info("List Files Operation Finished");
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

	// POST
	@MediaType(value = ANY, strict = false)
	public String insertFile(@Connection GoogleDriveConnection connection,
			@ParameterGroup(name = "Insert Paramaters") GoogleDriveInsertParamater param) {
		String out = null;
		try {
			LOGGER.info("Upload File Operation Started");
			out = postFile(connection, param.getFileName(), param.getFilePath(), param.getFileExtension()).toString();
		} catch (GDException ex) {
			throw new ModuleException(GoogleDriveError.INVALID_API_RESPONSE, ex);
		}
		LOGGER.info("Upload File Operation Finished");
		return out;
	}

	private JSONObject postFile(GoogleDriveConnection connection, String fileName, String filePath, String fileExt)
			throws GDException {

		WebTarget webTarget = client.target("https://www.googleapis.com/upload/drive/v3/files");
		JSONObject jsonResp = null;

		FileReader fr;
		try {
			fr = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			throw new GDException(e);
		}

		try {
			Response res = webTarget.queryParam("uploadType", "media").request(MediaType.ANY)
					.header("Authorization", "Bearer " + connection.getState().getAccessToken())
					.header("Content-Type", fileExt).post(Entity.entity(fr, fileExt));

			if (res.getStatus() == 200 || res.getStatus() == 201) {
				String stringResp = res.readEntity(String.class);
				jsonResp = new JSONObject(stringResp);
				LOGGER.info("Operation Successfull");
				return jsonResp;

			} else if (res.getStatus() == 401) {
				LOGGER.info("Access Token Expired");
				throw new AccessTokenExpiredException("Access Token Expired!!");
			} else {
				LOGGER.info("Invalid API response status code");
				throw new GDException(new Exception("Unable to upload file"));
			}
		} catch (Exception ex) {
			LOGGER.info("Invalid API response");
			throw new GDException(ex);
		}
	}
}
