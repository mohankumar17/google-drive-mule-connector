package org.mule.extension.GoogleDrive.internal;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class GoogleDriveErrorProvider implements ErrorTypeProvider {

	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
		HashSet<ErrorTypeDefinition> errors = new HashSet<>();

		errors.add(GoogleDriveError.INVALID_PARAMETER);
		errors.add(GoogleDriveError.BAD_CREDENTIALS);
		errors.add(GoogleDriveError.INVALID_API_RESPONSE);

		return errors;
	}
}
