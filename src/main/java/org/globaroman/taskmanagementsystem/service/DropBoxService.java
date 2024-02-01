package org.globaroman.taskmanagementsystem.service;

import com.dropbox.core.v2.DbxClientV2;
import java.io.InputStream;
import org.globaroman.taskmanagementsystem.model.Attachment;

public interface DropBoxService {

    String getDropBoxIdFromMetadataUploadFile(String taskDir,String filePath);

    DbxClientV2 configDropBox();

    InputStream downloadFileFromDropBoxById(Attachment attachment);

    void deleteFile(Attachment attachment);
}
