package pl.wilenskid.jamorganizer.rest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import pl.wilenskid.jamorganizer.service.FileService;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/files")
public class FileRest {

    private final FileService fileService;

    @Inject
    public FileRest(FileService fileService) {
        this.fileService = fileService;
    }

    @ResponseBody
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable("fileName") String fileName) throws IOException {
        Resource file = fileService.loadFile(fileName);
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
    }

    @ResponseBody
    @GetMapping("/html/{fileName}")
    public ResponseEntity<String> serveHtmlFile(@PathVariable("fileName") String fileName) throws IOException {
        Resource file = fileService.loadFile(fileName);
        String content;

        try (Reader reader = new InputStreamReader(file.getInputStream(), UTF_8)) {
            content = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return ResponseEntity
            .ok()
            .body(content);
    }
}
