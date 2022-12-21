package org.mule.extension.GoogleDrive.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

@Xml(prefix = "googledrive")
@Extension(name = "GoogleDrive")
@ErrorTypes(GoogleDriveError.class)
@Configurations(GoogleDriveConfiguration.class)
public class GoogleDriveExtension {

}
