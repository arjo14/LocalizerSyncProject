package project.plugin;

import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import project.exception.FileTypeNotSupportedException;
import project.model.Row;
import project.model.dto.PropertyFileDto;
import project.model.dto.property.KeyValuePropertyFile;
import project.model.dto.xml.flat.FlatXmlPropertyFile;
import project.model.dto.xml.withAttribute.XmlWithAttributePropertyFile;
import project.process.GetPropertyProcessor;
import project.strategy.ImportStrategy;
import project.strategy.impl.PropertyImportStrategy;
import project.strategy.impl.XmlImportStrategy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

/**
 * author John Smith .
 */
public class SyncPlugin extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        //Get required data keys
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        //Set visibility only in case of existing project and editor and if some text in the editor is selected
        e.getPresentation().setVisible((project != null && editor != null && editor.getSelectionModel().hasSelection()));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        String projectName = getProjectNameFromAnActionEvent(e);
        String fileName = getFileNameFromAnActionEvent(e);


        String absPath = getPathFromAnActionEvent(e);
        String path = absPath.substring(absPath.indexOf(projectName) + projectName.length() + 1);


        String fileType = "";
        try {
            fileType = getFileTypeFromPath(path);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Map<String, Long> projectTree = new HashMap<>();

        String urlString = "http://localhost:8082";
        String projectN = "taxpayer3";
        URL url;
        try {
            url = new URL(urlString + "/sync/getProjectTree?projectName=" + projectN);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            line = reader.readLine();
            JSONObject jsonObject = new JSONObject(line);
            for (int i = 0; i < jsonObject.names().length(); i++) {
                projectTree.put((String) jsonObject.names().get(i), Long.valueOf(String.valueOf(jsonObject.get((String) jsonObject.names().get(i)))));
            }
            reader.close();
        } catch (JSONException | IOException e1) {
            e1.printStackTrace();
        }
        //find id from projectTree
        Long propertyFileId = projectTree.get(path);
        String thisFileHash = getHashFromPath(absPath, fileType);

        URL checkForUpdate;
        try {
            checkForUpdate = new URL(urlString + "/sync/checkForUpdate?propertyFileId=" + propertyFileId + "&hash=" + thisFileHash + "&fileName=" + fileName + "&fileType=" + fileType);
            HttpURLConnection conn = (HttpURLConnection) checkForUpdate.openConnection();
            conn.setRequestMethod("GET");
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            line = reader.readLine();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(line.getBytes());
            updateFile(byteArrayOutputStream, absPath, fileName, fileType);
            System.out.println("Ok");

            System.out.println("asf");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    private static String getFileTypeFromPath(String path) throws Exception {

        Pattern patternForFlatXml = Pattern.compile("(<key>.*</key>)");
        Pattern patternForPropertyXml = Pattern.compile("(<property(\\s)*key(\\s)*=.*</property>)");
        Pattern patternForProperties = Pattern.compile(".*=.*");

        final StringBuilder text = new StringBuilder();
        Files.lines(Paths.get(path)).forEach(s -> {
            text.append(s);
        });

        if (patternForFlatXml.matcher(text).find()) {
            return "flat_xml";
        } else if (patternForPropertyXml.matcher(text).find()) {
            return "xml_with_attribute";
        } else if (patternForProperties.matcher(text).find()) {
            return "key_value";
        }
        throw new FileTypeNotSupportedException();
    }

    public void updateFile(ByteArrayOutputStream byteArrayOutputStream, String path, String fileName, String fileType) throws IOException {
        fileType = (fileType.equals("key_value") ? "properties" : "xml");

        File file = new File(path + "/" + fileName + "." + fileType);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
        System.out.println("khaf");


    }

    public String getHashFromPath(String path, String fileType) {
        try {
            ImportStrategy importStrategy;
            switch (fileType) {
                case "key_value":
                    importStrategy = new PropertyImportStrategy().withFileType(KeyValuePropertyFile.class);
                    break;
                case "xml_with_attribute":
                    importStrategy = new XmlImportStrategy().withFileType(XmlWithAttributePropertyFile.class);
                    break;
                case "flat_xml":
                    importStrategy = new XmlImportStrategy().withFileType(FlatXmlPropertyFile.class);
                    break;
                default:
                    throw new FileTypeNotSupportedException();
            }
            PropertyFileDto propertyFile = new GetPropertyProcessor(new File(path), importStrategy).getPropertyFileFromFile();

            @SuppressWarnings("unchecked")
            List<Row> rows = Lists.reverse(propertyFile.getProperties());

            StringBuilder builder = rows.stream()
                    .map(row -> row.getKey() + row.getValue())
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);

            return sha1Hex(builder.reverse().toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }


    private String getProjectNameFromAnActionEvent(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        return String.valueOf(psiFile.getProject().getName());

    }

    private String getFileNameFromAnActionEvent(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);

        return psiFile.getContainingFile().getName();
    }

    private String getPathFromAnActionEvent(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);

        String path = psiFile.getViewProvider().getDocument().toString();
        return path.substring(20, path.length() - 1);
    }
}



