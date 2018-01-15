package com.landvibe.nanum.service;

import com.landvibe.nanum.model.dto.TreeFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeService {

    @Value ("${project.code-location}")
    String basePath;

    public List<TreeFile> getFiles(String projectId) {
        File file = new File(basePath + projectId +File.separator + "Project");
        if (file.exists() == false) {
            file.mkdirs();
            return null;
        }
        File[] fileList = file.listFiles();
        return getFileList(fileList);
    }

    public List<TreeFile> getFilesByDirectory(String projectId, String directoryName, String path) {
        File file = new File(basePath + projectId + File.separator + path + File.separator + directoryName);
        if (file.exists() == false) {
            file.mkdirs();
            return null;
        }
        File[] fileList = file.listFiles();
        return getFileList(fileList);
    }

    List<TreeFile> getFileList(File[] fileList) {
        List<TreeFile> files = new ArrayList<>();
        List<TreeFile> directories = new ArrayList<>();

        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                TreeFile treeFile = new TreeFile(fileList[i].getName(), TreeFile.Type.DIRECTORY);
                directories.add(treeFile);
            } else {
                TreeFile treeFile = new TreeFile(fileList[i].getName(), TreeFile.Type.FILE);
                files.add(treeFile);
            }
        }
        directories.addAll(files);
        return directories;
    }

    public String getFile(String projectId, String fileName, String path) {
        StringBuilder message = new StringBuilder("");
        File file = new File(basePath + projectId + File.separator + path + File.separator + fileName);
        if (file.exists() == false) {
            return null;
        }
        if (file.isFile()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    message.append(line + "\n");
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

    public boolean addFile(String projectId, String fileName, String path,String type) {
        String filePath = basePath + projectId + File.separator + path + File.separator + fileName;
        if("FILE".equals(type)){
            File file = new File(filePath);
            try {
                BufferedWriter fw = new BufferedWriter(new FileWriter(file));
                fw.flush();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }else if("DIRECTORY".equals(type)){
            File file = new File(filePath);
            return file.mkdir();
        }
        return false;


    }

    public boolean deleteFile(String projectId,String fileName,String path){
        File file = new File(basePath + projectId + File.separator +path + File.separator + fileName);
        if (file.exists() == false) {
            System.out.println("경로가 존재하지 않습니다");
        }
        return file.delete();
    }

    public boolean updateFile(String projectId, String fileName, String path,String content) {
        String filePath = basePath + projectId + File.separator + path + File.separator + fileName;
        File file = new File(filePath);
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(file));
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
