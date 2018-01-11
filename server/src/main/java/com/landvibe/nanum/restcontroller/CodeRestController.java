package com.landvibe.nanum.restcontroller;


import com.landvibe.nanum.model.dto.TreeFile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CodeRestController {

    String filePath = "C:\\Users\\jgb62\\Desktop\\Code\\build";

    @GetMapping("/directories")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TreeFile> getBaseList() {
        String message = null;
        File file = new File(filePath );
        if (file.exists() == false) {
            System.out.println("경로가 존재하지 않습니다");
        }

        File[] fileList = file.listFiles();

        return getFileList(fileList);
    }

    @GetMapping("/directories/{directoryName}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TreeFile> getCodeList(@PathVariable String directoryName,@RequestParam("path") String path) {
        String message = null;
        File file = new File(filePath + path + "\\" + directoryName);
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

    @GetMapping("/files/{fileName:.+}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getCode(@PathVariable String fileName,@RequestParam("path") String path) {
        StringBuilder message = new StringBuilder("");
        File file = new File(filePath + path + "\\" + fileName);
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


