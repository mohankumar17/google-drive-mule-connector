package org.mule.extension.GoogleDrive.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.connectivity.oauth.AuthorizationCode;
import org.mule.runtime.extension.api.annotation.connectivity.oauth.OAuthCallbackValue;
import org.mule.runtime.extension.api.annotation.connectivity.oauth.OAuthParameter;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.connectivity.oauth.AuthorizationCodeState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AuthorizationCode(authorizationUrl = "https://accounts.google.com/o/oauth2/auth", accessTokenUrl = "https://oauth2.googleapis.com/token")
public class GoogleDriveConnectionProvider implements PoolingConnectionProvider<GoogleDriveConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveConnectionProvider.class);

	@OAuthCallbackValue(expression = "#[payload.access_token]")
	private String accessToken;

	private AuthorizationCodeState state;

	@Override
	public GoogleDriveConnection connect() throws ConnectionException {

		if (state.getAccessToken() == null) {
			throw new ConnectionException("Invalid Access Token");
		}
		LOGGER.info(state.getAccessToken());
		return new GoogleDriveConnection(state);
	}

	@Override
	public void disconnect(GoogleDriveConnection connection) {
		try {
			connection.invalidate();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public ConnectionValidationResult validate(GoogleDriveConnection connection) {
		return ConnectionValidationResult.success();
	}
}
