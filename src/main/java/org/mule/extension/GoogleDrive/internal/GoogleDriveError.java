package org.mule.extension.GoogleDrive.internal;

import java.util.Optional;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public enum GoogleDriveError implements ErrorTypeDefinition<GoogleDriveError> {
	INVALID_PARAMETER, BAD_CREDENTIALS, INVALID_API_RESPONSE;

	private ErrorTypeDefinition<? extends Enum<?>> parent;

	GoogleDriveError(ErrorTypeDefinition<? extends Enum<?>> parent) {
		this.parent = parent;
	}

	GoogleDriveError() {
	}

	@Override
	public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
		return Optional.ofNullable(parent);
	}
}
