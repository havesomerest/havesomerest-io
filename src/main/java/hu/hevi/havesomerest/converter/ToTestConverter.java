package hu.hevi.havesomerest.converter;

import hu.hevi.havesomerest.io.TestDirectory;
import hu.hevi.havesomerest.test.Test;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ToTestConverter {

    @Autowired
    private ToTestConverterHelper testConverterHelper;

    public Map<Test, JSONObject> convert(Map<Path, Optional<TestDirectory>> filesByDirectory) {

        List<TestDirectory> testDirectories = new ArrayList<>();
        filesByDirectory.entrySet().forEach((en) -> {
            en.getValue().ifPresent(testDirectory -> {
                testDirectories.add(testDirectory);
            });
        });

        Map<Test, JSONObject> testsByFileContent = testConverterHelper.getTests(testDirectories);

        log.info(MessageFormat.format("{0} tests found...\n", testsByFileContent.keySet().size()));

        return testsByFileContent;
    }
}


