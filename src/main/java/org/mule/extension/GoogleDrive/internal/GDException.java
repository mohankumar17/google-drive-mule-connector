package org.mule.extension.GoogleDrive.internal;

import org.mule.runtime.extension.api.exception.ModuleException;

public final class GDException extends ModuleException {

	public GDException(Exception cause) {
		super(GoogleDriveError.INVALID_API_RESPONSE, cause);
	}
}
