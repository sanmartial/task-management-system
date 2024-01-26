package org.globaroman.taskmanagementsystem.service.impl;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.mapper.AttachmentMapper;
import org.globaroman.taskmanagementsystem.model.Attachment;
import org.globaroman.taskmanagementsystem.model.Task;
import org.globaroman.taskmanagementsystem.model.User;
import org.globaroman.taskmanagementsystem.repository.AttachmentRepository;
import org.globaroman.taskmanagementsystem.repository.TaskRepository;
import org.globaroman.taskmanagementsystem.dto.attachment.AttachmentRespopnseDto;
import org.globaroman.taskmanagementsystem.service.AttachmentService;
import org.globaroman.taskmanagementsystem.dto.attachment.CreateAttachmentRequireDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final TaskRepository taskRepository;

    private final AttachmentMapper attachmentMapper;

    @Value("${ACCESS_TOKEN_DROPBOX}")
    private String accessToken;

    @Override
    public AttachmentRespopnseDto create(CreateAttachmentRequireDto requireDto,
                                         Long taskId, Authentication authentication) {
        Task task = getTaskFromDataBaseByTaskId(taskId);
        User user = (User) authentication.getPrincipal();
        Attachment attachment = new Attachment();
        attachment.setTask(task);
        attachment.setUser(user);
        attachment.setFileName(requireDto.getFileName());
        attachment.setUploadDate(LocalDateTime.now());
        attachment.setDropBoxId(
                getDropBoxIdFromMetadataUploadFile(requireDto.getFilePath()));

        Attachment saved = attachmentRepository.save(attachment);
        List<Attachment> attachmentList = new ArrayList<>();
        attachmentList.add(saved);
        task.setAttachments(attachmentList);
        taskRepository.save(task);

        return attachmentMapper.toDto(saved);
    }

    @Override
    public List<AttachmentRespopnseDto> getAllAttachmentsByTaskId(Long taskId) {

        return attachmentRepository.findByTaskId(taskId).stream()
                .map(attachmentMapper::toDto)
                .toList();
    }

    @Override
    public InputStreamResource getAttachmentById(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(
                () -> new EntityNotFoundCustomException("Cannot find attachment by id:"
                        + attachmentId)
        );
        return new InputStreamResource(downloadFileFromDropBoxById(attachment));
    }

    private String getDropBoxIdFromMetadataUploadFile(String filePath) {
        FileMetadata metadata = uploadToDropBox(filePath);
        return metadata.getId();
    }

    private Task getTaskFromDataBaseByTaskId(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find tsk by id:" + taskId)
        );
    }

    private DbxClientV2 configDropBox() {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, accessToken);
        return client;
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

    private InputStream downloadFileFromDropBoxById(Attachment attachment) {

        DbxClientV2 client = configDropBox();
        if (attachment != null) {
            try {
                DbxDownloader<FileMetadata> downloader = client.files()
                        .download(attachment.getDropBoxId());
                return downloader.getInputStream();
            } catch (DbxException e) {
                throw new RuntimeException("Error downloading attachment from Dropbox", e);
            }
        } else {
            throw new EntityNotFoundCustomException("Attachment not found for attachment ID: "
                    + attachment.getId());
        }
    }

    private FileMetadata downloadFromDropbox(String dropboxFilePath, String localFilePath) {
        DbxClientV2 client = configDropBox();

        try (OutputStream out = new FileOutputStream(localFilePath)) {
            // Download the file from Dropbox
            FileMetadata metadata = client.files().downloadBuilder(dropboxFilePath)
                    .download(out);
            return metadata;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DownloadErrorException e) {
            throw new RuntimeException(e);
        } catch (DbxException e) {
            throw new RuntimeException(e);
        }
    }

    private FullAccount getCurrentAccount(DbxClientV2 client) {
        try {
            FullAccount account = client.users().getCurrentAccount();
            return account;
        } catch (DbxException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getConsistFolderFromDropBox(String path) {
        DbxClientV2 client = configDropBox();
        ListFolderResult result = null;
        try {
            result = client.files().listFolder(path);
        } catch (DbxException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            try {
                result = client.files().listFolderContinue(result.getCursor());
            } catch (DbxException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
