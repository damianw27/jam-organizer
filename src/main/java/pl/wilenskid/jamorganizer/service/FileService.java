package pl.wilenskid.jamorganizer.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import pl.wilenskid.jamorganizer.entity.bean.FileBean;
import pl.wilenskid.jamorganizer.entity.bean.FileWithContentBean;
import pl.wilenskid.jamorganizer.entity.bean.FilesGroupAddBean;
import pl.wilenskid.jamorganizer.entity.bean.FilesGroupRemoveBean;
import pl.wilenskid.jamorganizer.entity.File;
import pl.wilenskid.jamorganizer.entity.FilesGroup;
import pl.wilenskid.jamorganizer.repository.FileRepository;
import pl.wilenskid.jamorganizer.repository.FilesGroupRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Named
public class FileService {

    private final Path rootLocation;
    private final FileRepository fileRepository;
    private final FilesGroupRepository filesGroupRepository;
    private final SecurityService securityService;
    private final UserService userService;
    private final FilenameService filenameService;

    @Inject
    public FileService(StoragePropertiesService propertiesConfig,
                       FileRepository fileRepository,
                       FilesGroupRepository filesGroupRepository,
                       SecurityService securityService,
                       UserService userService,
                       FilenameService filenameService) {
        this.rootLocation = Paths.get(propertiesConfig.getLocation());
        this.fileRepository = fileRepository;
        this.filesGroupRepository = filesGroupRepository;
        this.securityService = securityService;
        this.userService = userService;
        this.filenameService = filenameService;
    }

    public Optional<File> uploadFile(String filename, MultipartFile file) {
        if (file == null) {
            return Optional.empty();
        }

        if (file.isEmpty()) {
            return Optional.empty();
        }

        String randomizedFilename = filenameService.getRandomizedName(filename);

        Path destinationFile = this.rootLocation
            .resolve(Paths.get(randomizedFilename))
            .normalize()
            .toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new IllegalStateException("Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File uploadedFile = saveFileReference(randomizedFilename, "/files/" + randomizedFilename);
        return Optional.of(uploadedFile);
    }

    public Optional<File> uploadFile(String filename, String content) {
        String randomizedFilename = filenameService.getRandomizedName(filename);

        Path destinationFile = this.rootLocation
            .resolve(Paths.get(randomizedFilename))
            .normalize()
            .toAbsolutePath();

        try (FileWriter fileWriter = new FileWriter(destinationFile.toString())) {
            fileWriter.write(content);
        } catch (IOException e) {
            return Optional.empty();
        }

        File uploadedFile = saveFileReference(randomizedFilename, "/files/html/" + randomizedFilename);
        return Optional.of(uploadedFile);
    }

    public FilesGroup uploadFiles(String baseFilename, MultipartFile[] multipartFiles) {
        Set<File> files = new HashSet<>();

        if (multipartFiles != null && multipartFiles.length != 0) {
            AtomicReference<Integer> index = new AtomicReference<>(0);

            files = Arrays
                .stream(multipartFiles)
                .map((MultipartFile multipartFile) -> uploadFile(baseFilename + "-" + index.getAndSet(index.get() + 1), multipartFile))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        }


        FilesGroup filesGroup = new FilesGroup();
        filesGroup.setBaseFilename(baseFilename);
        filesGroup.setFiles(files);

        filesGroupRepository.save(filesGroup);

        return filesGroup;
    }

    public Optional<File> findFileById(long fileId) {
        return fileRepository.findById(fileId);
    }

    public FilesGroup findFilesWithId(long filesGroupId) {
        return filesGroupRepository
            .findById(filesGroupId)
            .orElseThrow(IllegalArgumentException::new);
    }

    public FilesGroup addFileToGroup(FilesGroupAddBean filesGroupAddBean) {
        FilesGroup filesGroup = filesGroupRepository
            .findById(filesGroupAddBean.getFilesGroupId())
            .orElseThrow(IllegalArgumentException::new);

        Set<File> files = filesGroup.getFiles();

        uploadFile(filesGroup.getBaseFilename() + "-" + files.size(), filesGroupAddBean.getFile())
            .ifPresent(files::add);

        filesGroup.setFiles(files);

        filesGroupRepository.save(filesGroup);

        return filesGroup;
    }

    public FilesGroup removeFileFromGroup(FilesGroupRemoveBean filesGroupRemoveBean) {
        FilesGroup filesGroup = filesGroupRepository
            .findById(filesGroupRemoveBean.getFilesGroupId())
            .orElseThrow(IllegalArgumentException::new);

        Set<File> files = filesGroup
            .getFiles()
            .stream()
            .filter(file -> !Objects.equals(file.getId(), filesGroupRemoveBean.getFileId()))
            .collect(Collectors.toSet());

        filesGroup.setFiles(files);

        filesGroupRepository.save(filesGroup);

        return filesGroup;
    }

    public Resource loadFile(String filename) {
        Path path = rootLocation.resolve(filename);

        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                System.err.println("Can not read file " + filename);
            }
        } catch (MalformedURLException e) {
            System.err.println("Could not read file: " + filename);
        }

        return null;
    }

    public String loadFileAsString(String filename) {
        Path path = rootLocation.resolve(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            return bufferedReader.lines().reduce("", (acc, next) -> acc + next);
        } catch (IOException e) {
            System.err.println("Could not read file: " + filename);
        }

        return "";
    }

    public Optional<File> updateFileContent(long fileId, String fileContent) {
        File file = findFileById(fileId).orElseThrow(IllegalArgumentException::new);

        Path destinationFile = this.rootLocation
            .resolve(Paths.get(file.getFilename()))
            .normalize()
            .toAbsolutePath();

        try (FileWriter fileWriter = new FileWriter(destinationFile.toString())) {
            fileWriter.write(fileContent);
        } catch (IOException e) {
            return Optional.empty();
        }

        return Optional.of(file);
    }

    public FileBean toBean(File file) {
        return FileBean
            .builder()
            .id(file.getId())
            .filename(file.getFilename())
            .fileUrl(file.getFileUrl())
            .author(userService.toBean(file.getAuthor()))
            .build();
    }

    public FileWithContentBean toBeanWithContent(File file) {
        return FileWithContentBean
            .builder()
            .id(file.getId())
            .filename(file.getFilename())
            .fileUrl(file.getFileUrl())
            .content(loadFileAsString(file.getFilename()))
            .author(userService.toBean(file.getAuthor()))
            .build();
    }

    private File saveFileReference(String filename, String fileUrl) {
        File file = new File();
        file.setFilename(filename);
        file.setFileUrl(fileUrl);
        file.setAuthor(securityService.getLoggedInUser());

        fileRepository.save(file);

        return file;
    }

}
