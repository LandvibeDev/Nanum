package com.landvibe.nanum.service;

import com.landvibe.nanum.model.dto.TreeFile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeService {

    String filePath = "C:\\Users\\jgb62\\Desktop\\Code\\";

    public List<TreeFile> getFiles(String projectId) {
        File file = new File(filePath+projectId);
        if (file.exists() == false) {
            System.out.println("경로가 존재하지 않습니다");
            return null;
        }
        File[] fileList = file.listFiles();
        return getFileList(fileList);
    }

    public List<TreeFile> getFilesByDirectory(String projectId,String directoryName,String path) {
        File file = new File(filePath + projectId +"\\"+ path + "\\" + directoryName);
        if (file.exists() == false) {
            System.out.println("경로가 존재하지 않습니다");
        }
        File[] fileList = file.listFiles();
        return getFileList(fileList);
    }

    List<TreeFile> getFileList(File[] fileList){
        List<TreeFile> files = new ArrayList<>();
        List<TreeFile> directories = new ArrayList<>();

        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                TreeFile treeFile = new TreeFile(fileList[i].getName(), TreeFile.Type.DIRECTORY);
                directories.add(treeFile);
            } else {
                TreeFile treeFile = new TreeFile(fileList[i].getName(),TreeFile.Type.FILE);
                files.add(treeFile);
            }
        }
        directories.addAll(files);
        return directories;
    }

    public String getCodeByFile(String projectId,String fileName,String path) {
        StringBuilder message = new StringBuilder("");
        File file = new File(filePath + projectId + "\\" +path + "\\" + fileName);
        if (file.exists() == false) {
            System.out.println("경로가 존재하지 않습니다");
        }
        if(file.isFile()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    message.append(line+"\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return message.toString();
    }
}
