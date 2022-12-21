package org.mule.extension.GoogleDrive.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@Operations(GoogleDriveOperations.class)
@ConnectionProviders(GoogleDriveConnectionProvider.class)
public class GoogleDriveConfiguration {

}
