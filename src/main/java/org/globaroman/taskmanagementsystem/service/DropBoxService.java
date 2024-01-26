package org.globaroman.taskmanagementsystem.service;

import com.dropbox.core.v2.DbxClientV2;
import org.globaroman.taskmanagementsystem.model.Attachment;

import java.io.InputStream;

public interface DropBoxService {

    String getDropBoxIdFromMetadataUploadFile(String filePath);

    DbxClientV2 configDropBox();

    InputStream downloadFileFromDropBoxById(Attachment attachment);
}
