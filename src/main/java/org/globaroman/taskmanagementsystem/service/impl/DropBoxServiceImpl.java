package org.globaroman.taskmanagementsystem.service.impl;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.service.DropBoxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class DropBoxServiceImpl implements DropBoxService {

    @Value("${ACCESS_TOKEN_DROPBOX}")
    private String accessToken;
    @Override
    public String getDropBoxIdFromMetadataUploadFile(String filePath) {
        FileMetadata metadata = uploadToDropBox(filePath);
        return metadata.getId();
    }
    @Override
    public DbxClientV2 configDropBox() {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, accessToken);
        return client;
    }

    @Override
    public InputStream downloadFileFromDropBoxById(Attachment attachment) {

        DbxClientV2 client = configDropBox();
        if (attachment != null) {
            try {
                DbxDownloader<FileMetadata> downloader = client.files().download(attachment.getDropBoxId());
                return downloader.getInputStream();
            } catch (DbxException e) {
                throw new RuntimeException("Error downloading attachment from Dropbox", e);
            }
        } else {
            throw new EntityNotFoundCustomException("Attachment not found for attachment ID: " + attachment.getId());
        }
    }

    private FileMetadata uploadToDropBox(String pathToFile) {
        DbxClientV2 client = configDropBox();

        try (InputStream in = new FileInputStream(pathToFile)) {
            FileMetadata metadata = client.files().uploadBuilder("/" + pathToFile)
                    .uploadAndFinish(in);
            return metadata;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UploadErrorException e) {
            throw new RuntimeException(e);
        } catch (DbxException e) {
            throw new RuntimeException(e);
        }
    }
}
