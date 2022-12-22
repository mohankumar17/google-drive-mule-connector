package org.mule.extension.GoogleDrive.internal;

import java.util.Set;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

public class StaticFileExtValueProvider implements ValueProvider {

	@Override
	public Set<Value> resolve() {
		return ValueBuilder.getValuesFor("image/jpeg", "image/png", "application/pdf", "text/plain", "text/csv",
				"text/tab-separated-values", "application/zip", "application/vnd.oasis.opendocument.text",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"application/vnd.openxmlformats-officedocument.presentationml.presentation");
	}
}
