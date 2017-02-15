package com.vanxd.admin.controller.common;

import com.vanxd.admin.util.FileUtil;
import com.vanxd.data.component.RespJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传的公共接口
 *
 * @author wyd on 2017/2/15.
 */
@Controller
@RequestMapping("/common")
public class FileUploadController {
    @Autowired
    private FileUtil fileUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/fileUpload.json", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON fileUpload(MultipartFile file) {
        if (null == file || file.isEmpty()) {
            return RespJSON.respCode(RespJSON.RespCode.PARAM_ILLEAGUE);
        }
        FileUtil.UploadFile uploadFile = fileUtil.save(file);
        return RespJSON.returnResult(uploadFile);
    }
}
