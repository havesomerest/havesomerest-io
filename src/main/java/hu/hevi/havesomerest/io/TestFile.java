package hu.hevi.havesomerest.io;

import lombok.Value;

import java.nio.file.Path;

@Value
public class TestFile {

    private static final int STATUS_CODE_LENGTH = 3;
    private static final int GET_OR_PUT_LENGTH = 3;
    private static final int POST_LENGTH = 4;
    private static final int DELETE_LENGTH = 6;

    private static final String JSON = "json";
    private static final String JSON_SUFFIX = ".json";

    private final Path path;

    public String getFileName() {
        return path.getFileName().toString();
    }

    public boolean isGet() {
        return path.getFileName().toString().startsWith("get");
    }

    public boolean isPost() {
        return path.getFileName().toString().startsWith("post");
    }

    public boolean isPut() {
        return path.getFileName().toString().startsWith("put");
    }

    public boolean isDelete() {
        return path.getFileName().toString().startsWith("delete");
    }

    public String getStatus() {
        String status = "";
        int offset = GET_OR_PUT_LENGTH;
        if (isPost()) {
            offset = POST_LENGTH;
        } else if (isDelete()) {
            offset = DELETE_LENGTH;
        }

        status = getFileName().substring(offset, offset + STATUS_CODE_LENGTH);
        return status;
    }

    public boolean isJson() {
        return this.getFileName().endsWith(JSON_SUFFIX);
    }

    public boolean isTestFile() {
        String fileName = this.getFileName();
        String[] split = fileName.split("[.]");
        boolean isTestFile = false;
        if (split.length == 2 && JSON.equals(split[1])) {
            isTestFile = true;
        }
        return isTestFile;
    }
}
