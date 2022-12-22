package org.mule.extension.GoogleDrive.internal;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;

public class GoogleDriveInsertParamater {

	@Parameter
	@DisplayName("File Name")
	@Summary("Provide the name of the file to upload")
	@Expression(ExpressionSupport.SUPPORTED)
	private String fileName;

	@Parameter
	@DisplayName("File Path")
	@Summary("Provide the path of the file to upload")
	@Expression(ExpressionSupport.SUPPORTED)
	private String filePath;

	@Parameter
	@DisplayName("File Extension")
	@OfValues(StaticFileExtValueProvider.class)
	@Summary("Provide the extension of the file")
	@Expression(ExpressionSupport.SUPPORTED)
	private String fileExtension;

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileExtension() {
		return fileExtension;
	}

}
