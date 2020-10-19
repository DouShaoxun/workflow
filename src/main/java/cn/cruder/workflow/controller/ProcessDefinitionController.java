package cn.cruder.workflow.controller;

import cn.cruder.workflow.common.ResponseCode;
import cn.cruder.workflow.common.ResponseEntity;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程定义
 *
 * @Author: Dsx
 * @Date: 2020-10-18 22:06
 * @Description: description
 */
@RestController
@RequestMapping("/processDefinition")
public class ProcessDefinitionController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessRuntime processRuntime;

    @GetMapping("/getDefinitions")
    public ResponseEntity getDefinitions() throws Exception {
        try {
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            //  获取流程定义 ProcessDefinition
            List<ProcessDefinition> list = processDefinitionQuery.list();
            LinkedList<HashMap<String, Object>> hashMaps = new LinkedList<>();
            list.forEach(processDefinition -> {
                HashMap<String, Object> map = new HashMap<>();
                map.put("processDefinitionId", processDefinition.getId());
                map.put("name", processDefinition.getName());
                map.put("key", processDefinition.getKey());
                map.put("resourceName", processDefinition.getResourceName());
                map.put("deploymentId", processDefinition.getDeploymentId());
                map.put("version", processDefinition.getVersion());
                hashMaps.add(map);
            });
            return new ResponseEntity(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), hashMaps);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取流程定义失败");
        }
    }


    /**
     * 上传BPMN文件 部署流程
     *
     * @param multipartFile  BPMN文件
     * @param deploymentName 流程名称
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadStreamAndDeployment")
    public ResponseEntity uploadStreamAndDeployment(@RequestParam("processFile") MultipartFile multipartFile,
                                                  @RequestParam("deploymentName") String deploymentName) throws Exception {
        try {
            // 获取文件名
            String filename = multipartFile.getOriginalFilename();
            // 获取文件扩展名
            String extension = FilenameUtils.getExtension(filename);
            // 获取文件字节流对象
            InputStream fileInputStream = multipartFile.getInputStream();
            Deployment deployment = null;
            if ("zip".equals(extension)) {
                ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment()
                        .addZipInputStream(zipInputStream)
                        .name(deploymentName)
                        .deploy();
            } else {
                deployment = repositoryService.createDeployment()
                        .addInputStream(filename, fileInputStream)
                        .name(deploymentName)
                        .deploy();
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("deploymentId", deployment.getId());
            map.put("deploymentName", deployment.getName());
            map.put("filename", filename);
            return new ResponseEntity(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("上传失败");
        }
    }


}
