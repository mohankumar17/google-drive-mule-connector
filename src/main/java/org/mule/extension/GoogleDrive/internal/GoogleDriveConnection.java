package org.mule.extension.GoogleDrive.internal;

import org.mule.runtime.extension.api.connectivity.oauth.AuthorizationCodeState;

public final class GoogleDriveConnection {

	private final AuthorizationCodeState state;

	public GoogleDriveConnection(AuthorizationCodeState state) {
		this.state = state;
	}

	public AuthorizationCodeState getState() {
		return state;
	}

	public void invalidate() {
	}

}
